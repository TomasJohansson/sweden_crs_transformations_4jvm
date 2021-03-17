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

class TransformStrategy_from_WGS84_to_SWEREF99_or_RT90 implements TransformStrategy {
    // Precondition: sourceCoordinate must be CRS WGS84
    @Override            
    public CrsCoordinate transform(
        CrsCoordinate sourceCoordinate,
        CrsProjection targetCrsProjection
    ) {
        GaussKreuger gkProjection = new GaussKreuger();
        gkProjection.swedish_params(targetCrsProjection);
        LatLon latLon = gkProjection.geodetic_to_grid(sourceCoordinate.getLatitudeY(), sourceCoordinate.getLongitudeX());
        return CrsCoordinate.createCoordinate(targetCrsProjection, latLon.LatitudeY, latLon.LongitudeX);
    }
}
