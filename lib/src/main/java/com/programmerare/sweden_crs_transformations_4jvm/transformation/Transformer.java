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

public class Transformer {

    /**
     * Transforms a coordinate from one CRS (Coordinate Reference System) to another CRS.
     * @param sourceCoordinate the source coordinate, i.e. X/Y values and a CRS.
     * @param targetCrsProjection the target CRS
     * @return a new coordinate instance representing the source coordinate in the target CRS 
     * @see CrsProjection
     */
    public static CrsCoordinate transform(CrsCoordinate sourceCoordinate, CrsProjection targetCrsProjection) {
        if(sourceCoordinate.getCrsProjection() == targetCrsProjection) return sourceCoordinate;

        final TransformStrategy _transFormStrategy = getTransformStrategyImplementation(
            sourceCoordinate.getCrsProjection(),
            targetCrsProjection
        );
        // the above method might throw an exception but never returns null i.e. no need for null check here         
        return _transFormStrategy.transform(sourceCoordinate, targetCrsProjection);
    }

    /**
     * @param sourceProjection
     * @param targetCrsProjection
     * @return never null (but rather an exception) i.e. no need for client code to check for null
     */
    private static TransformStrategy getTransformStrategyImplementation(
        CrsProjection sourceProjection,
        CrsProjection targetCrsProjection
    ) {
        // Transform FROM wgs84:
        if(
            sourceProjection.isWgs84()
                &&
            ( targetCrsProjection.isSweRef99() || targetCrsProjection.isRT90() )
        ) {
            return TransformStrategy_from_WGS84_to_SWEREF99_or_RT90.getInstance();
        }

        // Transform TO wgs84:
        else if(
            targetCrsProjection.isWgs84()
                &&
            ( sourceProjection.isSweRef99() || sourceProjection.isRT90() )
        ) {
            return TransformStrategy_from_SWEREF99_or_RT90_to_WGS84.getInstance();
        }

        // Transform between two non-wgs84:
        else if(
            ( sourceProjection.isSweRef99() || sourceProjection.isRT90() )
                &&
            ( targetCrsProjection.isSweRef99() || targetCrsProjection.isRT90() )
        ) {
            // the only direct transform supported is to/from WGS84, so therefore first transform to wgs84
            return TransFormStrategy_From_Sweref99OrRT90_to_WGS84_andThenToRealTarget.getInstance();
        }
        throw new IllegalArgumentException(String.format("Unhandled source/target projection transformation: %s ==> %s", sourceProjection, targetCrsProjection));        
    }

}
