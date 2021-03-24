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

final class TransFormStrategy_From_Sweref99OrRT90_to_WGS84_andThenToRealTarget implements TransformStrategy {
    private final static TransformStrategy _transformStrategy = new TransFormStrategy_From_Sweref99OrRT90_to_WGS84_andThenToRealTarget();

    public static TransformStrategy getInstance() {
        return _transformStrategy;
    }

    private TransFormStrategy_From_Sweref99OrRT90_to_WGS84_andThenToRealTarget() {}
    
    // Precondition: sourceCoordinate must be CRS SWEREF99 or RT90
    @Override
    public CrsCoordinate transform(
        CrsCoordinate sourceCoordinate,
        CrsProjection targetCrsProjection
    ) {
        CrsCoordinate wgs84coordinate = Transformer.transform(sourceCoordinate, CrsProjection.WGS84);
        return Transformer.transform(wgs84coordinate, targetCrsProjection);
    }
}
