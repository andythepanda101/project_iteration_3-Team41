package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VehicleColorDecoratorTest {

  private Vehicle testVehicle;
  private Route testRouteIn;
  private Route testRouteOut;


  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    List<Stop> stopsIn = new ArrayList<Stop>();
    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));

    Passenger testPassenger1 = new Passenger(3, "testPassenger1");
    Passenger testPassenger2 = new Passenger(2, "testPassenger2");
    stop2.addPassengers(testPassenger1);
    stop2.addPassengers(testPassenger2);

    stopsIn.add(stop1);
    stopsIn.add(stop2);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.843774422231134);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.025);
    probabilitiesIn.add(0.3);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(stopsIn, probabilitiesIn);

    testRouteIn = new Route(0, "testRouteIn",
        stopsIn, distancesIn, generatorIn);

    List<Stop> stopsOut = new ArrayList<Stop>();
    stopsOut.add(stop2);
    stopsOut.add(stop1);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.843774422231134);
    List<Double> probabilitiesOut = new ArrayList<Double>();
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.025);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(stopsOut, probabilitiesOut);

    testRouteOut = new Route(1, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    testVehicle = new VehicleTestImpl(1, new Line(10000, "testLine",
        "VEHICLE_LINE", testRouteOut, testRouteIn,
        new Issue()), 3, 1.0, new PassengerLoader(), new PassengerUnloader());
  }

  /**
   * Tests constructor.
   */
  @Test
  public void testConstructor() {
    VehicleColorDecorator vehicleColorDecorator = new MaroonColorDecorator(testVehicle);
    assertArrayEquals(new int[]{122, 0, 25}, vehicleColorDecorator.getrgbValues());

    vehicleColorDecorator = new PinkColorDecorator(testVehicle);
    assertArrayEquals(new int[]{239, 130, 238}, vehicleColorDecorator.getrgbValues());

    vehicleColorDecorator = new GreenColorDecorator(testVehicle);
    assertArrayEquals(new int[]{60, 179, 113}, vehicleColorDecorator.getrgbValues());

    vehicleColorDecorator = new YellowColorDecorator(testVehicle);
    assertArrayEquals(new int[]{255, 204, 51}, vehicleColorDecorator.getrgbValues());

    assertEquals(1, vehicleColorDecorator.getId());
  }

  /**
   * Tests if update function works properly.
   */
  @Test
  public void testUpdate() {
    VehicleColorDecorator vehicleColorDecorator = new MaroonColorDecorator(testVehicle);
    vehicleColorDecorator.update();
    assertEquals(255, vehicleColorDecorator.getAlphaValue());

    testVehicle.getLine().createIssue();
    vehicleColorDecorator.update();
    assertEquals(155, vehicleColorDecorator.getAlphaValue());
  }
}
