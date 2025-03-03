import 'package:flutter/material.dart';
import '../model/plant.dart';

//@author Christian Storck
//Class that SHOULD handle all the logic for the library page.
class LibraryProvider extends ChangeNotifier {
    List<Plant> _allPlants = [];


    void fillLibraryList(List<Plant> plants) {
      _allPlants = plants;
      notifyListeners();
    }
}