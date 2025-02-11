import 'package:flutter/material.dart';
import 'package:my_happy_plants_flutter/pages/login_page.dart';
import 'package:my_happy_plants_flutter/pages/home_page.dart';
import 'package:my_happy_plants_flutter/pages/plant_library_page.dart';
import 'package:my_happy_plants_flutter/pages/settings_page.dart';
import 'package:my_happy_plants_flutter/themes/light_mode.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: const LoginPage(),
      theme: lightMode,
      routes: {
        '/login_page': (context) => const LoginPage(),
        '/my_plants_page': (context) => const HomePage(),
        '/plant_library_page': (context) => const PlantLibraryPage(),
        '/settings_page': (context) => const SettingsPage(),
      },
    );
  }
}
