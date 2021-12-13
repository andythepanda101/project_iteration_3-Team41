package edu.umn.cs.csci3081w.project.model;

public class GreenColorDecorator extends VehicleColorDecorator {

  public GreenColorDecorator(Vehicle vehicle) {
    super(vehicle);
    rgbValues = new int[]{60,179,113};
  }

}
