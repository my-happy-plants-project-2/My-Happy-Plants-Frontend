import 'package:flutter/material.dart';
import 'package:my_happy_plants_flutter/components/plant_library_card.dart';
import 'package:my_happy_plants_flutter/model/plant.dart';
import 'package:provider/provider.dart';

import '../providers/plant_provider.dart';
import 'dart:async';

//@author Filip Claesson, Pehr Norten
//Main page for the plantlibrary
class PlantLibraryPage extends StatefulWidget {
  PlantLibraryPage({super.key});

  @override
  State<PlantLibraryPage> createState() => _PlantLibraryPageState();
}

class _PlantLibraryPageState extends State<PlantLibraryPage> {
  TextEditingController _searchController = TextEditingController();
  List<Plant> _filteredPlants = [];
  List<Plant> libraryPlants = [];

  @override
  void initState() {
    super.initState();
    _loadLibraryPlants();
  }

  Future<void> _loadLibraryPlants() async {
    final plantProvider = Provider.of<PlantProvider>(context, listen: false);
    libraryPlants = await plantProvider.getLibraryPlantList(context);
    if (mounted) {
      setState(() {
        plantProvider.fillLibraryList(libraryPlants);
        _filteredPlants = libraryPlants;
      });
    }
  }

  Timer? _debounce;
  void _filterPlants(String query) {
    if (_debounce?.isActive ?? false) _debounce!.cancel();
    _debounce = Timer(const Duration(milliseconds: 300), () {
      setState(() {
        _filteredPlants = libraryPlants
            .where((plant) =>
        plant.commonName.toLowerCase().contains(query.toLowerCase()) ||
            plant.scientificName.toLowerCase().contains(query.toLowerCase()))
            .toList();
      });
    });
  }
  Future<void> _loadUserPlants() async {
    final plantProvider = Provider.of<PlantProvider>(context, listen: false);
    List<Plant> userPlants = await plantProvider.getUserPlantList(context);
    plantProvider.fillUserList(userPlants);
  }



  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Theme.of(context).colorScheme.surface.withAlpha(200),
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(12),
            child: TextField(
              controller: _searchController,
              onChanged: _filterPlants,
              decoration: InputDecoration(
                hintText: 'Search plants',
                prefixIcon: Icon(Icons.search),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(8)
                ),
              ),
            ),
          ),
          Expanded(
            child: SingleChildScrollView(
              child: Padding(
                padding: const EdgeInsets.all(12),
                child: Center(
                  child: Wrap(
                    spacing: 8.0,
                    runSpacing: 8.0,
                    children: List.generate(_filteredPlants.length, (index) {
                      return PlantLibraryCard(plant: _filteredPlants[index]);
                    }),
                  ),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
