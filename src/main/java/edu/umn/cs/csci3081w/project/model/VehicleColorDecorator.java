package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

public abstract class VehicleColorDecorator extends Vehicle {
  protected Vehicle vehicle;
  protected int[] RGBValues = new int[]{0,0,0};
  protected int alphaValue = 255;

  public VehicleColorDecorator(Vehicle vehicle) {
    super(vehicle.getId(), vehicle.getLine(), vehicle.getCapacity(), vehicle.getSpeed(), vehicle.getPassengerLoader(), vehicle.getPassengerUnloader());
    this.vehicle = vehicle;
  }

  public void report(PrintStream out) {
    vehicle.report(out);
  }

  public int getCurrentCO2Emission() {
    return vehicle.getCurrentCO2Emission();
  }

  public int[] getRGBValues() {
    return this.RGBValues;
  }

  public int getAlphaValue() {
    return this.alphaValue;
  }

  public Vehicle getVehicle() {
    return vehicle;
  }

  public void update() {
    if (vehicle.getLine().isIssueExist()) {
      alphaValue = 155;
    } else {
      alphaValue = 255;
    }
    super.update();
  }

}
