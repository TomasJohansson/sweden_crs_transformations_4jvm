package com.programmerare.sweden_crs_transformations_4jvm

import com.programmerare.sweden_crs_transformations_4jvm.CrsProjection.* // wgs84 , sweref_99_tm, ...
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class KotlinCrsProjectionFactoryTest {
    private val epsgNumberForSweref99tm = 3006 // https://epsg.org/crs_3006/SWEREF99-TM.html
    
    private val numberOfSweref99projections = 13 // with EPSG numbers 3006-3018
    private val numberOfRT90projections = 6 // with EPSG numbers 3019-3024
    private val numberOfWgs84Projections = 1 // just to provide semantic instead of using a magic number 1 below
    private val totalNumberOfProjections = numberOfSweref99projections + numberOfRT90projections + numberOfWgs84Projections
    
    private var _allCrsProjections: List<CrsProjection> = listOf()

    @Before
    fun setUp() {
        _allCrsProjections = CrsProjectionFactory.getAllCrsProjections().toList()
    }

    @Test
    fun getCrsProjectionByEpsgNumber() {
        assertEquals(
            CrsProjection.sweref_99_tm,
            CrsProjectionFactory.getCrsProjectionByEpsgNumber(epsgNumberForSweref99tm)
        )
        assertEquals(
            CrsProjection.sweref_99_23_15,
            CrsProjectionFactory.getCrsProjectionByEpsgNumber(3018) // https://epsg.io/3018
        )
        assertEquals(
            CrsProjection.rt90_5_0_gon_o,
            CrsProjectionFactory.getCrsProjectionByEpsgNumber(3024) // https://epsg.io/3024
        )
    }

    @Test
    fun verifyTotalNumberOfProjections() {
        assertEquals(
            totalNumberOfProjections,
            _allCrsProjections.size // retrieved with 'getAllCrsProjections' in the setUp ('@Before' annotated) method
        )
    }

    @Test
    fun verifyNumberOfWgs84Projections() {
        assertEquals(
            numberOfWgs84Projections,
            getNumberOfProjections { crs: CrsProjection -> crs.isWgs84 }
        )
    }

    @Test
    fun verifyNumberOfSweref99Projections() {
        assertEquals(
            numberOfSweref99projections,
            getNumberOfProjections { crs: CrsProjection -> crs.isSweref }
        )
    }

    @Test
    fun verifyNumberOfRT90Projections() {
        assertEquals(
            numberOfRT90projections,
            getNumberOfProjections { crs: CrsProjection -> crs.isRT90 }
        )
    }

    private fun getNumberOfProjections(
        predicate: (CrsProjection) -> Boolean
    ): Int {
        return _allCrsProjections.filter(predicate).count()
    }

    @Test
    fun verifyThatAllProjectionsCanBeRetrievedByItsEpsgNumber() {
        for (crsProjection in _allCrsProjections) {
            val crsProj: CrsProjection = CrsProjectionFactory.getCrsProjectionByEpsgNumber(crsProjection.epsgNumber)
            assertEquals(crsProjection, crsProj)
        }
    }

    @Test
    fun verifyOrderOfProjectionsInEnum() {
        val expectedProjections: List<CrsProjection> = listOf(
            // the below items are from the enum 'CrsProjection' i.e. they are imported with "import com.programmerare.sweden_crs_transformations_4jvm.CrsProjection.*"  
            wgs84,  // 4326
            // the first item should be WGS84 (as above, but then below they are expected to be ordered by increasing EPSG number, i.e. from low to high) 
            sweref_99_tm,  // 3006  // national sweref99 CRS
            sweref_99_12_00,  // 3007
            sweref_99_13_30,  // 3008
            sweref_99_15_00,  // 3009
            sweref_99_16_30,  // 3010
            sweref_99_18_00,  // 3011
            sweref_99_14_15,  // 3012
            sweref_99_15_45,  // 3013
            sweref_99_17_15,  // 3014
            sweref_99_18_45,  // 3015
            sweref_99_20_15,  // 3016
            sweref_99_21_45,  // 3017
            sweref_99_23_15,  // 3018
            rt90_7_5_gon_v,  // 3019
            rt90_5_0_gon_v,  // 3020
            rt90_2_5_gon_v,  // 3021
            rt90_0_0_gon_v,  // 3022
            rt90_2_5_gon_o,  // 3023
            rt90_5_0_gon_o // 3024
        )
        assertEquals(
            expectedProjections.size,
            _allCrsProjections.size
        )
        for (i in _allCrsProjections.indices) {
            val actualProjection: CrsProjection = _allCrsProjections.get(i)
            val expectedProjection = expectedProjections[i]
            assertEquals(
                "mismatch at List index i: " + i,
                expectedProjection,
                actualProjection
            )
        }
    }

}
