import 'package:flutter/material.dart';
import 'package:my_happy_plants_flutter/components/my_plants_card.dart';
import 'package:my_happy_plants_flutter/model/plant.dart';
import 'package:provider/provider.dart';

import '../providers/plant_provider.dart';

//@author Filip Claesson, Pehr Norten, Christian Storck
class MyPlantsPage extends StatefulWidget {
  MyPlantsPage({super.key});

  @override
  State<MyPlantsPage> createState() => _MyPlantsPageState();
}

class _MyPlantsPageState extends State<MyPlantsPage> {
  @override
  void initState() {
    super.initState();
    _loadUserPlants();
  }

  Future<void> _loadUserPlants() async { //Method that fetches the plantlist from the plantprovider.
    final plantProvider = Provider.of<PlantProvider>(context, listen: false);
    List<Plant> userPlants = await plantProvider.getUserPlantList(context);
    plantProvider.fillUserList(userPlants);
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Padding(
        padding: const EdgeInsets.all(12),
        child: Consumer<PlantProvider>(
          builder: (context, plantProvider, child) {
            return Center(
              child: Wrap(
                spacing: 8.0,
                runSpacing: 8.0,
                alignment: WrapAlignment.start,
                children: List.generate(
                  plantProvider.userPlants.length,
                      (index) {
                    return MyPlantsCard(plant: plantProvider.userPlants[index]);
                  },
                ),
              ),
            );
          },
        ),
      ),
    );
  }
}
