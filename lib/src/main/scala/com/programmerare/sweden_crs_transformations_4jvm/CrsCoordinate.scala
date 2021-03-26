/*
 * Copyright (c) Tomas Johansson , http://www.programmerare.com
 * The code in this library is licensed with MIT.
 * The library is based on the C#.NET library 'sweden_crs_transformations_4net' (https://github.com/TomasJohansson/sweden_crs_transformations_4net)
 * which in turn is based on 'MightyLittleGeodesy' (https://github.com/bjornsallarp/MightyLittleGeodesy/)
 * which is also released with MIT.
 * License information about 'sweden_crs_transformations_4jvm' and 'MightyLittleGeodesy':
 * https://github.com/TomasJohansson/sweden_crs_transformations_4jvm/blob/java_SwedenCrsTransformations/LICENSE
 * For more information see the webpage below.
 * https://github.com/TomasJohansson/sweden_crs_transformations_4jvm
 */
package com.programmerare.sweden_crs_transformations_4jvm

import com.programmerare.sweden_crs_transformations_4jvm.transformation.Transformer

/**
 * Coordinate, defined by the three parameters for the factory methods. 
 */
object CrsCoordinate {
  /**
   * Factory method for creating an instance.
   *
   * @param epsgNumber represents the coordinate reference system that defines the location together with the other two parameters
   * @param yLatitude  the coordinate position value representing the latitude or Y or Northing
   * @param xLongitude the coordinate position value representing the longitude or X or Easting
   * @return an instance of CrsCoordinate 
   */
  def createCoordinate(
    epsgNumber: Int,
    yLatitude: Double,
    xLongitude: Double
  ): CrsCoordinate = {
    val crsProjection = CrsProjection.getCrsProjectionByEpsgNumber(epsgNumber)
    createCoordinate(crsProjection, yLatitude, xLongitude)
  }

  /**
   * Factory method for creating an instance.
   *
   * @param crsProjection represents the coordinate reference system that defines the location together with the other two parameters
   * @param yLatitude     the coordinate position value representing the latitude or Y or Northing
   * @param xLongitude    the coordinate position value representing the longitude or X or Easting
   * @return an instance of CrsCoordinate
   */
  def createCoordinate(
    crsProjection: CrsProjection,
    yLatitude: Double,
    xLongitude: Double
  ) = new CrsCoordinate(crsProjection, yLatitude, xLongitude)
}

/**
 * Class with private constructor. Client code must instead use the public factory methods.
 *
 * @param crsProjection represents the coordinate reference system that defines the location together with the other two parameters
 * @param yLatitude     the coordinate position value representing the latitude or Y or Northing
 * @param xLongitude    the coordinate position value representing the longitude or X or Easting
 */
class CrsCoordinate private(
  private val crsProjection: CrsProjection,
  private val yLatitude: Double,
  private val xLongitude: Double
)
{
 
  /**
   * @return the coordinate reference system that defines the location together with the other two properties (LongitudeX and LatitudeY).
   */
  def getCrsProjection(): CrsProjection = {
    crsProjection
  }

  /**
   * @return the coordinate value representing the longitude or X or Easting.
   */
  def getLongitudeX(): Double = {
    xLongitude
  }

  /**
   * @return the coordinate value representing the latitude or Y or Northing.
   */
  def getLatitudeY(): Double = {
    yLatitude
  }

  /**
   * Transforms the coordinate to another coordinate reference system
   *
   * @param targetCrsProjection the coordinate reference system that you want to transform to
   * @return a new instance representing the transformed coordinate
   */
  def transform(targetCrsProjection: CrsProjection): CrsCoordinate = {
    Transformer.transform(this, targetCrsProjection)
  }

  /**
   * Transforms the coordinate to another coordinate reference system
   *
   * @param targetEpsgNumber the coordinate reference system that you want to transform to
   * @return a new instance representing the transformed coordinate
   */
  def transform(targetEpsgNumber: Int): CrsCoordinate = {
    val targetCrsProjection: CrsProjection = CrsProjection.getCrsProjectionByEpsgNumber(targetEpsgNumber)
    this.transform(targetCrsProjection)
  }

  // ----------------------------------------------------------------------------------------------------------------------
  // These three methods below (i.e. 'canEqual' , 'equals' and 'hashCode') were generated with IntelliJ IDEA 2020.3
  def canEqual(other: Any): Boolean = other.isInstanceOf[CrsCoordinate]

  override def equals(other: Any): Boolean = other match {
    case that: CrsCoordinate =>
      (that canEqual this) &&
        crsProjection == that.crsProjection &&
        yLatitude == that.yLatitude &&
        xLongitude == that.xLongitude
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(crsProjection, yLatitude, xLongitude)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
  // ----------------------------------------------------------------------------------------------------------------------
  
  /**
   * @return Two examples of the string that can be returned:
   *         "CrsCoordinate [ Y: 6579457.649 , X: 153369.673 , CRS: SWEREF_99_18_00(EPSG:3011) ]"
   *         "CrsCoordinate [ Latitude: 59.330231 , Longitude: 18.059196 , CRS: WGS84(EPSG:4326) ]"
   */
  override def toString: String = {
    val crs: String = getCrsProjection.toString.toUpperCase
    val isWgs84: Boolean = getCrsProjection.isWgs84
    val yOrLatitude: String = if (isWgs84) {"Latitude"} else {"Y"}
    val xOrLongitude: String = if (isWgs84) {"Longitude" } else { "X"}
    // https://docs.scala-lang.org/overviews/core/string-interpolation.html
    s"CrsCoordinate [ $yOrLatitude: $getLatitudeY , $xOrLongitude: $getLongitudeX , CRS: $crs ]"
  }

}
