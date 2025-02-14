import 'package:flutter/material.dart';

//@author Pehr Norten
class CustomIconButton extends StatelessWidget {
  final IconData icon;
  final void Function() onPressed;

  const CustomIconButton({
    super.key,
    required this.icon,
    required this.onPressed,
  });

  @override
  Widget build(BuildContext context) {
    return IconButton(
      icon: Icon(icon),
      color: Theme.of(context).colorScheme.onSurface,
      onPressed: onPressed,
      style: ButtonStyle(
        // Background color
        backgroundColor: WidgetStateProperty.all<Color>(
          Theme.of(context).colorScheme.surface.withAlpha(120),
        ),
        // Padding
        padding: WidgetStateProperty.all<EdgeInsetsGeometry>(
          const EdgeInsets.all(16),
        ),
        // Custom shape with border
        shape: WidgetStateProperty.all<RoundedRectangleBorder>(
          RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(20),
            side: BorderSide(
              color: Colors.white.withAlpha(100),
              width: 2.0,
            ),
          ),
        ),
      ),
    );
  }
}
