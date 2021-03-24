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
package com.programmerare.sweden_crs_transformations_4jvm.transformation;

import com.programmerare.sweden_crs_transformations_4jvm.CrsCoordinate;
import com.programmerare.sweden_crs_transformations_4jvm.CrsProjection;
import com.programmerare.sweden_crs_transformations_4jvm.mighty_little_geodesy.GaussKreuger;
import com.programmerare.sweden_crs_transformations_4jvm.mighty_little_geodesy.GaussKreugerFactory;
import com.programmerare.sweden_crs_transformations_4jvm.mighty_little_geodesy.LatLon;


/**
 * TransformStrategy implementation that should be used when transforming 
 * from WGS84 to either SWEREF99 or RT90. 
 */
final class TransformStrategy_from_WGS84_to_SWEREF99_or_RT90 
    extends TransformStrategyBase
    implements TransformStrategy
{

    private final static TransformStrategy _transformStrategy = new TransformStrategy_from_WGS84_to_SWEREF99_or_RT90();
    
    public static TransformStrategy getInstance() {
        return _transformStrategy;
    }

    private TransformStrategy_from_WGS84_to_SWEREF99_or_RT90() {}
    
    // Preconditions:
    // sourceProjection must be CRS WGS84
    // targetProjection must be CRS SWEREF99 or RT90
    @Override            
    public CrsCoordinate transform(
        CrsCoordinate sourceCoordinate,
        CrsProjection targetCrsProjection
    ) {
        final CrsProjection sourceCoordinateProjection = sourceCoordinate.getCrsProjection();
        
        super.assertCoordinateProjections(
            sourceCoordinateProjection,
            sourceCoordinateProjection.isWgs84(),
            _transformStrategy,
            targetCrsProjection,
            targetCrsProjection.isRT90() || targetCrsProjection.isSweRef99()
        );

        final GaussKreuger gkProjection = GaussKreugerFactory.getInstance().getGaussKreuger(targetCrsProjection);
        final LatLon latLon = gkProjection.geodetic_to_grid(sourceCoordinate.getLatitudeY(), sourceCoordinate.getLongitudeX());
        return CrsCoordinate.createCoordinate(targetCrsProjection, latLon.LatitudeY, latLon.LongitudeX);
    }
}
