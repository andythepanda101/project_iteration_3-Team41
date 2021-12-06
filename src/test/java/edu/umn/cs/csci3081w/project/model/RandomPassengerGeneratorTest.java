package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RandomPassengerGeneratorTest {

  private RandomPassengerGenerator testRandomPassengerGenerator;
  private RandomPassengerGenerator testRandomPassengerGeneratorProbabilities;

  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {

    List<Stop> stops = new ArrayList<Stop>();
    Stop testStop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop testStop2 = new Stop(1, "test stop 2", new Position(-93.25631, 44.963211));

    stops.add(testStop1);
    stops.add(testStop2);

    List<Double> probabilities = new ArrayList<Double>();
    probabilities.add(.15);
    testRandomPassengerGenerator = new RandomPassengerGenerator(stops, probabilities);

    List<Double> probabilitiesM = new ArrayList<Double>();
    probabilitiesM.add(.60);
    probabilitiesM.add(.30);
    probabilitiesM.add(.15);
    testRandomPassengerGeneratorProbabilities = new RandomPassengerGenerator(stops, probabilitiesM);

  }

  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {

    assertEquals(0, testRandomPassengerGenerator.getStops()
        .get(0).getId());
    assertEquals("test stop 1", testRandomPassengerGenerator.getStops()
        .get(0).getName());
    assertEquals(-93.243774, testRandomPassengerGenerator.getStops()
        .get(0).getPosition().getLongitude());
    assertEquals(44.972392, testRandomPassengerGenerator.getStops()
        .get(0).getPosition().getLatitude());
    assertEquals(.15, testRandomPassengerGenerator.getProbabilities()
        .get(0));

  }

  /**
   * Test generatePassengers function.
   */
  @Test
  public void testGeneratePassengers() {
    testRandomPassengerGenerator.DETERMINISTIC = true;
    assertEquals(1, testRandomPassengerGenerator.generatePassengers());
  }

  /**
   * Test generatePassengers function with more probabilities.
   */
  @Test
  public void testGeneratePassengersMoreProbabilities() {
    testRandomPassengerGeneratorProbabilities.DETERMINISTIC = true;
    assertEquals(4, testRandomPassengerGeneratorProbabilities.generatePassengers());
    assertEquals(4, testRandomPassengerGeneratorProbabilities.generatePassengers());
    assertEquals(4, testRandomPassengerGeneratorProbabilities.generatePassengers());
  }

  /**
   * Test generatePassengers function if it's not deterministic.
   * Deterministic test with our configuration always generates exactly 1 passenger,
   * so, to test if the non-deterministic version is working, we can check the values
   * we get on 50 different trials. If any of then is not 1, then it already deviates
   * from the deterministic version.
   */
  @Test
  public void testGeneratePassengerNonDeterministic() {
    testRandomPassengerGeneratorProbabilities.DETERMINISTIC = false;
    boolean same = true;
    for (int i = 0; i < 50; i++) {
      if (testRandomPassengerGenerator.generatePassengers() != 1) {
        same = false;
        break;
      }
    }
    assertEquals(false, same);
  }

  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    testRandomPassengerGenerator = null;
  }


}
