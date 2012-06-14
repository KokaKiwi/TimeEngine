package com.kokakiwi.engine.time.storage;

public interface StorageEntry
{
    public int getId();
    
    public float getValue(int field);
    
    public void setValue(int field, float value);
}
