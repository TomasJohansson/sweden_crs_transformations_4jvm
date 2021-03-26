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
package com.programmerare.sweden_crs_transformations_4jvm.mighty_little_geodesy

import com.programmerare.sweden_crs_transformations_4jvm.CrsProjection

/**
 * This class 'GaussKreugerFactory' was not part of the original 'MightyLittleGeodesy' library. 
 */
object GaussKreugerFactory {
  private val _gaussKreugerFactory = new GaussKreugerFactory

  def getInstance(): GaussKreugerFactory = _gaussKreugerFactory
}

class GaussKreugerFactory private() {
  
  final private lazy val mapWithAllGaussKreugers: Map[CrsProjection, GaussKreuger] = {
    val map = scala.collection.mutable.Map[CrsProjection, GaussKreuger]()
    val crsProjections: Array[CrsProjection] = CrsProjection.values
    for (crsProjection <- crsProjections) {
      val gaussKreugerParameterObject = new GaussKreugerParameterObject(crsProjection)
      val gaussKreuger = GaussKreuger.create(gaussKreugerParameterObject)
      map(crsProjection) = gaussKreuger
    }
    map.toMap
  }

  def getGaussKreuger(crsProjection: CrsProjection): GaussKreuger = {
    if (mapWithAllGaussKreugers.contains(crsProjection)) return mapWithAllGaussKreugers(crsProjection)
    throw new IllegalArgumentException("Could not find GaussKreuger for crsProjection " + crsProjection)
  }
}
