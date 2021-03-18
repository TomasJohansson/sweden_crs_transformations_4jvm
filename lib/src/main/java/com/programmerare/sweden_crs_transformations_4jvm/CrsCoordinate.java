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

/// <summary>
/// Coordinate, defined by the three parameters for the factory methods.
/// </summary>
public class CrsCoordinate {
    
    private final CrsProjection crsProjection;
    private final double yLatitude;
    private final double xLongitude;

    /// <summary>
    /// The coordinate reference system that defines the location together with the other two properties (LongitudeX and LatitudeY).
    /// </summary>
    public CrsProjection getCrsProjection() {
        return crsProjection;
    }
    
    /// <summary>
    /// The coordinate value representing the longitude or X or Easting.
    /// </summary>    
    public double getLongitudeX() {
        return xLongitude;
    }

    /// <summary>
    /// The coordinate value representing the latitude or Y or Northing.
    /// </summary>
    public double getLatitudeY() {
        return yLatitude;
    }

    /// <summary>
    /// Private constructor. Client code must instead use the public factory methods.
    /// </summary>
    private CrsCoordinate(
        CrsProjection crsProjection,
        double yLatitude,
        double xLongitude
    ) {
        this.crsProjection = crsProjection;
        this.yLatitude = yLatitude;
        this.xLongitude = xLongitude;
    }

    /// <summary>
    /// Transforms the coordinate to another coordinate reference system
    /// </summary>
    /// <param name="targetCrsProjection">the coordinate reference system that you want to transform to</param>        
    public CrsCoordinate transform(CrsProjection targetCrsProjection) {
        return Transformer.transform(this, targetCrsProjection); 
    }

    /// <summary>
    /// Transforms the coordinate to another coordinate reference system
    /// </summary>
    /// <param name="targetEpsgNumber">the coordinate reference system that you want to transform to</param>        
    public CrsCoordinate transform(int targetEpsgNumber) {
        CrsProjection targetCrsProjection = CrsProjectionFactory.getCrsProjectionByEpsgNumber(targetEpsgNumber);
        return this.transform(targetCrsProjection);
    }

    /// <summary>
    /// Factory method for creating an instance.
    /// </summary>
    /// <param name="epsgNumber">represents the coordinate reference system that defines the location together with the other two parameters</param>
    /// <param name="xLongitude">the coordinate position value representing the longitude or X or Easting</param>
    /// <param name="yLatitude">the coordinate position value representing the latitude or Y or Northing</param>
    public static CrsCoordinate createCoordinate(
        int epsgNumber,
        double yLatitude,
        double xLongitude
    ) {
        CrsProjection crsProjection = CrsProjectionFactory.getCrsProjectionByEpsgNumber(epsgNumber);
        return createCoordinate(crsProjection, yLatitude, xLongitude);
    }

    /// <summary>
    /// Factory method for creating an instance.
    /// See also <see cref="CrsProjection"/>
    /// </summary>
    /// <param name="crsProjection">represents the coordinate reference system that defines the location together with the other two parameters</param>
    /// <param name="xLongitude">the coordinate position value representing the longitude or X or Easting</param>
    /// <param name="yLatitude">the coordinate position value representing the latitude or Y or Northing</param>
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

    /// <summary>
    /// Two examples of the string that can be returned:
    /// "CrsCoordinate [ X: 153369.673 , Y: 6579457.649 , CRS: SWEREF_99_18_00 ]"
    /// "CrsCoordinate [ Longitude: 18.059196 , Latitude: 59.330231 , CRS: WGS84 ]"
    /// </summary>
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
//
//        /// <summary>
//        /// Sets a custom method to be used for rendering an instance when the 'ToString' method is used.
//        /// </summary>
    public static void setToStringImplementation(Function<CrsCoordinate, String> toStringImplementation) {
        _toStringImplementation = toStringImplementation;
    }
//
//        /// <summary>
//        /// Sets the default method to be used for rendering an instance when the 'ToString' method is used.
//        /// </summary>
    public static void setToStringImplementationDefault() { 
        _toStringImplementation = CrsCoordinate::defaultToStringImplementation;
    }

}
