package com.programmerare.sweden_crs_transformations_4jvm.example;

// This class can be used for code examples e.g. verify that this code below works 
// and then it can be paste into some example page at github

// The only two types you need to use is 'CrsProjection' and 'CrsCoordinate' 
// (and when you have created an instance of 'CrsCoordinate' you can use its method 'transform' )
import com.programmerare.sweden_crs_transformations_4jvm.CrsProjection;
import com.programmerare.sweden_crs_transformations_4jvm.CrsCoordinate;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.List;

public class JavaExample  {

    @Test
    public void creating_instances_of_CrsProjection() {
        final CrsProjection crsSWEREF_99_TM__1 = CrsProjection.SWEREF_99_TM;
        // An enum instance of CrsProjection can be created either directly by enum access as
        // above, or by using a so called EPSG number as illustrated below
        final int epsgNumberFor_SWEREF_99_TM = 3006; // https://epsg.io/3006
        final CrsProjection crsSWEREF_99_TM__2 = CrsProjection.getCrsProjectionByEpsgNumber(epsgNumberFor_SWEREF_99_TM);
        // The above two instances of 'CrsProjection' are referencing the same enum 
        // 'CrsProjection.SWEREF_99_TM' as illustrated in the below test assertion: 
        assertEquals(crsSWEREF_99_TM__1, crsSWEREF_99_TM__2);

        // The EPSG number is above defined explicitly into 'epsgNumberFor_SWEREF_99_TM'
        // but you do not need to do that yourself since the value is available from the enum instance 
        // as illustrated in the below test assertion:
        assertEquals(
            epsgNumberFor_SWEREF_99_TM, // 3006
            CrsProjection.SWEREF_99_TM.getEpsgNumber() // 3006
        );

        // The 'toString' method renders as the name of the enum instance plus a an EPSG suffix
        // as illustrated in the below test assertion
        assertEquals(
            "SWEREF_99_TM(EPSG:3006)",
            CrsProjection.SWEREF_99_TM.toString()
        );
    }

    @Test
    public void creating_instances_of_CrsCoordinate() {
        final int epsgNumberFor_RT90_2_5_GON_V = 3021; // https://epsg.io/3021
        // Note that you do not need to define the EPSG numbers as above since 
        // they are available from within the enum as illustrated in the below test assertion:
        assertEquals(
            epsgNumberFor_RT90_2_5_GON_V, // 3021
            CrsProjection.RT90_2_5_GON_V.getEpsgNumber() // 3021
        );
        
        final double y_RT90_2_5_GON_V = 6583050;
        final double x_RT90_2_5_GON_V = 1627546;

        // Below are three ways to create a coordinate represented by the above x/y values and the 
        // above coordinate reference system RT90_2_5_GON_V (EPSG:3021) :
        CrsCoordinate crs_1 = CrsProjection.RT90_2_5_GON_V.createCoordinate(y_RT90_2_5_GON_V, x_RT90_2_5_GON_V);
        CrsCoordinate crs_2 = CrsCoordinate.createCoordinate(CrsProjection.RT90_2_5_GON_V, y_RT90_2_5_GON_V, x_RT90_2_5_GON_V);
        CrsCoordinate crs_3 = CrsCoordinate.createCoordinate(epsgNumberFor_RT90_2_5_GON_V, y_RT90_2_5_GON_V, x_RT90_2_5_GON_V);
        // The above three are equivalent as illustrated in the below test assertions: 
        assertEquals(crs_1, crs_2);
        assertEquals(crs_1, crs_3);
        
        // Now when you have created an instance of 'CrsCoordinate' you can use it for transforming 
        // to another coordinate reference system, for example WGS84 as illustrated below
        CrsCoordinate wgs84 = crs_1.transform(CrsProjection.WGS84);
        // The transform method is overloaded, and instead of an enum instance 
        // you may use an EPSG number (but of course onnly those 20 EPSG numbers that are supported)
        final int epsgNumberForWGS84 = CrsProjection.WGS84.getEpsgNumber(); // 4326
        CrsCoordinate wgs84_alternative = crs_1.transform(epsgNumberForWGS84); // 4326
        CrsCoordinate wgs84_alternative_2 = crs_1.transform(4326);
        // See also the below main method for more sample code
    }
    
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
