package com.programmerare.sweden_crs_transformations_4jvm;

import com.programmerare.sweden_crs_transformations_4jvm.CrsProjection;
import com.programmerare.sweden_crs_transformations_4jvm.GaussKreuger;
import com.programmerare.sweden_crs_transformations_4jvm.GaussKreugerFactory;
import com.programmerare.sweden_crs_transformations_4jvm.LatLon;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class JavaGaussKreugerTest {
    // https://kartor.eniro.se/m/XRCfh
    //WGS84 decimal (lat, lon)      59.330231, 18.059196
    //SWEREF99 TM (nord, öst)       6580822, 674032
    private final static double stockholmCentralStation_WGS84_latitude = 59.330231;
    private final static double  stockholmCentralStation_WGS84_longitude = 18.059196;
    private final static double stockholmCentralStation_SWEREF99TM_northing = 6580822;
    private final static double  stockholmCentralStation_SWEREF99TM_easting = 674032;

    private final static GaussKreugerFactory gaussKreugerFactory = GaussKreugerFactory.getInstance();
    private GaussKreuger gaussKreuger;
  
    @Before
    public void setUp() {
        gaussKreuger = gaussKreugerFactory.getGaussKreuger(CrsProjection.SWEREF_99_TM);
    }
  
    @Test
    public void geodetic_to_grid_transforming_from_WGS84_to_SWEREF99TM() {
        final LatLon resultSweref99 = gaussKreuger.geodetic_to_grid(stockholmCentralStation_WGS84_latitude, stockholmCentralStation_WGS84_longitude);
        // failure for the above if using equality comparison below
        // Expected: 6580822
        // Received: 6580821.991
        final double delta = 0.4; // max diff below is around 0.357
        assertEquals(stockholmCentralStation_SWEREF99TM_northing, resultSweref99.LatitudeY, delta);
        assertEquals(stockholmCentralStation_SWEREF99TM_easting, resultSweref99.LongitudeX, delta);
    }
  
    @Test
    public void grid_to_geodetic_transforming_from_SWEREF99TM_to_WGS84() {
        final LatLon resultWGS84 = gaussKreuger.grid_to_geodetic(stockholmCentralStation_SWEREF99TM_northing, stockholmCentralStation_SWEREF99TM_easting);
        // failure for the above if using 'toEqual'
        //  Expected :59.330231
        //  Actual   :59.33023122691265
        final double delta = 0.00001; // // max diff below is around 6.26E-6
        //  System.out.println("y " + (stockholmCentralStation_WGS84_latitude-resultWGS84.LatitudeY));
        //  System.out.println("x " + (stockholmCentralStation_WGS84_longitude-resultWGS84.LongitudeX));  
        assertEquals(stockholmCentralStation_WGS84_latitude, resultWGS84.LatitudeY, delta);
        assertEquals(stockholmCentralStation_WGS84_longitude, resultWGS84.LongitudeX, delta);
    }
}
