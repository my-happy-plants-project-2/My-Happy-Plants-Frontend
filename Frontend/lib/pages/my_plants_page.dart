import 'package:flutter/material.dart';
import 'package:my_happy_plants_flutter/components/my_plants_card.dart';
import 'package:my_happy_plants_flutter/model/plant.dart';
import 'package:provider/provider.dart';

import '../providers/plant_provider.dart';

//@author Filip Claesson, Pehr Norten, Christian Storck, Ida Nordenswan
class MyPlantsPage extends StatefulWidget {
  MyPlantsPage({super.key});

  //Stateful widget representing the My Plants page
  @override
  State<MyPlantsPage> createState() => _MyPlantsPageState();
}

class _MyPlantsPageState extends State<MyPlantsPage> {
  bool _isSortedAscending = true; //Controls the order of plants based on their water needs

  @override
  void initState() {
    super.initState();
    _loadUserPlants(); //The users plants are loaded when the page is initialized
  }

  Future<void> _loadUserPlants() async { //Method that fetches the plantlist from the plantprovider, and updates the users plant list.
    final plantProvider = Provider.of<PlantProvider>(context, listen: false);
    List<Plant> userPlants = await plantProvider.getUserPlantList(context);
    plantProvider.fillUserList(userPlants);
  }

  //Method for toggle sorting the order of plants based on their water needs
  void _sortByWaterNeeds(PlantProvider plantProvider) {
    setState(() {
      _isSortedAscending = !_isSortedAscending; //Toggle sorting order

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
        child: SizedBox.expand(
          child: Column(
            children: [
              Padding(
                padding: const EdgeInsets.only(top: 20.0),
                child: Align(
                  alignment: Alignment.center,
                  child: ElevatedButton.icon(
                    onPressed: () => _sortByWaterNeeds(context.read<PlantProvider>()),
                    icon: Icon(Icons.water_drop), //Water drop icon for sorting button
                    label: Text(
                      _isSortedAscending
                        ? 'Water Needs: High to Low'
                        : 'Water Needs: Low to High',
                    ),
                  ),
                ),
              ),
              const SizedBox(height: 12),
              Expanded(
                child: Consumer<PlantProvider>(
                  builder: (context, plantProvider, child) {
                    if (plantProvider.userPlants.isEmpty) {
                      return Center(
                        child: Text(
                          "No plants added yet!",
                          style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                        ),
                      );
                    }
                    return SingleChildScrollView(
                      child: Center(
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
                    );
                  },
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

}
