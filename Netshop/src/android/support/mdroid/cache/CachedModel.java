package android.support.mdroid.cache;

import java.io.Serializable;

/**
 * Superclass of all objects to be stored in {@link ModelCache}.
 * 
 * To save an object to the cache use {@link #save(ModelCache)}, when updating an object with any
 * new data use {@link #reload(ModelCache)}, and when wanting to find an object from the cache, use
 * {@link #find(ModelCache, String, Class)}.
 * 
 * 
 */
public abstract class CachedModel implements Serializable {

    private static final long serialVersionUID = 4685383243903578768L;
    private String serializableId;
    private long transactionId = Long.MIN_VALUE;

    /**
     * Simple parameter-less constructor. <b>Must</b> also have parameter-less constructor in
     * subclasses in order for parceling to work.
     */
    public CachedModel() {
    }

    /**
     * Constructor setting ID given.
     * 
     * @param id ID of new object (used when generating cache key).
     */
    public CachedModel(String id) {
        this.serializableId = id;
    }

    /**
     * Returns ID of object used in key generation.
     * 
     * @return ID of new object (used when generating cache key).
     */
    public String getSerializableId() {
        return serializableId;
    }

    /**
     * Set ID of object used in key generation.
     * 
     * @param id ID of new object (used when generating cache key).
     */
    public void setSerializableId(String id) {
        this.serializableId = id;
    }

    void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * @return Key used to store object in cache. null if id is null.
     */
    public String getKey() {
        if (serializableId == null) {
            return null;
        } else {
            return createKey(serializableId);
        }
    }

    /**
     * Method for looking up object in cache.
     * 
     * @param modelCache Cache to be searched.
     * @param id ID of object to be searched for.
     * @param clazz Class of desired cached object
     * @return Object from cache based upon given parameters.
     */
    public static CachedModel find(ModelCache modelCache, String id, Class<? extends CachedModel> clazz) {
        // Create empty object of type
        CachedModel testObject;
        try {
            testObject = clazz.newInstance();
        } catch (Exception e) {
            return null;
        }

        // Set ID so key can be generated
        testObject.setSerializableId(id);

        // Attempt to reload object from cache
        if (testObject.reload(modelCache)) {
            return testObject;
        } else {
            return null;
        }
    }

    /**
     * Attempts to store the object in the cache using this object's key. Generally this is the
     * required used.
     * 
     * Overwritten when saving subclasses over the top of separate superclass cache stores. e.g.:
     * 
     * <pre>
     * {@code
     * public boolean save(ModelCache modelCache) {
     *     return super.save(modelCache) && super.save(modelCache, super.getKey());
     * }
     * }
     * </pre>
     * 
     * @param modelCache Cache to save to.
     * @return Whether or not saving to the cache was successful.
     */
    public boolean save(ModelCache modelCache) {
        return save(modelCache, getKey());
    }

    /**
     * Attempts to save the object in the cache using a given key. Generally only used for saving
     * subclasses over the top of separate superclass cache stores.
     * 
     * @param modelCache Cache to save to.
     * @param saveKey Key to be saved under.
     * @return Whether or not saving to the cache was successful.
     */
    protected boolean save(ModelCache modelCache, String saveKey) {
        if ((modelCache != null) && (saveKey != null)) {
            modelCache.addToCache(saveKey, this);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Attempts to reload any new data from cache.
     * 
     * @param modelCache Cache to be reloaded from.
     * @return Whether or not newer data was found in the cache.
     */
    public boolean reload(ModelCache modelCache) {
        String key = getKey();
        if ((modelCache != null) && (key != null)) {
            CachedModel cachedModel = modelCache.getFromCache(key);
            if ((cachedModel != null) && (cachedModel.transactionId > this.transactionId)) {
                reloadFromCachedModel(modelCache, cachedModel);
                this.transactionId = cachedModel.transactionId;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Method called to determine a key in the cache using the object's id e.g.:
     * 
     * <pre>
     * {@code
     * public String createKey(String id) {
     *     "example_object_" + id;
     * }
     * }
     * </pre>
     * 
     * @param id ID of object to be stored.
     * @return Key that object with given ID should be stored in cache under.
     */
    public abstract String createKey(String id);

    /**
     * Method called to reload any data from a more recently stored object e.g.:
     * 
     * <pre>
     * {@code
     * public boolean reloadFromCachedModel(ModelCache modelCache, CachedModel cachedModel) {
     *     ExampleObject cachedExampleObject = (ExampleObject) cachedModel;
     *     this.exampleVariable = cachedExampleObject.exampleVariable;
     *     return false;
     * }
     * }
     * </pre>
     * 
     * Can also be used to reload internal cached objects. e.g.:
     * 
     * <pre>
     * {@code
     * public boolean reloadFromCachedModel(ModelCache modelCache, CachedModel cachedModel) {
     *     ExampleObject cachedExampleObject = (ExampleObject) cachedModel;
     *     this.exampleInternalCachedObject = cachedExampleObject.exampleInternalCachedObject;
     *     return this.exampleInternalCachedObject.reload(modelCache);
     * }
     * }
     * </pre>
     * 
     * @param modelCache Cache that is currently being reloaded from.
     * @param cachedModel Latest version of object in cache.
     * @return Whether or not an internal cached object was updated (useful for optimization
     *         purposes, especially on lists).
     */
    public abstract boolean reloadFromCachedModel(ModelCache modelCache, CachedModel cachedModel);

}
