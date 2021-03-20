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
import java.util.List;

/**
 * Class with methods for getting all projections, and for getting one projection by its EPSG number.
 * @see CrsProjection
 * @see <a href="https://epsg.io">https://epsg.io</a>
 * @see <a href="https://epsg.org">https://epsg.org</a>
 * @see <a href="https://en.wikipedia.org/wiki/EPSG_Geodetic_Parameter_Dataset">https://en.wikipedia.org/wiki/EPSG_Geodetic_Parameter_Dataset</a>
 */
@Deprecated // instead use the CrsProjection methods directly
public class CrsProjectionFactory {

    @Deprecated // instead use the CrsProjection method directly    
    public static CrsProjection getCrsProjectionByEpsgNumber(int epsg) {
        return CrsProjection.getCrsProjectionByEpsgNumber(epsg);
    }


    @Deprecated // instead use the CrsProjection method directly
    public static List<CrsProjection> getAllCrsProjections() {
        return CrsProjection.getAllCrsProjections();
    }
}
