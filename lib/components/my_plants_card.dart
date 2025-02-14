import 'dart:math';

import 'package:flutter/material.dart';
import 'package:my_happy_plants_flutter/components/custom_icon_button.dart';
import 'package:my_happy_plants_flutter/components/water_bar.dart';
import 'package:my_happy_plants_flutter/model/plant.dart';

// @Author Filip Claesson, Pehr Nortén
class MyPlantsCard extends StatelessWidget {
  final Plant plant;

  const MyPlantsCard({
    super.key,
    required this.plant,
  });

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: 450, // Fixed width for each card
      height: 420, // Fixed height for each card
      child: Container(
        decoration: BoxDecoration(
          color: Theme.of(context).colorScheme.primary,
          borderRadius: BorderRadius.circular(20),
        ),
        child: Column(
          children: [
            _buildPlantInfo(context),
            _buildPlantImage(context,
                value: Random()
                    .nextDouble()), // Set a random value between 0 and 1 for now can be calculated from the plant's water frequency and last watered date
            _buildActionButtons(context),
          ],
        ),
      ),
    );
  }

  // Helper method to build the plant info section
  Widget _buildPlantInfo(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Column(
        children: [
          Text(
            plant.nickname,
            style: const TextStyle(
              fontSize: 24,
              fontWeight: FontWeight.bold,
            ),
          ),
          Text(plant.commonName),
        ],
      ),
    );
  }

// Helper method to build the plant image section
  Widget _buildPlantImage(BuildContext context, {required double value}) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 20),
      child: Container(
        height: 240,
        width: double.infinity,
        decoration: BoxDecoration(
          color: Colors.white.withAlpha(120),
          borderRadius: BorderRadius.circular(20),
        ),
        child: Stack(
          children: [
            Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Expanded(
                  flex: 15,
                  child: Image.asset(plant.imagePath),
                ),
                WaterBar(value: value), // WaterBar widget on top of the image
                const SizedBox(height: 15),
              ],
            ),
            // Add the warning icon conditionally
            if (value < 0.3)
              Positioned(
                top: 20,
                right: 20,
                child: Icon(
                  Icons.warning,
                  color: Colors.red.shade700,
                  size: 35,
                ),
              ),
          ],
        ),
      ),
    );
  }

  // Helper method to build the action buttons section
  Widget _buildActionButtons(BuildContext context) {
    return Expanded(
      child: Center(
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            CustomIconButton(
              icon: Icons.edit,
              onPressed: () {
                // Todo: Add edit functionality here
              },
            ),
            _buildCustomImageButton(
              context,
              child: Image.asset(
                'lib/assets/images/watering_can.png',
                color: Theme.of(context).colorScheme.onSurface,
              ),
              onPressed: () {
                // Todo: Add watering functionality here
              },
            ),
            CustomIconButton(
              icon: Icons.info_outline,
              onPressed: () {
                // Todo: Add delete functionality here
              },
            ),
          ],
        ),
      ),
    );
  }

  // Helper method to build the Image button section
  Widget _buildCustomImageButton(
    BuildContext context, {
    required Widget child,
    required VoidCallback onPressed,
  }) {
    return IconButton(
      icon: child,
      onPressed: onPressed,
      style: ButtonStyle(
        // Background color
        backgroundColor: WidgetStateProperty.all<Color>(
          Theme.of(context).colorScheme.surface.withAlpha(120),
        ),
        // Padding
        padding: WidgetStateProperty.all<EdgeInsetsGeometry>(
          const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
        ),
        // Custom shape
        shape: WidgetStateProperty.all<RoundedRectangleBorder>(
          RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(30),
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
