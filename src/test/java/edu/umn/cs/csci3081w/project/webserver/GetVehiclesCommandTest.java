package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class GetVehiclesCommandTest {

  private VisualTransitSimulator simulator;
  private WebServerSession webServerSessionSpy;
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
      webServerSessionSpy = spy(WebServerSession.class);
      simulator = new VisualTransitSimulator(
          URLDecoder.decode(getClass().getClassLoader()
              .getResource("config.txt").getFile(), "UTF-8"), webServerSessionSpy);
    } catch (UnsupportedEncodingException uee) {
      fail();
    }
  }

  /**
   * Tests execute with small bus
   */
  @Test
  public void testExecuteSmallBus() {
/*
    commandFromClient = new JsonObject();
    commandFromClient.addProperty("id", "10001");

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

    testBus = new SmallBus(1, new Line(10000, "testLine", "SmallBus", testRouteOut, testRouteIn,
        new Issue()), 3, 1.0);

    simulator.getActiveVehicles().add(new MaroonColorDecorator(testBus));
    VisualTransitSimulator simulatorSpy = spy(simulator);

    GetVehiclesCommand getVehiclesCommand = new GetVehiclesCommand(simulatorSpy);
    GetVehiclesCommand getVehiclesCommandSpy = spy(getVehiclesCommand);

    getVehiclesCommandSpy.execute(webServerSessionSpy, commandFromClient);

    doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(webServerSessionSpy).sendJson(messageCaptor.capture());
    JsonObject commandToClient = messageCaptor.getValue();
    assertEquals("", commandToClient.get("type").getAsString());
*/
  }
}
