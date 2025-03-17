import 'dart:math';
import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:my_happy_plants_flutter/components/custom_icon_button.dart';
import 'package:my_happy_plants_flutter/components/water_bar.dart';
import 'package:my_happy_plants_flutter/model/plant.dart';
import 'package:my_happy_plants_flutter/model/plant_facts.dart';
import 'package:my_happy_plants_flutter/providers/plant_provider.dart';
import 'package:provider/provider.dart';

// @Author Filip Claesson, Pehr Nortén, Christian Storck
class MyPlantsCard extends StatelessWidget {
  final Plant plant;

  MyPlantsCard({
    super.key,
    required this.plant,
  });

  final _controller = TextEditingController();

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
                value: plant.calculateWaterLevel),
            _buildActionButtons(context),
          ],
        ),
      ),
    );
  }

  //Helper method to build the plant info section
  Widget _buildPlantInfo(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Column(
            crossAxisAlignment: CrossAxisAlignment.start,
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
    ],
    ),
    );
  }

//Helper method to build the plant image section
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
                child: Tooltip(
                  message: "Plant needs water!!",
                  child: Icon(
                    Icons.warning,
                    color: Colors.red.shade700,
                    size: 35,
                  ),
                ),
              ),
          ],
        ),
      ),
    );
  }

  //Helper method to build the action buttons section
  Widget _buildActionButtons(BuildContext context) {
    return Expanded(
      child: Center(
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            CustomIconButton(
                icon: Icons.edit,
                popupMenuItems: [
                  PopupMenuItem<int>(
                      value: 0,
                      child: const Text('Edit Name'),
                      onTap: () => _editPlantName(context),
                  ),
                  PopupMenuItem<int>(
                    value: 1,
                    child: const Text('Delete Plant'),
                    onTap: () => _deletePlant(context),
                  ),
                ],
            ),
            Tooltip(
              message: "Water the plant",
              child: _buildCustomImageButton(
                context,
                child: Image.asset(
                  'lib/assets/images/watering_can.png',
                  color: Theme.of(context).colorScheme.onSurface,
                ),
                onPressed: () => _waterPlant(context),
              ),
            ),
            CustomIconButton(
              icon: Icons.info_outline,
              popupMenuItems: [
                PopupMenuItem<int>(
                  value: 0,
                  child: const Text("Fun Fact"),
                  onTap: () => _fetchDescription(context),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  void _editPlantName(BuildContext context) {
    _controller.text = plant.nickname;

    showDialog(
        context: context,
        builder: (context) {
          return AlertDialog(
            title:const Text("Edit Plant Name"),
            content: TextField(
              controller: _controller,
              decoration: const InputDecoration(hintText: "Enter new name"),
            ),
            actions: [
              TextButton(
                  onPressed: () {
                    _controller.clear();
                    Navigator.pop(context);
                  },
                  child: const Text("Cancel"),
              ),
              TextButton(onPressed: () {
                final plantProvider = context.read<PlantProvider>();

                plantProvider.changeNickName(context, plant.plantId, _controller.text);

                Navigator.pop(context);
              },
                  child: const Text("Save")
              ),
            ],
          );
      },
    );
  }

  void _waterPlant(BuildContext context) {
    final plantProvider = context.read<PlantProvider>();

    plantProvider.waterPlant(context, plant.plantId);
  }

  void _deletePlant(BuildContext context) {
    final plantProvider = context.read<PlantProvider>();

    plantProvider.removePlant(context, plant.plantId);
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

  void _fetchDescription(BuildContext context) async { //Fetches the plant description based on its common name
    String? description = await Provider.of<PlantProvider>(context, listen: false)
        .getPlantDescription(context, plant.commonName);

    if (description != null) {
      _showDescriptionDialog(context, description);
    } else {
      _showDescriptionDialog(context, "No description found.");
    }
  }

//Helper function to show the description in a dialog
  void _showDescriptionDialog(BuildContext context, String description) {
    showDialog(
      context: context,
      barrierDismissible: true,
      builder: (context) {
        return _buildInfoDialog(
          context,
          title: "Fun Fact about ${plant.commonName}",
          content: description,
        );
      },
    );
  }

//Custom dialog that shows information
  Widget _buildInfoDialog(BuildContext context, {required String title, required String content}) {
    return Stack(
      children: [
        BackdropFilter(
          filter: ImageFilter.blur(sigmaX: 4, sigmaY: 4),
          child: Container(
            color: Colors.black.withOpacity(0.2),
          ),
        ),
        Center(
          child: Dialog(
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(20),
            ),
            backgroundColor: Theme.of(context).colorScheme.primary,
            child: Padding(
              padding: const EdgeInsets.all(14.0),
              child: Column(
                mainAxisSize: MainAxisSize.min,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  Text(
                    title,
                    style: const TextStyle(
                      fontSize: 24,
                      fontWeight: FontWeight.bold,
                    ),
                    textAlign: TextAlign.center,
                  ),
                  const SizedBox(height: 10),
                  Padding(
                    padding: const EdgeInsets.all(10),
                    child: Text(
                      content,
                      style: const TextStyle(fontSize: 16),
                      textAlign: TextAlign.center,
                    ),
                  ),
                  TextButton(
                    onPressed: () => Navigator.pop(context),
                    child: Text(
                      "Close",
                      style: TextStyle(
                        fontSize: 16,
                        color: Theme.of(context).colorScheme.surface,
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ),
        ),
      ],
    );
  }


}
