import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:provider/provider.dart';
import 'login_provider.dart';

//@author Christian Storck
//Class that handles actions that changes/creates user information.
class AuthenticationProvider extends ChangeNotifier {
  final String _baseUrl = "http://localhost:8080/api/v1";

  String? _getToken(BuildContext context) { //Fetches the token from the loginprovider.
    return Provider.of<LoginProvider>(context, listen: false).token;
  }

  Map<String, String> _headers({String? token}) { //Creates the header for the request.
    return {
      "Content-Type": "application/json",
      if(token != null) "Authorization": "Bearer $token",
    };
  }

  Future<bool> createAccount (String userName, String email, String password) async { //Method for creating account, does not require token.
    final response = await _makeRequest("POST", "user", null, body: {
      "username" : userName,
      "email" : email,
      "password" : password,
      "colorTheme": "1",
    },
      requiresAuth: false,
    );

    print(userName + email + password);

    if(response == null) {
      print("Failed to create account, null");
      return false;
    }

    if(response.statusCode == 201) {
      print("Account created");
      return true;
    } else {
      print("Failed to create account");
      print("Status Code: ${response.statusCode}");
      print("Response Body: ${response.body}");
      return false;
    }
  }
  
  Future<bool> deleteUser (BuildContext context) async {
  final response = await _makeRequest("DELETE", "user",
        null,
        body: jsonEncode({"email": email, "password": password}),
        );
      if(response == null) {
        print("Failed to delete account");
        return false;
      }

      if(response.statusCode == 200) {
        print("Account deleted");
        return true;
      } else {
        print("Could not delete account");
        return false;
      }
  }

  Future<bool> changeUserName (BuildContext context, String newName) async {
    final response = await _makeRequest("PATCH", "/user", context, body: {
      "userName" : newName,
    },
    );
      if(response == null) {
        print("Failed to change username");
        return false;
      }

      if(response.statusCode == 200) {
        print("Username successfully changed");
        return true;
      } else {
        print("Failed to change username");
        return false;
      }
  }

  Future<bool> changeUserTheme (BuildContext context, String themeID) async {
    final response = await _makeRequest("PATCH", "/user/theme", context, body: {
      "theme" : themeID,
    },
    );
      if(response == null) {
        print("Failed to change theme");
        return false;
      }

      if(response.statusCode == 200) {
        print("Theme changed successfully");
        return true;
      } else {
        print("Failed to change theme");
        return false;
      }
  }

  Future<http.Response?> _makeRequest( //Method that makes the actual call to the server. Builds the Json with the body/header.
      String method,                    // The context used in the parameters is the token, we get the token from the context.
      String endpoint,
      BuildContext ? context,{
        Map<String, dynamic>? body,
        bool requiresAuth = true,
      }) async {
    final token = requiresAuth && context != null ? _getToken(context) : null;

    final url = Uri.parse("$_baseUrl/$endpoint");

    try { //Dynamic way of creating the Json File.
      var response = (method == "POST"
          ? await http.post(url, headers: _headers(token: token), body: jsonEncode(body))
          : method == "DELETE"
          ? await http.delete(url, headers: _headers(token: token))
          : method == "PATCH"
          ? await http.patch(url, headers: _headers(token: token), body: jsonEncode(body))
          : null);

      return response;
    } catch (e) {
      print("Error making request: $e");
      return null;
    }
  }
}
