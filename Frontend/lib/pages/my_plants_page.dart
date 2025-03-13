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
  bool _isSortedAscending = true;

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
  void _sortByWaterNeeds(PlantProvider plantProvider) {
    setState(() {
      _isSortedAscending = !_isSortedAscending; // Toggle sorting order

      plantProvider.userPlants.sort((a, b) {
        return _isSortedAscending
            ? a.waterFrequency.compareTo(b.waterFrequency) //Ascending order
            : b.waterFrequency.compareTo(a.waterFrequency); //Descending order
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        decoration: BoxDecoration(
          gradient: LinearGradient(
            begin: Alignment.topLeft,
            end: Alignment.bottomRight,
            colors: [
              Color.fromARGB(192, 204, 255, 204), // Very light green
              Color.fromARGB(166, 255, 255, 204), // Very light yellow
            ],
          ),
        ),
        child: SingleChildScrollView(
          child: Padding(
            padding: const EdgeInsets.all(12),
            child: Consumer<PlantProvider>(
              builder: (context, plantProvider, child) {
                return Column(
                  children: [
                    Align(
                      alignment: Alignment.centerLeft,
                      child: ElevatedButton.icon(
                        onPressed: () => _sortByWaterNeeds(plantProvider),
                        icon: Icon(Icons.water_drop),
                        label: Text(
                          _isSortedAscending
                              ? 'Water Needs: High to Low'
                              : 'Water Needs: Low to High',
                        ),
                      ),
                    ),
                    const SizedBox(height: 12),
                    Center(
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
                    ),
                  ],
                );
              },
            ),
          ),
        ),
      ),
    );
  }
}
