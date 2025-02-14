import 'package:flutter/material.dart';

//@author Filip Claesson, Pehr Norten
ThemeData lightMode = ThemeData(
  colorScheme: ColorScheme.light(
    primary: Color.fromARGB(255, 122, 162, 79), // Primary color (green)
    surface:
        Color.fromARGB(255, 230, 230, 230), // Darker surface color (light gray)
    secondary:
        Color.fromARGB(255, 255, 157, 77), // Secondary color (muted orange)
    inversePrimary:
        Color.fromARGB(255, 255, 255, 255), // Inverse primary (white)
    onSurface:
        Color.fromARGB(255, 80, 80, 80), // Darker text/icon color on surface
  ),
  dividerTheme: DividerThemeData(color: Colors.grey.shade400),
  fontFamily: 'Merienda',
);
