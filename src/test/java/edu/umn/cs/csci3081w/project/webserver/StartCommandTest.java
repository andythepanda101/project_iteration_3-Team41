package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class StartCommandTest {

  private VisualTransitSimulator simulator;
  private WebServerSession webServerSessionDummy;
  private JsonObject commandFromClient;

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
    } catch (UnsupportedEncodingException uee) {
      fail();
    }
  }

  /**
   * Test execute() in StartCommand
   */
  @Test
  public void testExecute() {
    /*
    VisualTransitSimulator simulatorSpy = spy(simulator);
    doNothing().when(simulatorSpy).setVehicleFactories(Mockito.isA(Integer.class));
    doNothing().when(simulatorSpy).start(Mockito.isA(List.class), Mockito.isA(Integer.class));

    // create JSON
    commandFromClient = new JsonObject();
    commandFromClient.addProperty("numTimeSteps", "420");
    commandFromClient.addProperty("timeBetweenVehicles", "5, 6");


    StartCommand startCommandTest = new StartCommand(simulatorSpy);
    StartCommand startCommandSpy = spy(startCommandTest);

    startCommandSpy.execute(webServerSessionDummy, commandFromClient);

    ArgumentCaptor<Integer> messageCaptor1 = ArgumentCaptor.forClass(Integer.class);
    verify(simulatorSpy).setVehicleFactories(messageCaptor1.capture());
    ArgumentCaptor<Integer> messageCaptor2 = ArgumentCaptor.forClass(Integer.class);
    ArgumentCaptor<List> messageCaptor3 = ArgumentCaptor.forClass(List.class);
    verify(simulatorSpy).start(messageCaptor3.capture(), (messageCaptor2.capture()));

    int v1 = messageCaptor1.getValue();
    int v2 = messageCaptor2.getValue();
    List<List> v3 = messageCaptor3.getAllValues();

    assertEquals(420, v1);
    assertEquals(5, v2);
    assertEquals(6, v3); */
  }
}
