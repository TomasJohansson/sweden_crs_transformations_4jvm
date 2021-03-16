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
package com.programmerare.sweden_crs_transformations_4jvm;


    /// <summary>
    /// Crs = Coordinate reference system.
    /// 
    /// The integer values for these enums are the EPSG numbers for the corresponding coordinate reference systems.
    /// There are three kind of coordinate systems supported and defined in this enum type below:
    ///     WGS84
    ///     SWEREF99 (the new Swedish grid, 13 versions, one national grid and 12 local projection zones)
    ///     RT90 (the old Swedish grid, 6 local projection zones)
    /// There are extensions methods for the enum which can be used to determine one of the above three types. 
    /// See also <see cref="CrsProjectionExtensions"/>
    /// 
    /// Regarding the mentioned EPSG numbers (the enum values), at the links below you may find some more information about "EPSG".
    /// https://en.wikipedia.org/wiki/EPSG_Geodetic_Parameter_Dataset
    /// https://epsg.org
    /// https://epsg.io
    /// </summary>    
    public enum CrsProjection {

            /// <summary>
            /// https://epsg.org/crs_4326/WGS-84.html
            /// https://epsg.io/4326
            /// https://spatialreference.org/ref/epsg/4326/
            /// https://en.wikipedia.org/wiki/World_Geodetic_System#A_new_World_Geodetic_System:_WGS_84
            /// </summary>
            wgs84(4326),

            /// <summary>
            /// "SWEREF 99 TM" (with EPSG code 3006) is the new national projection.
            /// https://www.lantmateriet.se/sv/Kartor-och-geografisk-information/gps-geodesi-och-swepos/referenssystem/tvadimensionella-system/sweref-99-projektioner/
            /// https://epsg.org/crs_3006/SWEREF99-TM.html
            /// https://epsg.io/3006
            /// https://spatialreference.org/ref/epsg/3006/
            /// </summary>
            sweref_99_tm(3006), // national sweref99 CRS

            // local sweref99 systems (the new swedish national system):
            sweref_99_12_00(3007),
            sweref_99_13_30(3008),
            sweref_99_15_00(3009),
            sweref_99_16_30(3010),
            sweref_99_18_00(3011),
            sweref_99_14_15(3012),
            sweref_99_15_45(3013),
            sweref_99_17_15(3014),
            sweref_99_18_45(3015),
            sweref_99_20_15(3016),
            sweref_99_21_45(3017),
            sweref_99_23_15(3018),

            
            // local RT90 systems (the old swedish national system):
            rt90_7_5_gon_v(3019),
            rt90_5_0_gon_v(3020),
 
            /// <summary>
            /// https://epsg.org/crs_3021/RT90-2-5-gon-V.html
            /// https://epsg.io/3021
            /// https://spatialreference.org/ref/epsg/3021/
            /// </summary>
            rt90_2_5_gon_v(3021),

            rt90_0_0_gon_v(3022),
            rt90_2_5_gon_o(3023),
            rt90_5_0_gon_o(3024);

            private final int epsg;
            private CrsProjection(final int epsg) {
                this.epsg = epsg;
            }

            private final static int epsgForSweref99tm = 3006;

            //private final static int epsgLowerValueForSwerefLocal = 3007; // the NATIONAL sweref99TM has value 3006 as in the above constant
            //private final static int epsgUpperValueForSwerefLocal = 3018;
            private final static int epsgLowerValueForSweref = epsgForSweref99tm;
            private final static int epsgUpperValueForSweref = 3018;

            private final static int epsgLowerValueForRT90 = 3019;
            private final static int epsgUpperValueForRT90 = 3024;

            /// <summary>
            /// The EPSG number for the enum instance representing a coordinate reference system.
            /// The implementation is trivial but it is a convenience method that provides semantic 
            /// through the method name i.e. what the enum value represents 
            /// and it also lets the client code avoid to do the casting.
            /// </summary>
            /// <returns>
            /// An EPSG number.
            /// https://en.wikipedia.org/wiki/EPSG_Geodetic_Parameter_Dataset
            /// </returns>
            public int GetEpsgNumber() {
                // the EPSG numbers have been used as the values in this enum
                return epsg;
            }

            /// <summary>
            /// True if the coordinate reference system is WGS84.
            /// </summary>
            public boolean IsWgs84() {
                return epsg == CrsProjection.wgs84.epsg;
            }

            /// <summary>
            /// True if the coordinate reference system is a version of SWEREF99.
            /// </summary>
            public boolean IsSweref() {
                return epsgLowerValueForSweref <= epsg && epsg <= epsgUpperValueForSweref;
            }

            /// <summary>
            /// True if the coordinate reference system is a version of RT90.
            /// </summary>
            public boolean IsRT90() {
                return epsgLowerValueForRT90 <= epsg && epsg <= epsgUpperValueForRT90;
            }

    }