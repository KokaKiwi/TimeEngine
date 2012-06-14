package com.kokakiwi.engine.time.storage;

import java.util.List;

public interface Storage
{
    public List<? extends StorageEntry> getEntries(long timestamp);
    
    public StorageEntry getEntry(long timestamp, int id);
}
