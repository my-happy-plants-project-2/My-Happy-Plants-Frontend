import 'package:flutter/material.dart';
import 'package:my_happy_plants_flutter/components/plant_library_card.dart';
import 'package:my_happy_plants_flutter/model/plant.dart';
import 'package:provider/provider.dart';

import '../providers/plant_provider.dart';

//@author Filip Claesson, Pehr Norten
//Main page for the plantlibrary
class PlantLibraryPage extends StatefulWidget {
  PlantLibraryPage({super.key});

  //Sample plant data, "hardcoded"
  final List<Plant> plants = [
    Plant(
      plantId: '1',
      commonName: 'Devil\'s Ivy',
      scientificName: 'Epipremnum aureum',
      familyName: 'Araceae',
      imagePath: 'lib/assets/images/plants/devils_ivy.png',
      nickname: 'Ivy',
      lastWatered: DateTime.now(),
      waterFrequency: 5,
      light: 4,
    ),
    Plant(
      plantId: '2',
      commonName: 'Monstera',
      scientificName: 'Monstera deliciosa',
      familyName: 'Araceae',
      imagePath: 'lib/assets/images/plants/monstera.png',
      nickname: 'Vera',
      lastWatered: DateTime.now(),
      waterFrequency: 6,
      light: 7,
    ),
    Plant(
      plantId: '3',
      commonName: "Snake Plant",
      scientificName: "Sansevieria trifasciata",
      familyName: "Asparagaceae",
      imagePath: 'lib/assets/images/plants/snake_plant.png',
      nickname: 'Lily',
      lastWatered: DateTime.now(),
      waterFrequency: 2,
      light: 3,
    ),
    Plant(
      plantId: '4',
      commonName: "Parlor Palm",
      scientificName: "Chamaedorea elegans",
      familyName: "Arecaceae",
      imagePath: 'lib/assets/images/plants/parlor_palm.png',
      nickname: 'Spidey',
      lastWatered: DateTime.now(),
      waterFrequency: 5,
      light: 5,
    ),
    Plant(
      plantId: '5',
      commonName: "Anthurium Plant",
      scientificName: "Anthurium andraeanum",
      familyName: "Arums",
      imagePath: 'lib/assets/images/plants/anthurium_plant.png',
      nickname: 'Spidey',
      lastWatered: DateTime.now(),
      waterFrequency: 6,
      light: 7,
    ),
    Plant(
      plantId: '6',
      commonName: "Spider Plant",
      scientificName: "Chlorophytum comosum",
      familyName: "Asparagaceae",
      imagePath: 'lib/assets/images/plants/spider_plant.png',
      nickname: 'Spidey',
      lastWatered: DateTime.now(),
      waterFrequency: 7,
      light: 4,
    ),
    Plant(
      plantId: '7',
      commonName: "Fiddle Leaf Fig",
      scientificName: "Ficus lyrata",
      familyName: "Moraceae",
      imagePath: 'lib/assets/images/plants/fiddle_leaf_fig.png',
      nickname: 'Figgy',
      lastWatered: DateTime.now(),
      waterFrequency: 7,
      light: 8,
    ),
    Plant(
      plantId: '8',
      commonName: "Dragon Tree",
      scientificName: "Dracaena marginata",
      familyName: "Asparagaceae",
      imagePath: 'lib/assets/images/plants/dragon_tree.png',
      nickname: 'Drago',
      lastWatered: DateTime.now(),
      waterFrequency: 7,
      light: 7,
    ),
    Plant(
      plantId: '9',
      commonName: "Orange Tree",
      scientificName: "Citrus × sinensis",
      familyName: "Rutaceae",
      imagePath: 'lib/assets/images/plants/orange_tree.png',
      nickname: 'Sunny',
      lastWatered: DateTime.now(),
      waterFrequency: 5,
      light: 9,
    ),
    Plant(
      plantId: '10',
      commonName: "Peace Lily",
      scientificName: "Spathiphyllum",
      familyName: "Araceae",
      imagePath: 'lib/assets/images/plants/peace_lily.png',
      nickname: 'Lily',
      lastWatered: DateTime.now(),
      waterFrequency: 4,
      light: 6,
    ),
    Plant(
      plantId: '11',
      commonName: "Bromeliad",
      scientificName: "Bromeliaceae",
      familyName: "Bromeliads",
      imagePath: 'lib/assets/images/plants/bromeliad.png',
      nickname: 'Flare',
      lastWatered: DateTime.now(),
      waterFrequency: 5,
      light: 7,
    ),
    Plant(
      plantId: '12',
      commonName: "Trailing Peperomia",
      scientificName: "Peperomia angulata",
      familyName: "Piperaceae",
      imagePath: 'lib/assets/images/plants/trailing_peperomia.png',
      nickname: 'Green Cascade',
      lastWatered: DateTime.now(),
      waterFrequency: 5,
      light: 4,
    ),
  ];

  @override
  State<PlantLibraryPage> createState() => _PlantLibraryPageState();
}

class _PlantLibraryPageState extends State<PlantLibraryPage> {
  @override
  void initState() {
    super.initState();
    final plantProvider = Provider.of<PlantProvider>(context, listen: false);
    plantProvider.fillLibraryList(widget.plants);
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Theme.of(context).colorScheme.surface.withAlpha(200),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(12),
          child: Center(
            child: Wrap(
              spacing: 8.0,
              runSpacing: 8.0,
              children: List.generate(widget.plants.length, (index) {
                return PlantLibraryCard(plant: widget.plants[index]);
              }),
            ),
          ),
        ),
      ),
    );
  }
}
