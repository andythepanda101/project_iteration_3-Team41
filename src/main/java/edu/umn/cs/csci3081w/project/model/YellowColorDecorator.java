package edu.umn.cs.csci3081w.project.model;

public class YellowColorDecorator extends VehicleColorDecorator {

  public YellowColorDecorator(Vehicle vehicle) {
    super(vehicle);
    rgbValues = new int[]{255, 204, 51};
  }

}
