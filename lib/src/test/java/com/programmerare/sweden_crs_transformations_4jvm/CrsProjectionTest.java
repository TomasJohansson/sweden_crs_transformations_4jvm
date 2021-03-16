package com.programmerare.sweden_crs_transformations_4jvm;

import static com.programmerare.sweden_crs_transformations_4jvm.CrsProjectionFactoryTest.numberOfSweref99projections;
import static com.programmerare.sweden_crs_transformations_4jvm.CrsProjectionFactoryTest.numberOfWgs84Projections;
import static com.programmerare.sweden_crs_transformations_4jvm.CrsProjectionFactoryTest.numberOfRT90projections;
import static com.programmerare.sweden_crs_transformations_4jvm.CrsProjectionFactoryTest.epsgNumberForWgs84;
import static com.programmerare.sweden_crs_transformations_4jvm.CrsProjectionFactoryTest.epsgNumberForSweref99tm;
import static com.programmerare.sweden_crs_transformations_4jvm.CrsProjection.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.HashSet;
import static org.junit.Assert.*;
    
public class CrsProjectionTest  {

    private HashSet<CrsProjection> _wgs84Projections;
    private HashSet<CrsProjection> _sweref99Projections;
    private HashSet<CrsProjection> _rt90Projections;

    @Before
    public void SetUp() {
        _wgs84Projections = new HashSet<CrsProjection>(Arrays.asList(CrsProjection.wgs84));
        _sweref99Projections = new HashSet<CrsProjection>(Arrays.asList( 
            sweref_99_12_00, sweref_99_13_30, sweref_99_14_15,
            sweref_99_15_00, sweref_99_15_45, sweref_99_16_30,
            sweref_99_17_15, sweref_99_18_00, sweref_99_18_45,
            sweref_99_20_15, sweref_99_21_45, sweref_99_23_15, sweref_99_tm
        ));
        _rt90Projections = new HashSet<CrsProjection>(Arrays.asList( 
            rt90_0_0_gon_v, rt90_2_5_gon_o, rt90_2_5_gon_v,
            rt90_5_0_gon_o, rt90_5_0_gon_v, rt90_7_5_gon_v
        ));
    }

    @Test
    public void GetEpsgNumber() {
        assertEquals(
            epsgNumberForSweref99tm, // constant defined in CrsProjectionFactoryTest
            CrsProjection.sweref_99_tm.GetEpsgNumber()
        );

        assertEquals(
            epsgNumberForWgs84, // constant defined in CrsProjectionFactoryTest
            CrsProjection.wgs84.GetEpsgNumber()
        );
    }


    @Test
    public void isWgs84() {
        assertEquals(numberOfWgs84Projections, _wgs84Projections.size());

        for(CrsProjection item : _wgs84Projections) {
            assertTrue(item.IsWgs84());
        }
        for(CrsProjection item : _sweref99Projections) {
            assertFalse(item.IsWgs84());
        }
        for(CrsProjection item : _rt90Projections) {
            assertFalse(item.IsWgs84());
        }
    }

    @Test
    public void isSweref() {
        assertEquals(numberOfSweref99projections, _sweref99Projections.size());

        for(CrsProjection item : _wgs84Projections) {
            assertFalse(item.IsSweref());
        }
        for(CrsProjection item : _sweref99Projections) {
            assertTrue(item.IsSweref());
        }
        for(CrsProjection item : _rt90Projections) {
            assertFalse(item.IsSweref());
        }
    }

    @Test
    public void isRT90() {
        assertEquals(numberOfRT90projections, _rt90Projections.size());

        for(CrsProjection item : _wgs84Projections) {
            assertFalse(item.IsRT90());
        }
        for(CrsProjection item : _sweref99Projections) {
            assertFalse(item.IsRT90());
        }
        for(CrsProjection item : _rt90Projections) {
            assertTrue(item.IsRT90());
        }
    }


    @Test
    public void getAsString() {
        assertEquals(
            "WGS84",
            CrsProjection.wgs84.getAsString()
        );


        assertEquals(
            "SWEREF_99_TM",
            CrsProjection.sweref_99_tm.getAsString()
        );

        assertEquals(
            "SWEREF_99_14_15",
            CrsProjection.sweref_99_14_15.getAsString()
        );

        assertEquals(
            "RT90_0_0_GON_V",
            CrsProjection.rt90_0_0_gon_v.getAsString()
        );
    }

}
