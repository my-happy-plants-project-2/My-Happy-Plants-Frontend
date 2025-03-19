import 'package:flutter/material.dart';
import 'package:my_happy_plants_flutter/components/plant_library_card.dart';
import 'package:my_happy_plants_flutter/model/plant.dart';
import 'package:provider/provider.dart';

import '../providers/library_provider.dart';
import 'dart:async';

//@author Filip Claesson, Pehr Norten, Ida Nordenswan

//Main page for the plantlibrary
class PlantLibraryPage extends StatefulWidget {
  PlantLibraryPage({super.key});

  @override
  State<PlantLibraryPage> createState() => _PlantLibraryPageState();
}

class _PlantLibraryPageState extends State<PlantLibraryPage> {
  TextEditingController _searchController = TextEditingController(); //Controller for search input
  List<Plant> _filteredPlants = []; //List to store filtered plants based on search
  List<Plant> libraryPlants = [];//Complete list of plants

  @override
  void initState() {
    super.initState();
    _loadLibraryPlants(); //Load the library whe page is initialized
  }

  //Fetches the plant library list from the PlantProvider, and updates the library
  Future<void> _loadLibraryPlants() async {
    final libraryProvider = Provider.of<LibraryProvider>(context, listen: false);
    libraryPlants = await libraryProvider.getLibraryPlantList(context);
    if (mounted) {
      setState(() {
        libraryProvider.fillLibraryList(libraryPlants);
        _filteredPlants = libraryPlants; //Initialize filtered list with all the plants
      });
    }
  }

  //Filters the plants based on search query
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
        child: Column(
          children: [
            Padding(
              padding: const EdgeInsets.all(12),
              child: TextField(
                controller: _searchController,
                onChanged: _filterPlants, //Calls the filtering method
                decoration: InputDecoration(
                  hintText: 'Search plants',
                  prefixIcon: Icon(Icons.search),
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(8),
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
                        return PlantLibraryCard(plant: _filteredPlants[index]); //Display filtered plants
                      }),
                    ),
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
