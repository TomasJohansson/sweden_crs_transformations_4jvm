package com.programmerare.sweden_crs_transformations_4jvm.mighty_little_geodesy;

import org.junit.Test
import org.junit.Assert.assertEquals

class KotlinLatLonTest {
    private val delta = 0.00000000000000000001

    @Test
    fun latLon() {
        val latLon = LatLon(12.34, 56.78)
        assertEquals(latLon.LatitudeY, 12.34, delta)
        assertEquals(latLon.LongitudeX, 56.78, delta)
    }

}
