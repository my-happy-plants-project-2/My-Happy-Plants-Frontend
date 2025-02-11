import 'package:flutter/material.dart';
import 'package:my_happy_plants_flutter/components/plant_library_image.dart';
import 'package:my_happy_plants_flutter/model/plant.dart';

class PlantLibraryCard extends StatelessWidget {
  final Plant plant;
  const PlantLibraryCard({super.key, required this.plant});

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: 450,
      height: 530,
      child: Container(
        padding: const EdgeInsets.all(20),
        decoration: BoxDecoration(
          color: Theme.of(context).colorScheme.primary,
          borderRadius: BorderRadius.circular(20),
          border: Border.all(
            color: Theme.of(context).colorScheme.primary,
            width: 3,
          ),
        ),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Column(
              children: [
                Padding(
                  padding: const EdgeInsets.only(bottom: 10),
                  child: PlantImage(plant: plant),
                ),
                Text(
                  plant.commonName,
                  style: Theme.of(context).textTheme.titleLarge,
                ),
                Divider(
                  color: Theme.of(context).colorScheme.onSurface,
                  thickness: 2,
                ),
                ..._buildPlantInfo(context),
              ],
            ),
            _buildAddButton(context),
          ],
        ),
      ),
    );
  }

  List<Widget> _buildPlantInfo(BuildContext context) {
    return [
      _buildInfoRow('Sci. name:', plant.scientificName),
      _buildInfoRow('Fam. name:', plant.familyName),
      _buildInfoRow('Water req.:', '${plant.waterFrequency}/10'),
      _buildInfoRow('Light req.:', '${plant.light}/10'),
    ];
  }

  Widget _buildInfoRow(String label, String value) {
    return Align(
      alignment: Alignment.centerLeft,
      child: Text(
        '$label $value',
        style: const TextStyle(
          fontSize: 16,
          fontFamily: 'Merienda',
          fontWeight: FontWeight.bold,
        ),
      ),
    );
  }

  Widget _buildAddButton(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.end,
      children: [
        IconButton(
          onPressed: () {},
          icon: Icon(
            Icons.add,
            size: 40,
            color: Theme.of(context).colorScheme.onSurface,
          ),
          style: ButtonStyle(
            backgroundColor: WidgetStatePropertyAll(
              Colors.white.withAlpha(100),
            ),
          ),
        ),
      ],
    );
  }
}
