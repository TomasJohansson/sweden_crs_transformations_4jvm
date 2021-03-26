package com.programmerare.sweden_crs_transformations_4jvm.mighty_little_geodesy;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class JavaLatLonTest {
    private final static double delta = 0.00000000000000000001;
    
    @Test
    public void latLon() {
        final LatLon latLon = new LatLon(12.34, 56.78);
        assertEquals(latLon.LatitudeY(), 12.34, delta);
        assertEquals(latLon.LongitudeX(), 56.78, delta);        
    }
}
