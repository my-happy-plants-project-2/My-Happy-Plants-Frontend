import 'package:flutter/material.dart';
import 'package:my_happy_plants_flutter/components/app_drawer.dart';
import 'package:my_happy_plants_flutter/pages/my_plants_page.dart';
import 'package:my_happy_plants_flutter/pages/plant_library_page.dart';
import 'package:my_happy_plants_flutter/pages/settings_page.dart';

//@author Filip Claesson, Pehr Norten
class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  int _selectedIndex = 0;

  final List<Widget> _pages = [
    MyPlantsPage(),
    PlantLibraryPage(),
    SettingsPage(),
  ];

  final List<String> _appBarTitles = [
    'My Plants',
    'Plants Library',
    'Settings',
  ];

  void navigateDrawer(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Theme.of(context).colorScheme.surface,
      appBar: AppBar(
        toolbarHeight: 80,
        title: Text(
          _appBarTitles[_selectedIndex],
          style: TextStyle(
            color: Theme.of(context).colorScheme.inversePrimary,
            fontSize: 28,
          ),
        ),
        centerTitle: true,
        backgroundColor: Theme.of(context).colorScheme.primary,
      ),
      drawer: AppDrawer(
        selectedIndex: _selectedIndex,
        onItemTapped: navigateDrawer,
      ),
      body: IndexedStack(
        index: _selectedIndex,
        children: _pages,
      ),
    );
  }
}
