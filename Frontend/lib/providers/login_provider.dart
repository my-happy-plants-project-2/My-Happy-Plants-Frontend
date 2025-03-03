import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';

//@author Christian Storck
//Controller class that handles the login. Stores the JWT-token.

class LoginProvider extends ChangeNotifier {
  final String _baseUrl = "http://localhost:8080/api/v1/auth";

  String? _token;
  String? get token => _token;

  Future<bool> login(String email, String password) async {
    final url =Uri.parse("$_baseUrl/login");

    try {
      final response = await http.post(  //This is where we make our request to the server.
        url,
        headers: {"Content-Type": "application/json"},
        body: jsonEncode({"email": email, "password": password}),
      );

      print("Response Code: ${response.statusCode}");  //This is just for troubleshooting.
      print("Response Body: ${response.body}");
      print(email + password);

      if(response.statusCode == 200) { //Statuscode 200 = OK
        final data =jsonDecode(response.body); // We extract the information server gives.
        _token = data['token']; // Extracts the info that has the key "token".
        print("Successfull login: ${response.statusCode}");
        notifyListeners();
        return true;
      } else {
        return false;
      }
    } catch (e) {
      print("Failed Login: $e");
      return false;
    }
  }

  void logout() { //Method that clears the token info, this together with a redirect to the "login page" should be enough.
    _token = null;
    notifyListeners();
  }
}
