package com.programmerare.sweden_crs_transformations_4jvm.coordinate_files;

import com.google.common.io.Resources;
import com.programmerare.sweden_crs_transformations_4jvm.CrsCoordinate;
import com.programmerare.sweden_crs_transformations_4jvm.CrsProjection;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
    
public class TransformingCoordinatesFromFileTest {

    final static String columnSeparator = "\\|";
    
    // the below file "swedish_crs_transformations.csv" was copied from: https://github.com/TomasJohansson/crsTransformations/blob/a1da6c74daf040a521beb32f9f395124ffe76aa6/crs-transformation-adapter-test/src/test/resources/generated/swedish_crs_coordinates.csv
    // and it was generated with a method "createFileWithTransformationResultsForCoordinatesInSweden()" at https://github.com/TomasJohansson/crsTransformations/blob/a1da6c74daf040a521beb32f9f395124ffe76aa6/crs-transformation-adapter-test/src/test/java/com/programmerare/com/programmerare/testData/CoordinateTestDataGeneratedFromEpsgDatabaseTest.java
    private final static String relativePathForFileWith_swedish_crs_transformations = "coordinate_files/swedish_crs_coordinates.csv";
    // the above file should be located within 'test/resources' i.e. at the path ".../test/resources/coordinate_files/swedish_crs_coordinates.csv"        

    @Test
    public void AssertThatTransformationsDoNotDifferTooMuchFromExpectedResultInFile() {
        List<String> problemTransformationResults = new ArrayList<String>();
        List<String> lines = this.readAllLinesFromResourceFile(relativePathForFileWith_swedish_crs_transformations);
        // The first two lines of the input file (the header row, and a data row):
            // EPSG 4326 (WGS84)Longitude for WGS84 (EPSG 4326)|Latitude for WGS84 (EPSG 4326)|EPSG 3006|X for EPSG 3006|Y for EPSG 3006|EPSG 3007-3024|X for EPSG 3007-3024|Y for EPSG 3007-3024|Implementation count for EPSG 3006 transformation|Implementation count for EPSG 3007-3024 transformation
            // 4326|12.146151472138385|58.46573396912418|3006|333538.2957000149|6484098.2550872|3007|158529.85136620898|6483166.205771873|6|6
        // The last two columns can be ignored here, but the first nine columns are in three pairs with three columns each:
        // an epsg number, and then the longitude(x) and latitude(y) for that coordinate.
        // All three coordinates in one row represents the same location but in different coordinate reference systems.
        // The first two, of the three, coordinates are for the same coordinate reference systems, WGS84 and SWEREF99TM, 
        // but the third is different for all rows (18 data rows for the local swedish CRS systems, RT90 and SWEREF99, with EPSG codes 3007-3024).
        
        // The below loop iterates all lines and makes transformations between (to and from) the three coordinate reference systems
        // and verifies the expected result according to the file, and asserts with an error if the difference is too big.
        // Note that the expected coordinates have been calculated in another project, by using a median value for 6 different implementations.
        // (and the number 6 is actually what the last columns means i.e. how many implementations were used to create the data file)
        List<Coordinates> listOfCoordinates = lines.stream() //line => new Coordinates(line)).Skip(1).ToList();
            .skip(1)
            .map(line -> new Coordinates(line))
            .collect(Collectors.toList());
        assertEquals(18, listOfCoordinates.size());
        int numberOfTransformations = 0;
        for (Coordinates listOfCoordinatesWhichRepresentTheSameLocation : listOfCoordinates) {
            List<CrsCoordinate> coordinates = listOfCoordinatesWhichRepresentTheSameLocation.coordinateList;
            for(int i=0; i<coordinates.size()-1; i++) {
                for(int j=i+1; j<coordinates.size(); j++) {
                    Transform(coordinates.get(i), coordinates.get(j), problemTransformationResults);
                    Transform(coordinates.get(j), coordinates.get(i), problemTransformationResults);
                    numberOfTransformations += 2;
                }
            }
        }
        if (problemTransformationResults.size() > 0) {
            for (String s : problemTransformationResults) {
                System.out.println(s);
            }
        }
        assertEquals("For further details see the Console output",0, problemTransformationResults.size());
        
        final int expectedNumberOfTransformations = 108; // for an explanation, see the lines below:
        // Each line in the input file "swedish_crs_coordinates.csv" has three coordinates (and let's below call then A B C)
        // and then for each line we should have done six number of transformations:
        // A ==> B
        // A ==> C
        // B ==> C
        // (and three more in the opposite directions)
        // And there are 18 local CRS for sweden (i.e number of data rows in the file)
        // Thus the total number of transformations should be 18 * 6 = 108
        assertEquals(expectedNumberOfTransformations, numberOfTransformations);
    }

    private void Transform(
        CrsCoordinate sourceCoordinate,
        CrsCoordinate targetCoordinateExpected,
        List<String> problemTransformationResults
    ) {
        CrsProjection targetCrs = targetCoordinateExpected.getCrsProjection();
        CrsCoordinate targetCoordinate = sourceCoordinate.Transform(targetCrs);
        boolean isTargetEpsgWgs84 = targetCrs.IsWgs84();
        // double maxDifference = isTargetEpsgWgs84 ? 0.000002 : 0.2;   // fails, Epsg 3022 ==> 4326 , diffLongitude 2.39811809521484E-06
        // double maxDifference = isTargetEpsgWgs84 ? 0.000003 : 0.1;     // fails, Epsg 4326 ==> 3022 , diffLongitude 0.117090131156147
        double maxDifference = isTargetEpsgWgs84 ? 0.000003 : 0.2; // the other (i.e. non-WGS84) are using meter as unit, so 0.2 is just two decimeters difference
        double diffLongitude = Math.abs((targetCoordinate.getLongitudeX() - targetCoordinateExpected.getLongitudeX()));
        double diffLatitude = Math.abs((targetCoordinate.getLatitudeY() - targetCoordinateExpected.getLatitudeY()));

        if (diffLongitude > maxDifference || diffLatitude > maxDifference) {
            String problem = String.format(
                "Projection {0} ==> {1} , diffLongitude {2}  , diffLatitude {3}"
                + "sourceCoordinate xLongitude/yLatitude: {4}/{5}" 
                + "targetCoordinate xLongitude/yLatitude: {6}/{7}" 
                + "targetCoordinateExpected xLongitude/yLatitude: {8}/{9}",
                sourceCoordinate.getCrsProjection(), targetCoordinateExpected.getCrsProjection(),
                diffLongitude, diffLatitude,
                sourceCoordinate.getLongitudeX(), sourceCoordinate.getLatitudeY(),
                targetCoordinate.getLongitudeX(), targetCoordinate.getLatitudeY(),
                targetCoordinateExpected.getLongitudeX(), targetCoordinateExpected.getLatitudeY()
            );
            problemTransformationResults.add(problem);
        }
    }

    // example of the path parameter for the below method:
    //      "coordinate_files/swedish_crs_coordinates.csv"
    // Then that file must exist within "test/resources" i.e. the file
    // must then exist at the following path:
    //      ".../test/resources/coordinate_files/swedish_crs_coordinates.csv"
    private List<String> readAllLinesFromResourceFile(String pathForResourceFile) {
        try {
            URL url = Resources.getResource(pathForResourceFile);
            return Resources.readLines(url, Charset.forName("UTF-8"));                
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class Coordinates {
    final List<CrsCoordinate> coordinateList;
    Coordinates(
        String lineFromFile
    ) {
        String[] array = lineFromFile.split(TransformingCoordinatesFromFileTest.columnSeparator);
        coordinateList = Arrays.asList(
            // Note that the order of the parameters in the input file (with its lines being used here)
            // are in the order x/Longitude first, but the create method below takes the y/Latitude first
            // (and therefore the parameters are not in the sequential order regarding the array indexes)
            CrsCoordinate.CreateCoordinate(Integer.parseInt(array[0]), Double.parseDouble(array[2]), Double.parseDouble(array[1])),
            CrsCoordinate.CreateCoordinate(Integer.parseInt(array[3]), Double.parseDouble(array[5]), Double.parseDouble(array[4])),
            CrsCoordinate.CreateCoordinate(Integer.parseInt(array[6]), Double.parseDouble(array[8]), Double.parseDouble(array[7]))
        );
    }

}

