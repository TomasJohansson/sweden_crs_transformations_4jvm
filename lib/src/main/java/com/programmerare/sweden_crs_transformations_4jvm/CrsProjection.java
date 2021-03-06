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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Crs = Coordinate reference system.
 *
 *  The integer values used as constructor parameters for this enum 'CrsProjection'
 *  are the EPSG numbers for the corresponding coordinate reference systems.
 *  There are three kind of coordinate systems supported and defined in this enum type below:
 *      WGS84 
 *      SWEREF99 (the new Swedish grid, 13 versions, one national grid and 12 local projection zones) 
 *      RT90 (the old Swedish grid, 6 local projection zones) 
 *
 * Regarding the mentioned EPSG numbers (the enum constructor parameter values), 
 * at the links below you may find some more information about "EPSG".
 * @see CrsProjection
 * @see <a href="https://epsg.io">https://epsg.io</a>
 * @see <a href="https://epsg.org">https://epsg.org</a>
 * @see <a href="https://en.wikipedia.org/wiki/EPSG_Geodetic_Parameter_Dataset">https://en.wikipedia.org/wiki/EPSG_Geodetic_Parameter_Dataset</a> 
 */
public enum CrsProjection {
    /**
     * EPSG 4326
     * @see <a href="https://epsg.org/crs_4326/WGS-84.html">https://epsg.org/crs_4326/WGS-84.html</a>
     * @see <a href="https://epsg.io/4326">https://epsg.io/4326</a>
     * @see <a href="https://spatialreference.org/ref/epsg/4326/">https://spatialreference.org/ref/epsg/4326/</a>
     * @see <a href="https://en.wikipedia.org/wiki/World_Geodetic_System#A_new_World_Geodetic_System:_WGS_84">https://en.wikipedia.org/wiki/World_Geodetic_System#A_new_World_Geodetic_System:_WGS_84</a>
     */
    WGS84(4326),
    
    // Note: Keep the order as it is in this enum, i.e. the very first item should be 
    // WGS84, but then the rest (i.e. the Swedish projections) should be declared by EPSG number (in increasing order with low values first)
    // There is also a test method implemented for this specified return order. 

    /**
     * EPSG 3006
     * "SWEREF 99 TM" (with EPSG code 3006) is the new national projection.
     * @see <a href="https://www.lantmateriet.se/sv/Kartor-och-geografisk-information/gps-geodesi-och-swepos/referenssystem/tvadimensionella-system/sweref-99-projektioner/">https://www.lantmateriet.se/sv/Kartor-och-geografisk-information/gps-geodesi-och-swepos/referenssystem/tvadimensionella-system/sweref-99-projektioner/</a>
     * @see <a href="https://epsg.org/crs_3006/SWEREF99-TM.html">https://epsg.org/crs_3006/SWEREF99-TM.html</a>
     * @see <a href="https://epsg.io/3006">https://epsg.io/3006</a>
     * @see <a href="https://spatialreference.org/ref/epsg/3006/">https://spatialreference.org/ref/epsg/3006/</a>
     */
    SWEREF_99_TM(3006), // national sweref99 CRS

    // local sweref99 systems (the new swedish national system):
    /**
     * EPSG 3007
     */
    SWEREF_99_12_00(3007),

    /**
     * EPSG 3008
     */    
    SWEREF_99_13_30(3008),

    /**
     * EPSG 3009
     */    
    SWEREF_99_15_00(3009),

    /**
     * EPSG 3010
     */    
    SWEREF_99_16_30(3010),

    /**
     * EPSG 3011
     */    
    SWEREF_99_18_00(3011),

    /**
     * EPSG 3012
     */    
    SWEREF_99_14_15(3012),

    /**
     * EPSG 3013
     */
    SWEREF_99_15_45(3013),

    /**
     * EPSG 3014
     */    
    SWEREF_99_17_15(3014),

    /**
     * EPSG 3015
     */    
    SWEREF_99_18_45(3015),

    /**
     * EPSG 3016
     */    
    SWEREF_99_20_15(3016),

    /**
     * EPSG 3017
     */
    SWEREF_99_21_45(3017),

    /**
     * EPSG 3018
     */    
    SWEREF_99_23_15(3018),

    
    // local RT90 systems (the old swedish national system):
    /**
     * EPSG 3019
     */    
    RT90_7_5_GON_V(3019),

    /**
     * EPSG 3020
     */
    RT90_5_0_GON_V(3020),

    /**
     * EPSG 3021
     * @see <a href="https://epsg.org/crs_3021/RT90-2-5-gon-V.html">https://epsg.org/crs_3021/RT90-2-5-gon-V.html</a>
     * @see <a href="https://epsg.io/3021">https://epsg.io/3021</a>
     * @see <a href="https://spatialreference.org/ref/epsg/3021/">https://spatialreference.org/ref/epsg/3021/</a>
     */
    RT90_2_5_GON_V(3021),

    /**
     * EPSG 3022
     */    
    RT90_0_0_GON_V(3022),

    /**
     * EPSG 3023
     */    
    RT90_2_5_GON_O(3023),

    /**
     * EPSG 3024
     */    
    RT90_5_0_GON_O(3024);


    private final int epsg;
    private CrsProjection(final int epsg) {
        this.epsg = epsg;
    }

    private final static int epsgForSweref99tm = 3006;

    // private final static int epsgLowerValueForSwerefLocal = 3007; // the NATIONAL sweref99TM has value 3006 as in the above constant
    // private final static int epsgUpperValueForSwerefLocal = 3018;
    private final static int epsgLowerValueForSweref = epsgForSweref99tm;
    private final static int epsgUpperValueForSweref = 3018;

    private final static int epsgLowerValueForRT90 = 3019;
    private final static int epsgUpperValueForRT90 = 3024;

    /**
     * @return the EPSG number for the enum instance representing a coordinate reference system.
     */
    public int getEpsgNumber() {
        // the EPSG numbers have been used as the values in this enum
        return epsg;
    }

    /**
     * @return true if the coordinate reference system is WGS84. Otherwise false.
     */
    public boolean isWgs84() {
        return epsg == CrsProjection.WGS84.epsg;
    }

    /**
     * @return true if the coordinate reference system is SWEREF99. Otherwise false.
     */
    public boolean isSweRef99() {
        return epsgLowerValueForSweref <= epsg && epsg <= epsgUpperValueForSweref;
    }

    /**
     * @return true if the coordinate reference system is RT90. Otherwise false.
     */    
    public boolean isRT90() {
        return epsgLowerValueForRT90 <= epsg && epsg <= epsgUpperValueForRT90;
    }

    /**
     * Factory method creating a coordinate with the projection represented by the enum instance. 
     * @param yLatitude the coordinate value representing the latitude or Y or Northing. 
     * @param xLongitude the coordinate value representing the longitude or X or Easting.
     * @return an instance of CrsCoordinate
     */
    public CrsCoordinate createCoordinate(
        double yLatitude,
        double xLongitude
    ) {
        return CrsCoordinate.createCoordinate(this, yLatitude, xLongitude);
    }

    /**
     * @return a string representation of the enum.
     * For example "WGS84(EPSG:4326)" or "SWEREF_99_TM(EPSG:3006)" or "RT90_0_0_GON_V(EPSG:3022)"
     * If you want just the name without the EPSG suffix then you can use the method 'name()'
     * to return strings such as for example "WGS84" or "SWEREF_99_TM" 
     */
    @Override
    public String toString() {
        return String.format(
            "%s(EPSG:%s)",
                this.name(),
                this.getEpsgNumber()
        );
    }
    
    private final static Map<Integer, CrsProjection>
        mapWithAllCrsProjections = new HashMap<Integer, CrsProjection>();

    static {
        CrsProjection[] crsProjections = CrsProjection.values();
        for (CrsProjection crsProjection : crsProjections) {
            mapWithAllCrsProjections.put(crsProjection.getEpsgNumber(), crsProjection);
        }
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

}
