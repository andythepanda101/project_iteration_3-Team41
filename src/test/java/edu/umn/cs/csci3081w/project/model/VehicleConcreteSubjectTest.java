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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class VehicleConcreteSubjectTest {

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
    VehicleConcreteSubject vehicleConcreteSubject =
        new VehicleConcreteSubject(new WebServerSession());
    assertEquals(0, vehicleConcreteSubject.getObservers().size());
  }

  /**
   * Tests attach observer.
   */
  @Test
  public void testAttachObserver() {
    VehicleConcreteSubject vehicleConcreteSubject =
        new VehicleConcreteSubject(new WebServerSession());
    vehicleConcreteSubject.attachObserver(testVehicle);
    assertEquals(1, vehicleConcreteSubject.getObservers().size());
  }

  /**
   * Tests detach observer.
   */
  @Test
  public void testDetachObserver() {
    VehicleConcreteSubject vehicleConcreteSubject =
        new VehicleConcreteSubject(new WebServerSession());
    vehicleConcreteSubject.attachObserver(testVehicle);
    vehicleConcreteSubject.detachObserver(testVehicle);
    assertEquals(0, vehicleConcreteSubject.getObservers().size());
  }

  /**
   * Tests detach observer.
   */
  @Test
  public void testNotifyObservers() {
    String expectedCommand = "observedVehicle";
    String expectedText = "1" + System.lineSeparator()
        + "-----------------------------" + System.lineSeparator()
        + "* Type: " + System.lineSeparator()
        + "* Position: (-93.235071,44.973580)" + System.lineSeparator()
        + "* Passengers: 0" + System.lineSeparator()
        + "* CO2: 0" + System.lineSeparator();

    WebServerSession webServerSessionSpy = spy(WebServerSession.class);
    doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
    Session sessionDummy = mock(Session.class);
    webServerSessionSpy.onOpen(sessionDummy);

    VehicleConcreteSubject vehicleConcreteSubject =
        new VehicleConcreteSubject(webServerSessionSpy);
    vehicleConcreteSubject.attachObserver(testVehicle);
    testVehicle.update();
    vehicleConcreteSubject.notifyObservers();

    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(webServerSessionSpy).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();

    assertEquals(expectedCommand, message.get("command").getAsString());
    assertEquals(expectedText, message.get("text").getAsString());
  }

  /**
   * Tests notify observer if trip is completed.
   */
  @Test
  public void testNotifyObserversTripCompleted() {
    String expectedCommand = "observedVehicle";
    String expectedText = "";

    WebServerSession webServerSessionSpy = spy(WebServerSession.class);
    doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
    Session sessionDummy = mock(Session.class);
    webServerSessionSpy.onOpen(sessionDummy);

    VehicleConcreteSubject vehicleConcreteSubject =
        new VehicleConcreteSubject(webServerSessionSpy);
    vehicleConcreteSubject.attachObserver(testVehicle);
    testVehicle.update();
    testVehicle.update();
    testVehicle.update();
    testVehicle.update();
    vehicleConcreteSubject.notifyObservers();

    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(webServerSessionSpy).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();

    assertEquals(expectedCommand, message.get("command").getAsString());
    assertEquals(expectedText, message.get("text").getAsString());
    assertEquals(0, vehicleConcreteSubject.getObservers().size());
  }

}
