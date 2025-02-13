import 'package:flutter/material.dart';

class PlantLibrarySavecancelbutton extends StatelessWidget {
  final String text;
  VoidCallback onPressed;

  PlantLibrarySavecancelbutton(
      {super.key, required this.text, required this.onPressed});

  @override
  Widget build(BuildContext context) {
    return MaterialButton(
      onPressed: onPressed,
      color: Theme.of(context).colorScheme.surface.withAlpha(120),
      child: Text(text),
    );
  }
}
