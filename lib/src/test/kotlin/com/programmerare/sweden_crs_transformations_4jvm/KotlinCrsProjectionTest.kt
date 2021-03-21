package com.programmerare.sweden_crs_transformations_4jvm

import com.programmerare.sweden_crs_transformations_4jvm.JavaCrsProjectionTest.numberOfSweref99projections
import com.programmerare.sweden_crs_transformations_4jvm.JavaCrsProjectionTest.numberOfWgs84Projections
import com.programmerare.sweden_crs_transformations_4jvm.JavaCrsProjectionTest.numberOfRT90projections
import com.programmerare.sweden_crs_transformations_4jvm.JavaCrsProjectionTest.totalNumberOfProjections
import com.programmerare.sweden_crs_transformations_4jvm.JavaCrsProjectionTest.epsgNumberForWgs84
import com.programmerare.sweden_crs_transformations_4jvm.JavaCrsProjectionTest.epsgNumberForSweref99tm
import com.programmerare.sweden_crs_transformations_4jvm.CrsProjection.* // WGS84 , SWEREF_99_TM, ...
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class KotlinCrsProjectionTest {
    private var _wgs84Projections       = HashSet<CrsProjection>()
    private var _sweref99Projections    = HashSet<CrsProjection>()
    private var _rt90Projections        = HashSet<CrsProjection>()

    private var _allCrsProjections: List<CrsProjection> = listOf()
    
    @Before
    fun setUp() {
        _wgs84Projections = HashSet(listOf(WGS84))
        
        _sweref99Projections = HashSet(
            listOf(
                SWEREF_99_12_00,
                SWEREF_99_13_30,
                SWEREF_99_14_15,
                SWEREF_99_15_00,
                SWEREF_99_15_45,
                SWEREF_99_16_30,
                SWEREF_99_17_15,
                SWEREF_99_18_00,
                SWEREF_99_18_45,
                SWEREF_99_20_15,
                SWEREF_99_21_45,
                SWEREF_99_23_15,
                SWEREF_99_TM
            )
        )
        _rt90Projections = HashSet(
            listOf(
                RT90_0_0_GON_V,
                RT90_2_5_GON_O,
                RT90_2_5_GON_V,
                RT90_5_0_GON_O,
                RT90_5_0_GON_V,
                RT90_7_5_GON_V
            )
        )

        _allCrsProjections = CrsProjection.getAllCrsProjections().toList()
    }

    @Test
    fun getEpsgNumber() {
        Assert.assertEquals(
            epsgNumberForSweref99tm,
            CrsProjection.SWEREF_99_TM.epsgNumber
        )
        Assert.assertEquals(
            epsgNumberForWgs84,
            CrsProjection.WGS84.epsgNumber
        )
    }


    @Test
    fun isWgs84() {
        Assert.assertEquals(
            numberOfWgs84Projections,
            _wgs84Projections.size
        )
        for (item in _wgs84Projections) {
            Assert.assertTrue(item.isWgs84)
        }
        for (item in _sweref99Projections) {
            Assert.assertFalse(item.isWgs84)
        }
        for (item in _rt90Projections) {
            Assert.assertFalse(item.isWgs84)
        }
    }

    @Test
    fun isSweref() {
        Assert.assertEquals(
            numberOfSweref99projections,
            _sweref99Projections.size
        )
        for (item in _wgs84Projections) {
            Assert.assertFalse(item.isSweref)
        }
        for (item in _sweref99Projections) {
            Assert.assertTrue(item.isSweref)
        }
        for (item in _rt90Projections) {
            Assert.assertFalse(item.isSweref)
        }
    }

    @Test
    fun isRT90() {
        Assert.assertEquals(
            numberOfRT90projections,
            _rt90Projections.size
        )
        for (item in _wgs84Projections) {
            Assert.assertFalse(item.isRT90)
        }
        for (item in _sweref99Projections) {
            Assert.assertFalse(item.isRT90)
        }
        for (item in _rt90Projections) {
            Assert.assertTrue(item.isRT90)
        }
    }

    @Test
    fun toStringTest() {
        Assert.assertEquals(
            "WGS84(EPSG:4326)",
            CrsProjection.WGS84.toString()
        )
        Assert.assertEquals(
            "SWEREF_99_TM(EPSG:3006)",
            CrsProjection.SWEREF_99_TM.toString()
        )
        Assert.assertEquals(
            "SWEREF_99_14_15(EPSG:3012)",
            CrsProjection.SWEREF_99_14_15.toString()
        )
        Assert.assertEquals(
            "RT90_0_0_GON_V(EPSG:3022)",
            CrsProjection.RT90_0_0_GON_V.toString()
        )
    }

    @Test
    fun getCrsProjectionByEpsgNumber() {
        Assert.assertEquals(
            SWEREF_99_TM,
            getCrsProjectionByEpsgNumber(epsgNumberForSweref99tm)
        )
        Assert.assertEquals(
            SWEREF_99_23_15,
            getCrsProjectionByEpsgNumber(3018) // https://epsg.io/3018
        )
        Assert.assertEquals(
            RT90_5_0_GON_O,
            getCrsProjectionByEpsgNumber(3024) // https://epsg.io/3024
        )
    }

    @Test
    fun verifyTotalNumberOfProjections() {
        Assert.assertEquals(
            totalNumberOfProjections,
            _allCrsProjections.size // retrieved with 'getAllCrsProjections' in the setUp ('@Before' annotated) method
        )
    }

    @Test
    fun verifyNumberOfWgs84Projections() {
        Assert.assertEquals(
            numberOfWgs84Projections,
            getNumberOfProjections { crs: CrsProjection -> crs.isWgs84 }
        )
    }

    @Test
    fun verifyNumberOfSweref99Projections() {
        Assert.assertEquals(
            numberOfSweref99projections,
            getNumberOfProjections { crs: CrsProjection -> crs.isSweref }
        )
    }

    @Test
    fun verifyNumberOfRT90Projections() {
        Assert.assertEquals(
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
            val crsProj: CrsProjection = CrsProjection.getCrsProjectionByEpsgNumber(crsProjection.epsgNumber)
            Assert.assertEquals(crsProjection, crsProj)
        }
    }

    @Test
    fun verifyOrderOfProjectionsInEnum() {
        val expectedProjections: List<CrsProjection> = listOf(
            // the below items are from the enum 'CrsProjection' i.e. they are imported with "import com.programmerare.sweden_crs_transformations_4jvm.CrsProjection.*"  
            WGS84,  // 4326
            // the first item should be WGS84 (as above, but then below they are expected to be ordered by increasing EPSG number, i.e. from low to high) 
            SWEREF_99_TM,  // 3006  // national sweref99 CRS
            SWEREF_99_12_00,  // 3007
            SWEREF_99_13_30,  // 3008
            SWEREF_99_15_00,  // 3009
            SWEREF_99_16_30,  // 3010
            SWEREF_99_18_00,  // 3011
            SWEREF_99_14_15,  // 3012
            SWEREF_99_15_45,  // 3013
            SWEREF_99_17_15,  // 3014
            SWEREF_99_18_45,  // 3015
            SWEREF_99_20_15,  // 3016
            SWEREF_99_21_45,  // 3017
            SWEREF_99_23_15,  // 3018
            RT90_7_5_GON_V,  // 3019
            RT90_5_0_GON_V,  // 3020
            RT90_2_5_GON_V,  // 3021
            RT90_0_0_GON_V,  // 3022
            RT90_2_5_GON_O,  // 3023
            RT90_5_0_GON_O // 3024
        )
        Assert.assertEquals(
            expectedProjections.size,
            _allCrsProjections.size
        )
        for (i in _allCrsProjections.indices) {
            val actualProjection: CrsProjection = _allCrsProjections.get(i)
            val expectedProjection = expectedProjections[i]
            Assert.assertEquals(
                "mismatch at List index i: " + i,
                expectedProjection,
                actualProjection
            )
        }
    }

}
