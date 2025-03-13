import 'package:flutter/material.dart';

// @Author Filip Claesson, Pehr Nortén, Christian Storck
class CustomIconButton extends StatelessWidget {
  final IconData icon;
  final VoidCallback? onPressed;
  final List<PopupMenuEntry<int>>? popupMenuItems;

  const CustomIconButton({
    super.key,
    required this.icon,
    this.onPressed,
    this.popupMenuItems,
  });

  @override
  Widget build(BuildContext context) {
    final buttonStyle = BoxDecoration(
      color: Theme.of(context).colorScheme.surface.withAlpha(120), // Background color
      borderRadius: BorderRadius.circular(20),
      border: Border.all(
        color: Colors.white.withAlpha(100),
        width: 2.0,
      ),
    );

    return popupMenuItems == null
        ? IconButton(
      icon: Icon(icon),
      color: Theme.of(context).colorScheme.onSurface,
      onPressed: onPressed,
      style: ButtonStyle(
        backgroundColor: WidgetStateProperty.all<Color>(
          Theme.of(context).colorScheme.surface.withAlpha(120),
        ),
        padding: WidgetStateProperty.all<EdgeInsetsGeometry>(
          const EdgeInsets.all(16),
        ),
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
    )
        : Container(
      decoration: buttonStyle,
      child: PopupMenuButton<int>(
        icon: Padding(
          padding: const EdgeInsets.all(8.0),
          child: Icon(icon, color: Theme.of(context).colorScheme.onSurface),
        ),
        onSelected: (value) {
          if (onPressed != null) onPressed!();
        },
        itemBuilder: (context) => popupMenuItems!,
        color: Theme.of(context).colorScheme.surface,
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(10),
        ),
      ),
    );
  }
}
