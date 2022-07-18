package org.akip.minio;

import java.time.LocalDateTime;

public interface IDocumentStorageService {

    void put(String key, byte[] bytes);

    byte[] get(String key);

    void delete(String key);

    boolean isActive();

    default void put(String entityName, LocalDateTime localDateTime, String key, byte[] bytes) {
        this.put(buildObjectKey(entityName, localDateTime, key), bytes);
    }

    default byte[] get(String entityName, LocalDateTime localDateTime, String key) {
        return this.get(buildObjectKey(entityName, localDateTime, key));
    }

    default void delete(String entityName, LocalDateTime localDateTime, String key) {
        this.delete(buildObjectKey(entityName, localDateTime, key));
    }

    /**
     * The objectKey is build with three inputs:
     * 1. entityName (ex: Attachment, Certificate, etc).
     * 2. date.
     * 3. key. The object key.
     *
     * Example: ("Attachment", Date (15/02/2021), "Atachment5001") will generate the following objectKey:
     * "Attachment/2021/2/Atachment5001".
     *
     */
    private String buildObjectKey (String entityName, LocalDateTime localDateTime, String key) {
        int month = localDateTime.getMonthValue();
        int year = localDateTime.getYear();
        return entityName + "/" + year + "/" + month + "/" + key;
    }

}
