package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

public abstract class VehicleColorDecorator extends Vehicle {
  protected Vehicle vehicle;
  protected int[] rgbValues = new int[]{0, 0, 0};
  protected int alphaValue = 255;

  /**
   * Constructor for a vehicle color decorator and decorates a vehicle.
   *
   * @param vehicle       vehicle to be decorated
   */
  public VehicleColorDecorator(Vehicle vehicle) {
    super(vehicle.getId(), vehicle.getLine(), vehicle.getCapacity(), vehicle.getSpeed(),
        vehicle.getPassengerLoader(), vehicle.getPassengerUnloader());
    this.vehicle = vehicle;
  }

  public void report(PrintStream out) {
    vehicle.report(out);
  }

  public int getCurrentCO2Emission() {
    return vehicle.getCurrentCO2Emission();
  }

  public int[] getrgbValues() {
    return this.rgbValues;
  }

  public int getAlphaValue() {
    return this.alphaValue;
  }


  /**
   * Retrieves the current vehicle being decorated with the RGB and alpha value.
   *
   * @return the vehicle being decorated
   */
  public Vehicle getVehicle() {
    return vehicle;
  }

  /**
   * Update the simulation state for the vehicle being decorated.
   */
  public void update() {
    if (vehicle.getLine().isIssueExist()) {
      alphaValue = 155;
    } else {
      alphaValue = 255;
    }
    super.update();
  }

}
