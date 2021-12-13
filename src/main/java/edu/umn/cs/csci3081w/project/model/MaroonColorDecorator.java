package edu.umn.cs.csci3081w.project.model;

public class MaroonColorDecorator extends VehicleColorDecorator {

  public MaroonColorDecorator(Vehicle vehicle) {
    super(vehicle);
    RGBValues = new int[]{122,0,25};
  }

}
