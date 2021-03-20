package com.programmerare.sweden_crs_transformations_4jvm

import JavaCrsProjectionTest.numberOfSweref99projections
import JavaCrsProjectionTest.numberOfWgs84Projections
import JavaCrsProjectionTest.numberOfRT90projections
import JavaCrsProjectionTest.totalNumberOfProjections
import JavaCrsProjectionTest.epsgNumberForWgs84
import JavaCrsProjectionTest.epsgNumberForSweref99tm
import org.junit.Assert._
import scala.collection.immutable.HashSet
import com.programmerare.sweden_crs_transformations_4jvm.CrsProjection._ // WGS84 , SWEREF_99_TM, ...
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import scala.jdk.CollectionConverters._ // asScala

class ScalaCrsProjectionTest {

  private var _wgs84Projections     = new HashSet[CrsProjection]()
  private var _sweref99Projections  = new HashSet[CrsProjection]()
  private var _rt90Projections      = new HashSet[CrsProjection]()

  private var _allCrsProjections: List[CrsProjection] = List()
  
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

    _allCrsProjections = CrsProjection.getAllCrsProjections().asScala.toList
  }

  @Test
  def getEpsgNumber(): Unit = {
    assertEquals(
      epsgNumberForSweref99tm,
      CrsProjection.SWEREF_99_TM.getEpsgNumber
    )
    assertEquals(
      epsgNumberForWgs84,
      CrsProjection.WGS84.getEpsgNumber
    )
  }

  @Test
  def isWgs84(): Unit = {
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

  @Test
  def isSweref(): Unit = {
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

  @Test
  def isRT90(): Unit = {
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

  @Test
  def toStringTest(): Unit = {
    assertEquals("WGS84",           CrsProjection.WGS84.toString)
    assertEquals("SWEREF_99_TM",    CrsProjection.SWEREF_99_TM.toString)
    assertEquals("SWEREF_99_14_15", CrsProjection.SWEREF_99_14_15.toString)
    assertEquals("RT90_0_0_GON_V",  CrsProjection.RT90_0_0_GON_V.toString)
  }

  @Test
  def getCrsProjectionByEpsgNumber(): Unit = {
    assertEquals(
      CrsProjection.SWEREF_99_TM,
      CrsProjection.getCrsProjectionByEpsgNumber(epsgNumberForSweref99tm)
    )

    assertEquals(
      CrsProjection.SWEREF_99_23_15,
      CrsProjection.getCrsProjectionByEpsgNumber(3018) // https://epsg.io/3018)
    )

    assertEquals(
      CrsProjection.RT90_5_0_GON_O,
      CrsProjection.getCrsProjectionByEpsgNumber(3024) // https://epsg.io/3024)
    )
  }

  @Test
  def verifyTotalNumberOfProjections(): Unit = {
    assertEquals(totalNumberOfProjections, _allCrsProjections.size) // retrieved with 'getAllCrsProjections' in the setUp ('@Before' annotated) method)
  }

  @Test
  def verifyNumberOfWgs84Projections(): Unit = {
    assertEquals(numberOfWgs84Projections, getNumberOfProjections(crs => crs.isWgs84))
  }

  @Test
  def verifyNumberOfSweref99Projections(): Unit = {
    assertEquals(numberOfSweref99projections, getNumberOfProjections(crs => crs.isSweref))
  }

  @Test
  def verifyNumberOfRT90Projections(): Unit = {
    assertEquals(numberOfRT90projections, getNumberOfProjections(crs => crs.isRT90))
  }

  private def getNumberOfProjections(predicate: CrsProjection => Boolean): Int = {
    _allCrsProjections.filter(predicate).size
  }

  @Test
  def verifyThatAllProjectionsCanBeRetrievedByItsEpsgNumber(): Unit = {
    for (crsProjection <- _allCrsProjections) {
      val crsProj: CrsProjection = CrsProjection.getCrsProjectionByEpsgNumber(crsProjection.getEpsgNumber)
      assertEquals(crsProjection, crsProj)
    }
  }

  @Test
  def verifyOrderOfProjectionsInEnum(): Unit = {
    val expectedProjections: List[CrsProjection] = List(
      // the below items are from the enum 'CrsProjection' i.e. they are imported with "import com.programmerare.sweden_crs_transformations_4jvm.CrsProjection._"
      WGS84, // 4326
      // the first item should be WGS84 (as above, but then below they are expected to be ordered by increasing EPSG number, i.e. from low to high) 
      SWEREF_99_TM, // 3006  // national sweref99 CRS
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
      RT90_7_5_GON_V, // 3019
      RT90_5_0_GON_V, // 3020
      RT90_2_5_GON_V, // 3021
      RT90_0_0_GON_V, // 3022
      RT90_2_5_GON_O, // 3023
      RT90_5_0_GON_O // 3024)
    )
    assertEquals(expectedProjections.size, _allCrsProjections.size)
    for (i <- _allCrsProjections.indices) {
      val actualProjection: CrsProjection = _allCrsProjections(i)
      val expectedProjection: CrsProjection = expectedProjections(i)
      assertEquals("mismatch at List index i: " + i, expectedProjection, actualProjection)
    }
  }

}
