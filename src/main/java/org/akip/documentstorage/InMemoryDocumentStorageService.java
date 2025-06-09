package org.akip.documentstorage;

import org.akip.documentstorage.IDocumentStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InMemoryDocumentStorageService implements IDocumentStorageService {
    private final Logger log = LoggerFactory.getLogger(InMemoryDocumentStorageService.class);
    private Map<String, byte[]> storage = new HashMap<>();

    private boolean isActive = false;

    public InMemoryDocumentStorageService(Environment env) {
        if (env.getProperty("akip.document-storage.in-memory.active") != null && "true" == env.getProperty("akip.document-storage.in-memory.active")) {
            isActive = true;
        }
    }

    public void put(String key, byte[] bytes) {
        this.log.debug("Putting object {}", key);
        storage.put(key, bytes);
    }

    public byte[] get(String key) {
        this.log.debug("Getting object {}", key);
        return this.storage.get(key);
    }

    public void delete(String key) {
        this.log.debug("Deleting object {}", key);
        this.storage.remove(key);
    }

    public boolean isActive() {
        return isActive;
    }

}
