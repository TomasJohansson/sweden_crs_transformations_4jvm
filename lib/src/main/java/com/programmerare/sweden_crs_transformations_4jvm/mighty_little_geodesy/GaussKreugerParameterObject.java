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

import com.programmerare.sweden_crs_transformations_4jvm.CrsProjection;
import static com.programmerare.sweden_crs_transformations_4jvm.CrsProjection.*; // wgs84 , sweref_99_tm , and so on

/**
 * This class 'GaussKreugerParameterObject' was not part of the original 'MightyLittleGeodesy' library,
 * but most of the code in this class has been moved here from the class 'GaussKreuger'.
 */
class GaussKreugerParameterObject {
    GaussKreugerParameterObject(CrsProjection crsProjection)
    {
        this.swedish_params(crsProjection);
    }

    double axis; // Semi-major axis of the ellipsoid.
    double flattening; // Flattening of the ellipsoid.
    double central_meridian; // Central meridian for the projection.    
    double scale; // Scale on central meridian.
    double false_northing; // Offset for origo.
    double false_easting; // Offset for origo.        

    // Parameters for RT90 and SWEREF99TM.
    // Note: Parameters for RT90 are choosen to eliminate the 
    // differences between Bessel and GRS80-ellipsoides.
    // Bessel-variants should only be used if lat/long are given as
    // RT90-lat/long based on the Bessel ellipsoide (from old maps).
    // Parameter: projection (string). Must match if-statement.
    private void swedish_params(CrsProjection projection)
    {
        // RT90 parameters, GRS 80 ellipsoid.
        if (projection == RT90_7_5_GON_V) {
            grs80_params();
            central_meridian = 11.0 + 18.375 / 60.0;
            scale = 1.000006000000;
            false_northing = -667.282;
            false_easting = 1500025.141;
        } else if (projection == RT90_5_0_GON_V) {
            grs80_params();
            central_meridian = 13.0 + 33.376 / 60.0;
            scale = 1.000005800000;
            false_northing = -667.130;
            false_easting = 1500044.695;
        } else if (projection == RT90_2_5_GON_V) {
            grs80_params();
            central_meridian = 15.0 + 48.0 / 60.0 + 22.624306 / 3600.0;
            scale = 1.00000561024;
            false_northing = -667.711;
            false_easting = 1500064.274;
        } else if (projection == RT90_0_0_GON_V) {
            grs80_params();
            central_meridian = 18.0 + 3.378 / 60.0;
            scale = 1.000005400000;
            false_northing = -668.844;
            false_easting = 1500083.521;
        } else if (projection == RT90_2_5_GON_O) {
            grs80_params();
            central_meridian = 20.0 + 18.379 / 60.0;
            scale = 1.000005200000;
            false_northing = -670.706;
            false_easting = 1500102.765;
        } else if (projection == RT90_5_0_GON_O) {
            grs80_params();
            central_meridian = 22.0 + 33.380 / 60.0;
            scale = 1.000004900000;
            false_northing = -672.557;
            false_easting = 1500121.846;
        }

        // SWEREF99TM and SWEREF99ddmm  parameters.
        else if (projection == SWEREF_99_TM) {
            sweref99_params();
            central_meridian = 15.00;
            scale = 0.9996;
            false_northing = 0.0;
            false_easting = 500000.0;
        } else if (projection == SWEREF_99_12_00) {
            sweref99_params();
            central_meridian = 12.00;
        } else if (projection == SWEREF_99_13_30) {
            sweref99_params();
            central_meridian = 13.50;
        } else if (projection == SWEREF_99_15_00) {
            sweref99_params();
            central_meridian = 15.00;
        } else if (projection == SWEREF_99_16_30) {
            sweref99_params();
            central_meridian = 16.50;
        } else if (projection == SWEREF_99_18_00) {
            sweref99_params();
            central_meridian = 18.00;
        } else if (projection == SWEREF_99_14_15) {
            sweref99_params();
            central_meridian = 14.25;
        } else if (projection == SWEREF_99_15_45) {
            sweref99_params();
            central_meridian = 15.75;
        } else if (projection == SWEREF_99_17_15) {
            sweref99_params();
            central_meridian = 17.25;
        } else if (projection == SWEREF_99_18_45) {
            sweref99_params();
            central_meridian = 18.75;
        } else if (projection == SWEREF_99_20_15) {
            sweref99_params();
            central_meridian = 20.25;
        } else if (projection == SWEREF_99_21_45) {
            sweref99_params();
            central_meridian = 21.75;
        } else if (projection == SWEREF_99_23_15) {
            sweref99_params();
            central_meridian = 23.25;
        } else {
            central_meridian = Double.MIN_VALUE;
        }
    }

    // Sets of default parameters.
    private void grs80_params()
    {
        axis = 6378137.0; // GRS 80.
        flattening = 1.0 / 298.257222101; // GRS 80.
        central_meridian = Double.MIN_VALUE;
    }

    private void bessel_params()
    {
        axis = 6377397.155; // Bessel 1841.
        flattening = 1.0 / 299.1528128; // Bessel 1841.
        central_meridian = Double.MIN_VALUE;
        scale = 1.0;
        false_northing = 0.0;
        false_easting = 1500000.0;
    }

    private void sweref99_params()
    {
        axis = 6378137.0; // GRS 80.
        flattening = 1.0 / 298.257222101; // GRS 80.
        central_meridian = Double.MIN_VALUE;
        scale = 1.0;
        false_northing = 0.0;
        false_easting = 150000.0;
    }
}
