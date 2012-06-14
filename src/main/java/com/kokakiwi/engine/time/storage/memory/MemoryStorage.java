package com.kokakiwi.engine.time.storage.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kokakiwi.engine.time.storage.Storage;
import com.kokakiwi.engine.time.storage.StorageEntry;

public class MemoryStorage implements Storage
{
    private final Map<Long, List<MemoryStorageEntry>> entries = new HashMap<Long, List<MemoryStorageEntry>>();
    
    public List<? extends StorageEntry> getEntries(long timestamp)
    {
        List<MemoryStorageEntry> entries = this.entries.get(timestamp);
        
        if (entries == null)
        {
            entries = new ArrayList<MemoryStorageEntry>();
            this.entries.put(timestamp, entries);
        }
        
        return entries;
    }
    
    @SuppressWarnings("unchecked")
    public StorageEntry getEntry(long timestamp, int id)
    {
        List<MemoryStorageEntry> entries = (List<MemoryStorageEntry>) getEntries(timestamp);
        
        for (StorageEntry entry : entries)
        {
            if (entry.getId() == id)
            {
                return entry;
            }
        }
        
        MemoryStorageEntry entry = new MemoryStorageEntry(id);
        entries.add(entry);
        
        return entry;
    }
    
}
