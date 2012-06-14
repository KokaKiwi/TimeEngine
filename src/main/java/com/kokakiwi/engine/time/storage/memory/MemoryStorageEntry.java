package com.kokakiwi.engine.time.storage.memory;

import java.util.HashMap;
import java.util.Map;

import com.kokakiwi.engine.time.storage.StorageEntry;

public class MemoryStorageEntry implements StorageEntry
{
    private final int                 id;
    private final Map<Integer, Float> values = new HashMap<Integer, Float>();
    
    public MemoryStorageEntry(int id)
    {
        this.id = id;
    }
    
    public int getId()
    {
        return id;
    }
    
    public float getValue(int field)
    {
        Float value = values.get(field);
        
        if (value == null)
        {
            value = 0.0f;
        }
        
        return value.floatValue();
    }
    
    public void setValue(int field, float value)
    {
        values.put(field, value);
    }
    
}
