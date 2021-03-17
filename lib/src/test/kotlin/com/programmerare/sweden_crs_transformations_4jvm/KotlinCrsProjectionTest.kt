package com.programmerare.sweden_crs_transformations_4jvm

import com.programmerare.sweden_crs_transformations_4jvm.CrsProjectionFactoryTest.numberOfSweref99projections
import com.programmerare.sweden_crs_transformations_4jvm.CrsProjectionFactoryTest.numberOfWgs84Projections
import com.programmerare.sweden_crs_transformations_4jvm.CrsProjectionFactoryTest.numberOfRT90projections
import com.programmerare.sweden_crs_transformations_4jvm.CrsProjectionFactoryTest.epsgNumberForWgs84
import com.programmerare.sweden_crs_transformations_4jvm.CrsProjectionFactoryTest.epsgNumberForSweref99tm
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
        _wgs84Projections = HashSet(listOf(wgs84))
        
        _sweref99Projections = HashSet(
            listOf(
                sweref_99_12_00,
                sweref_99_13_30,
                sweref_99_14_15,
                sweref_99_15_00,
                sweref_99_15_45,
                sweref_99_16_30,
                sweref_99_17_15,
                sweref_99_18_00,
                sweref_99_18_45,
                sweref_99_20_15,
                sweref_99_21_45,
                sweref_99_23_15,
                sweref_99_tm
            )
        )
        _rt90Projections = HashSet(
            listOf(
                rt90_0_0_gon_v,
                rt90_2_5_gon_o,
                rt90_2_5_gon_v,
                rt90_5_0_gon_o,
                rt90_5_0_gon_v,
                rt90_7_5_gon_v
            )
        )
    }

    @Test
    fun getEpsgNumber() {
        Assert.assertEquals(
            epsgNumberForSweref99tm,  // constant defined in CrsProjectionFactoryTest
            CrsProjection.sweref_99_tm.epsgNumber
        )
        Assert.assertEquals(
            epsgNumberForWgs84,  // constant defined in CrsProjectionFactoryTest
            CrsProjection.wgs84.epsgNumber
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
            CrsProjection.wgs84.asString
        )
        Assert.assertEquals(
            "SWEREF_99_TM",
            CrsProjection.sweref_99_tm.asString
        )
        Assert.assertEquals(
            "SWEREF_99_14_15",
            CrsProjection.sweref_99_14_15.asString
        )
        Assert.assertEquals(
            "RT90_0_0_GON_V",
            CrsProjection.rt90_0_0_gon_v.asString
        )
    }

}
