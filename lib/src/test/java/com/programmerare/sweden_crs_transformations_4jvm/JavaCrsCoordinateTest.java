package com.programmerare.sweden_crs_transformations_4jvm;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class JavaCrsCoordinateTest
{
    public final static int epsgNumberForSweref99tm = 3006; // https://epsg.org/crs_3006/SWEREF99-TM.html
    public final static int numberOfSweref99projections = 13; // with EPSG numbers 3006-3018
    public final static int numberOfRT90projections = 6; // with EPSG numbers 3019-3024
    public final static int numberOfWgs84Projections = 1; // just to provide semantic instead of using a magic number 1 below
    private final static int totalNumberOfProjections = numberOfSweref99projections + numberOfRT90projections + numberOfWgs84Projections;
    
    // https://kartor.eniro.se/m/XRCfh
        //WGS84 decimal (lat, lon)      59.330231, 18.059196
        //RT90 (nord, öst)              6580994, 1628294
        //SWEREF99 TM (nord, öst)       6580822, 674032
    private final static double stockholmCentralStation_WGS84_latitude = 59.330231;
    private final static double stockholmCentralStation_WGS84_longitude = 18.059196;
    private final static double stockholmCentralStation_RT90_northing = 6580994;
    private final static double stockholmCentralStation_RT90_easting = 1628294;
    private final static double stockholmCentralStation_SWEREF99TM_northing = 6580822;
    private final static double stockholmCentralStation_SWEREF99TM_easting = 674032;

    @Test
    public void transform() { 
        CrsCoordinate stockholmWGS84 = CrsCoordinate.createCoordinate(
            CrsProjection.WGS84,
            stockholmCentralStation_WGS84_latitude,
            stockholmCentralStation_WGS84_longitude
        );
        CrsCoordinate stockholmSWEREF99TM = CrsCoordinate.createCoordinate(
            CrsProjection.SWEREF_99_TM,
            stockholmCentralStation_SWEREF99TM_northing,
            stockholmCentralStation_SWEREF99TM_easting
        );
        CrsCoordinate stockholmRT90 = CrsCoordinate.createCoordinate(
            CrsProjection.RT90_2_5_GON_V,
            stockholmCentralStation_RT90_northing,
            stockholmCentralStation_RT90_easting
        );
        
        // Transformations to WGS84 (from SWEREF99TM and RT90):
        assertEqual(
            stockholmWGS84, // expected WGS84
            stockholmSWEREF99TM.transform(CrsProjection.WGS84) // actual/transformed WGS84
        );
        assertEqual(
            stockholmWGS84, // expected WGS84
            stockholmRT90.transform(CrsProjection.WGS84) // actual/transformed WGS84
        );
        // below is a similar test as one of the above tests but using the overloaded transform method
        // which takes an integer as parameter instead of an instance of the enum CrsProjection
        int epsgNumberForWgs84 = CrsProjection.WGS84.getEpsgNumber();
        assertEqual(
            stockholmWGS84,
            stockholmRT90.transform(epsgNumberForWgs84) // testing the overloaded transform method with an integer parameter
        );
        

        // Transformations to SWEREF99TM (from WGS84 and RT90):
        assertEqual(
            stockholmSWEREF99TM, // expected SWEREF99TM
            stockholmWGS84.transform(CrsProjection.SWEREF_99_TM) // actual/transformed SWEREF99TM
        );
        assertEqual(
            stockholmSWEREF99TM, // expected SWEREF99TM
            stockholmRT90.transform(CrsProjection.SWEREF_99_TM) // actual/transformed SWEREF99TM
        );


        // Transformations to RT90 (from WGS84 and SWEREF99TM):
        assertEqual(
            stockholmRT90,  // expected RT90
            stockholmWGS84.transform(CrsProjection.RT90_2_5_GON_V) // actual/transformed RT90
        );
        assertEqual(
            stockholmRT90,  // expected RT90
            stockholmSWEREF99TM.transform(CrsProjection.RT90_2_5_GON_V) // actual/transformed RT90
        );
    }

    private void assertEqual(CrsCoordinate crsCoordinate_1, CrsCoordinate crsCoordinate_2)  {
        String messageToDisplayIfAssertionFails = "crsCoordinate_1: " + crsCoordinate_1 + " , crsCoordinate_2 : " + crsCoordinate_2;
        assertEquals(messageToDisplayIfAssertionFails, crsCoordinate_1.getCrsProjection(), crsCoordinate_2.getCrsProjection());
        double maxDifference = crsCoordinate_1.getCrsProjection().isWgs84() ? 0.000007 : 0.5; // the other (i.e. non-WGS84) value is using meter as unit, so 0.5 is just five decimeters difference
        double diffLongitude = Math.abs((crsCoordinate_1.getLongitudeX() - crsCoordinate_2.getLongitudeX()));
        double diffLatitude = Math.abs((crsCoordinate_1.getLatitudeY() - crsCoordinate_2.getLatitudeY()));
        assertTrue(messageToDisplayIfAssertionFails, diffLongitude < maxDifference);
        assertTrue(messageToDisplayIfAssertionFails, diffLatitude < maxDifference);
    }


    @Test
    public void createCoordinateByEpsgNumber() {
        final double x = 20.0;
        final double y = 60.0;
        CrsCoordinate crsCoordinate = CrsCoordinate.createCoordinate(epsgNumberForSweref99tm, y, x);
        assertEquals(CrsProjection.SWEREF_99_TM, crsCoordinate.getCrsProjection());
        assertEquals(epsgNumberForSweref99tm, crsCoordinate.getCrsProjection().getEpsgNumber());
        final double delta = 0.000001;
        assertEquals(x, crsCoordinate.getLongitudeX(), delta);
        assertEquals(y, crsCoordinate.getLatitudeY(), delta);
    }

    @Test
    public void createCoordinate() {
        final double x = 22.5;
        final double y = 62.5;
        CrsCoordinate crsCoordinate = CrsCoordinate.createCoordinate(CrsProjection.SWEREF_99_TM, y, x);
        assertEquals(epsgNumberForSweref99tm, crsCoordinate.getCrsProjection().getEpsgNumber());
        assertEquals(CrsProjection.SWEREF_99_TM, crsCoordinate.getCrsProjection());
        final double delta = 0.000001;
        assertEquals(x, crsCoordinate.getLongitudeX(), delta);
        assertEquals(y, crsCoordinate.getLatitudeY(), delta);
    }


    @Test
    public void equalityTest() { 
        CrsCoordinate coordinateInstance_1 = CrsCoordinate.createCoordinate(CrsProjection.WGS84, stockholmCentralStation_WGS84_longitude, stockholmCentralStation_WGS84_latitude);
        CrsCoordinate coordinateInstance_2 = CrsCoordinate.createCoordinate(CrsProjection.WGS84, stockholmCentralStation_WGS84_longitude, stockholmCentralStation_WGS84_latitude);
        assertEquals(coordinateInstance_1, coordinateInstance_2);
        assertEquals(coordinateInstance_1.hashCode(), coordinateInstance_2.hashCode());
        assertTrue(coordinateInstance_1.equals(coordinateInstance_2));
        assertTrue(coordinateInstance_2.equals(coordinateInstance_1));

        double delta = 0.000000000000001; // see comments further below regarding the value of "delta"
        CrsCoordinate coordinateInstance_3 = CrsCoordinate.createCoordinate(
            CrsProjection.WGS84,
            stockholmCentralStation_WGS84_longitude + delta,
            stockholmCentralStation_WGS84_latitude + delta
        );
        assertEquals(coordinateInstance_1, coordinateInstance_3);
        assertEquals(coordinateInstance_1.hashCode(), coordinateInstance_3.hashCode());
        assertTrue(coordinateInstance_1.equals(coordinateInstance_3));
        assertTrue(coordinateInstance_3.equals(coordinateInstance_1));

        // Regarding the chosen value for "delta" (which is added to the lon/lat values, to create a slightly different value) above and below,
        // it is because of experimentation this "breakpoint" value has been determined, i.e. the above value still resulted in equality 
        // but when it was increased as below with one decimal then the above kind of assertions failed and therefore 
        // the other assertions below are used instead.
        // You should generally be cautious when comparing floating point values but the above test indicate that values are considered equal even though 
        // the difference is as 'big' as in the "delta" value above.
        
        // Note that the chosen values for the 'delta' above and below has been determined by experimentation 
        // to see where it succeeds/fails, and it was originally implemented for C#.NET
        // but later has been verified that those 'breakpoint' delta values are the same for this JVM/Java implementation. 

        delta = delta * 10; // moving the decimal one bit to get a somewhat larger values, and then the instances are not considered equal, as you can see in the tests below.
        CrsCoordinate coordinateInstance_4 = CrsCoordinate.createCoordinate(
            CrsProjection.WGS84,
            stockholmCentralStation_WGS84_longitude + delta,
            stockholmCentralStation_WGS84_latitude + delta
        );
        // Note that below are the Are*NOT*Equal assertions made instead of AreEqual as further above when a smaller delta value was used
        assertNotEquals(coordinateInstance_1, coordinateInstance_4);
        assertNotEquals(coordinateInstance_1.hashCode(), coordinateInstance_4.hashCode());
        assertNotSame(coordinateInstance_1, coordinateInstance_4); // Note that the method "operator !=" becomes used here
        assertNotSame(coordinateInstance_4, coordinateInstance_1);
        assertNotEquals(coordinateInstance_1, coordinateInstance_4);
        assertNotEquals(coordinateInstance_4, coordinateInstance_1);
    }


    @Test
    public void toStringTest() {
        CrsCoordinate coordinate = CrsCoordinate.createCoordinate(CrsProjection.SWEREF_99_18_00, 6579457.649, 153369.673);
        assertEquals(
            "CrsCoordinate [ Y: 6579457.649 , X: 153369.673 , CRS: SWEREF_99_18_00(EPSG:3011) ]",
            coordinate.toString()
        );
        CrsCoordinate coordinate2 = CrsCoordinate.createCoordinate(CrsProjection.WGS84, 59.330231, 18.059196);
        final String expectedDefaultToStringResultForCoordinate2 = "CrsCoordinate [ Latitude: 59.330231 , Longitude: 18.059196 , CRS: WGS84(EPSG:4326) ]";
        assertEquals(
            expectedDefaultToStringResultForCoordinate2 ,
            coordinate2.toString()
        );
    }
    private String myCustomToStringMethod(CrsCoordinate coordinate) {
        return String.format(
            "%s , %s",
                coordinate.getLongitudeX(),
                coordinate.getLatitudeY()
        );
    }



}
