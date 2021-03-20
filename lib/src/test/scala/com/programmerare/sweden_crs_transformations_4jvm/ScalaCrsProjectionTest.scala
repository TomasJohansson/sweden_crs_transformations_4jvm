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
    _wgs84Projections     = HashSet(CrsProjection.WGS84)
    
    _sweref99Projections  = HashSet(
                              SWEREF_99_TM,
                              SWEREF_99_12_00, SWEREF_99_13_30, SWEREF_99_14_15,
                              SWEREF_99_15_00, SWEREF_99_15_45, SWEREF_99_16_30,
                              SWEREF_99_17_15, SWEREF_99_18_00, SWEREF_99_18_45,
                              SWEREF_99_20_15, SWEREF_99_21_45, SWEREF_99_23_15
                            )
    
    _rt90Projections      = HashSet(
                              RT90_0_0_GON_V, RT90_2_5_GON_O, RT90_2_5_GON_V,
                              RT90_5_0_GON_O, RT90_5_0_GON_V, RT90_7_5_GON_V
                            )
  }

  @Test def getEpsgNumber(): Unit = {
    assertEquals(
      epsgNumberForSweref99tm, // constant defined in JavaCrsProjectionFactoryTest
      CrsProjection.SWEREF_99_TM.getEpsgNumber
    )
    assertEquals(
      epsgNumberForWgs84, // constant defined in JavaCrsProjectionFactoryTest
      CrsProjection.WGS84.getEpsgNumber
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
    assertEquals("WGS84",           CrsProjection.WGS84.getAsString)
    assertEquals("SWEREF_99_TM",    CrsProjection.SWEREF_99_TM.getAsString)
    assertEquals("SWEREF_99_14_15", CrsProjection.SWEREF_99_14_15.getAsString)
    assertEquals("RT90_0_0_GON_V",  CrsProjection.RT90_0_0_GON_V.getAsString)
  }
}
