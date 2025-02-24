//Class for handling plant related logic and list of plant owned by user.
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:my_happy_plants_flutter/providers/login_provider.dart';
import 'package:provider/provider.dart';
import 'package:http/http.dart' as http;
import '../model/plant.dart';

class PlantProvider extends ChangeNotifier{

    final String _baseUrl = "/api/v1/user"; //TODO: Add actual URL
    List<Plant> _allPlants = [];
    List<Plant> _userPlants = [];
    List<Plant> get userPlants => _userPlants;
    List<Plant> get allPlants => _allPlants;
    
    String? _getToken(BuildContext context) {
      return Provider.of<LoginProvider>(context, listen: false).token;
    }

    Map<String, String> _headers(String token) => {
        "Content-Type": "application(json",
        "Authorization": "Bearer $token",
    };
    
    Future<http.Response?> _makeRequest(
        String method,
        String endpoint,
        BuildContext context,{
        Map<String, dynamic>? body,
        })async {
          final token = _getToken(context);
          if(token == null) {
            print("No token was found");
            return null;
          }
          final url = Uri.parse("$_baseUrl/$endpoint");

          try {
            var response = await (method == "POST"? http.post(url, headers: _headers(token), body: jsonEncode(body))
            : method == "DELETE"? http.delete(url, headers: _headers(token))
            : null);

            return response;
          } catch (e) {
            print("Error making request: $e");
            return null;
          }
        }

    Future<void> addPlants(BuildContext context, Plant plant, String newName) async {

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
    final response = await _makeRequest("POST", "plant", context, body: {
      "plantId": newPlant.plantId,
      "commonName": newPlant.commonName,
      "scientifiName": newPlant.scientificName,
      "familyName": newPlant.familyName,
      "imagePath": newPlant.imagePath,
      "nickName": newPlant.nickname,
      "lastWatered": newPlant.lastWatered,
      "waterFrequency": newPlant.waterFrequency,
      "light": newPlant.light,
    });

      if(response.statusCode == 201) {
        _userPlants.add(newPlant);
        notifyListeners();
        print("We added plant");
      } else{
        print("We did not add plant");
      }
    } catch (e) {
      print("Error adding plant: $e");
    }
    */
    _userPlants.add(newPlant); //TODO: Remove when not needed.
    notifyListeners();//Placeholder.
  }

    Future <void> removePlant(BuildContext context, String plantId) async {
      final response = await _makeRequest("DELETE", "{email}/plant/$plantId", context);

      /** //TODO: Add this to the code when back-end is ready.
       *
              if(response != null && response.statusCode == 200) {
                _userPlants.removeWhere((p) => p.plantId == plantId);
                notifyListeners();
                print("Plant Removed");
              } else {
                print("Failed to remove"),
              }
      */
      _userPlants.removeWhere((p) => p.plantId == plantId);
      //TODO: Update the plantlist in the database.
      notifyListeners();
    }

    Future<void> waterPlant(BuildContext context, String plantId) async {
      final response = await _makeRequest("PATCH", "{email}/plant/$plantId/water", context);

      /** //TODO: Add this to the code when back-end is ready.
       *
          if(response != null && response.statusCode == 200) {
          _userPlants.firstWhere((p) => p.plantId == plantId)lastWatered = DateTime.now();;
          notifyListeners();
          print("Plant Watered");
          } else {
          print("Failed to water plant"),
          }
       */

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
    } //TODO: Should remake so it collects the plants from server and THEN fills the list.

    void fillUserList(List<Plant> plants) {
      _userPlants = plants;
    } //TODO: Should remake so it collects the plants from server and THEN fills the list.
}
