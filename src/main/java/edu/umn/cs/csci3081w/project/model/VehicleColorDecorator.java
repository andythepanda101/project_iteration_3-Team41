package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

public abstract class VehicleColorDecorator extends Vehicle {
  protected Vehicle vehicle;
  protected int[] rgbValues = new int[]{0,0,0};
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

  public int[] getRgbValues() {
    return this.rgbValues;
  }

  public int getAlphaValue() {
    return this.alphaValue;
  }

  public void setAlphaValue(int val) {
    this.alphaValue = val;
  }

  public Vehicle getVehicle() {
    return vehicle;
  }
}
