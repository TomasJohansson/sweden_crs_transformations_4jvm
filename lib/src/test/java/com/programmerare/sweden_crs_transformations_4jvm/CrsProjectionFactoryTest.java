package com.programmerare.sweden_crs_transformations_4jvm;

import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.Assert.assertEquals;

public class CrsProjectionFactoryTest {

    public final static int epsgNumberForWgs84 = 4326;
    public final static int epsgNumberForSweref99tm = 3006; // https://epsg.org/crs_3006/SWEREF99-TM.html
    public final static int numberOfSweref99projections = 13; // with EPSG numbers 3006-3018
    public final static int numberOfRT90projections = 6; // with EPSG numbers 3019-3024
    public final static int numberOfWgs84Projections = 1; // just to provide semantic instead of using a magic number 1 below
    private final static int totalNumberOfProjections = numberOfSweref99projections + numberOfRT90projections + numberOfWgs84Projections;

    private List<CrsProjection> _allCrsProjections;

    @Before
    public void setUp() {
        _allCrsProjections = CrsProjectionFactory.getAllCrsProjections();;
    }

    @Test
    public void getCrsProjectionByEpsgNumber() {
        assertEquals(
            CrsProjection.sweref_99_tm,
            CrsProjectionFactory.getCrsProjectionByEpsgNumber(epsgNumberForSweref99tm)
        );

        assertEquals(
            CrsProjection.sweref_99_23_15,
            CrsProjectionFactory.getCrsProjectionByEpsgNumber(3018) // https://epsg.io/3018
        );

        assertEquals(
            CrsProjection.rt90_5_0_gon_o,
            CrsProjectionFactory.getCrsProjectionByEpsgNumber(3024)  // https://epsg.io/3018
        );
    }

    @Test
    public void verifyTotalNumberOfProjections() {
        assertEquals(
            totalNumberOfProjections,
            _allCrsProjections.size() // retrieved with 'GetAllCrsProjections' in the SetUp method
        );
    }

    @Test
    public void verifyNumberOfWgs84Projections() {
        // C#.NET
        // assertEquals(numberOfWgs84Projections, _allCrsProjections.Where(crs => crs.IsWgs84()).Count());
       assertEquals(
           numberOfWgs84Projections,
           
           _allCrsProjections.stream()
               .filter(crs -> crs.isWgs84())
               .collect(Collectors.toList())
               .size()
       );
    }
    // TODO refactor duplication in above and below methods (stream ... Collectors)
    @Test
    public void verifyNumberOfSweref99Projections() {
        // C#.NET
        // assertEquals(numberOfSweref99projections, _allCrsProjections.Where(crs => crs.IsSweref()).Count());
        assertEquals(
            numberOfSweref99projections,

            _allCrsProjections.stream()
                .filter(crs -> crs.isSweref())
                .collect(Collectors.toList())
                .size()
        );            
    }
    // TODO refactor duplication in above and below methods (stream ... Collectors)
    @Test
    public void verifyNumberOfRT90Projections() {
        // C#.NET
        // assertEquals(numberOfRT90projections, _allCrsProjections.Where(crs => crs.IsRT90()).Count());
        assertEquals(
            numberOfRT90projections,

            _allCrsProjections.stream()
                .filter(crs -> crs.isRT90())
                .collect(Collectors.toList())
                .size()
        );
    }

    @Test
    public void verifyThatAllProjectionsCanBeRetrievedByItsEpsgNumber() {
        for(CrsProjection crsProjection : _allCrsProjections) {
            CrsProjection crsProj = CrsProjectionFactory.getCrsProjectionByEpsgNumber(crsProjection.getEpsgNumber());
            assertEquals(crsProjection, crsProj);
        }
    }    

}
