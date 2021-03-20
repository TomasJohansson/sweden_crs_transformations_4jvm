package com.programmerare.sweden_crs_transformations_4jvm

import com.programmerare.sweden_crs_transformations_4jvm.JavaCrsProjectionFactoryTest.epsgNumberForSweref99tm
import org.junit.Test
import org.junit.Assert._
import scala.jdk.CollectionConverters._ // asScala

class ScalaCrsCoordinateTest {
  // https://kartor.eniro.se/m/XRCfh
  //WGS84 decimal (lat, lon)      59.330231, 18.059196
  //RT90 (nord, öst)              6580994, 1628294
  //SWEREF99 TM (nord, öst)       6580822, 674032
  private val stockholmCentralStation_WGS84_latitude: Double = 59.330231
  private val stockholmCentralStation_WGS84_longitude: Double = 18.059196
  private val stockholmCentralStation_RT90_northing: Double = 6580994
  private val stockholmCentralStation_RT90_easting: Double = 1628294
  private val stockholmCentralStation_SWEREF99TM_northing: Double = 6580822
  private val stockholmCentralStation_SWEREF99TM_easting: Double = 674032

  @Test
  def transform(): Unit = {
    val stockholmWGS84: CrsCoordinate = CrsCoordinate.createCoordinate(CrsProjection.WGS84, stockholmCentralStation_WGS84_latitude, stockholmCentralStation_WGS84_longitude)
    val stockholmSWEREF99TM: CrsCoordinate = CrsCoordinate.createCoordinate(CrsProjection.SWEREF_99_TM, stockholmCentralStation_SWEREF99TM_northing, stockholmCentralStation_SWEREF99TM_easting)
    val stockholmRT90: CrsCoordinate = CrsCoordinate.createCoordinate(CrsProjection.RT90_2_5_GON_V, stockholmCentralStation_RT90_northing, stockholmCentralStation_RT90_easting)
    // Transformations to WGS84 (from SWEREF99TM and RT90):
    assertEqual(
      stockholmWGS84, // expected WGS84
      stockholmSWEREF99TM.transform(CrsProjection.WGS84) // actual/transformed WGS84)
    )
    assertEqual(
      stockholmWGS84,
      stockholmRT90.transform(CrsProjection.WGS84)
    )
    // below is a similar test as one of the above tests but using the overloaded transform method
    // which takes an integer as parameter instead of an instance of the enum CrsProjection
    val epsgNumberForWgs84: Int = CrsProjection.WGS84.getEpsgNumber
    assertEqual(stockholmWGS84, stockholmRT90.transform(epsgNumberForWgs84)) // testing the overloaded transform method with an integer parameter)

    // Transformations to SWEREF99TM (from WGS84 and RT90):
    assertEqual(
      stockholmSWEREF99TM, // expected SWEREF99TM
      stockholmWGS84.transform(CrsProjection.SWEREF_99_TM) // actual/transformed SWEREF99TM)
    )
    assertEqual(
      stockholmSWEREF99TM,
      stockholmRT90.transform(CrsProjection.SWEREF_99_TM)
    )
    
    // Transformations to RT90 (from WGS84 and SWEREF99TM):
    assertEqual(
      stockholmRT90, // expected RT90
      stockholmWGS84.transform(CrsProjection.RT90_2_5_GON_V) // actual/transformed RT90)
    )
    assertEqual(
      stockholmRT90,
      stockholmSWEREF99TM.transform(CrsProjection.RT90_2_5_GON_V)
    )
  }

  private def assertEqual(crsCoordinate_1: CrsCoordinate, crsCoordinate_2: CrsCoordinate): Unit = {
    val messageToDisplayIfAssertionFails: String = "crsCoordinate_1: " + crsCoordinate_1 + " , crsCoordinate_2 : " + crsCoordinate_2
    assertEquals(messageToDisplayIfAssertionFails, crsCoordinate_1.getCrsProjection, crsCoordinate_2.getCrsProjection)
    val maxDifference: Double = if (crsCoordinate_1.getCrsProjection.isWgs84) {
      0.000007
    }
    else {
      0.5
    } // the other (i.e. non-WGS84) value is using meter as unit, so 0.5 is just five decimeters difference
    val diffLongitude: Double = Math.abs((crsCoordinate_1.getLongitudeX - crsCoordinate_2.getLongitudeX))
    val diffLatitude: Double = Math.abs((crsCoordinate_1.getLatitudeY - crsCoordinate_2.getLatitudeY))
    assertTrue(messageToDisplayIfAssertionFails, diffLongitude < maxDifference)
    assertTrue(messageToDisplayIfAssertionFails, diffLatitude < maxDifference)
  }


  @Test
  def createCoordinateByEpsgNumber(): Unit = {
    val x: Double = 20.0
    val y: Double = 60.0
    val crsCoordinate: CrsCoordinate = CrsCoordinate.createCoordinate(epsgNumberForSweref99tm, y, x)
    assertEquals(CrsProjection.SWEREF_99_TM, crsCoordinate.getCrsProjection)
    assertEquals(epsgNumberForSweref99tm, crsCoordinate.getCrsProjection.getEpsgNumber)
    val delta: Double = 0.000001
    assertEquals(x, crsCoordinate.getLongitudeX, delta)
    assertEquals(y, crsCoordinate.getLatitudeY, delta)
  }

  @Test
  def createCoordinate(): Unit = {
    val x: Double = 22.5
    val y: Double = 62.5
    val crsCoordinate: CrsCoordinate = CrsCoordinate.createCoordinate(CrsProjection.SWEREF_99_TM, y, x)
    assertEquals(epsgNumberForSweref99tm, crsCoordinate.getCrsProjection.getEpsgNumber)
    assertEquals(CrsProjection.SWEREF_99_TM, crsCoordinate.getCrsProjection)
    val delta: Double = 0.000001
    assertEquals(x, crsCoordinate.getLongitudeX, delta)
    assertEquals(y, crsCoordinate.getLatitudeY, delta)
  }


  @Test
  def equalityTest(): Unit = {
    val coordinateInstance_1: CrsCoordinate = CrsCoordinate.createCoordinate(CrsProjection.WGS84, stockholmCentralStation_WGS84_longitude, stockholmCentralStation_WGS84_latitude)
    val coordinateInstance_2: CrsCoordinate = CrsCoordinate.createCoordinate(CrsProjection.WGS84, stockholmCentralStation_WGS84_longitude, stockholmCentralStation_WGS84_latitude)
    assertEquals(coordinateInstance_1, coordinateInstance_2)
    assertEquals(coordinateInstance_1.hashCode, coordinateInstance_2.hashCode)
    assertTrue(coordinateInstance_1 == coordinateInstance_2)
    assertTrue(coordinateInstance_2 == coordinateInstance_1)
    var delta: Double = 0.000000000000001 // see comments further below regarding the value of "delta"
    val coordinateInstance_3: CrsCoordinate = CrsCoordinate.createCoordinate(CrsProjection.WGS84, stockholmCentralStation_WGS84_longitude + delta, stockholmCentralStation_WGS84_latitude + delta)
    assertEquals(coordinateInstance_1, coordinateInstance_3)
    assertEquals(coordinateInstance_1.hashCode, coordinateInstance_3.hashCode)
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

    val coordinateInstance_4: CrsCoordinate = CrsCoordinate.createCoordinate(CrsProjection.WGS84, stockholmCentralStation_WGS84_longitude + delta, stockholmCentralStation_WGS84_latitude + delta)
    // Note that below are the Are*NOT*Equal assertions made instead of AreEqual as further above when a smaller delta value was used
    assertNotEquals(coordinateInstance_1, coordinateInstance_4)
    assertNotEquals(coordinateInstance_1.hashCode, coordinateInstance_4.hashCode)
    assertNotSame(coordinateInstance_1, coordinateInstance_4) // Note that the method "operator !=" becomes used here

    assertNotSame(coordinateInstance_4, coordinateInstance_1)
    assertNotEquals(coordinateInstance_1, coordinateInstance_4)
    assertNotEquals(coordinateInstance_4, coordinateInstance_1)
  }

  @Test
  def toStringTest(): Unit = {
    val coordinate: CrsCoordinate = CrsCoordinate.createCoordinate(
      CrsProjection.SWEREF_99_18_00,
      6579457.649,
      153369.673
    )
    assertEquals(
      "CrsCoordinate [ Y: 6579457.649 , X: 153369.673 , CRS: SWEREF_99_18_00 ]",
      coordinate.toString
    )
    val coordinate2: CrsCoordinate = CrsCoordinate.createCoordinate(CrsProjection.WGS84, 59.330231, 18.059196)
    val expectedDefaultToStringResultForCoordinate2: String = "CrsCoordinate [ Latitude: 59.330231 , Longitude: 18.059196 , CRS: WGS84 ]"
    assertEquals(
      expectedDefaultToStringResultForCoordinate2,
      coordinate2.toString
    )
    // now testing the same coordinate as above but with a custom 'toString' implementation
    CrsCoordinate.setToStringImplementation(this.myCustomToStringMethod)
    assertEquals("18.059196 , 59.330231", coordinate2.toString)
    CrsCoordinate.setToStringImplementationDefault() // restores the default 'toString' implementation
    assertEquals(expectedDefaultToStringResultForCoordinate2, coordinate2.toString)
  }

  private def myCustomToStringMethod(coordinate: CrsCoordinate): String = {
    s"${coordinate.getLongitudeX} , ${coordinate.getLatitudeY}"
    // String.format("%s , %s", coordinate.getLongitudeX, coordinate.getLatitudeY)
  }


  // This is not really a "Test" method used for assertions, but can be used for code examples 
  // e.g. verify that this code below works and then it can be paste into some example page at github
  //@Test
  def example(): Unit = {
    val stockholmWGS84: CrsCoordinate = CrsCoordinate.createCoordinate(CrsProjection.WGS84, stockholmCentralStation_WGS84_latitude, stockholmCentralStation_WGS84_longitude)
    val stockholmSweref99tm: CrsCoordinate = stockholmWGS84.transform(CrsProjection.SWEREF_99_TM)
    println("stockholmSweref99tm X: " + stockholmSweref99tm.getLongitudeX)
    println("stockholmSweref99tm Y: " + stockholmSweref99tm.getLatitudeY)
    println("stockholmSweref99tm 'ToString': " + stockholmSweref99tm.toString)
    // Output from the above:
    //stockholmSweref99tm X: 674032.357
    //stockholmSweref99tm Y: 6580821.991
    //stockholmSweref99tm 'ToString': CrsCoordinate [ Y: 6580821.991 , X: 674032.357 , CRS: SWEREF_99_TM ]
    val allProjections: List[CrsProjection] = CrsProjectionFactory.getAllCrsProjections.asScala.toList
    for (crsProjection <- allProjections) {
      println(stockholmWGS84.transform(crsProjection))
    }
    // Output from the above loop:
    //CrsCoordinate [ Latitude: 59.330231 , Longitude: 18.059196 , CRS: WGS84 ]
    //CrsCoordinate [ Y: 6580821.991 , X: 674032.357 , CRS: SWEREF_99_TM ]
    //CrsCoordinate [ Y: 6595151.116 , X: 494604.69 , CRS: SWEREF_99_12_00 ]
    //CrsCoordinate [ Y: 6588340.147 , X: 409396.217 , CRS: SWEREF_99_13_30 ]
    //CrsCoordinate [ Y: 6583455.373 , X: 324101.998 , CRS: SWEREF_99_15_00 ]
    //CrsCoordinate [ Y: 6580494.921 , X: 238750.424 , CRS: SWEREF_99_16_30 ]
    //CrsCoordinate [ Y: 6579457.649 , X: 153369.673 , CRS: SWEREF_99_18_00 ]
    //CrsCoordinate [ Y: 6585657.12 , X: 366758.045 , CRS: SWEREF_99_14_15 ]
    //CrsCoordinate [ Y: 6581734.696 , X: 281431.616 , CRS: SWEREF_99_15_45 ]
    //CrsCoordinate [ Y: 6579735.93 , X: 196061.94 , CRS: SWEREF_99_17_15 ]
    //CrsCoordinate [ Y: 6579660.051 , X: 110677.129 , CRS: SWEREF_99_18_45 ]
    //CrsCoordinate [ Y: 6581507.028 , X: 25305.238 , CRS: SWEREF_99_20_15 ]
    //CrsCoordinate [ Y: 6585277.577 , X: -60025.629 , CRS: SWEREF_99_21_45 ]
    //CrsCoordinate [ Y: 6590973.148 , X: -145287.219 , CRS: SWEREF_99_23_15 ]
    //CrsCoordinate [ Y: 6598325.639 , X: 1884004.1 , CRS: RT90_7_5_GON_V ]
    //CrsCoordinate [ Y: 6587493.237 , X: 1756244.287 , CRS: RT90_5_0_GON_V ]
    //CrsCoordinate [ Y: 6580994.18 , X: 1628293.886 , CRS: RT90_2_5_GON_V ]
    //CrsCoordinate [ Y: 6578822.84 , X: 1500248.374 , CRS: RT90_0_0_GON_V ]
    //CrsCoordinate [ Y: 6580977.349 , X: 1372202.721 , CRS: RT90_2_5_GON_O ]
    //CrsCoordinate [ Y: 6587459.595 , X: 1244251.702 , CRS: RT90_5_0_GON_O ]
  }  
}
