package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.DieselTrain;
import edu.umn.cs.csci3081w.project.model.ElectricTrain;
import edu.umn.cs.csci3081w.project.model.LargeBus;
import edu.umn.cs.csci3081w.project.model.SmallBus;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import edu.umn.cs.csci3081w.project.model.VehicleColorDecorator;
import java.util.List;

public class GetVehiclesCommand extends SimulatorCommand {

  private VisualTransitSimulator simulator;

  public GetVehiclesCommand(VisualTransitSimulator simulator) {
    this.simulator = simulator;
  }

  /**
   * Retrieves vehicles information from the simulation.
   *
   * @param session current simulation session
   * @param command the get vehicles command content
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    List<Vehicle> vehicles = simulator.getActiveVehicles();
    JsonObject data = new JsonObject();
    data.addProperty("command", "updateVehicles");
    JsonArray vehiclesArray = new JsonArray();
    for (int i = 0; i < vehicles.size(); i++) {
      Vehicle currVehicle = vehicles.get(i);
      // currVehicle needs to be cast as VehicleColorDecorator to access RGBA vals
      int[] currVehicleRGB = ((VehicleColorDecorator) currVehicle).getRGBValues();
      int currVehicleAlpha = ((VehicleColorDecorator) currVehicle).getAlphaValue();
      // strip the decorator from currVehicle
      Vehicle currVehicleStripped = ((VehicleColorDecorator) currVehicle).getVehicle();
      JsonObject s = new JsonObject();
      s.addProperty("id", currVehicle.getId());
      s.addProperty("numPassengers", currVehicle.getPassengers().size());
      s.addProperty("capacity", currVehicle.getCapacity());
      String vehicleType = "";
      if (currVehicleStripped instanceof SmallBus) {
        vehicleType = SmallBus.SMALL_BUS_VEHICLE;
      } else if (currVehicleStripped instanceof LargeBus) {
        vehicleType = LargeBus.LARGE_BUS_VEHICLE;
      } else if (currVehicleStripped instanceof ElectricTrain) {
        vehicleType = ElectricTrain.ELECTRIC_TRAIN_VEHICLE;
      } else if (currVehicleStripped instanceof DieselTrain) {
        vehicleType = DieselTrain.DIESEL_TRAIN_VEHICLE;
      }
      s.addProperty("type", vehicleType);
      s.addProperty("co2", currVehicle.getCurrentCO2Emission());
      JsonObject positionJsonObject = new JsonObject();
      positionJsonObject.addProperty("longitude", currVehicle.getPosition().getLongitude());
      positionJsonObject.addProperty("latitude", currVehicle.getPosition().getLatitude());
      s.add("position", positionJsonObject);
      JsonObject colorJsonObject = new JsonObject();
      colorJsonObject.addProperty("r", currVehicleRGB[0]);
      colorJsonObject.addProperty("g", currVehicleRGB[1]);
      colorJsonObject.addProperty("b", currVehicleRGB[2]);
      colorJsonObject.addProperty("alpha", currVehicleAlpha);
      s.add("color", colorJsonObject);
      vehiclesArray.add(s);
    }
    data.add("vehicles", vehiclesArray);
    session.sendJson(data);
  }

}
