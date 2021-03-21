# sweden_crs_transformations_4jvm
'sweden_crs_transformations_4jvm' is a Java/JVM library (with Java, Scala and Kotlin tests) ported from the
[C#.NET library 'sweden_crs_transformations_4net'](https://github.com/TomasJohansson/sweden_crs_transformations_4net/)
for transforming geographic coordinates between the following three kind of CRS (Coordinate Reference Systems): WGS84, SWEREF99 and RT90.
(13 versions of SWEREF99, and 6 versions of RT90)

That C#.NET library ('sweden_crs_transformations_4net') is
based on [C# library MightyLittleGeodesy](https://github.com/bjornsallarp/MightyLittleGeodesy/) which in turn is based on a [javascript library by Arnold Andreasson](https://latlong.mellifica.se/).

The main part of 'MightyLittleGeodesy' which has been at least partially kept (in the C# library 'sweden_crs_transformations_4net') is the mathematical calculations in the class 'GaussKreuger.cs'.  
Regarding the port to this 'sweden_crs_transformations_4jvm' then of course there had to be more modifications since Java has differences in syntax compared with C#, although
the mathematical logic has still been kept from the original 'MightyLittleGeodesy' class 'GaussKreuger.cs'.

# Maven/Gradle release

No, not yet.

# Implementations in other programming languages
Currently I have implemented this JVM library also with the following programming languages and github repositories:   
C#.NET: [sweden_crs_transformations_4net](https://github.com/TomasJohansson/sweden_crs_transformations_4net)   
TypeScript: [sweden_crs_transformations_4typescript](https://github.com/TomasJohansson/sweden_crs_transformations_4typescript)   
Dart: [sweden_crs_transformations_4net](https://github.com/TomasJohansson/sweden_crs_transformations_4dart)   

# Code example for Java
```java
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
```
# Code example for Scala and Kotlin
[Scala example](https://github.com/TomasJohansson/sweden_crs_transformations_4jvm/blob/java_SwedenCrsTransformations/lib/src/test/scala/com/programmerare/sweden_crs_transformations_4jvm/example/ScalaExample.scala)  
[Kotlin example](https://github.com/TomasJohansson/sweden_crs_transformations_4jvm/blob/java_SwedenCrsTransformations/lib/src/test/kotlin/com/programmerare/sweden_crs_transformations_4jvm/example/KotlinExample.kt)  

# Comparison with the Java library [goober/coordinate-transformation-library](https://github.com/goober/coordinate-transformation-library)
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
```java
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
```

# Accuracy of the transformations

This JVM/Java library is a port of the [C#.NET library 'sweden_crs_transformations_4net'](https://github.com/TomasJohansson/sweden_crs_transformations_4net/) and therefore it is using the same file "swedish_crs_coordinates.csv" as the C# library, for the regression testing of the Java implementation.  
There are 18 rows with coordinates in that file, and it will lead to 108 transformations being done when executing all Java/Scala/Kotlin tests, e.g. with the command 'gradlew test'.  
The coordinate values in the file have been created as median values from six different Java implementations of CRS transformations.  
For more information about the origin of the data file being used, please see the webpage linked above for the C# library 'sweden_crs_transformations_4net'.  

# License

MIT.  
'sweden_crs_transformations_4jvm' is ported from the C# library 'sweden_crs_transformations_4net'
which is also licensed with MIT since it started as a fork of the C# library 'MightyLittleGeodesy' which is licensed with the MIT license. (see below).  
[License text for 'sweden_crs_transformations_4jvm'](https://github.com/TomasJohansson/sweden_crs_transformations_4jvm/blob/java_SwedenCrsTransformations/LICENSE)

# License for the original C# repository [MightyLittleGeodesy](https://github.com/bjornsallarp/MightyLittleGeodesy/)

The text below has been copied from the above linked webpage:
> The calculations in this library is based on the excellent javascript library by Arnold Andreasson which is published under the Creative Commons license. However, as agreed with mr Andreasson, MightyLittleGeodesy is now licensed under the MIT license.

The text below has been copied from [one of the source files for MightyLittleGeodesy](https://github.com/bjornsallarp/MightyLittleGeodesy/blob/83491fc6e7454f5d90d792610b317eca7a332334/MightyLittleGeodesy/Classes/GaussKreuger.cs).
```C#
/*
 * MightyLittleGeodesy 
 * RT90, SWEREF99 and WGS84 coordinate transformation library
 * 
 * Read my blog @ http://blog.sallarp.com
 * 
 * 
 * Copyright (C) 2009 Björn Sållarp
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this 
 * software and associated documentation files (the "Software"), to deal in the Software 
 * without restriction, including without limitation the rights to use, copy, modify, 
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to 
 * permit persons to whom the Software is furnished to do so, subject to the following 
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or 
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
 ```
# Swedish coordinate reference systems
There are two kind of national CRS being used in Sweden:   
The old [RT90](https://www.lantmateriet.se/sv/Kartor-och-geografisk-information/gps-geodesi-och-swepos/Referenssystem/Tvadimensionella-system/RT-90/) (six versions for different local regions)    
The new [SWEREF99](https://www.lantmateriet.se/sv/Kartor-och-geografisk-information/gps-geodesi-och-swepos/referenssystem/tvadimensionella-system/sweref-99-projektioner/) (thirteen versions, one for the national "TM" and twelve local regions)

The above links are for pages in Swedish at the website for [Lantmäteriet](https://en.wikipedia.org/wiki/Lantm%C3%A4teriet) which is a swedish authority for mapping.

[https://www.lantmateriet.se/en/about-lantmateriet/about-lantmateriet/](https://www.lantmateriet.se/en/about-lantmateriet/about-lantmateriet/)   
Quote from the above URL:
```Text
We map the country, demarcate boundaries and help guarantee secure ownership of Sweden’s real property.   
You can get more information and documentation on Sweden’s geography and real properties from us.
```
