//Class for handling plant related logic and list of plant owned by user.
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:my_happy_plants_flutter/providers/login_provider.dart';
import 'package:provider/provider.dart';
import 'package:http/http.dart' as http;
import '../model/plant.dart';

//@author Christian Storck

class PlantProvider extends ChangeNotifier{

    final String _baseUrl = "http://localhost:8080/api/v1";
    List<Plant> _allPlants = [];
    List<Plant> _userPlants = [];
    List<Plant> get userPlants => _userPlants;
    List<Plant> get allPlants => _allPlants;
    
    String? _getToken(BuildContext context) {
      return Provider.of<LoginProvider>(context, listen: false).token;
    }

    Map<String, String> _headers(String token) => {
        "Content-Type": "application/json",
        "Authorization": "Bearer $token",
    };
    
    Future<http.Response?> _makeRequest(
        String method,
        String endpoint,
        BuildContext context,{
        Map<String, dynamic>? body,
        }) async {
          final token = _getToken(context);
          if(token == null) {
            print("No token was found");
            return null;
          }
          final url = Uri.parse("$_baseUrl/$endpoint");

          try {
            var response = (method == "POST"
                ? await http.post(url, headers: _headers(token), body: jsonEncode(body))
            : method == "DELETE"
                ? await http.delete(url, headers: _headers(token))
            : method == "PATCH"
                ? await http.patch(url, headers: _headers(token), body: jsonEncode(body))
            : method == "GET"
                ? await http.get(url, headers: _headers(token))
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
    final response = await _makeRequest("POST", "/user/plant", context, body: {
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
    _userPlants.add(newPlant);
    notifyListeners();
  }

    Future <void> removePlant(BuildContext context, String plantId) async {
      // final response = await _makeRequest("DELETE", "/user/plant/$plantId", context);

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
      /**
      final response = await _makeRequest("PATCH", "/user/plant/water/$plantId", context);


          if(response != null && response.statusCode == 200) {
          _userPlants.firstWhere((p) => p.plantId == plantId)lastWatered = DateTime.now();
          notifyListeners();
          print("Plant Watered");
          } else {
          print("Failed to water plant");
          }
       */

      _userPlants.firstWhere((p) => p.plantId == plantId).lastWatered = DateTime.now();
      notifyListeners();
    }

    Future<void> changeNickName(BuildContext context, String plantId, String newName) async {
      /*final response = await _makeRequest("PATCH", "/user/plant/$plantId", context);

      if(response != null && response.statusCode == 200) {
        _userPlants.firstWhere((p) => p.plantId == plantId).nickname = newName;
        notifyListeners();
        print("Plant nickname changed");
      } else {
        print("Failed to change nickname");
      } */

      _userPlants.firstWhere((p) => p.plantId == plantId).nickname = newName;
      notifyListeners();
    }

    void fillLibraryList(List<Plant> plants) {
      _allPlants = plants;
    } //TODO: These two methods will be removed and replaced with the ones below them.

    void fillUserList(List<Plant> plants) {
      _userPlants = plants;
    }

    Future<List<Plant>> getLibraryPlantList(BuildContext context) async {
      final response = await _makeRequest("GET", "/plants", context);

      if(response != null && response.statusCode == 200) {
        List<dynamic> responseData = jsonDecode(response.body);
        return responseData.map((plant) => Plant.fromJson(plant)).toList();
      } else {
        print("Failed to fetch plants");
        return [];
      }
    }

    Future<List<Plant>> getUserPlantList(BuildContext context) async {
      final response = await _makeRequest("GET", "/plant", context);

      if(response != null && response.statusCode == 200) {
        List<dynamic> responseData = jsonDecode(response.body);
        return responseData.map((plant) => Plant.fromJson(plant)).toList();
      } else {
        print("Failed to fetch plants");
        return [];
      }
    }
}
