import 'package:flutter/material.dart';
import 'package:my_happy_plants_flutter/components/custom_icon_button.dart';
import 'package:my_happy_plants_flutter/components/plant_library_addialog.dart';
import 'package:my_happy_plants_flutter/components/plant_library_image.dart';
import 'package:my_happy_plants_flutter/model/plant.dart';

// Card widget displaying plant information for some pre-set plants, with add to your own plants function
// @author Filip Claesson, Pehr Norten
class PlantLibraryCard extends StatelessWidget {
  final Plant plant;
  PlantLibraryCard({super.key, required this.plant});

  final _controller =
      TextEditingController(); //Här får vi tillgång till namnet vi gett plantan

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: 450,
      height: 500,
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
            Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                CustomIconButton(
                    icon: Icons.add,
                    onPressed: () => giveSpecificPlantName(context)),
              ],
            )
          ],
        ),
      ),
    );
  }

  //Generates plant information as a row
  List<Widget> _buildPlantInfo(BuildContext context) {
    return [
      _buildInfoRow('Sci. name:', plant.scientificName),
      _buildInfoRow('Fam. name:', plant.familyName),
      _buildInfoRow('Water req.:', '${plant.waterFrequency}/10'),
      _buildInfoRow('Light req.:', '${plant.light}/10'),
    ];
  }

  //Creates a single information row with label and value
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

  //Save a plant name that user put in
  //TO DO:
  void saveNewPlantName() {
    //Här ska skrivas det som sparas till en planta och sen vidare till databasen
    //Det är i själva _controller den biten sparas och tanken är att det ska skickas till en post-endpoint
  }

  //Shows dialog for specifying plant nickname, and what to do when pressing save/cancel
  void giveSpecificPlantName(BuildContext context) {
    showDialog(
      context: context,
      builder: (context) {
        return PlantLibraryAddDialog(
          controller: _controller,
          plant: plant,
          onSave: saveNewPlantName,
          onCancel: () {
            _controller.clear();
            Navigator.pop(context);
          },
        );
      },
    );
  }
}
