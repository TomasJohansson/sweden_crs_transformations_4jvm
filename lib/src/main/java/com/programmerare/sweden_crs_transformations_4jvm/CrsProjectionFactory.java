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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

/**
 * Class with methods for getting all projections, and for getting one projection by its EPSG number.
 * @see CrsProjection
 * @see <a href="https://epsg.io">https://epsg.io</a>
 * @see <a href="https://epsg.org">https://epsg.org</a>
 * @see <a href="https://en.wikipedia.org/wiki/EPSG_Geodetic_Parameter_Dataset">https://en.wikipedia.org/wiki/EPSG_Geodetic_Parameter_Dataset</a>
 */
public class CrsProjectionFactory {

    private final static Map<Integer, CrsProjection>
        mapWithAllCrsProjections = new HashMap<Integer, CrsProjection>();
    
    static {
        CrsProjection[] crsProjections = CrsProjection.values();
        for (CrsProjection crsProjection : crsProjections) {
            mapWithAllCrsProjections.put(crsProjection.getEpsgNumber(), crsProjection);
        }        
    }
    
    /**
     * Factory method creating an enum 'CrsProjection' by its number (EPSG) value. 
     * @param epsg An EPSG number
     * @return an instance of the enum 'CrsProjection' which representents the EPSG number provided as method parameter
     * @see CrsProjection
     */
    public static CrsProjection getCrsProjectionByEpsgNumber(int epsg) {
        if(mapWithAllCrsProjections.containsKey(epsg)) {
            return mapWithAllCrsProjections.get(epsg);
        }
        throw new IllegalArgumentException("Could not find CrsProjection for EPSG " + epsg);
    }

    /**
     * Convenience method for retrieving all the projections in a List.
     * They are returned ordered by EPSG number (from low to high values)
     * with the exception that WGS84 is the first projection in the returned list 
     * @return a list of all CrsProjection items, in the sort order from low to high EPSG number 
     * (with WGS84 as an exception, being the first item returned)
     * @see CrsProjection
     */    
    public static List<CrsProjection> getAllCrsProjections() {
        // The method 'values()' returns the items in the order declared.
        // WGS84 must be declared first, and then the rest sorted by EPSG number,
        // and please note that this specified sort order is also verified in test code.
        return Arrays.asList(CrsProjection.values());
    }
}
