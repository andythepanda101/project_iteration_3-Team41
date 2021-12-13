package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.PassengerFactory;
import edu.umn.cs.csci3081w.project.model.RandomPassengerGenerator;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import javax.websocket.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import edu.umn.cs.csci3081w.project.model.Line;
import edu.umn.cs.csci3081w.project.model.StorageFacility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VisualTransitSimulatorTest {

  @BeforeEach
  public void setup() {
    VisualTransitSimulator.setLogging(false);
  }

  /**
   * Testing the Virtual Transit Simulator Constructor
   */
  @Test
  public void testVTSConstructor() {
    try {
      WebServerSession webServerSessionSpy = spy(WebServerSession.class);
      doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
      Session sessionDummy = mock(Session.class);
      webServerSessionSpy.onOpen(sessionDummy);
      VisualTransitSimulator simulator = new VisualTransitSimulator(URLDecoder.decode(getClass().getClassLoader()
          .getResource("config.txt").getFile(), "UTF-8"), webServerSessionSpy);

      List<Vehicle> activeVehicles = simulator.getActiveVehicles();
      assertEquals(0, activeVehicles.size());

      List<Line> lines = simulator.getLines();
      assertEquals(2, lines.size());

    } catch (UnsupportedEncodingException uee) {
      fail();
    }
  }

  /**
   * Testing the VTS Constructor with a null storage facility
   */
  @Test
  public void testVTSConstructorNoStorage() {
    try {
      WebServerSession webServerSessionSpy = spy(WebServerSession.class);
      doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
      Session sessionDummy = mock(Session.class);
      webServerSessionSpy.onOpen(sessionDummy);
      VisualTransitSimulator simulator = new VisualTransitSimulator(URLDecoder.decode(getClass().getClassLoader()
          .getResource("configNoStorage.txt").getFile(), "UTF-8"), webServerSessionSpy);

      List<Vehicle> activeVehicles = simulator.getActiveVehicles();
      assertEquals(0, activeVehicles.size());

      List<Line> lines = simulator.getLines();
      assertEquals(2, lines.size());

      StorageFacility storage = simulator.getStorageFacility();
      assertEquals(Integer.MAX_VALUE, storage.getLargeBusesNum());
      assertEquals(Integer.MAX_VALUE, storage.getSmallBusesNum());
      assertEquals(Integer.MAX_VALUE, storage.getDieselTrainsNum());
      assertEquals(Integer.MAX_VALUE, storage.getElectricTrainsNum());

    } catch (UnsupportedEncodingException uee) {
      fail();
    }
  }

  /**
   * Tests that the Virtual Transit Simulator updates properly
   */
  @Test
  public void testVTSUpdate() {
    try {
      WebServerSession webServerSessionSpy = spy(WebServerSession.class);
      doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
      Session sessionDummy = mock(Session.class);
      webServerSessionSpy.onOpen(sessionDummy);
      VisualTransitSimulator simulator = new VisualTransitSimulator(URLDecoder.decode(getClass().getClassLoader()
          .getResource("config.txt").getFile(), "UTF-8"), webServerSessionSpy);

      List<Integer> vehicleStartTimes = Arrays.asList(0, 0);
      simulator.start(vehicleStartTimes, 10);
      simulator.setVehicleFactories(0);

      List<Vehicle> activeVehicles = simulator.getActiveVehicles();
      assertEquals(0, activeVehicles.size());

      List<Line> lines = simulator.getLines();
      assertEquals(2, lines.size());

      assertEquals(0, simulator.getTimeElapsed());
      assertEquals(0, simulator.getActiveVehicles().size());
      simulator.update();
      assertEquals(1, simulator.getTimeElapsed());
      assertEquals(2, simulator.getActiveVehicles().size());


    } catch (UnsupportedEncodingException uee) {
      fail();
    }
  }

  /**
   * Tests that logging branches work in the constructor and update methods
   */
  @Test
  public void testLogging() {
    try {
      VisualTransitSimulator.setLogging(true);
      WebServerSession webServerSessionSpy = spy(WebServerSession.class);
      doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
      Session sessionDummy = mock(Session.class);
      webServerSessionSpy.onOpen(sessionDummy);
      VisualTransitSimulator simulator = new VisualTransitSimulator(URLDecoder.decode(getClass().getClassLoader()
          .getResource("config.txt").getFile(), "UTF-8"), webServerSessionSpy);

      List<Integer> vehicleStartTimes = Arrays.asList(0, 0);
      simulator.start(vehicleStartTimes, 10);
      simulator.setVehicleFactories(0);

      List<Vehicle> activeVehicles = simulator.getActiveVehicles();
      assertEquals(0, activeVehicles.size());

      List<Line> lines = simulator.getLines();
      assertEquals(2, lines.size());

      assertEquals(0, simulator.getTimeElapsed());
      assertEquals(0, simulator.getActiveVehicles().size());
      simulator.update();
      assertEquals(1, simulator.getTimeElapsed());
      assertEquals(2, simulator.getActiveVehicles().size());


    } catch (UnsupportedEncodingException uee) {
      fail();
    }
  }

  /**
   * Tests that the simulation is not updated when over the maximum number of time steps.
   */
  @Test
  public void testOverTimeSteps() {
    try {
      WebServerSession webServerSessionSpy = spy(WebServerSession.class);
      doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
      Session sessionDummy = mock(Session.class);
      webServerSessionSpy.onOpen(sessionDummy);
      VisualTransitSimulator simulator = new VisualTransitSimulator(URLDecoder.decode(getClass().getClassLoader()
          .getResource("config.txt").getFile(), "UTF-8"), webServerSessionSpy);

      List<Integer> vehicleStartTimes = Arrays.asList(0, 1);
      simulator.start(vehicleStartTimes, 10);
      simulator.setVehicleFactories(0);

      List<Vehicle> activeVehicles = simulator.getActiveVehicles();
      assertEquals(0, activeVehicles.size());

      List<Line> lines = simulator.getLines();
      assertEquals(2, lines.size());

      for (int i = 0; i < 10; i++) {
        simulator.update();
      }
      double distBefore = activeVehicles.get(0).getDistanceRemaining();
      simulator.update();
      assertEquals(distBefore, activeVehicles.get(0).getDistanceRemaining());

    } catch (UnsupportedEncodingException uee) {
      fail();
    }
  }
}