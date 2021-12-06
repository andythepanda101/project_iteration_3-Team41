package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BusFactoryTest {
  private StorageFacility storageFacility;
  private BusFactory busFactoryDay;
  private BusFactory busFactoryNight;

  @BeforeEach
  public void setUp() {
    storageFacility = new StorageFacility(3, 2, 0, 0);
    busFactoryDay = new BusFactory(storageFacility, new Counter(), 9);
    busFactoryNight = new BusFactory(storageFacility, new Counter(), 18);
  }

  /**
   * Testing the constructor for day train factory.
   */
  @Test
  public void testConstructorDay() {
    assertTrue(busFactoryDay.getGenerationStrategy() instanceof BusStrategyDay);
  }

  /**
   * Testing the constructor for night train factory.
   */
  @Test
  public void testConstructorNight() {
    assertTrue(busFactoryNight.getGenerationStrategy() instanceof BusStrategyNight);

    // checking if times before 8 am also create night factory
    BusFactory midnight = new BusFactory(storageFacility, new Counter(), 0);
    assertTrue(midnight.getGenerationStrategy() instanceof BusStrategyNight);
  }

  /**
   * Testing if generated vehicle is working according to day strategy.
   */
  @Test
  public void testGenerateVehicleDay() {
    List<Stop> stopsIn = new ArrayList<Stop>();
    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.843774422231134);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.025);
    probabilitiesIn.add(0.3);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(stopsIn, probabilitiesIn);

    Route testRouteIn = new Route(0, "testRouteIn",
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

    Route testRouteOut = new Route(1, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    Line line = new Line(10000, "testLine", "BUS", testRouteOut, testRouteIn,
        new Issue());

    // checking if buses are created according to day strategy
    Vehicle vehicle = busFactoryDay.generateVehicle(line);
    assertTrue(vehicle instanceof LargeBus);
    vehicle = busFactoryDay.generateVehicle(line);
    assertTrue(vehicle instanceof LargeBus);
    vehicle = busFactoryDay.generateVehicle(line);
    assertTrue(vehicle instanceof SmallBus);
  }

  /**
   * Testing if generated vehicle is working according to night strategy.
   */
  @Test
  public void testGenerateVehicleNight() {
    List<Stop> stopsIn = new ArrayList<Stop>();
    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.843774422231134);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.025);
    probabilitiesIn.add(0.3);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(stopsIn, probabilitiesIn);

    Route testRouteIn = new Route(0, "testRouteIn",
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

    Route testRouteOut = new Route(1, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    Line line = new Line(10000, "testLine", "BUS", testRouteOut, testRouteIn,
        new Issue());

    Vehicle vehicle = busFactoryNight.generateVehicle(line);
    assertTrue(vehicle instanceof SmallBus);
    vehicle = busFactoryNight.generateVehicle(line);
    assertTrue(vehicle instanceof SmallBus);
    vehicle = busFactoryNight.generateVehicle(line);
    assertTrue(vehicle instanceof SmallBus);
    vehicle = busFactoryNight.generateVehicle(line);
    assertTrue(vehicle instanceof LargeBus);
  }

  /**
   * Testing if vehicle got returned.
   */
  @Test
  public void testReturnVehicle() {
    List<Stop> stopsIn = new ArrayList<Stop>();
    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.843774422231134);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.025);
    probabilitiesIn.add(0.3);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(stopsIn, probabilitiesIn);

    Route testRouteIn = new Route(0, "testRouteIn",
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

    Route testRouteOut = new Route(1, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    Bus testBus = new LargeBus(1, new Line(10000, "testLine", "BUS", testRouteOut, testRouteIn,
        new Issue()), 3, 1.0);

    Bus testBus2 = new SmallBus(1, new Line(10000, "testLine", "BUS", testRouteOut, testRouteIn,
        new Issue()), 3, 1.0);

    assertEquals(3, busFactoryDay.getStorageFacility().getSmallBusesNum());
    assertEquals(2, busFactoryDay.getStorageFacility().getLargeBusesNum());
    busFactoryDay.returnVehicle(testBus);
    assertEquals(3, busFactoryDay.getStorageFacility().getSmallBusesNum());
    assertEquals(3, busFactoryDay.getStorageFacility().getLargeBusesNum());
    busFactoryDay.returnVehicle(testBus2);
    assertEquals(4, busFactoryDay.getStorageFacility().getSmallBusesNum());
    assertEquals(3, busFactoryDay.getStorageFacility().getLargeBusesNum());
  }

  /**
   * Tests generateVehicle if storage facility is empty.
   */
  @Test
  public void testGenerateVehicleWithEmptyStorageFacility() {
    StorageFacility empty = new StorageFacility(0, 0, 0, 0);
    BusFactory emptyFactory = new BusFactory(empty, new Counter(), 9);
    assertEquals(null, emptyFactory.generateVehicle(null));
    emptyFactory.returnVehicle(null);
    assertEquals(0, emptyFactory.getStorageFacility().getSmallBusesNum());
    assertEquals(0, emptyFactory.getStorageFacility().getLargeBusesNum());
  }

  /**
   * Tests returning null vehicle.
   */
  @Test
  public void testReturnVehicleNull() {
    busFactoryDay.returnVehicle(null);
    assertEquals(3, busFactoryDay.getStorageFacility().getSmallBusesNum());
    assertEquals(2, busFactoryDay.getStorageFacility().getLargeBusesNum());
  }
}
