import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:my_happy_plants_flutter/providers/login_provider.dart';
import 'package:provider/provider.dart';
import 'package:http/http.dart' as http;
import '../model/plant.dart';

//@author Christian Storck, Ida Nordenswan


// This class handles the logic relating ti managing the users collection of plants
class LibraryProvider extends ChangeNotifier {
    final String _baseUrl = "http://localhost:8080/api/v1";
    List<Plant> _allPlants = [];

    List<Plant> get allPlants => _allPlants;

    //Authentication from "LoginProvider"
    String? _getToken(BuildContext context) {
      return Provider.of<LoginProvider>(context, listen: false).token;
    }

    Map<String, String> _headers(String token) => {
      "Content-Type": "application/json",
      "Authorization": "Bearer $token",
    };

    //Handles HTTP GET requests to the API
    Future<http.Response?> _makeRequest(String method, String endpoint, BuildContext context) async {
      final token = _getToken(context);
      if (token == null) {
        print("No token was found");
        return null;
      }
      final url = Uri.parse("$_baseUrl/$endpoint");

      try {
        var response = await http.get(url, headers: _headers(token));
        return response;
      } catch (e) {
        print("Error making request: $e"); //Logs network error
        return null;
      }
    }

    void fillLibraryList(List<Plant> plants) {
      _allPlants = plants;
      notifyListeners(); //Notify UI about changes
    }

    //Fetches the list of plants from the API, returns them as a list of Plant
    Future<List<Plant>> getLibraryPlantList(BuildContext context) async {
      final response = await _makeRequest("GET", "species", context);
      if(response != null && response.statusCode == 200) {
        List<dynamic> responseData = jsonDecode(response.body);
        return responseData.map((plant) => Plant.fromJson(plant)).toList();
      } else {
        print("Failed to fetch plants");
        return []; //If failed, returns an empty list
      }
    }

    //Fetching plants fun fact based on its common name
    Future<String?> getPlantDescription(BuildContext context, String plantName) async {
      if (plantName.isEmpty) {
        print("Plant name is empty.");
        return null;
      }

      String endpoint = "species?common_name=$plantName";
      print("Fetching plant description from: $endpoint");

      final response = await _makeRequest("GET", endpoint, context);

      if (response != null && response.statusCode == 200) {
        final List<dynamic> responseData = jsonDecode(response.body);
        //print("API Response: $responseData");

        //Filter the plants based on common_name and find the plant with the correct description
        final plant = responseData.firstWhere(
              (plant) => plant["commonName"].toLowerCase() == plantName.toLowerCase(),
          orElse: () => null, //no matching plant is found
        );

        if (plant != null && plant.containsKey("description")) {
          return plant["description"];
        } else {
          print("No description found for $plantName.");
          return null;
        }
      } else {
        print("Failed to fetch plant information. Status: ${response?.statusCode}");
        return null;
      }
    }

    //Fetching plants care tip based on its common name
    Future<String?> getPlantCare(BuildContext context, String plantName) async {
      if (plantName.isEmpty) {
        print("Plant name is empty.");
        return null;
      }

      String endpoint = "species?common_name=$plantName";
      print("Fetching plant description from: $endpoint");

      final response = await _makeRequest("GET", endpoint, context);

      if (response != null && response.statusCode == 200) {
        final List<dynamic> responseData = jsonDecode(response.body);
        //print("API Response: $responseData");

        //Filter the plants based on common_name and find the plant with the correct description
        final plant = responseData.firstWhere(
              (plant) => plant["commonName"].toLowerCase() == plantName.toLowerCase(),
          orElse: () => null, //no matching plant is found
        );

        if (plant != null && plant.containsKey("caring")) {
          return plant["caring"];
        } else {
          print("No care tip found for $plantName.");
          return null;
        }
      } else {
        print("Failed to fetch plant care tip. Status: ${response?.statusCode}");
        return null;
      }
    }
}