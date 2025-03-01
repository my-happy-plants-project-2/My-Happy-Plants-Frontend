import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';

//@author Christian Storck

class LoginProvider extends ChangeNotifier {
  final String _baseUrl = "http://localhost:8080/api/v1/auth"; //Tror detta bör funka

  String? _token;
  String? get token => _token;

  Future<bool> login(String email, String password) async {
    final url =Uri.parse("$_baseUrl/login"); //Tror det funkar.

    try {
      final response = await http.post(
        url,
        headers: {"Content-Type": "application/json"},
        body: jsonEncode({"email": email, "password": password}),
      );

      print("Response Code: ${response.statusCode}");
      print("Response Body: ${response.body}");

      print(email + password);

      if(response.statusCode == 200) {
        final data =jsonDecode(response.body);
        _token = data['token'];
        print("Successfull login: ${response.statusCode}");//Det är här auth token sparas. OM vi skickar det från data servern?
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

  void logout() {
    _token = null;
    notifyListeners();
  }
}
