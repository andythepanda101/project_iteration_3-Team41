package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import javax.websocket.Session;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class RegisterVehicleCommandTest {

  private VisualTransitSimulator simulator;
  private WebServerSession webServerSessionDummy;
  private JsonObject commandFromClient;
  private Bus testBus;
  private Route testRouteIn;
  private Route testRouteOut;

  /**
   * Setup mocks for testing Commands
   */
  @BeforeEach
  public void setUp() {
    // catch error when reading in config.txt
    try {
      // create simulator used in initializing LineIssueCommand
      // for testing execute()
      // create dummy webServerSession
      webServerSessionDummy = mock(WebServerSession.class);
      simulator = new VisualTransitSimulator(
          URLDecoder.decode(getClass().getClassLoader()
              .getResource("config.txt").getFile(), "UTF-8"), webServerSessionDummy);

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

      testBus = new SmallBus(1, new Line(10000, "testLine", "BUS", testRouteOut, testRouteIn,
          new Issue()), 3, 1.0);

      simulator.getActiveVehicles().add(testBus);

    } catch (UnsupportedEncodingException uee) {
      fail();
    }
  }

  /**
   * Test execute() in RegisterVehicleCommand
   */
  @Test
  public void testExecute() {
    VisualTransitSimulator simulatorSpy = spy(simulator);
    doNothing().when(simulatorSpy).addObserver(Mockito.isA(Vehicle.class));

    // create JSON
    commandFromClient = new JsonObject();
    commandFromClient.addProperty("id", "1");

    // starting
    RegisterVehicleCommand registerVehicleCommandTest = new RegisterVehicleCommand(simulatorSpy);
    RegisterVehicleCommand registerVehicleCommandTestSpy = spy(registerVehicleCommandTest);

    registerVehicleCommandTestSpy.execute(webServerSessionDummy, commandFromClient);

    ArgumentCaptor<Vehicle> messageCaptor = ArgumentCaptor.forClass(Vehicle.class);
    verify(simulatorSpy).addObserver(messageCaptor.capture());
    Vehicle addedVehicle = messageCaptor.getValue();
    assertEquals(1, addedVehicle.getId());

  }
}
