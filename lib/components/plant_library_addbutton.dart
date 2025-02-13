import 'package:flutter/material.dart';
import 'package:my_happy_plants_flutter/components/custom_text_button.dart';
import 'package:my_happy_plants_flutter/model/plant.dart';

class PlantLibraryAddDialog extends StatelessWidget {
  final TextEditingController controller;
  final Plant plant;
  final VoidCallback onSave;
  final VoidCallback onCancel;

  const PlantLibraryAddDialog({
    super.key,
    required this.controller,
    required this.plant,
    required this.onSave,
    required this.onCancel,
  });

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      backgroundColor: Theme.of(context).colorScheme.primary,
      content: Container(
        constraints: BoxConstraints(maxHeight: 300),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Text(
              plant.commonName,
              style: Theme.of(context).textTheme.titleLarge,
            ),
            const SizedBox(height: 15),
            _buildImageSection(context),
            const SizedBox(height: 20),
            TextField(
              controller: controller,
              decoration: InputDecoration(
                border:
                    OutlineInputBorder(borderRadius: BorderRadius.circular(10)),
                hintText: "Fill in a name for your plant!",
              ),
            ),
            const SizedBox(height: 15),
            // Buttons -> Save + cancel
            Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                CustomTextButton(text: "Save", onPressed: onSave),
                const SizedBox(width: 5),
                CustomTextButton(text: "Cancel", onPressed: onCancel),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildImageSection(BuildContext context) {
    return Container(
      height: 120,
      width: double.infinity,
      decoration: BoxDecoration(
        color: Colors.white.withAlpha(120),
        borderRadius: BorderRadius.circular(20),
      ),
      child: Stack(
        children: [
          Center(
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Image.asset(
                plant.imagePath,
                fit: BoxFit.contain,
              ),
            ),
          ),
        ],
      ),
    );
  }

/*
  Widget _buildIndicator({
    required IconData icon,
    required Color color,
    required String value,
  }) {
    return Row(
      children: [
        Icon(icon, color: color, size: 20),
        const SizedBox(width: 2),
        Text(
          value,
          style: const TextStyle(
            fontSize: 16,
            fontFamily: 'Merienda',
          ),
        ),
      ],
    );
  }
  */
}
