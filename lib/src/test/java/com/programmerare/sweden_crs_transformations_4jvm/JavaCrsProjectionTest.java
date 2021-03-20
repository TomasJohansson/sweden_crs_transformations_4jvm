package com.programmerare.sweden_crs_transformations_4jvm;

import static com.programmerare.sweden_crs_transformations_4jvm.JavaCrsProjectionFactoryTest.numberOfSweref99projections;
import static com.programmerare.sweden_crs_transformations_4jvm.JavaCrsProjectionFactoryTest.numberOfWgs84Projections;
import static com.programmerare.sweden_crs_transformations_4jvm.JavaCrsProjectionFactoryTest.numberOfRT90projections;
import static com.programmerare.sweden_crs_transformations_4jvm.JavaCrsProjectionFactoryTest.epsgNumberForWgs84;
import static com.programmerare.sweden_crs_transformations_4jvm.JavaCrsProjectionFactoryTest.epsgNumberForSweref99tm;
import static com.programmerare.sweden_crs_transformations_4jvm.CrsProjection.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.HashSet;
import static org.junit.Assert.*;
    
public class JavaCrsProjectionTest
{

    private HashSet<CrsProjection> _wgs84Projections;
    private HashSet<CrsProjection> _sweref99Projections;
    private HashSet<CrsProjection> _rt90Projections;

    @Before
    public void setUp() {
        _wgs84Projections = new HashSet<CrsProjection>(Arrays.asList(CrsProjection.WGS84));
        _sweref99Projections = new HashSet<CrsProjection>(Arrays.asList(SWEREF_99_12_00,
            SWEREF_99_13_30, SWEREF_99_14_15, SWEREF_99_15_00, SWEREF_99_15_45, SWEREF_99_16_30,
            SWEREF_99_17_15, SWEREF_99_18_00, SWEREF_99_18_45, SWEREF_99_20_15, SWEREF_99_21_45,
            SWEREF_99_23_15, SWEREF_99_TM
        ));
        _rt90Projections = new HashSet<CrsProjection>(Arrays.asList(RT90_0_0_GON_V, RT90_2_5_GON_O,
            RT90_2_5_GON_V, RT90_5_0_GON_O, RT90_5_0_GON_V, RT90_7_5_GON_V
        ));
    }

    @Test
    public void getEpsgNumber() {
        assertEquals(
            epsgNumberForSweref99tm, // constant defined in JavaCrsProjectionFactoryTest
            CrsProjection.SWEREF_99_TM.getEpsgNumber()
        );

        assertEquals(
            epsgNumberForWgs84, // constant defined in JavaCrsProjectionFactoryTest
            CrsProjection.WGS84.getEpsgNumber()
        );
    }


    @Test
    public void isWgs84() {
        assertEquals(numberOfWgs84Projections, _wgs84Projections.size());

        for(CrsProjection item : _wgs84Projections) {
            assertTrue(item.isWgs84());
        }
        for(CrsProjection item : _sweref99Projections) {
            assertFalse(item.isWgs84());
        }
        for(CrsProjection item : _rt90Projections) {
            assertFalse(item.isWgs84());
        }
    }

    @Test
    public void isSweref() {
        assertEquals(numberOfSweref99projections, _sweref99Projections.size());

        for(CrsProjection item : _wgs84Projections) {
            assertFalse(item.isSweref());
        }
        for(CrsProjection item : _sweref99Projections) {
            assertTrue(item.isSweref());
        }
        for(CrsProjection item : _rt90Projections) {
            assertFalse(item.isSweref());
        }
    }

    @Test
    public void isRT90() {
        assertEquals(numberOfRT90projections, _rt90Projections.size());

        for(CrsProjection item : _wgs84Projections) {
            assertFalse(item.isRT90());
        }
        for(CrsProjection item : _sweref99Projections) {
            assertFalse(item.isRT90());
        }
        for(CrsProjection item : _rt90Projections) {
            assertTrue(item.isRT90());
        }
    }


    @Test
    public void getAsString() {
        assertEquals(
            "WGS84",
            CrsProjection.WGS84.getAsString()
        );


        assertEquals(
            "SWEREF_99_TM",
            CrsProjection.SWEREF_99_TM.getAsString()
        );

        assertEquals(
            "SWEREF_99_14_15",
            CrsProjection.SWEREF_99_14_15.getAsString()
        );

        assertEquals(
            "RT90_0_0_GON_V",
            CrsProjection.RT90_0_0_GON_V.getAsString()
        );
    }

}
