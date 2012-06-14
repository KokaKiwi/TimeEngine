package com.kokakiwi.engine.time;

import java.util.HashMap;

import com.kokakiwi.engine.time.data.Entity;
import com.kokakiwi.engine.time.storage.Storage;
import com.kokakiwi.engine.time.storage.StorageEntry;

/**
 * Main class, more docs on Wiki (URL HERE)
 * 
 * @author Koka El Kiwi
 * @version 0.1.0
 * 
 */
public class TimeEngine
{
    private Storage                        storage  = null;
    
    private final HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>();
    
    public TimeEngine(Storage storage)
    {
        this.storage = storage;
    }
    
    public Storage getStorage()
    {
        return storage;
    }
    
    public void setStorage(Storage storage)
    {
        this.storage = storage;
    }
    
    public HashMap<Integer, Entity> getEntities()
    {
        return entities;
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Entity> T getEntity(int id)
    {
        return (T) entities.get(id);
    }
    
    public <T extends Entity> void registerEntity(T entity)
    {
        if (!entities.containsKey(entity.getId()))
        {
            entities.put(entity.getId(), entity);
        }
    }
    
    public void save(long timestamp)
    {
        if (storage == null)
        {
            throw new NullPointerException("storage");
        }
        
        for (Entity entity : entities.values())
        {
            StorageEntry entry = storage.getEntry(timestamp, entity.getId());
            
            for (int field : entity.getFields())
            {
                float value = entity.getValue(field);
                entry.setValue(field, value);
            }
        }
    }
    
    public void apply(long timestamp)
    {
        if (storage == null)
        {
            throw new NullPointerException("storage");
        }
        
        for (Entity entity : entities.values())
        {
            StorageEntry entry = storage.getEntry(timestamp, entity.getId());
            
            for (int field : entity.getFields())
            {
                float value = entry.getValue(field);
                entity.setValue(field, value);
            }
        }
    }
}
