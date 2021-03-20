package com.programmerare.sweden_crs_transformations_4jvm

import com.programmerare.sweden_crs_transformations_4jvm.JavaCrsProjectionFactoryTest.numberOfSweref99projections
import com.programmerare.sweden_crs_transformations_4jvm.JavaCrsProjectionFactoryTest.numberOfWgs84Projections
import com.programmerare.sweden_crs_transformations_4jvm.JavaCrsProjectionFactoryTest.numberOfRT90projections
import com.programmerare.sweden_crs_transformations_4jvm.JavaCrsProjectionFactoryTest.epsgNumberForWgs84
import com.programmerare.sweden_crs_transformations_4jvm.JavaCrsProjectionFactoryTest.epsgNumberForSweref99tm
import com.programmerare.sweden_crs_transformations_4jvm.CrsProjection.* // wgs84 , sweref_99_tm , and so on
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class KotlinCrsProjectionTest {
    private var _wgs84Projections       = HashSet<CrsProjection>()
    private var _sweref99Projections    = HashSet<CrsProjection>()
    private var _rt90Projections        = HashSet<CrsProjection>()

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
    }

    @Test
    fun getEpsgNumber() {
        Assert.assertEquals(
            epsgNumberForSweref99tm,  // constant defined in JavaCrsProjectionFactoryTest
            CrsProjection.SWEREF_99_TM.epsgNumber
        )
        Assert.assertEquals(
            epsgNumberForWgs84,  // constant defined in JavaCrsProjectionFactoryTest
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
    fun getAsString() {
        Assert.assertEquals(
            "WGS84",
            CrsProjection.WGS84.asString
        )
        Assert.assertEquals(
            "SWEREF_99_TM",
            CrsProjection.SWEREF_99_TM.asString
        )
        Assert.assertEquals(
            "SWEREF_99_14_15",
            CrsProjection.SWEREF_99_14_15.asString
        )
        Assert.assertEquals(
            "RT90_0_0_GON_V",
            CrsProjection.RT90_0_0_GON_V.asString
        )
    }

}
