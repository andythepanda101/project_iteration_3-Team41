package edu.umn.cs.csci3081w.project.model;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrainFactoryTest {
  private StorageFacility storageFacility;
  private TrainFactory trainFactoryDay;
  private TrainFactory trainFactoryNight;

  @BeforeEach
  public void setUp() {
    storageFacility = new StorageFacility(0, 0, 3, 3);
    trainFactoryDay = new TrainFactory(storageFacility, new Counter(), 9);
    trainFactoryNight = new TrainFactory(storageFacility, new Counter(), 18);
  }

  /**
   * Testing the constructor for day train factory.
   */
  @Test
  public void testConstructorDay() {
    assertTrue(trainFactoryDay.getGenerationStrategy() instanceof TrainStrategyDay);
  }

  /**
   * Testing the constructor for night train factory.
   */
  @Test
  public void testConstructorNight() {
    assertTrue(trainFactoryNight.getGenerationStrategy() instanceof TrainStrategyNight);

    // checking if times before 8 am also create night factory
    TrainFactory midnight = new TrainFactory(storageFacility, new Counter(), 0);
    assertTrue(midnight.getGenerationStrategy() instanceof TrainStrategyNight);
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

    Line line = new Line(10000, "testLine", "TRAIN", testRouteOut, testRouteIn,
        new Issue());

    // checking if trains are created according to day strategy
    Vehicle vehicle1 = trainFactoryDay.generateVehicle(line);
    assertTrue(vehicle1 instanceof ElectricTrain);
    vehicle1 = trainFactoryDay.generateVehicle(line);
    assertTrue(vehicle1 instanceof ElectricTrain);
    vehicle1 = trainFactoryDay.generateVehicle(line);
    assertTrue(vehicle1 instanceof ElectricTrain);
    vehicle1 = trainFactoryDay.generateVehicle(line);
    assertTrue(vehicle1 instanceof DieselTrain);
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

    Line line = new Line(10000, "testLine", "TRAIN", testRouteOut, testRouteIn,
        new Issue());

    // checking if trains are created according to day strategy
    Vehicle vehicle1 = trainFactoryNight.generateVehicle(line);
    assertTrue(vehicle1 instanceof ElectricTrain);
    vehicle1 = trainFactoryNight.generateVehicle(line);
    assertTrue(vehicle1 instanceof DieselTrain);
    vehicle1 = trainFactoryNight.generateVehicle(line);
    assertTrue(vehicle1 instanceof ElectricTrain);
    vehicle1 = trainFactoryNight.generateVehicle(line);
    assertTrue(vehicle1 instanceof DieselTrain);
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

    Train testTrain = new ElectricTrain(1, new Line(10000, "testLine", "BUS",
        testRouteOut, testRouteIn, new Issue()), 3, 1.0);
    Train testTrain2 = new DieselTrain(1, new Line(10000, "testLine", "BUS",
        testRouteOut, testRouteIn, new Issue()), 3, 1.0);

    assertEquals(3, trainFactoryDay.getStorageFacility().getElectricTrainsNum());
    assertEquals(3, trainFactoryDay.getStorageFacility().getDieselTrainsNum());
    trainFactoryDay.returnVehicle(testTrain);
    assertEquals(4, trainFactoryDay.getStorageFacility().getElectricTrainsNum());
    assertEquals(3, trainFactoryDay.getStorageFacility().getDieselTrainsNum());
    trainFactoryDay.returnVehicle(testTrain2);
    assertEquals(4, trainFactoryDay.getStorageFacility().getElectricTrainsNum());
    assertEquals(4, trainFactoryDay.getStorageFacility().getDieselTrainsNum());

  }

  /**
   * Tests generateVehicle if storage facility is empty.
   */
  @Test
  public void testGenerateVehicleWithEmptyStorageFacility() {
    StorageFacility empty = new StorageFacility(0, 0, 0, 0);
    TrainFactory emptyFactory = new TrainFactory(empty, new Counter(), 9);
    assertEquals(null, emptyFactory.generateVehicle(null));
    emptyFactory.returnVehicle(null);
    assertEquals(0, emptyFactory.getStorageFacility().getElectricTrainsNum());
    assertEquals(0, emptyFactory.getStorageFacility().getDieselTrainsNum());
  }

  /**
   * Tests returning null vehicle.
   */
  @Test
  public void testReturnVehicleNull() {
    trainFactoryDay.returnVehicle(null);
    assertEquals(3, trainFactoryDay.getStorageFacility().getElectricTrainsNum());
    assertEquals(3, trainFactoryDay.getStorageFacility().getDieselTrainsNum());
  }
}
