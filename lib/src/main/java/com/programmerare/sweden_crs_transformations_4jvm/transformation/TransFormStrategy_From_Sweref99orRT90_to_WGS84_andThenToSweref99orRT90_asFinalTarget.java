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

final class TransFormStrategy_From_Sweref99orRT90_to_WGS84_andThenToSweref99orRT90_asFinalTarget
    extends TransformStrategyBase
    implements TransformStrategy
{
    private final static TransformStrategy _transformStrategy = new TransFormStrategy_From_Sweref99orRT90_to_WGS84_andThenToSweref99orRT90_asFinalTarget();

    public static TransformStrategy getInstance() {
        return _transformStrategy;
    }

    private TransFormStrategy_From_Sweref99orRT90_to_WGS84_andThenToSweref99orRT90_asFinalTarget() {}

    // Preconditions:
    // sourceProjection must be CRS SWEREF99 or RT90
    // targetProjection must be CRS SWEREF99 or RT90
    // (if any of them is WGS84 then another TransFormStrategy implementation should be used) 
    @Override
    public CrsCoordinate transform(
        CrsCoordinate sourceCoordinate,
        CrsProjection finalTargetCrsProjection
    ) {
        final CrsProjection sourceCoordinateProjection = sourceCoordinate.getCrsProjection();
        // In this implementation class neither the source projection nor the final projection should be WGS84
        // which is asserted below
        super.assertCoordinateProjections(
            sourceCoordinateProjection,
            sourceCoordinateProjection.isRT90() || sourceCoordinateProjection.isSweRef99(),
            _transformStrategy,
            finalTargetCrsProjection,
            finalTargetCrsProjection.isRT90() || finalTargetCrsProjection.isSweRef99()
        );

        final CrsProjection intermediateCrsProjection = CrsProjection.WGS84;
        final CrsCoordinate intermediateWgs84coordinate = Transformer.transform(sourceCoordinate, intermediateCrsProjection);
        
        return Transformer.transform(intermediateWgs84coordinate, finalTargetCrsProjection);
    }
}
