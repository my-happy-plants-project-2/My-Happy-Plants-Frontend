//Class for handling plant related logic and list of plant owned by user.
import 'package:flutter/material.dart';

import '../model/plant.dart';

class PlantProvider extends ChangeNotifier{
    List<Plant> _allPlants = [];
    List<Plant> _userPlants = [];


  void addPlants(Plant plant, String newName) {

    Plant newPlant = Plant(
      plantId: DateTime.now().millisecondsSinceEpoch.toString(),
      commonName: plant.commonName,
      scientificName: plant.scientificName,
      familyName: plant.familyName,
      imagePath: plant.imagePath,
      nickname: newName,
      lastWatered: DateTime.now(),
      waterFrequency: plant.waterFrequency,
      light: plant.light,
    );

      _userPlants.add(newPlant);
      //TODO: Update the plantlist in the database.
      notifyListeners();
    }

    void removePlant(String plantId) {
      _userPlants.removeWhere((p) => p.plantId == plantId);
      //TODO: Update the plantlist in the database.
      notifyListeners();
    }

    void waterPlant(String plantId) {
      _userPlants.firstWhere((p) => p.plantId == plantId).lastWatered = DateTime.now();
      //TODO: Update the plantlist in the database.
      notifyListeners();
    }

    //TODO:Save a plant name that user put in
    void saveNewPlantName(String plantId, String newName) {
      Plant plant = _allPlants.firstWhere((p) => p.plantId == plantId);

      plant.nickname = newName;
    //TODO: Update the plantlist in the database.
      notifyListeners();
    } //This already happens when creating a new plant. Might be removed.

    void changeNickName(String plantId, String newName) {
      _userPlants.firstWhere((p) => p.plantId == plantId).nickname = newName;
      notifyListeners();
    }

    void fillLibraryList(List<Plant> plants) {
      _allPlants = plants;
    }

    void fillUserList(List<Plant> plants) {
      _userPlants = plants;
    }

    List<Plant> get userPlants => _userPlants;
    List<Plant> get allPlants => _allPlants;
}
