import 'package:flutter/material.dart';
import '../model/plant.dart';

class LibraryProvider extends ChangeNotifier {
    List<Plant> _allPlants = [];

    void fillLibraryList(List<Plant> plants) {
      _allPlants = plants;
      notifyListeners();
    }
}