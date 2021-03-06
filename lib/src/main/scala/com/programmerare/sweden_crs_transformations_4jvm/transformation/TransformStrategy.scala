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

/**
 * Defines a method for transforming a coordinate to another coordinate reference system.
 *
 * @see CrsCoordinate
 * @see CrsProjection
 */
trait TransformStrategy {
  /**
   * Transforms a coordinate from one CRS (Coordinate Reference System) to another CRS.
   *
   * @param sourceCoordinate    the source coordinate, i.e. X/Y values and a CRS.
   * @param targetCrsProjection the target CRS
   * @return a new coordinate instance representing the source coordinate in the target CRS 
   * @see CrsCoordinate
   * @see CrsProjection
   */
  def transform(
    sourceCoordinate: CrsCoordinate,
    targetCrsProjection: CrsProjection
  ): CrsCoordinate
}
