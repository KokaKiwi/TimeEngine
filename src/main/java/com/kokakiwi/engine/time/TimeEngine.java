package com.kokakiwi.engine.time;

import java.util.HashMap;

import com.kokakiwi.engine.time.data.Entity;
import com.kokakiwi.engine.time.storage.Storage;
import com.kokakiwi.engine.time.storage.StorageEntry;
import com.kokakiwi.engine.time.utils.MathsUtils;

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
    
    private long                           pointer  = 0;
    
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
    
    public void put()
    {
        put(System.currentTimeMillis());
    }
    
    public void put(long pointer)
    {
        this.pointer = pointer;
    }
    
    public long get()
    {
        return pointer;
    }
    
    public void rewind(int delta)
    {
        if (storage == null)
        {
            throw new NullPointerException("storage");
        }
        
        long timestamp = pointer - delta;
        long timestamp_low = Long.MAX_VALUE;
        long timestamp_high = Long.MAX_VALUE;
        
        for (long key : storage.getKeys())
        {
            long diff = Math.abs(key - timestamp);
            long diff_high = Math.abs(timestamp_high - timestamp);
            long diff_low = Math.abs(timestamp - timestamp_low);
            
            if (timestamp == key)
            {
                timestamp_high = timestamp;
                timestamp_low = timestamp;
                
                break;
            }
            
            if (diff <= diff_high && (key - timestamp) >= 0)
            {
                timestamp_high = key;
            }
            
            if (diff <= diff_low && (key - timestamp) <= 0)
            {
                timestamp_low = key;
            }
        }
        
        apply(timestamp, timestamp_low, timestamp_high);
        pointer = timestamp;
    }
    
    public void save()
    {
        put();
        
        save(pointer);
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
        apply(timestamp, timestamp, timestamp);
    }
    
    public void apply(long timestamp, long timestamp_low, long timestamp_high)
    {
        if (storage == null)
        {
            throw new NullPointerException("storage");
        }
        
        for (Entity entity : entities.values())
        {
            StorageEntry entry_low = storage.getEntry(timestamp_low,
                    entity.getId());
            StorageEntry entry_high = null;
            if (timestamp_high > timestamp_low)
            {
                entry_high = storage.getEntry(timestamp_high, entity.getId());
            }
            
            for (int field : entity.getFields())
            {
                float value = entry_low.getValue(field);
                
                if (entry_high != null)
                {
                    float value_high = entry_high.getValue(field);
                    float t = (timestamp - timestamp_low)
                            / (timestamp_high - timestamp_low);
                    
                    value = MathsUtils.lerp(value, value_high, t);
                }
                
                entity.setValue(field, value);
            }
        }
    }
}
