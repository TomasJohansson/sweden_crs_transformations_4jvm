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
package com.programmerare.sweden_crs_transformations_4jvm;

import java.util.Arrays;
import java.util.List;

/// <summary>
/// Class with methods for getting all projections, and for getting one projection by its EPSG number.
/// (since such custom methods can not be located within the CrsProjection enum type itself)
/// </summary>
/// See also <see cref="CrsProjection"/>
public class CrsProjectionFactory {

    /// <summary>
    /// Factory method creating an enum 'CrsProjection' by its number (EPSG) value.
    /// </summary>
    /// <param name="epsg">
    /// An EPSG number.
    /// https://en.wikipedia.org/wiki/EPSG_Geodetic_Parameter_Dataset
    /// https://epsg.org
    /// https://epsg.io
    /// </param>
    /// See also <see cref="CrsProjection"/>        
    public static CrsProjection getCrsProjectionByEpsgNumber(int epsg) {
        List<CrsProjection> values = getAllCrsProjections();
        for(CrsProjection value : values) {
            if(value.getEpsgNumber() == epsg) {
                return value;
            }
        }
        throw new IllegalArgumentException("Could not find CrsProjection for EPSG " + epsg);
    }

    /// <summary>
    /// Convenience method for retrieving all the projections in a List.
    /// They are returned ordered by EPSG number (from low to high values)
    /// with the exception that WGS84 is the first projection in the returned list
    /// </summary>
    public static List<CrsProjection> getAllCrsProjections() {
        // The method 'values()' returns the items in the order declared.
        return Arrays.asList(CrsProjection.values());
    }
}
