package com.programmerare.sweden_crs_transformations_4jvm

import JavaCrsProjectionFactoryTest.numberOfSweref99projections
import JavaCrsProjectionFactoryTest.numberOfWgs84Projections
import JavaCrsProjectionFactoryTest.numberOfRT90projections
import JavaCrsProjectionFactoryTest.epsgNumberForWgs84
import JavaCrsProjectionFactoryTest.epsgNumberForSweref99tm
import com.programmerare.sweden_crs_transformations_4jvm.CrsProjection._
import org.junit.Before
import org.junit.Test
import org.junit.Assert._
import scala.collection.immutable.HashSet

class ScalaCrsProjectionTest {

  private var _wgs84Projections     = new HashSet[CrsProjection]()
  private var _sweref99Projections  = new HashSet[CrsProjection]()
  private var _rt90Projections      = new HashSet[CrsProjection]()

  @Before def setUp(): Unit = {
    _wgs84Projections     = HashSet(CrsProjection.wgs84)
    
    _sweref99Projections  = HashSet(
                              sweref_99_tm,
                              sweref_99_12_00, sweref_99_13_30, sweref_99_14_15,
                              sweref_99_15_00, sweref_99_15_45, sweref_99_16_30,
                              sweref_99_17_15, sweref_99_18_00, sweref_99_18_45,
                              sweref_99_20_15, sweref_99_21_45, sweref_99_23_15
                            )
    
    _rt90Projections      = HashSet(
                              rt90_0_0_gon_v, rt90_2_5_gon_o, rt90_2_5_gon_v,
                              rt90_5_0_gon_o, rt90_5_0_gon_v, rt90_7_5_gon_v
                            )
  }

  @Test def getEpsgNumber(): Unit = {
    assertEquals(
      epsgNumberForSweref99tm, // constant defined in JavaCrsProjectionFactoryTest
      CrsProjection.sweref_99_tm.getEpsgNumber
    )
    assertEquals(
      epsgNumberForWgs84, // constant defined in JavaCrsProjectionFactoryTest
      CrsProjection.wgs84.getEpsgNumber
    )
  }

  @Test def isWgs84(): Unit = {
    assertEquals(numberOfWgs84Projections, _wgs84Projections.size)
    
    for (item <- _wgs84Projections) {
      assertTrue(item.isWgs84)
    }
    for (item <- _sweref99Projections) {
      assertFalse(item.isWgs84)
    }
    for (item <- _rt90Projections) {
      assertFalse(item.isWgs84)
    }
  }

  @Test def isSweref(): Unit = {
    assertEquals(numberOfSweref99projections, _sweref99Projections.size)
    
    for (item <- _wgs84Projections) {
      assertFalse(item.isSweref)
    }
    for (item <- _sweref99Projections) {
      assertTrue(item.isSweref)
    }
    for (item <- _rt90Projections) {
      assertFalse(item.isSweref)
    }
  }

  @Test def isRT90(): Unit = {
    assertEquals(numberOfRT90projections, _rt90Projections.size)

    for (item <- _wgs84Projections) {
      assertFalse(item.isRT90)
    }
    for (item <- _sweref99Projections) {
      assertFalse(item.isRT90)
    }
    for (item <- _rt90Projections) {
      assertTrue(item.isRT90)
    }
  }

  @Test def getAsString(): Unit = {
    assertEquals("WGS84",           CrsProjection.wgs84.getAsString)
    assertEquals("SWEREF_99_TM",    CrsProjection.sweref_99_tm.getAsString)
    assertEquals("SWEREF_99_14_15", CrsProjection.sweref_99_14_15.getAsString)
    assertEquals("RT90_0_0_GON_V",  CrsProjection.rt90_0_0_gon_v.getAsString)
  }
}
