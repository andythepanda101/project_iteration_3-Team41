package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TrainStrategyNightTest {

  /**
   * Test constructor normal.
   */
  @Test
  public void testConstructor() {
    TrainStrategyNight trainStrategyNight = new TrainStrategyNight();
    assertEquals(0, trainStrategyNight.getCounter());
  }

  /**
   * Testing to get correct vehicle according to the strategy.
   */
  @Test
  public void testGetTypeOfVehicle() {
    StorageFacility storageFacility = new StorageFacility(0, 0, 1, 1);
    TrainStrategyNight trainStrategyDay = new TrainStrategyNight();
    String strToCmpr;
    for (int i = 0; i < 1; i++) {
      strToCmpr = trainStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, strToCmpr);
      strToCmpr = trainStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(DieselTrain.DIESEL_TRAIN_VEHICLE, strToCmpr);
    }
  }

  /**
   * Testing to get type of vehicle if storage facility reports no trains.
   */
  @Test
  public void testGetTypeOfVehicleNoTrains() {
    StorageFacility storageFacility = new StorageFacility(0, 0, 0, 0);
    TrainStrategyNight trainStrategyNight = new TrainStrategyNight();
    assertEquals(null, trainStrategyNight.getTypeOfVehicle(storageFacility));
  }

  /**
   * Testing to get type vehicle if there are less vehicles than required for sequence.
   */
  @Test
  public void testGetTypeOfVehicleLessVehicles() {
    StorageFacility storageFacility = new StorageFacility(0, 0, 1, 0);
    TrainStrategyNight trainStrategyDay = new TrainStrategyNight();
    String strToCmpr;
    for (int i = 0; i < 1; i++) {
      strToCmpr = trainStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, strToCmpr);
      strToCmpr = trainStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(null, strToCmpr);
    }
  }
}
