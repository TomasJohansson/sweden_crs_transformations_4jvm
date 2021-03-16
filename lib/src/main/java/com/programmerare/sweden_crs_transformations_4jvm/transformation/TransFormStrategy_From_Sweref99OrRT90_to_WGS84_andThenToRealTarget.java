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
import com.programmerare.sweden_crs_transformations_4jvm.mighty_little_geodesy.GaussKreuger;
import com.programmerare.sweden_crs_transformations_4jvm.mighty_little_geodesy.LatLon;


    class TransFormStrategy_From_Sweref99OrRT90_to_WGS84_andThenToRealTarget implements TransformStrategy {
        // Precondition: sourceCoordinate must be CRS SWEREF99 or RT90
        @Override
        public CrsCoordinate Transform(
            CrsCoordinate sourceCoordinate,
            CrsProjection targetCrsProjection
        ) {
            CrsCoordinate wgs84coordinate = Transformer.Transform(sourceCoordinate, CrsProjection.wgs84);
            return Transformer.Transform(wgs84coordinate, targetCrsProjection);
        }
    }
