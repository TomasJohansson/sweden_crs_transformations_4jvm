package com.programmerare.sweden_crs_transformations_4jvm.example;

/*
https://github.com/TomasJohansson/sweden_crs_transformations_4jvm  
https://github.com/goober/coordinate-transformation-library  
Both libraries (in the two above URL's) are based on the C#.NET library at the below URL:   
https://github.com/bjornsallarp/MightyLittleGeodesy  
You can see the similarity in the C# class [GaussKreuger.cs](https://github.com/bjornsallarp/MightyLittleGeodesy/blob/master/MightyLittleGeodesy/Classes/GaussKreuger.cs)  
The methods 'geodetic_to_grid' and 'grid_to_geodetic' are corresponding to methods with the same name in the below two classes     
[TomasJohansson/sweden_crs_transformations_4jvm/GaussKreuger.java](https://github.com/TomasJohansson/sweden_crs_transformations_4jvm/blob/java_SwedenCrsTransformations/lib/src/main/java/com/programmerare/sweden_crs_transformations_4jvm/mighty_little_geodesy/GaussKreuger.java)  
[goober/coordinate-transformation-library/GaussKreuger.java](https://github.com/goober/coordinate-transformation-library/blob/master/src/main/java/com/github/goober/coordinatetransformation/GaussKreuger.java)   
Since the same mathematics are the same for the above two libraries, there should be no difference in the results.  
However, there is a significant difference in the API for you to use.  
When you use this 'sweden_crs_transformations_4jvm' library you only need to use the enum 'CrsProjection' and the class 'CrsCoordinate'.  
When you use the 'goober' library there are three classes (WGS84Position, SWEREF99Position, RT90Position) and two enums (SWEREFProjection, RT90Projection) to use.  
See the code example below.  
 */
import com.github.goober.coordinatetransformation.positions.WGS84Position;
import com.github.goober.coordinatetransformation.positions.SWEREF99Position;
import com.github.goober.coordinatetransformation.positions.RT90Position;
import com.github.goober.coordinatetransformation.positions.SWEREF99Position.SWEREFProjection;
import com.github.goober.coordinatetransformation.positions.RT90Position.RT90Projection;

import com.programmerare.sweden_crs_transformations_4jvm.CrsCoordinate;
import com.programmerare.sweden_crs_transformations_4jvm.CrsProjection;

public class ExampleComparingWithGooberLibrary {
    public static void main(String[] args) {
        final double x_RT90_2_5_GON_V = 1627546;
        final double y_RT90_2_5_GON_V = 6583050;

        transform_to_SWEREF99TM_using_sweden_crs_transformations_4jvm(y_RT90_2_5_GON_V, x_RT90_2_5_GON_V);

        transform_to_SWEREF99TM_using_goober_coordinate_transformation_library(y_RT90_2_5_GON_V, x_RT90_2_5_GON_V);
    }

    private static void transform_to_SWEREF99TM_using_sweden_crs_transformations_4jvm(
        double y_rt90_2_5_gon_v,
        double x_rt90_2_5_gon_v
    )
    {
        // This method uses the library at this URL for transforming from "RT90 2,5 gon v" to SWEREF99TM:
        // https://github.com/TomasJohansson/sweden_crs_transformations_4jvm
        // These two types are the only types needed:
        //import com.programmerare.sweden_crs_transformations_4jvm.CrsCoordinate;
        //import com.programmerare.sweden_crs_transformations_4jvm.CrsProjection;        
        
        CrsCoordinate coordinate_RT90_2_5_GON_V = CrsProjection.RT90_2_5_GON_V.createCoordinate(y_rt90_2_5_gon_v, x_rt90_2_5_gon_v);
        CrsCoordinate coordinate_sweref99tm = coordinate_RT90_2_5_GON_V.transform(CrsProjection.SWEREF_99_TM);
        System.out.println("coordinate_sweref99tm.getLongitudeX() " + coordinate_sweref99tm.getLongitudeX());
        System.out.println("coordinate_sweref99tm.getLatitudeY() " + coordinate_sweref99tm.getLatitudeY());

        // Of course it is not necessary to store intermediate variable as above, but you might 
        // choose to do as below instead, if you would prefer that:
        CrsCoordinate coordinate_sweref99tm_alternative = 
            CrsProjection.RT90_2_5_GON_V.createCoordinate(y_rt90_2_5_gon_v, x_rt90_2_5_gon_v)
            .transform(CrsProjection.SWEREF_99_TM);
        System.out.println("coordinate_sweref99tm_alternative.getLongitudeX() " + coordinate_sweref99tm_alternative.getLongitudeX());
        System.out.println("coordinate_sweref99tm_alternative.getLatitudeY() " + coordinate_sweref99tm_alternative.getLatitudeY());
        

        // Output printed by this method:
        // coordinate_sweref99tm.getLongitudeX() 673259.81
        // coordinate_sweref99tm.getLatitudeY() 6582868.119
        // coordinate_sweref99tm_alternative.getLongitudeX() 673259.81
        // coordinate_sweref99tm_alternative.getLatitudeY() 6582868.119        
    }


    // 'build.gradle' dependency which enables the below used types within 'com.github.goober': 
    //      'com.github.goober:coordinate-transformation-library:1.1'
    private static void transform_to_SWEREF99TM_using_goober_coordinate_transformation_library(
        double y_rt90_2_5_gon_v,
        double x_rt90_2_5_gon_v
    )
    {
        // This method uses the library at the below URL for transforming from "RT90 2,5 gon v" to SWEREF99TM:
        // https://github.com/goober/coordinate-transformation-library
        // https://mvnrepository.com/artifact/com.github.goober/coordinate-transformation-library/1.1
        // https://github.com/goober/coordinate-transformation-library/blob/master/src/test/java/com/github/goober/coordinatetransformation/CoordinateTransformationJUnit4Test.java
        // These five types are used:        
        //import com.github.goober.coordinatetransformation.positions.RT90Position;
        //import com.github.goober.coordinatetransformation.positions.RT90Position.RT90Projection;
        //import com.github.goober.coordinatetransformation.positions.SWEREF99Position;
        //import com.github.goober.coordinatetransformation.positions.SWEREF99Position.SWEREFProjection;
        //import com.github.goober.coordinatetransformation.positions.WGS84Position;

        RT90Position position_rt90_2_5_gon_v = new RT90Position(y_rt90_2_5_gon_v, x_rt90_2_5_gon_v, RT90Projection.rt90_2_5_gon_v);
        WGS84Position position_wgs84 = position_rt90_2_5_gon_v.toWGS84();
        SWEREF99Position position_sweref_99_tm = new SWEREF99Position(position_wgs84, SWEREFProjection.sweref_99_tm);
        
        System.out.println("position_sweref_99_tm.getLongitude() " + position_sweref_99_tm.getLongitude());
        System.out.println("position_sweref_99_tm.getLatitude() " + position_sweref_99_tm.getLatitude());

        // Of course it is not necessary to store intermediate variable as above, but you might 
        // choose to do as below instead, if you would prefer that:
        SWEREF99Position position_sweref_99_tm_alternative = new SWEREF99Position(
            (new RT90Position(y_rt90_2_5_gon_v, x_rt90_2_5_gon_v, RT90Position.RT90Projection.rt90_2_5_gon_v)).toWGS84(),
            SWEREF99Position.SWEREFProjection.sweref_99_tm
        );
        System.out.println("position_sweref_99_tm_alternative.getLongitude() " + position_sweref_99_tm_alternative.getLongitude());
        System.out.println("position_sweref_99_tm_alternative.getLatitude() " + position_sweref_99_tm_alternative.getLatitude());
        
        // Output printed by this method:
        // position_sweref_99_tm.getLongitude() 673259.81
        // position_sweref_99_tm.getLatitude() 6582868.119
        // position_sweref_99_tm_alternative.getLongitude() 673259.81
        // position_sweref_99_tm_alternative.getLatitude() 6582868.119
    }
}
