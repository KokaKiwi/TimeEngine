package com.kokakiwi.engine.time.utils;

/**
 * Some utility math functions.
 * 
 * @author Koka El Kiwi
 * @version 0.1.0
 * 
 */
public class MathsUtils
{
    /**
     * Linearly interpolates between va and vb by the parameter t.
     * 
     * @param va
     *            first value.
     * @param vb
     *            second value.
     * @param t
     *            parameter.
     * @return result value.
     */
    public static float lerp(float va, float vb, float t)
    {
        return va + t * (vb - va);
    }
}
