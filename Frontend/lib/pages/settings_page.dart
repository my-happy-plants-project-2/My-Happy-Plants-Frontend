import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:my_happy_plants_flutter/providers/authentication_provider.dart';

//@author Filip Claesson, Pehr Norten


class SettingsPage extends StatefulWidget {
  const SettingsPage({super.key});

  @override
  State<SettingsPage> createState() => _SettingsPageState();
}

class _SettingsPageState extends State<SettingsPage> {
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
                onPressed: () => Navigator.of(context).pop(),
                child: const Text("Cancel"),
              ),
              ElevatedButton(
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.green,
                ),
                onPressed: () {
                  Navigator.of(context).pop();
                  _deleteUser();
                },
                child: const Text(
                    "Delete", style: TextStyle(color: Colors.white)),
              ),
            ],
          ),
    );
  }

  void _deleteUser() async {
    final authentication = context.read<AuthenticationProvider>();
    try {
      final success = await authentication.deleteUser(context);

      if (success) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text("Account deleted")),
        );
        Navigator.pushReplacementNamed(context, '/login_page');
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
      body: Center(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Image.asset(
                'lib/assets/images/plants/parlor_palm.png', height: 150),
            const SizedBox(height: 20),
            ElevatedButton(
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.green,
                padding: const EdgeInsets.symmetric(
                    horizontal: 24, vertical: 12),
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(8)),
              ),
              onPressed: _confirmDeleteAccount,
              child: const Text(
                  "Delete Account", style: TextStyle(color: Colors.white)),
            ),
          ],
        ),
      ),
    );
  }
}










