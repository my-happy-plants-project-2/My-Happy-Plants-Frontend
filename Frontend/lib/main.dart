import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:my_happy_plants_flutter/pages/login_page.dart';
import 'package:my_happy_plants_flutter/pages/home_page.dart';
import 'package:my_happy_plants_flutter/providers/library_provider.dart';
import 'package:my_happy_plants_flutter/providers/plant_provider.dart';
import 'package:my_happy_plants_flutter/themes/light_mode.dart';
import 'package:provider/provider.dart';

// @Author Filip Claesson, Pehr Nortén
void main() {
  runApp(MultiProvider(
    providers: [
      ChangeNotifierProvider(create: (context) => PlantProvider()),
    ],
    child: const MyApp(),
  ));
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    // Fixa färgen på overlay i Android
    SystemChrome.setSystemUIOverlayStyle(
      const SystemUiOverlayStyle(
        systemNavigationBarColor: Color.fromARGB(255, 122, 162, 79),
        systemNavigationBarIconBrightness: Brightness.light,
      ),
    );

    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: const LoginPage(),
      theme: lightMode, // Import the lightMode ThemeData from light_mode.dart
      routes: {
        '/login_page': (context) => const LoginPage(),
        '/home_page': (context) => const HomePage(),
      },
    );
  }
}
