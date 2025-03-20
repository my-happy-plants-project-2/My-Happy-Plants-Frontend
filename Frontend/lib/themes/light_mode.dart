import 'package:flutter/material.dart';

// @Author Filip Claesson, Pehr Nortén
ThemeData lightMode = ThemeData(
  colorScheme: ColorScheme.light(
    primary: Color.fromARGB(239, 61, 148, 66), // Primary color (green)
    surface: //Colors.transparent,
        Color.fromARGB(244, 255, 255, 240),
    secondary:
        Color.fromARGB(255, 255, 157, 77), // Secondary color (muted orange)
    inversePrimary:
        //Color.fromARGB(255, 255, 255, 255), // Inverse primary (white)
        Color.fromARGB(244, 255, 255, 240),
    onSurface:
        Color.fromARGB(174, 40, 31, 27), // Darker text/icon color on surface
  ),
  dividerTheme: DividerThemeData(color: Colors.grey.shade400),
  fontFamily: 'Merienda',

);
