package org.akip.documentstorage;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.credentials.AwsEnvironmentProvider;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.MinioException;
import org.akip.exception.BadRequestErrorException;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class S3Service implements IDocumentStorageService {

    private final Logger log = LoggerFactory.getLogger(S3Service.class);

    private final Environment env;

    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private Long maxFileSizeInBytes;

    private boolean withCredentials = false;

    public S3Service(Environment env) {
        this.env = env;
        this.endpoint = env.getProperty("akip.document-storage.s3.endpoint");
        this.accessKey = env.getProperty("akip.document-storage.s3.accessKey");
        this.secretKey = env.getProperty("akip.document-storage.s3.secretKey");
        this.bucketName = env.getProperty("akip.document-storage.s3.bucketName");
        if (env.getProperty("akip.document-storage.s3.maxFileSizeInBytes") != null) {
            this.maxFileSizeInBytes = Long.parseLong(env.getProperty("akip.document-storage.s3.maxFileSizeInBytes"));
        }
        if (!isActive()) {
            log.debug("S3 component not configured. If you want to use this component provide the properties akip.s3.*");
            return;
        }

        if (this.accessKey != null && this.secretKey != null) {
            this.withCredentials = true;
        }

        log.debug("S3 component successfully configured");
    }

    public void put(String key, byte[] bytes) {
        log.debug("Putting object {}", key);
        MinioClient minioClient = getMinioClient();

        validateMaxFileSize(bytes);

        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(key)
                    .stream(bais, bais.available(), -1)
                    .build();

            minioClient.putObject(putObjectArgs);
        } catch (IOException e) {
            log.error("Error getting object {}: {}", key, e);
            throw new BadRequestErrorException("error.s3.ioException", this.endpoint );
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error("Error getting object {}: {}", key, e);
            throw new BadRequestErrorException("error.s3.generalError", e.getMessage());
        }
    }

    private void validateMaxFileSize(byte[] bytes) {
        if (bytes.length > this.maxFileSizeInBytes * 1024) {
            BigDecimal oneMB = new BigDecimal(1024);
            throw new BadRequestErrorException("error.s3.maxFileSize", new BigDecimal(this.maxFileSizeInBytes).divide(oneMB, 2, RoundingMode.HALF_DOWN) + " MB",  new BigDecimal(bytes.length).divide(oneMB).divide(oneMB, 2, RoundingMode.HALF_DOWN) + " MB");
        }
    }

    public byte[] get(String key) {
        MinioClient minioClient = getMinioClient();

        GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                .bucket(bucketName)
                .object(key)
                .build();

        try {
            InputStream is = minioClient.getObject(getObjectArgs);
            return IOUtils.toByteArray(is);
        } catch (ErrorResponseException e) {
            log.error("Error getting object {}: {}", key, e);
            throw new BadRequestErrorException("error.minio." + e.errorResponse().code(), key );
        } catch (IOException e) {
            log.error("Error getting object {}: {}", key, e);
            throw new BadRequestErrorException("error.s3.ioException", this.endpoint );
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error("Error getting object {}: {}", key, e);
            throw new BadRequestErrorException("error.s3.generalError", e.getMessage());
        }
    }

    public void delete(String key) {
        MinioClient minioClient = getMinioClient();

        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(key)
                .build();

        try {
            minioClient.removeObject(removeObjectArgs);
        } catch (ErrorResponseException e) {
            log.error("Error getting object {}: {}", key, e);
            throw new BadRequestErrorException("error.s3." + e.errorResponse().code(), key );
        } catch (IOException e) {
            log.error("Error getting object {}: {}", key, e);
            throw new BadRequestErrorException("error.s3.ioException", this.endpoint );
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error("Error getting object {}: {}", key, e);
            throw new BadRequestErrorException("error.s3.generalError", e.getMessage());
        }
    }

    /**
     * If the any required property is not defined, the Spring component is not active.
     * @return
     */
    @Override
    public boolean isActive() {
        if (env.getProperty("akip.document-storage.s3.active") == null || !"true".equals(env.getProperty("akip.document-storage.s3.active"))) {
            return false;
        }

        if (endpoint == null || bucketName == null) {
            return false;
        }

        return true;
    }

    private MinioClient getMinioClient() {
        if (withCredentials) {
            log.debug("Creating S3 client with credentials");
            return MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();
        }
        log.debug("Creating S3 client without credentials");
        return MinioClient.builder()
                .credentialsProvider(new AwsEnvironmentProvider())
                .endpoint(endpoint)
                .build();
    }

}
