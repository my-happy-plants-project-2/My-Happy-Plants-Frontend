import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:my_happy_plants_flutter/providers/authentication_provider.dart';

//@author Filip Claesson, Pehr Norten, Ida Nordenswa

//Setting page where users can delete their account
class SettingsPage extends StatefulWidget {
  const SettingsPage({super.key});

  @override
  State<SettingsPage> createState() => _SettingsPageState();
}

class _SettingsPageState extends State<SettingsPage> {
  //Confirmation dialog before deleting users account
  void _confirmDeleteAccount() {
    showDialog(
      context: context,
      builder: (context) =>
          AlertDialog(
            title: const Text("Delete Account"),
            content: const Text(
                "Are you sure you want to delete your account?"),
            actions: [
              TextButton(
                onPressed: () => Navigator.of(context).pop(), //Close dialog
                child: const Text("Cancel"),
              ),
              ElevatedButton(
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.green,
                ),
                onPressed: () {
                  Navigator.of(context).pop(); //Close dialog
                  _deleteUser(); //Account deletion
                },
                child: const Text(
                    "Delete", style: TextStyle(color: Colors.white)),
              ),
            ],
          ),
    );
  }

  //Handles the deletion of a user account
  void _deleteUser() async {
    final authentication = context.read<AuthenticationProvider>();
    try {
      final success = await authentication.deleteUser(context);

      if (success) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text("Account deleted")),
        );
        Navigator.pushReplacementNamed(context, '/login_page'); //Redirects to the login page
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text("Account could not be deleted")),
        );
      }
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text("Error: $e")),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Colors.transparent,
        body: Container(
          decoration: BoxDecoration(
            gradient: LinearGradient(
              begin: Alignment.topLeft,
              end: Alignment.bottomRight,
              colors: [
                Color.fromARGB(192, 204, 255, 204), //Very light green
                Color.fromARGB(166, 255, 255, 204), //Very light yellow
              ],
            ),
          ),
        child: Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Image.asset(
                  'lib/assets/images/plants/parlor_palm.png', height: 150), //Image above deletion button
              const SizedBox(height: 20),
              ElevatedButton(
                style: ElevatedButton.styleFrom(
                  backgroundColor: Color.fromARGB(239, 61, 148, 66),
                  padding: const EdgeInsets.symmetric(
                      horizontal: 24, vertical: 12),
                  shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(8)),
                ),
                onPressed: _confirmDeleteAccount, //open deletion dialog
                child: const Text(
                    "Delete Account", style: TextStyle(color: Colors.white)),
              ),
            ],
          ),
        ),
      ),
    );
  }
}










