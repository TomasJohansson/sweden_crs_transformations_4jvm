package com.programmerare.sweden_crs_transformations_4jvm.mighty_little_geodesy

import com.programmerare.sweden_crs_transformations_4jvm.CrsProjection
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class KotlinGaussKreugerTest {
    // https://kartor.eniro.se/m/XRCfh
    //WGS84 decimal (lat, lon)      59.330231, 18.059196
    //SWEREF99 TM (nord, öst)       6580822, 674032
    private val stockholmCentralStation_WGS84_latitude = 59.330231
    private val stockholmCentralStation_WGS84_longitude = 18.059196
    private val stockholmCentralStation_SWEREF99TM_northing = 6580822.0
    private val stockholmCentralStation_SWEREF99TM_easting = 674032.0

    private val gaussKreugerFactory = GaussKreugerFactory.getInstance()
    private var gaussKreuger: GaussKreuger = gaussKreugerFactory.getGaussKreuger(CrsProjection.SWEREF_99_TM)

    @Before
    fun setUp() {
//        gaussKreuger = gaussKreugerFactory.getGaussKreuger(CrsProjection.SWEREF_99_TM)
    }

    @Test
    fun geodetic_to_grid_transforming_from_WGS84_to_SWEREF99TM() {
        val resultSweref99 = gaussKreuger.geodetic_to_grid(
            stockholmCentralStation_WGS84_latitude,
            stockholmCentralStation_WGS84_longitude
        )
        // failure if using equality assertion
        // Expected: 6580822
        // Received: 6580821.991
        val delta = 0.4 // max diff below is around 0.357
        assertEquals(
            stockholmCentralStation_SWEREF99TM_northing,
            resultSweref99.LatitudeY,
            delta
        )
        assertEquals(
            stockholmCentralStation_SWEREF99TM_easting,
            resultSweref99.LongitudeX,
            delta
        )
    }

    @Test
    fun grid_to_geodetic_transforming_from_SWEREF99TM_to_WGS84() {
        val resultWGS84 = gaussKreuger.grid_to_geodetic(
            stockholmCentralStation_SWEREF99TM_northing,
            stockholmCentralStation_SWEREF99TM_easting
        )
        // failure if using equality assertion
        //  Expected :59.330231
        //  Actual   :59.33023122691265
        val delta = 0.00001 // // max diff below is around 6.26E-6
        // println("y " + (stockholmCentralStation_WGS84_latitude-resultWGS84.LatitudeY))
        // println("x " + (stockholmCentralStation_WGS84_longitude-resultWGS84.LongitudeX))  
        assertEquals(
            stockholmCentralStation_WGS84_latitude,
            resultWGS84.LatitudeY,
            delta
        )
        assertEquals(
            stockholmCentralStation_WGS84_longitude,
            resultWGS84.LongitudeX,
            delta
        )
    }

}
