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
package com.programmerare.sweden_crs_transformations_4jvm.transformation

import com.programmerare.sweden_crs_transformations_4jvm.{CrsCoordinate, CrsProjection}
import com.programmerare.sweden_crs_transformations_4jvm.mighty_little_geodesy.{GaussKreuger, GaussKreugerFactory, LatLon}

object TransformStrategy_from_SWEREF99_or_RT90_to_WGS84 {
  private val _transformStrategy = new TransformStrategy_from_SWEREF99_or_RT90_to_WGS84
  def getInstance(): TransformStrategy = _transformStrategy  
}
/**
 * TransformStrategy implementation that should be used when transforming 
 * from either SWEREF99 or RT90, to WGS84. 
 */
final class TransformStrategy_from_SWEREF99_or_RT90_to_WGS84 private()
  extends TransformStrategyBase
  with TransformStrategy
{
  // Preconditions:
  // sourceProjection must be CRS SWEREF99 or RT90
  // targetProjection must be CRS WGS84
  override def transform(
    sourceCoordinate: CrsCoordinate,
    targetCrsProjection: CrsProjection
  ): CrsCoordinate = {
    val sourceCoordinateProjection = sourceCoordinate.getCrsProjection
    super.assertCoordinateProjections(
      sourceCoordinateProjection,
      sourceCoordinateProjection.isRT90 || sourceCoordinateProjection.isSweRef99,
      this,
      targetCrsProjection,
      targetCrsProjection.isWgs84
    )
    
    val gkProjection = GaussKreugerFactory.getInstance.getGaussKreuger(sourceCoordinate.getCrsProjection)
    val latLon = gkProjection.grid_to_geodetic(sourceCoordinate.getLatitudeY, sourceCoordinate.getLongitudeX)
    CrsCoordinate.createCoordinate(targetCrsProjection, latLon.LatitudeY, latLon.LongitudeX)
  }
}
