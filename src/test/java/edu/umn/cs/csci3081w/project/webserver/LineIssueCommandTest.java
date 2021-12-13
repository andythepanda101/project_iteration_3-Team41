package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Line;
import edu.umn.cs.csci3081w.project.model.PassengerFactory;
import edu.umn.cs.csci3081w.project.model.RandomPassengerGenerator;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import javax.websocket.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class LineIssueCommandTest {

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
   * Test execute() in LineIssueCommand
   */
  @Test
  public void testExecute() {

      commandFromClient = new JsonObject();
      commandFromClient.addProperty("id", "10001");
      //Line mockLine = mock(Line.class);
      //simulator.getLines().add(mockLine);
      // starting
      LineIssueCommand lineIssueCommandTest = new LineIssueCommand(simulator);
      LineIssueCommand lineIssueCommandTestSpy = spy(lineIssueCommandTest);
      lineIssueCommandTestSpy.execute(webServerSessionDummy, commandFromClient);
      verify(lineIssueCommandTestSpy).execute(webServerSessionDummy, commandFromClient);
      //verify(mockLine).createIssue();
      assertEquals(true, simulator.getLines().get(1).isIssueExist());

  }
}
