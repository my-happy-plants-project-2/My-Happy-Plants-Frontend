import 'package:flutter/material.dart';

// @Author Filip Claesson, Pehr Norten
// A customizable text button with consistent styling for the application, to make the buttons in the app look the same
class CustomTextButton extends StatelessWidget {
  final String text;
  final void Function() onPressed;

  const CustomTextButton({
    super.key,
    required this.text,
    required this.onPressed,
  });

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
      onPressed: onPressed,
      style: ButtonStyle(
        backgroundColor: WidgetStateProperty.all<Color>(
          Colors.white.withAlpha(120),
        ),
        foregroundColor: WidgetStateProperty.all<Color>(
          Theme.of(context).colorScheme.onSurface,
        ),
        // Cutom shape with border
        shape: WidgetStateProperty.all<RoundedRectangleBorder>(
          RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(8),
            side: BorderSide(
              color: Colors.white.withAlpha(100),
              width: 2.0,
            ),
          ),
        ),
      ),
      child: Text(text),
    );
  }
}
