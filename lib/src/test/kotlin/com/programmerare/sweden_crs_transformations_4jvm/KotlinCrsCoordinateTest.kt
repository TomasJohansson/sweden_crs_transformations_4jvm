package com.programmerare.sweden_crs_transformations_4jvm

import com.programmerare.sweden_crs_transformations_4jvm.JavaCrsProjectionTest.epsgNumberForSweref99tm
import org.junit.Test
import org.junit.Assert.*

class KotlinCrsCoordinateTest {
    // https://kartor.eniro.se/m/XRCfh
    //WGS84 decimal (lat, lon)      59.330231, 18.059196
    //RT90 (nord, öst)              6580994, 1628294
    //SWEREF99 TM (nord, öst)       6580822, 674032
    private val stockholmCentralStation_WGS84_latitude = 59.330231
    private val stockholmCentralStation_WGS84_longitude = 18.059196
    private val stockholmCentralStation_RT90_northing = 6580994.0
    private val stockholmCentralStation_RT90_easting = 1628294.0
    private val stockholmCentralStation_SWEREF99TM_northing = 6580822.0
    private val stockholmCentralStation_SWEREF99TM_easting = 674032.0

    @Test
    fun transform() {
        val stockholmWGS84: CrsCoordinate = CrsCoordinate.createCoordinate(
            CrsProjection.WGS84,
            stockholmCentralStation_WGS84_latitude,
            stockholmCentralStation_WGS84_longitude
        )
        val stockholmSWEREF99TM: CrsCoordinate = CrsCoordinate.createCoordinate(
            CrsProjection.SWEREF_99_TM,
            stockholmCentralStation_SWEREF99TM_northing,
            stockholmCentralStation_SWEREF99TM_easting
        )
        val stockholmRT90: CrsCoordinate = CrsCoordinate.createCoordinate(
            CrsProjection.RT90_2_5_GON_V,
            stockholmCentralStation_RT90_northing,
            stockholmCentralStation_RT90_easting
        )

        // Transformations to WGS84 (from SWEREF99TM and RT90):
        assertEqual(
            stockholmWGS84,  // expected WGS84
            stockholmSWEREF99TM.transform(CrsProjection.WGS84) // actual/transformed WGS84
        )
        assertEqual(
            stockholmWGS84,  // expected WGS84
            stockholmRT90.transform(CrsProjection.WGS84) // actual/transformed WGS84
        )
        // below is a similar test as one of the above tests but using the overloaded transform method
        // which takes an integer as parameter instead of an instance of the enum CrsProjection
        val epsgNumberForWgs84: Int = CrsProjection.WGS84.epsgNumber
        assertEqual(
            stockholmWGS84,
            stockholmRT90.transform(epsgNumberForWgs84) // testing the overloaded transform method with an integer parameter
        )


        // Transformations to SWEREF99TM (from WGS84 and RT90):
        assertEqual(
            stockholmSWEREF99TM,  // expected SWEREF99TM
            stockholmWGS84.transform(CrsProjection.SWEREF_99_TM) // actual/transformed SWEREF99TM
        )
        assertEqual(
            stockholmSWEREF99TM,  // expected SWEREF99TM
            stockholmRT90.transform(CrsProjection.SWEREF_99_TM) // actual/transformed SWEREF99TM
        )


        // Transformations to RT90 (from WGS84 and SWEREF99TM):
        assertEqual(
            stockholmRT90,  // expected RT90
            stockholmWGS84.transform(CrsProjection.RT90_2_5_GON_V) // actual/transformed RT90
        )
        assertEqual(
            stockholmRT90,  // expected RT90
            stockholmSWEREF99TM.transform(CrsProjection.RT90_2_5_GON_V) // actual/transformed RT90
        )
    }

    private fun assertEqual(crsCoordinate_1: CrsCoordinate, crsCoordinate_2: CrsCoordinate) {
        val messageToDisplayIfAssertionFails =
            "crsCoordinate_1: $crsCoordinate_1 , crsCoordinate_2 : $crsCoordinate_2"
        assertEquals(
            messageToDisplayIfAssertionFails,
            crsCoordinate_1.crsProjection,
            crsCoordinate_2.crsProjection
        )
        val maxDifference =
            if (crsCoordinate_1.crsProjection.isWgs84) 0.000007 else 0.5 // the other (i.e. non-WGS84) value is using meter as unit, so 0.5 is just five decimeters difference
        val diffLongitude: Double =
            Math.abs(crsCoordinate_1.longitudeX - crsCoordinate_2.longitudeX)
        val diffLatitude: Double =
            Math.abs(crsCoordinate_1.latitudeY - crsCoordinate_2.latitudeY)
        assertTrue(messageToDisplayIfAssertionFails, diffLongitude < maxDifference)
        assertTrue(messageToDisplayIfAssertionFails, diffLatitude < maxDifference)
    }


    @Test
    fun createCoordinateByEpsgNumber() {
        val x = 20.0
        val y = 60.0
        val crsCoordinate: CrsCoordinate = CrsCoordinate.createCoordinate(
            epsgNumberForSweref99tm,
            y,
            x
        )
        assertEquals(CrsProjection.SWEREF_99_TM, crsCoordinate.crsProjection)
        assertEquals(
            epsgNumberForSweref99tm,
            crsCoordinate.crsProjection.epsgNumber
        )
        val delta = 0.000001
        assertEquals(x, crsCoordinate.longitudeX, delta)
        assertEquals(y, crsCoordinate.latitudeY, delta)
    }

    @Test
    fun createCoordinate() {
        val x = 22.5
        val y = 62.5
        val crsCoordinate: CrsCoordinate =
            CrsCoordinate.createCoordinate(CrsProjection.SWEREF_99_TM, y, x)
        
        assertEquals(
            epsgNumberForSweref99tm,
            crsCoordinate.crsProjection.epsgNumber
        )
        assertEquals(
            CrsProjection.SWEREF_99_TM,
            crsCoordinate.crsProjection
        )
        val delta = 0.000001
        assertEquals(x, crsCoordinate.longitudeX, delta)
        assertEquals(y, crsCoordinate.latitudeY, delta)
    }


    @Test
    fun equalityTest() {
        val coordinateInstance_1: CrsCoordinate = CrsCoordinate.createCoordinate(
            CrsProjection.WGS84,
            stockholmCentralStation_WGS84_longitude,
            stockholmCentralStation_WGS84_latitude
        )
        val coordinateInstance_2: CrsCoordinate = CrsCoordinate.createCoordinate(
            CrsProjection.WGS84,
            stockholmCentralStation_WGS84_longitude,
            stockholmCentralStation_WGS84_latitude
        )
        assertEquals(coordinateInstance_1, coordinateInstance_2)
        assertEquals(
            coordinateInstance_1.hashCode(),
            coordinateInstance_2.hashCode()
        )
        assertTrue(coordinateInstance_1 == coordinateInstance_2)
        assertTrue(coordinateInstance_2 == coordinateInstance_1)
        var delta = 0.000000000000001 // see comments further below regarding the value of "delta"
        val coordinateInstance_3: CrsCoordinate = CrsCoordinate.createCoordinate(
            CrsProjection.WGS84,
            stockholmCentralStation_WGS84_longitude + delta,
            stockholmCentralStation_WGS84_latitude + delta
        )
        assertEquals(coordinateInstance_1, coordinateInstance_3)
        assertEquals(
            coordinateInstance_1.hashCode(),
            coordinateInstance_3.hashCode()
        )
        assertTrue(coordinateInstance_1 == coordinateInstance_3)
        assertTrue(coordinateInstance_3 == coordinateInstance_1)

        // Regarding the chosen value for "delta" (which is added to the lon/lat values, to create a slightly different value) above and below,
        // it is because of experimentation this "breakpoint" value has been determined, i.e. the above value still resulted in equality 
        // but when it was increased as below with one decimal then the above kind of assertions failed and therefore 
        // the other assertions below are used instead.
        // You should generally be cautious when comparing floating point values but the above test indicate that values are considered equal even though 
        // the difference is as 'big' as in the "delta" value above.

        // Note that the chosen values for the 'delta' above and below has been determined by experimentation 
        // to see where it succeeds/fails, and it was originally implemented for C#.NET
        // but later has been verified that those 'breakpoint' delta values are the same for this JVM/Java implementation. 
        delta = delta * 10 // moving the decimal one bit to get a somewhat larger values, and then the instances are not considered equal, as you can see in the tests below.
        val coordinateInstance_4: CrsCoordinate = CrsCoordinate.createCoordinate(
            CrsProjection.WGS84,
            stockholmCentralStation_WGS84_longitude + delta,
            stockholmCentralStation_WGS84_latitude + delta
        )
        // Note that below are the Are*NOT*Equal assertions made instead of AreEqual as further above when a smaller delta value was used
        assertNotEquals(coordinateInstance_1, coordinateInstance_4)
        assertNotEquals(
            coordinateInstance_1.hashCode(),
            coordinateInstance_4.hashCode()
        )
        assertNotSame(
            coordinateInstance_1,
            coordinateInstance_4
        ) // Note that the method "operator !=" becomes used here
        assertNotSame(coordinateInstance_4, coordinateInstance_1)
        assertNotEquals(coordinateInstance_1, coordinateInstance_4)
        assertNotEquals(coordinateInstance_4, coordinateInstance_1)
    }


    @Test
    fun toStringTest() {
        val coordinate: CrsCoordinate =
            CrsCoordinate.createCoordinate(CrsProjection.SWEREF_99_18_00, 6579457.649, 153369.673)
        assertEquals(
            "CrsCoordinate [ Y: 6579457.649 , X: 153369.673 , CRS: SWEREF_99_18_00(EPSG:3011) ]",
            coordinate.toString()
        )
        
        val coordinate2: CrsCoordinate =
            CrsCoordinate.createCoordinate(CrsProjection.WGS84, 59.330231, 18.059196)
        val expectedDefaultToStringResultForCoordinate2 =
            "CrsCoordinate [ Latitude: 59.330231 , Longitude: 18.059196 , CRS: WGS84(EPSG:4326) ]"
        assertEquals(
            expectedDefaultToStringResultForCoordinate2,
            coordinate2.toString()
        )
        // TODO some example with a Kotlin extension method customizing a String representation
//        assertEquals(
//            "18.059196 , 59.330231",
//            coordinate2.getAsString() // TODO implement some example Kotlin extension method like this 
//        )
    }
    private fun myCustomToStringMethod(coordinate: CrsCoordinate): String {
        return "${coordinate.longitudeX} , ${coordinate.latitudeY}"
    }
}
