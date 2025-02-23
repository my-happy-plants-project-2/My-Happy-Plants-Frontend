//Class for handling plant related logic and list of plant owned by user.
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:my_happy_plants_flutter/providers/login_provider.dart';
import 'package:provider/provider.dart';
import 'package:http/http.dart' as http;
import '../model/plant.dart';

class PlantProvider extends ChangeNotifier{

    final String _baseUrl = "/api/v1/user";
    List<Plant> _allPlants = [];
    List<Plant> _userPlants = [];
    List<Plant> get userPlants => _userPlants;
    List<Plant> get allPlants => _allPlants;


    Future<void> addPlants(BuildContext context, Plant plant, String newName) async {
    final loginProvider = Provider.of<LoginProvider>(context, listen:  false);
    final String? token = loginProvider.token;
/** ----- //TODO: Add to code if we're using JsonWebToken.
    if(token == null) {
      print("Error: No token");
      return;
    } */

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

    //Below is hopefully a method that works for adding plants to the database.
    //Includes a token for verifications so might have to remove that.
/**
    try {
      final url = Uri.parse("$_baseUrl/plant");
      final response = await http.post(
        url,
        headers: {
          "Content-Type": "application(json",
          "Authorization": "Bearer $token",
        },
        body: jsonEncode({
          "plantId": newPlant.plantId,
          "commonName": newPlant.commonName,
          "scientifiName": newPlant.scientificName,
          "familyName": newPlant.familyName,
          "imagePath": newPlant.imagePath,
          "nickName": newPlant.nickname,
          "lastWatered": newPlant.lastWatered,
          "waterFrequency": newPlant.waterFrequency,
          "light": newPlant.light,
        }),
      );

      if(response.statusCode == 201) {
        _userPlants.add(newPlant); //TODO: Remove when not needed.
        notifyListeners();
        print("We added plant");
      } else{
        print("We did not add plant");
      }
    } catch (e) {
      print("Error adding plant: $e");
    }
    */
    _userPlants.add(newPlant);
    notifyListeners();//Placeholder.
  }

    Future <void> removePlant(BuildContext context, String plantId) async {
      final loginProvider = Provider.of<LoginProvider>(context, listen:  false);
      final String? token = loginProvider.token;
/** //TODO: Add this to the code when back-end is ready.
      if(token == null) {
        print("Error: No token");
        return;
      }

      try {
        final url = Uri.parse("$_baseUrl/{user-email}/plant/$plantId"); //TODO: Change {user-email} till riktig email.

        final response = await http.delete(
            url,
            headers: {
              "Content-Type": "application(json",
              "Authorization": "Bearer $token",
            },
        );

        if(response.statusCode == 200) {
          _userPlants.removeWhere((p) => p.plantId == plantId);
          notifyListeners();
          print("Plant Removed");
        } else {
          print("Failed to remove"),
        }
      } catch (e) {
        print("Error removing: $e");
      }
*/
      _userPlants.removeWhere((p) => p.plantId == plantId);
      //TODO: Update the plantlist in the database.
      notifyListeners();
    }

    void waterPlant(String plantId) {
      _userPlants.firstWhere((p) => p.plantId == plantId).lastWatered = DateTime.now();
      //TODO: Update the plantlist in the database.
      notifyListeners();
    }

    void changeNickName(String plantId, String newName) {
      //TODO: Update the new name in database.
      _userPlants.firstWhere((p) => p.plantId == plantId).nickname = newName;
      notifyListeners();
    }

    void fillLibraryList(List<Plant> plants) {
      _allPlants = plants;
    }

    void fillUserList(List<Plant> plants) {
      _userPlants = plants;
    }
}
