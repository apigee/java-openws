/*
 * Copyright [2007] [University Corporation for Advanced Internet Development, Inc.]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensaml.util.storage;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * Class that uses an underlying {@link StorageService} to track information associated with messages in order to detect
 * message replays.
 * 
 * This class is thread-safe and uses a basic reentrant lock to avoid corruption of the underlying store, as 
 * well as to prevent race conditions with respect to replay checking.
 */
public class ReplayCache {
    
    /** Logger. */
    private static Logger log = Logger.getLogger(ReplayCache.class);

    /** Backing storage for the replay cache. */
    private StorageService<String, ReplayCacheEntry> storage;
    
    /** Storage service partition used by this cache. default: replay */
    private String partition;

    /** Time, in milliseconds, that message state is valid. */
    private long entryDuration;

    /** Read/Write lock. */
    private ReadWriteLock readWriteLock;

    /**
     * Constructor.
     * 
     * @param storageService the StorageService which serves as the backing store for the cache
     * @param duration default length of time that message state is valid
     */
    public ReplayCache(StorageService<String, ReplayCacheEntry> storageService, long duration) {
        storage = storageService;
        entryDuration = duration;
        partition = "replay";
        readWriteLock = new ReentrantReadWriteLock(true);
    }
    
    /**
     * Constructor.
     * 
     * @param storageService the StorageService which serves as the backing store for the cache
     * @param storageParition name of storage service partition to use
     * @param duration default length of time that message state is valid
     */
    public ReplayCache(StorageService<String, ReplayCacheEntry> storageService, String storageParition, long duration) {
        storage = storageService;
        entryDuration = duration;
        if(!DatatypeHelper.isEmpty(storageParition)){
            partition = DatatypeHelper.safeTrim(storageParition);
        }else{
            partition = "replay";
        }
        readWriteLock = new ReentrantReadWriteLock(true);
    }

    /**
     * Checks if the message has been replayed. If the message has not been seen before then it is added to the list of
     * seen of messages for the default duration.
     * 
     * @param issuerId unique ID of the message issuer
     * @param messageId unique ID of the message
     * 
     * @return true if the given message ID has been seen before
     */
    public boolean isReplay(String issuerId, String messageId) {
        log.debug("Attempting to acquire lock for replay cache check");
        Lock readLock = readWriteLock.readLock();

        log.debug("Lock acquired");
        
        boolean replayed = true;
        String entryHash = issuerId + messageId;
        
        readLock.lock();
        ReplayCacheEntry cacheEntry = storage.get(partition, entryHash);
        readLock.unlock();
        
        if (cacheEntry == null || cacheEntry.isExpired()) {
            if (log.isDebugEnabled()) {
                if (cacheEntry == null) {
                    log.debug(String.format("Message ID '%s' was not a replay", messageId));
                } else if (cacheEntry.isExpired()){
                    log.debug(String.format("Message ID '%s' expired in replay cache at '%s'", messageId, 
                            cacheEntry.getExpirationTime().toString()));
                    storage.remove(partition, entryHash);
                }
            }
            replayed = false;
            addMessageID(entryHash, new DateTime().plus(entryDuration));
        } else {
            if (log.isDebugEnabled()) {
                log.debug(String.format("Replay of message ID '%s' detected in replay cache, will expire at '%s'",
                        messageId, cacheEntry.getExpirationTime().toString()));
            }
        }

        return replayed;
    }

    /**
     * Accquires a write lock and adds the message state to the underlying storage service.
     * 
     * @param messageId unique ID of the message
     * @param expiration time the message state expires
     */
    protected void addMessageID(String messageId, DateTime expiration) {
        log.debug("Attempting to acquire lock for replay cache write");
        Lock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        log.debug("Lock acquired");
        
        try {
            if (log.isDebugEnabled()) {
                log.debug(String.format("Writing message ID '%s' to replay cache with expiration time '%s'",
                        messageId, expiration.toString()) );
            }
            storage.put(partition, messageId, new ReplayCacheEntry(expiration));
        } finally {
            writeLock.unlock();
            log.debug("Lock released for replay cache write");
        }
        
    }
    
    /** Replay cache storage service entry. */
    public class ReplayCacheEntry implements ExpiringObject, Serializable{
        
        /** Serial version UID. */
        private static final long serialVersionUID = 2398693920546938083L;
        
        /** Time when this entry expires. */
        private DateTime expirationTime;
        
        /**
         * Constructor.
         *
         * @param expiration time when this entry expires
         */
        public ReplayCacheEntry(DateTime expiration){
            expirationTime = expiration;
        }
        
        /** {@inheritDoc} */
        public DateTime getExpirationTime() {
            return expirationTime;
        }
        
        /** {@inheritDoc} */
        public boolean isExpired() {
            return expirationTime.isBeforeNow();
        }
        
        /** {@inheritDoc} */
        public void onExpire() {
            
        }
    }
}