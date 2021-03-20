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

import com.programmerare.sweden_crs_transformations_4jvm.transformation.Transformer;
import java.util.Objects;
import java.util.function.Function;

/**
 * Coordinate, defined by the three parameters for the factory methods. 
 */
public class CrsCoordinate {
    
    private final CrsProjection crsProjection;
    private final double yLatitude;
    private final double xLongitude;

    /**
     * @return the coordinate reference system that defines the location together with the other two properties (LongitudeX and LatitudeY).
     */
    public CrsProjection getCrsProjection() {
        return crsProjection;
    }
    
    /**
     * @return the coordinate value representing the longitude or X or Easting.
     */
    public double getLongitudeX() {
        return xLongitude;
    }

    /**
     * @return the coordinate value representing the latitude or Y or Northing.
     */    
    public double getLatitudeY() {
        return yLatitude;
    }

    /**
     * Private constructor. Client code must instead use the public factory methods.
     * @param crsProjection represents the coordinate reference system that defines the location together with the other two parameters
     * @param yLatitude the coordinate position value representing the latitude or Y or Northing
     * @param xLongitude the coordinate position value representing the longitude or X or Easting
     */
    private CrsCoordinate(
        CrsProjection crsProjection,
        double yLatitude,
        double xLongitude
    ) {
        this.crsProjection = crsProjection;
        this.yLatitude = yLatitude;
        this.xLongitude = xLongitude;
    }

    /**
     * Transforms the coordinate to another coordinate reference system
     * @param targetCrsProjection the coordinate reference system that you want to transform to
     * @return a new instance representing the transformed coordinate
     */
    public CrsCoordinate transform(CrsProjection targetCrsProjection) {
        return Transformer.transform(this, targetCrsProjection); 
    }

    /**
     * Transforms the coordinate to another coordinate reference system
     * @param targetEpsgNumber the coordinate reference system that you want to transform to
     * @return a new instance representing the transformed coordinate
     */
    public CrsCoordinate transform(int targetEpsgNumber) {
        CrsProjection targetCrsProjection = CrsProjection.getCrsProjectionByEpsgNumber(targetEpsgNumber);
        return this.transform(targetCrsProjection);
    }

    /**
     * Factory method for creating an instance.
     * @param epsgNumber represents the coordinate reference system that defines the location together with the other two parameters
     * @param yLatitude the coordinate position value representing the latitude or Y or Northing
     * @param xLongitude the coordinate position value representing the longitude or X or Easting
     * @return an instance of CrsCoordinate 
     */
    public static CrsCoordinate createCoordinate(
        int epsgNumber,
        double yLatitude,
        double xLongitude
    ) {
        CrsProjection crsProjection = CrsProjection.getCrsProjectionByEpsgNumber(epsgNumber);
        return createCoordinate(crsProjection, yLatitude, xLongitude);
    }

    /**
     * Factory method for creating an instance.
     * @param crsProjection  represents the coordinate reference system that defines the location together with the other two parameters
     * @param yLatitude the coordinate position value representing the latitude or Y or Northing
     * @param xLongitude the coordinate position value representing the longitude or X or Easting
     * @return an instance of CrsCoordinate
     */
    public static CrsCoordinate createCoordinate(
        CrsProjection crsProjection,
        double yLatitude,
        double xLongitude
    ) {
        return new CrsCoordinate(crsProjection, yLatitude, xLongitude);
    }

    // ----------------------------------------------------------------------------------------------------------------------
    // These two methods below (i.e. 'equals' and 'hashCode') was generated with IntelliJ IDEA 2020.3
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CrsCoordinate that = (CrsCoordinate) o;
        return Double.compare(that.yLatitude, yLatitude) == 0
            && Double.compare(that.xLongitude, xLongitude) == 0
            && crsProjection == that.crsProjection;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(crsProjection, yLatitude, xLongitude);
    }
    // These two methods above (i.e. 'equals' and 'hashCode') was generated with IntelliJ IDEA 2020.3
    // ----------------------------------------------------------------------------------------------------------------------

    /**
     * @return Two examples of the string that can be returned:
     *  "CrsCoordinate [ X: 153369.673 , Y: 6579457.649 , CRS: SWEREF_99_18_00 ]"
     *  "CrsCoordinate [ Longitude: 18.059196 , Latitude: 59.330231 , CRS: WGS84 ]"
     */
    @Override
    public String toString() {
        return _toStringImplementation.apply(this);
    }

    private static Function<CrsCoordinate, String> _toStringImplementation = CrsCoordinate::defaultToStringImplementation;

    private static String defaultToStringImplementation(CrsCoordinate coordinate) {
        String crs = coordinate.getCrsProjection().toString().toUpperCase();
        boolean isWgs84 =  coordinate.getCrsProjection().isWgs84();
        String yOrLatitude = isWgs84 ? "Latitude" : "Y";
        String xOrLongitude = isWgs84 ? "Longitude" : "X";
        return String.format(
            "CrsCoordinate [ %s: %s , %s: %s , CRS: %s ]",
                yOrLatitude,
                coordinate.getLatitudeY(),
                xOrLongitude,
                coordinate.getLongitudeX(),
                crs
        );
    }

    /**
     * Sets a custom method to be used for rendering an instance when the 'ToString' method is used. 
     * @param toStringImplementation a method/function with 'CrsCoordinate' as parameter, and returning a String
     */
    public static void setToStringImplementation(Function<CrsCoordinate, String> toStringImplementation) {
        _toStringImplementation = toStringImplementation;
    }

    /**
     * Sets the default method to be used for rendering an instance when the 'toString' method is used.
     */
    public static void setToStringImplementationDefault() { 
        _toStringImplementation = CrsCoordinate::defaultToStringImplementation;
    }

}
