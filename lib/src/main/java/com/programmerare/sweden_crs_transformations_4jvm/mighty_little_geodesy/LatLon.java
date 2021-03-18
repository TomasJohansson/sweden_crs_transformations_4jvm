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
package com.programmerare.sweden_crs_transformations_4jvm.mighty_little_geodesy;

// This class was not part of the original 'MightyLittleGeodesy'
// but the class 'GaussKreuger' has later been changed to return this 'LatLon' instead of array 'double[]'
public class LatLon {
    public final double LongitudeX;
    public final double LatitudeY;
    public LatLon(double yLatitude, double xLongitude) {
        this.LongitudeX = xLongitude;
        this.LatitudeY = yLatitude;
    }
}
