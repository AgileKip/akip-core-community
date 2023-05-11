package org.akip.minio;

import io.minio.*;
import io.minio.errors.*;
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
public class MinioService implements IDocumentStorageService {

    private final Logger log = LoggerFactory.getLogger(MinioService.class);

    private final Environment env;

    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private Long maxFileSizeInBytes;

    public MinioService(Environment env) {
        this.env = env;
        this.endpoint = env.getProperty("akip.minio.endpoint");
        this.accessKey = env.getProperty("akip.minio.accessKey");
        this.secretKey = env.getProperty("akip.minio.secretKey");
        this.bucketName = env.getProperty("akip.minio.bucketName");
        if (env.getProperty("akip.minio.maxFileSizeInBytes") != null) {
            this.maxFileSizeInBytes = Long.parseLong(env.getProperty("akip.minio.maxFileSizeInBytes"));
        }
        if (!isActive()) {
            log.debug("MinioService component not configured. If you want to use this component provide the properties akip.minio.*");
            return;
        }
        log.debug("MinioService successfully configured");
    }

    public void put(String key, byte[] bytes) {
        log.debug("Putting object {}", key);
        MinioClient minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();

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
            throw new BadRequestErrorException("error.minio.ioException", this.endpoint );
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error("Error getting object {}: {}", key, e);
            throw new BadRequestErrorException("error.minio.generalError", e.getMessage());
        }
    }

    private void validateMaxFileSize(byte[] bytes) {
        if (bytes.length > this.maxFileSizeInBytes * 1024) {
            BigDecimal oneMB = new BigDecimal(1024);
            throw new BadRequestErrorException("error.minio.maxFileSize", new BigDecimal(this.maxFileSizeInBytes).divide(oneMB, 2, RoundingMode.HALF_DOWN) + " MB",  new BigDecimal(bytes.length).divide(oneMB).divide(oneMB, 2, RoundingMode.HALF_DOWN) + " MB");
        }
    }

    public byte[] get(String key) {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();

        GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                .bucket(bucketName)
                .object(key)
                .build();

        try {
            InputStream is = minioClient.getObject(getObjectArgs);
            return IOUtils.toByteArray(is);
        } catch (ErrorResponseException e) {
            log.error("Error getting object {}: {}", key, e);
            throw new BadRequestErrorException("error.minio." + e.errorResponse().errorCode().code(), key );
        } catch (IOException e) {
            log.error("Error getting object {}: {}", key, e);
            throw new BadRequestErrorException("error.minio.ioException", this.endpoint );
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error("Error getting object {}: {}", key, e);
            throw new BadRequestErrorException("error.minio.generalError", e.getMessage());
        }
    }

    public void delete(String key) {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();

        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(key)
                .build();

        try {
            minioClient.removeObject(removeObjectArgs);
        } catch (ErrorResponseException e) {
            log.error("Error getting object {}: {}", key, e);
            throw new BadRequestErrorException("error.minio." + e.errorResponse().errorCode(), key );
        } catch (IOException e) {
            log.error("Error getting object {}: {}", key, e);
            throw new BadRequestErrorException("error.minio.ioException", this.endpoint );
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error("Error getting object {}: {}", key, e);
            throw new BadRequestErrorException("error.minio.generalError", e.getMessage());
        }
    }

    /**
     * If the any required property is not defined, the Spring component is not active.
     * @return
     */
    @Override
    public boolean isActive() {
        if (env.getProperty("akip.minio.inactive") != null && "true" == env.getProperty("akip.minio.inactive")) {
            return false;
        }

        if (endpoint == null || accessKey == null || secretKey == null || bucketName == null) {
            return false;
        }

        return true;
    }

}
