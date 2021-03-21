package com.programmerare.sweden_crs_transformations_4jvm.example;

// This is NOT a "test" class with assertions, but it can be used for code examples 
// e.g. verify that this code below works and then it can be paste into some example page at github

import com.programmerare.sweden_crs_transformations_4jvm.CrsCoordinate;
import com.programmerare.sweden_crs_transformations_4jvm.CrsProjection;
import java.util.List;

public class JavaExample  {
    public static void main(String[] args) {
        
        final double stockholmCentralStation_WGS84_latitude = 59.330231;
        final double stockholmCentralStation_WGS84_longitude = 18.059196;

        final CrsCoordinate stockholmWGS84 = CrsCoordinate.createCoordinate(
            CrsProjection.WGS84,
            stockholmCentralStation_WGS84_latitude,
            stockholmCentralStation_WGS84_longitude
        );

        final CrsCoordinate stockholmSweref99tm = stockholmWGS84.transform(CrsProjection.SWEREF_99_TM);
        System.out.println("stockholmSweref99tm X: " + stockholmSweref99tm.getLongitudeX());
        System.out.println("stockholmSweref99tm Y: " + stockholmSweref99tm.getLatitudeY());
        System.out.println("stockholmSweref99tm 'toString': " + stockholmSweref99tm.toString());
        // Output from the above:
        //stockholmSweref99tm X: 674032.357
        //stockholmSweref99tm Y: 6580821.991
        //stockholmSweref99tm 'toString': CrsCoordinate [ Y: 6580821.991 , X: 674032.357 , CRS: SWEREF_99_TM(EPSG:3006) ]

        final List<CrsProjection> allProjections = CrsProjection.getAllCrsProjections();
        for(final CrsProjection crsProjection : allProjections) {
            System.out.println(stockholmWGS84.transform(crsProjection));
        }
        // Output from the above loop:
        //CrsCoordinate [ Latitude: 59.330231 , Longitude: 18.059196 , CRS: WGS84(EPSG:4326) ]
        //CrsCoordinate [ Y: 6580821.991 , X: 674032.357 , CRS: SWEREF_99_TM(EPSG:3006) ]
        //CrsCoordinate [ Y: 6595151.116 , X: 494604.69 , CRS: SWEREF_99_12_00(EPSG:3007) ]
        //CrsCoordinate [ Y: 6588340.147 , X: 409396.217 , CRS: SWEREF_99_13_30(EPSG:3008) ]
        //CrsCoordinate [ Y: 6583455.373 , X: 324101.998 , CRS: SWEREF_99_15_00(EPSG:3009) ]
        //CrsCoordinate [ Y: 6580494.921 , X: 238750.424 , CRS: SWEREF_99_16_30(EPSG:3010) ]
        //CrsCoordinate [ Y: 6579457.649 , X: 153369.673 , CRS: SWEREF_99_18_00(EPSG:3011) ]
        //CrsCoordinate [ Y: 6585657.12 , X: 366758.045 , CRS: SWEREF_99_14_15(EPSG:3012) ]
        //CrsCoordinate [ Y: 6581734.696 , X: 281431.616 , CRS: SWEREF_99_15_45(EPSG:3013) ]
        //CrsCoordinate [ Y: 6579735.93 , X: 196061.94 , CRS: SWEREF_99_17_15(EPSG:3014) ]
        //CrsCoordinate [ Y: 6579660.051 , X: 110677.129 , CRS: SWEREF_99_18_45(EPSG:3015) ]
        //CrsCoordinate [ Y: 6581507.028 , X: 25305.238 , CRS: SWEREF_99_20_15(EPSG:3016) ]
        //CrsCoordinate [ Y: 6585277.577 , X: -60025.629 , CRS: SWEREF_99_21_45(EPSG:3017) ]
        //CrsCoordinate [ Y: 6590973.148 , X: -145287.219 , CRS: SWEREF_99_23_15(EPSG:3018) ]
        //CrsCoordinate [ Y: 6598325.639 , X: 1884004.1 , CRS: RT90_7_5_GON_V(EPSG:3019) ]
        //CrsCoordinate [ Y: 6587493.237 , X: 1756244.287 , CRS: RT90_5_0_GON_V(EPSG:3020) ]
        //CrsCoordinate [ Y: 6580994.18 , X: 1628293.886 , CRS: RT90_2_5_GON_V(EPSG:3021) ]
        //CrsCoordinate [ Y: 6578822.84 , X: 1500248.374 , CRS: RT90_0_0_GON_V(EPSG:3022) ]
        //CrsCoordinate [ Y: 6580977.349 , X: 1372202.721 , CRS: RT90_2_5_GON_O(EPSG:3023) ]
        //CrsCoordinate [ Y: 6587459.595 , X: 1244251.702 , CRS: RT90_5_0_GON_O(EPSG:3024) ]
    }
}
