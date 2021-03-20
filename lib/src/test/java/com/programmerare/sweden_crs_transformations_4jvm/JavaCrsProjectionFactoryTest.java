package com.programmerare.sweden_crs_transformations_4jvm;

import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import static org.junit.Assert.assertEquals;

// TODO move these CrsProjectionFactoryTest tests into CrsProjectionTest since the methods of CrsProjectionFactory has been moved into CrsProjection 

public class JavaCrsProjectionFactoryTest
{

    public final static int epsgNumberForWgs84 = 4326;
    public final static int epsgNumberForSweref99tm = 3006; // https://epsg.org/crs_3006/SWEREF99-TM.html
    public final static int numberOfSweref99projections = 13; // with EPSG numbers 3006-3018
    public final static int numberOfRT90projections = 6; // with EPSG numbers 3019-3024
    public final static int numberOfWgs84Projections = 1; // just to provide semantic instead of using a magic number 1 below
    private final static int totalNumberOfProjections = numberOfSweref99projections + numberOfRT90projections + numberOfWgs84Projections;

    private List<CrsProjection> _allCrsProjections;

    @Before
    public void setUp() {
        _allCrsProjections = CrsProjection.getAllCrsProjections();
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
           getNumberOfProjections(CrsProjection::isWgs84) //  crs -> crs.isWgs84()
       );
    }
    @Test
    public void verifyNumberOfSweref99Projections() {
        assertEquals(
            numberOfSweref99projections,
            getNumberOfProjections(CrsProjection::isSweref) // crs -> crs.isSweref()
        );            
    }
    @Test
    public void verifyNumberOfRT90Projections() {
        assertEquals(
            numberOfRT90projections,
            getNumberOfProjections(CrsProjection::isRT90) // crs -> crs.isRT90()
        );
    }
    private int getNumberOfProjections(Predicate<CrsProjection> predicate) {
        //    return _allCrsProjections.stream()
        //        .filter(predicate) // .filter(predicate::test)  or  .filter(crs -> predicate.test(crs))
        //        .collect(Collectors.toList())
        //        .size();
        // The below row is a shorter alternative to the above implementation
        return (int) _allCrsProjections.stream().filter(predicate).count();
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
            CrsProjection.WGS84,           // 4326
            // the first item should be WGS84 (as above, but then below they are expected to be ordered by increasing EPSG number, i.e. from low to high) 
            CrsProjection.SWEREF_99_TM,    // 3006  // national sweref99 CRS
            CrsProjection.SWEREF_99_12_00, // 3007
            CrsProjection.SWEREF_99_13_30, // 3008
            CrsProjection.SWEREF_99_15_00, // 3009
            CrsProjection.SWEREF_99_16_30, // 3010
            CrsProjection.SWEREF_99_18_00, // 3011
            CrsProjection.SWEREF_99_14_15, // 3012
            CrsProjection.SWEREF_99_15_45, // 3013
            CrsProjection.SWEREF_99_17_15, // 3014
            CrsProjection.SWEREF_99_18_45, // 3015
            CrsProjection.SWEREF_99_20_15, // 3016
            CrsProjection.SWEREF_99_21_45, // 3017
            CrsProjection.SWEREF_99_23_15, // 3018
            CrsProjection.RT90_7_5_GON_V,  // 3019
            CrsProjection.RT90_5_0_GON_V,  // 3020
            CrsProjection.RT90_2_5_GON_V,  // 3021
            CrsProjection.RT90_0_0_GON_V,  // 3022
            CrsProjection.RT90_2_5_GON_O,  // 3023
            CrsProjection.RT90_5_0_GON_O   // 3024
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

}
