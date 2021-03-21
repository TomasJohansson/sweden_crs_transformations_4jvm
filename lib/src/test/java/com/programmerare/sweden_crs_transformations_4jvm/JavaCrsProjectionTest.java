package com.programmerare.sweden_crs_transformations_4jvm;

import static com.programmerare.sweden_crs_transformations_4jvm.CrsProjection.*; // WGS84 , SWEREF_99_TM, ...
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;
    
public class JavaCrsProjectionTest
{
    public final static int epsgNumberForWgs84 = 4326;
    public final static int epsgNumberForSweref99tm = 3006; // https://epsg.org/crs_3006/SWEREF99-TM.html
    public final static int numberOfSweref99projections = 13; // with EPSG numbers 3006-3018
    public final static int numberOfRT90projections = 6; // with EPSG numbers 3019-3024
    public final static int numberOfWgs84Projections = 1; // just to provide semantic instead of using a magic number 1 below
    public final static int totalNumberOfProjections = numberOfSweref99projections + numberOfRT90projections + numberOfWgs84Projections;
    
    private HashSet<CrsProjection> _wgs84Projections;
    private HashSet<CrsProjection> _sweref99Projections;
    private HashSet<CrsProjection> _rt90Projections;

    private List<CrsProjection> _allCrsProjections;
    
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

        _allCrsProjections = CrsProjection.getAllCrsProjections();
    }

    @Test
    public void getEpsgNumber() {
        assertEquals(
            epsgNumberForSweref99tm,
            CrsProjection.SWEREF_99_TM.getEpsgNumber()
        );

        assertEquals(
            epsgNumberForWgs84,
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
    public void isSweRef99() {
        assertEquals(numberOfSweref99projections, _sweref99Projections.size());

        for(CrsProjection item : _wgs84Projections) {
            assertFalse(item.isSweRef99());
        }
        for(CrsProjection item : _sweref99Projections) {
            assertTrue(item.isSweRef99());
        }
        for(CrsProjection item : _rt90Projections) {
            assertFalse(item.isSweRef99());
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
    public void toStringTest() {
        assertEquals(
            "WGS84(EPSG:4326)",
            CrsProjection.WGS84.toString()
        );

        assertEquals(
            "SWEREF_99_TM(EPSG:3006)",
            CrsProjection.SWEREF_99_TM.toString()
        );

        assertEquals(
            "SWEREF_99_14_15(EPSG:3012)",
            CrsProjection.SWEREF_99_14_15.toString()
        );

        assertEquals(
            "RT90_0_0_GON_V(EPSG:3022)",
            CrsProjection.RT90_0_0_GON_V.toString()
        );
    }

    @Test
    public void getCrsProjectionByEpsgNumber() {
        assertEquals(
            CrsProjection.SWEREF_99_TM,
            CrsProjection.getCrsProjectionByEpsgNumber(epsgNumberForSweref99tm)
        );

        assertEquals(
            CrsProjection.SWEREF_99_23_15,
            CrsProjection.getCrsProjectionByEpsgNumber(3018) // https://epsg.io/3018
        );

        assertEquals(
            CrsProjection.RT90_5_0_GON_O,
            CrsProjection.getCrsProjectionByEpsgNumber(3024)  // https://epsg.io/3024
        );
    }

    @Test
    public void verifyTotalNumberOfProjections() {
        assertEquals(
            totalNumberOfProjections,
            _allCrsProjections.size() // retrieved with 'getAllCrsProjections' in the setUp ('@Before' annotated) method
        );
    }

    @Test
    public void verifyNumberOfWgs84Projections() {
        assertEquals(
            numberOfWgs84Projections,

            // Java8:
            // getNumberOfProjections(CrsProjection::isWgs84) //  crs -> crs.isWgs84()
            // Java6:
            getNumberOfProjections(ProjectionToCount.WGS)
        );
    }
    @Test
    public void verifyNumberOfSweref99Projections() {
        assertEquals(
            numberOfSweref99projections,
            
            // Java8:
            // getNumberOfProjections(CrsProjection::isSweref) // crs -> crs.isSweref()
            // Java6:
            getNumberOfProjections(ProjectionToCount.SWEREF)            
        );
    }
    @Test
    public void verifyNumberOfRT90Projections() {
        assertEquals(
            numberOfRT90projections,
            
            // Java8:
            // getNumberOfProjections(CrsProjection::isRT90) // crs -> crs.isRT90()
            // Java6:
            getNumberOfProjections(ProjectionToCount.RT90)
        );
    }
    // Java8:
//    private int getNumberOfProjections(Predicate<CrsProjection> predicate) {
//        //    return _allCrsProjections.stream()
//        //        .filter(predicate) // .filter(predicate::test)  or  .filter(crs -> predicate.test(crs))
//        //        .collect(Collectors.toList())
//        //        .size();
//        // The below row is a shorter alternative to the above implementation
//        return (int) _allCrsProjections.stream().filter(predicate).count();
//    }
    // Java6:    
    enum ProjectionToCount { WGS, RT90, SWEREF }
    private int getNumberOfProjections(ProjectionToCount projectionToCount) {
        // Java6 implementation i.e. without Java8 Predicate and lambda features
        int count = 0;
        for (CrsProjection crsProjection: _allCrsProjections) {
            if(shouldBeCounted(projectionToCount, crsProjection)) {
                count++;
            }
        }
        return count;
    }
    // helper method used within a loop
    private boolean shouldBeCounted(
        ProjectionToCount projectionToCount,
        CrsProjection crsProjection
    ) {
        // Java6 implementation i.e. without Java8 Predicate and lambda features
        final ProjectionToCount p = projectionToCount;
        final CrsProjection c = crsProjection;
        return (
            p == ProjectionToCount.WGS && c.isWgs84()
            ||
            p == ProjectionToCount.SWEREF && c.isSweRef99()
            ||
            p == ProjectionToCount.RT90 && c.isRT90()
        );
    }

    @Test
    public void verifyThatAllProjectionsCanBeRetrievedByItsEpsgNumber() {
        for(CrsProjection crsProjection : _allCrsProjections) {
            CrsProjection crsProj = CrsProjection.getCrsProjectionByEpsgNumber(crsProjection.getEpsgNumber());
            assertEquals(crsProjection, crsProj);
        }
    }

    @Test
    public void verifyOrderOfProjectionsInEnum() {
        List<CrsProjection> expectedProjections = Arrays.asList(
            WGS84,           // 4326
            // the first item should be WGS84 (as above, but then below they are expected to be ordered by increasing EPSG number, i.e. from low to high) 
            SWEREF_99_TM,    // 3006  // national sweref99 CRS
            SWEREF_99_12_00, // 3007
            SWEREF_99_13_30, // 3008
            SWEREF_99_15_00, // 3009
            SWEREF_99_16_30, // 3010
            SWEREF_99_18_00, // 3011
            SWEREF_99_14_15, // 3012
            SWEREF_99_15_45, // 3013
            SWEREF_99_17_15, // 3014
            SWEREF_99_18_45, // 3015
            SWEREF_99_20_15, // 3016
            SWEREF_99_21_45, // 3017
            SWEREF_99_23_15, // 3018
            RT90_7_5_GON_V,  // 3019
            RT90_5_0_GON_V,  // 3020
            RT90_2_5_GON_V,  // 3021
            RT90_0_0_GON_V,  // 3022
            RT90_2_5_GON_O,  // 3023
            RT90_5_0_GON_O   // 3024
        );
        assertEquals(
            expectedProjections.size(),
            _allCrsProjections.size()
        );
        for(int i=0; i<_allCrsProjections.size(); i++) {
            CrsProjection actualProjection = _allCrsProjections.get(i);
            CrsProjection expectedProjection = expectedProjections.get(i);
            assertEquals(
                "mismatch at List index i: " + i,
                expectedProjection,
                actualProjection
            );
        }
    }

    @Test
    public void createCoordinate() {
        final double x = 22.5;
        final double y = 62.5;
        CrsCoordinate crsCoordinate = CrsProjection.SWEREF_99_TM.createCoordinate(y, x);
        assertEquals(epsgNumberForSweref99tm, crsCoordinate.getCrsProjection().getEpsgNumber());
        assertEquals(CrsProjection.SWEREF_99_TM, crsCoordinate.getCrsProjection());
        final double delta = 0.000001;
        assertEquals(x, crsCoordinate.getLongitudeX(), delta);
        assertEquals(y, crsCoordinate.getLatitudeY(), delta);
    }
}
