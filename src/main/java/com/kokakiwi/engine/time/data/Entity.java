package com.kokakiwi.engine.time.data;

public interface Entity
{
    public int getId();
    
    public float getValue(int field);
    
    public void setValue(int field, float value);
    
    public int[] getFields();
}
