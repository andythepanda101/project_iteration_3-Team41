package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.webserver.WebServerSession;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class VehicleTest {

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
    assertEquals(1, testVehicle.getId());
    assertEquals("testRouteOut1", testVehicle.getName());
    assertEquals(3, testVehicle.getCapacity());
    assertEquals(1, testVehicle.getSpeed());
    assertEquals(testRouteOut, testVehicle.getLine().getOutboundRoute());
    assertEquals(testRouteIn, testVehicle.getLine().getInboundRoute());
  }

  /**
   * Tests if testIsTripComplete function works properly.
   */
  @Test
  public void testIsTripComplete() {
    assertEquals(false, testVehicle.isTripComplete());
    testVehicle.move();
    testVehicle.move();
    testVehicle.move();
    testVehicle.move();
    assertEquals(true, testVehicle.isTripComplete());

  }


  /**
   * Tests if loadPassenger function works properly.
   */
  @Test
  public void testLoadPassenger() {

    Passenger testPassenger1 = new Passenger(3, "testPassenger1");
    Passenger testPassenger2 = new Passenger(2, "testPassenger2");
    Passenger testPassenger3 = new Passenger(1, "testPassenger3");
    Passenger testPassenger4 = new Passenger(1, "testPassenger4");

    assertEquals(1, testVehicle.loadPassenger(testPassenger1));
    assertEquals(1, testVehicle.loadPassenger(testPassenger2));
    assertEquals(1, testVehicle.loadPassenger(testPassenger3));
    assertEquals(0, testVehicle.loadPassenger(testPassenger4));
  }


  /**
   * Tests if move function works properly.
   */
  @Test
  public void testMove() {

    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());
    testVehicle.move();

    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.move();
    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.move();
    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());

    testVehicle.move();
    assertEquals(null, testVehicle.getNextStop());
  }

  /**
   * Tests condition for move for some edge cases.
   * Some of them include larger distance remaining amounts and negative speed.
   */
  @Test
  public void testMoveEdgeCases() {
    testVehicle.move();
    testVehicle.setDistanceRemaining(5.0);
    assertEquals(5.0, testVehicle.getDistanceRemaining());
    testVehicle.move();
    testVehicle.move();
    testVehicle.move();
    assertEquals(2.0, testVehicle.getDistanceRemaining());
    testVehicle.setSpeed(-5.0);
    testVehicle.move();
    assertEquals(2.0, testVehicle.getDistanceRemaining());
    testVehicle.move();
    assertEquals(2.0, testVehicle.getDistanceRemaining());

  }

  /**
   * Checking remaining distance if trip is completed.
   */
  @Test
  public void testMoveTripCompleted() {
    testVehicle.move();
    testVehicle.move();
    testVehicle.move();
    testVehicle.move();
    testVehicle.move();
    assertEquals(999.0, testVehicle.getDistanceRemaining());
  }

  /**
   * Tests if update function works properly.
   */
  @Test
  public void testUpdate() {

    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());
    testVehicle.update();

    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.update();
    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.update();
    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());

    testVehicle.update();
    assertEquals(null, testVehicle.getNextStop());

  }

  /**
   * Tests if update function works properly if there are passengers on stops.
   */
  @Test
  public void testUpdateWithPassengers() {
    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());

    testVehicle.update();
    assertEquals(2, testVehicle.getPassengers().size());
    assertEquals(3, testVehicle.getPassengers().get(0).getDestination());
    assertEquals(1, testVehicle.getPassengers().get(0).getTimeOnVehicle());
    assertEquals(2, testVehicle.getPassengers().get(1).getDestination());
    assertEquals(1, testVehicle.getPassengers().get(1).getTimeOnVehicle());

    testVehicle.update();
    assertEquals(2, testVehicle.getPassengers().size());
  }

  /**
   * Tests if update function works properly if there is an issue on the line.
   */
  @Test
  public void testUpdateWithIssue() {
    testVehicle.update();
    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.getLine().createIssue();
    testVehicle.update();
    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());
  }

  /**
   * Test to see if observer got attached.
   */
  @Test
  public void testProvideInfo() {
    String expectedCommand = "observedVehicle";
    String expectedText = "1" + System.lineSeparator()
        + "-----------------------------" + System.lineSeparator()
        + "* Type: " + System.lineSeparator()
        + "* Position: (-93.235071,44.973580)" + System.lineSeparator()
        + "* Passengers: 2" + System.lineSeparator()
        + "* CO2: 0" + System.lineSeparator();

    WebServerSession webServerSessionSpy = spy(WebServerSession.class);
    doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
    Session sessionDummy = mock(Session.class);
    webServerSessionSpy.onOpen(sessionDummy);

    VehicleConcreteSubject vehicleConcreteSubject =
          new VehicleConcreteSubject(webServerSessionSpy);
    testVehicle.setVehicleSubject(vehicleConcreteSubject);
    testVehicle.update();
    testVehicle.provideInfo();

    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(webServerSessionSpy).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();

    assertEquals(expectedCommand, message.get("command").getAsString());
    assertEquals(expectedText, message.get("text").getAsString());
  }

  /**
   * Tests provided info if the vehicle completed the trip.
   */
  @Test
  public void testProvideInfoTripCompleted() {
    String expectedCommand = "observedVehicle";
    String expectedText = "";

    WebServerSession webServerSessionSpy = spy(WebServerSession.class);
    doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
    Session sessionDummy = mock(Session.class);
    webServerSessionSpy.onOpen(sessionDummy);

    VehicleConcreteSubject vehicleConcreteSubject =
        new VehicleConcreteSubject(webServerSessionSpy);
    testVehicle.setVehicleSubject(vehicleConcreteSubject);
    testVehicle.update();
    testVehicle.update();
    testVehicle.update();
    testVehicle.update();
    testVehicle.provideInfo();

    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(webServerSessionSpy).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();

    assertEquals(expectedCommand, message.get("command").getAsString());
    assertEquals(expectedText, message.get("text").getAsString());
  }

  /**
   * Tests provided info for small bus.
   */
  @Test
  public void testProvideInfoSmallBus() {

    Vehicle smallBus = new SmallBus(1, new Line(10000, "testLine",
        "VEHICLE_LINE", testRouteOut, testRouteIn,
        new Issue()), 20, 0.5);

    String expectedCommand = "observedVehicle";
    String expectedText = "1" + System.lineSeparator()
        + "-----------------------------" + System.lineSeparator()
        + "* Type: SMALL_BUS_VEHICLE" + System.lineSeparator()
        + "* Position: (-93.235071,44.973580)" + System.lineSeparator()
        + "* Passengers: 2" + System.lineSeparator()
        + "* CO2: 3" + System.lineSeparator();

    WebServerSession webServerSessionSpy = spy(WebServerSession.class);
    doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
    Session sessionDummy = mock(Session.class);
    webServerSessionSpy.onOpen(sessionDummy);

    VehicleConcreteSubject vehicleConcreteSubject =
        new VehicleConcreteSubject(webServerSessionSpy);
    smallBus.setVehicleSubject(vehicleConcreteSubject);
    smallBus.update();
    smallBus.provideInfo();

    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(webServerSessionSpy).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();

    assertEquals(expectedCommand, message.get("command").getAsString());
    assertEquals(expectedText, message.get("text").getAsString());
  }

  /**
   * Tests provided info for large bus.
   */
  @Test
  public void testProvideInfoLargeBus() {

    Vehicle largeBus = new LargeBus(1, new Line(10000, "testLine",
        "VEHICLE_LINE", testRouteOut, testRouteIn,
        new Issue()), 80, 0.5);

    String expectedCommand = "observedVehicle";
    String expectedText = "1" + System.lineSeparator()
        + "-----------------------------" + System.lineSeparator()
        + "* Type: LARGE_BUS_VEHICLE" + System.lineSeparator()
        + "* Position: (-93.235071,44.973580)" + System.lineSeparator()
        + "* Passengers: 2" + System.lineSeparator()
        + "* CO2: 5" + System.lineSeparator();

    WebServerSession webServerSessionSpy = spy(WebServerSession.class);
    doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
    Session sessionDummy = mock(Session.class);
    webServerSessionSpy.onOpen(sessionDummy);

    VehicleConcreteSubject vehicleConcreteSubject =
        new VehicleConcreteSubject(webServerSessionSpy);
    largeBus.setVehicleSubject(vehicleConcreteSubject);
    largeBus.update();
    largeBus.provideInfo();

    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(webServerSessionSpy).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();

    assertEquals(expectedCommand, message.get("command").getAsString());
    assertEquals(expectedText, message.get("text").getAsString());
  }

  /**
   * Tests provided info for electric train.
   */
  @Test
  public void testProvideInfoElectricTrain() {

    Vehicle electricTrain = new ElectricTrain(1, new Line(10000, "testLine",
        "VEHICLE_LINE", testRouteOut, testRouteIn,
        new Issue()), 120, 1);

    String expectedCommand = "observedVehicle";
    String expectedText = "1" + System.lineSeparator()
        + "-----------------------------" + System.lineSeparator()
        + "* Type: ELECTRIC_TRAIN_VEHICLE" + System.lineSeparator()
        + "* Position: (-93.235071,44.973580)" + System.lineSeparator()
        + "* Passengers: 2" + System.lineSeparator()
        + "* CO2: 0" + System.lineSeparator();

    WebServerSession webServerSessionSpy = spy(WebServerSession.class);
    doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
    Session sessionDummy = mock(Session.class);
    webServerSessionSpy.onOpen(sessionDummy);

    VehicleConcreteSubject vehicleConcreteSubject =
        new VehicleConcreteSubject(webServerSessionSpy);
    electricTrain.setVehicleSubject(vehicleConcreteSubject);
    electricTrain.update();
    electricTrain.provideInfo();

    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(webServerSessionSpy).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();

    assertEquals(expectedCommand, message.get("command").getAsString());
    assertEquals(expectedText, message.get("text").getAsString());
  }

  /**
   * Tests provided info for diesel train.
   */
  @Test
  public void testProvideInfoDieselTrain() {

    Vehicle dieselTrain = new DieselTrain(1, new Line(10000, "testLine",
        "VEHICLE_LINE", testRouteOut, testRouteIn,
        new Issue()), 120, 1);

    String expectedCommand = "observedVehicle";
    String expectedText = "1" + System.lineSeparator()
        + "-----------------------------" + System.lineSeparator()
        + "* Type: DIESEL_TRAIN_VEHICLE" + System.lineSeparator()
        + "* Position: (-93.235071,44.973580)" + System.lineSeparator()
        + "* Passengers: 2" + System.lineSeparator()
        + "* CO2: 10" + System.lineSeparator();

    WebServerSession webServerSessionSpy = spy(WebServerSession.class);
    doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
    Session sessionDummy = mock(Session.class);
    webServerSessionSpy.onOpen(sessionDummy);

    VehicleConcreteSubject vehicleConcreteSubject =
        new VehicleConcreteSubject(webServerSessionSpy);
    dieselTrain.setVehicleSubject(vehicleConcreteSubject);
    dieselTrain.update();
    dieselTrain.provideInfo();

    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(webServerSessionSpy).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();

    assertEquals(expectedCommand, message.get("command").getAsString());
    assertEquals(expectedText, message.get("text").getAsString());
  }

  /**
   * Tests provided info for correct CO2 history.
   */
  @Test
  public void testProvideCO2History() {

    Vehicle dieselTrain = new DieselTrain(1, new Line(10000, "testLine",
        "VEHICLE_LINE", testRouteOut, testRouteIn,
        new Issue()), 120, 1);

    String expectedCommand = "observedVehicle";
    String expectedText = "1" + System.lineSeparator()
        + "-----------------------------" + System.lineSeparator()
        + "* Type: DIESEL_TRAIN_VEHICLE" + System.lineSeparator()
        + "* Position: (-93.243774,44.972392)" + System.lineSeparator()
        + "* Passengers: 2" + System.lineSeparator()
        + "* CO2: 10, 10, 10" + System.lineSeparator();

    WebServerSession webServerSessionSpy = spy(WebServerSession.class);
    doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
    Session sessionDummy = mock(Session.class);
    webServerSessionSpy.onOpen(sessionDummy);

    VehicleConcreteSubject vehicleConcreteSubject =
        new VehicleConcreteSubject(webServerSessionSpy);
    dieselTrain.setVehicleSubject(vehicleConcreteSubject);
    dieselTrain.update();
    dieselTrain.update();
    dieselTrain.update();

    dieselTrain.provideInfo();

    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(webServerSessionSpy).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();

    assertEquals(expectedCommand, message.get("command").getAsString());
    assertEquals(expectedText, message.get("text").getAsString());
  }

  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    testVehicle = null;
  }

}
