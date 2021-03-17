/*
* Copyright (c) Tomas Johansson , http://www.programmerare.com
* The code in this library is licensed with MIT.
* The library is based on the library 'MightyLittleGeodesy' (https://github.com/bjornsallarp/MightyLittleGeodesy/) 
* which is also released with MIT.
* License information about 'sweden_crs_transformations_4net' and 'MightyLittleGeodesy':
* https://github.com/TomasJohansson/sweden_crs_transformations_4net/blob/csharpe_SwedenCrsTransformations/LICENSE
* For more information see the webpage below.
* https://github.com/TomasJohansson/sweden_crs_transformations_4net
*/
package com.programmerare.sweden_crs_transformations_4jvm.transformation;

import com.programmerare.sweden_crs_transformations_4jvm.CrsCoordinate;
import com.programmerare.sweden_crs_transformations_4jvm.CrsProjection;

public class Transformer {

    // Implementations of transformations from WGS84:
    private static final TransformStrategy _transformStrategy_from_WGS84_to_SWEREF99_or_RT90 = new TransformStrategy_from_WGS84_to_SWEREF99_or_RT90();

    // Implementations of transformations to WGS84:
    private static final TransformStrategy _transformStrategy_from_SWEREF99_or_RT90_to_WGS84 = new TransformStrategy_from_SWEREF99_or_RT90_to_WGS84();

    // Implementation first transforming to WGS84 and then to the real target:
    private static final TransformStrategy _transFormStrategy_From_Sweref99OrRT90_to_WGS84_andThenToRealTarget  = new TransFormStrategy_From_Sweref99OrRT90_to_WGS84_andThenToRealTarget();

    public static CrsCoordinate transform(CrsCoordinate sourceCoordinate, CrsProjection targetCrsProjection) {
        if(sourceCoordinate.getCrsProjection() == targetCrsProjection) return sourceCoordinate;

        TransformStrategy _transFormStrategy = null;

        // Transform FROM wgs84:
        if(
            sourceCoordinate.getCrsProjection().isWgs84()
            &&
            ( targetCrsProjection.isSweref() || targetCrsProjection.isRT90() )
        ) {
            _transFormStrategy = _transformStrategy_from_WGS84_to_SWEREF99_or_RT90;
        }

        // Transform TO wgs84:
        else if(
            targetCrsProjection.isWgs84()
            &&
            ( sourceCoordinate.getCrsProjection().isSweref() || sourceCoordinate.getCrsProjection().isRT90() )
        ) {
            _transFormStrategy = _transformStrategy_from_SWEREF99_or_RT90_to_WGS84;
        }

        // Transform between two non-wgs84:
        else if(
            ( sourceCoordinate.getCrsProjection().isSweref() || sourceCoordinate.getCrsProjection().isRT90() )
            &&
            ( targetCrsProjection.isSweref() || targetCrsProjection.isRT90() )
        ) {
            // the only direct transform supported is to/from WGS84, so therefore first transform to wgs84
            _transFormStrategy = _transFormStrategy_From_Sweref99OrRT90_to_WGS84_andThenToRealTarget;
        }
        
        if(_transFormStrategy != null) {
            return _transFormStrategy.transform(sourceCoordinate, targetCrsProjection);
        }

        throw new IllegalArgumentException(String.format("Unhandled source/target projection transformation: %s ==> %s", sourceCoordinate.getCrsProjection(), targetCrsProjection));
    }

}
