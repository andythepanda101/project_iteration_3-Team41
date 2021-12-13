package edu.umn.cs.csci3081w.project.model;

public class PinkColorDecorator extends VehicleColorDecorator {

  public PinkColorDecorator(Vehicle vehicle) {
    super(vehicle);
    rgbValues = new int[]{239,130,238};
  }

}

