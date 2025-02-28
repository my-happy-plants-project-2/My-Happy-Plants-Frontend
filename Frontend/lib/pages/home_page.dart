import 'package:flutter/material.dart';
import 'package:my_happy_plants_flutter/components/app_drawer.dart';
import 'package:my_happy_plants_flutter/pages/my_plants_page.dart';
import 'package:my_happy_plants_flutter/pages/plant_library_page.dart';
import 'package:my_happy_plants_flutter/pages/settings_page.dart';

//@author Filip Claesson, Pehr Norten, Christian Storck
class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  int _selectedIndex = 0;
  bool _isHovered = false;

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
        backgroundColor: Theme.of(context).colorScheme.primary,
        title: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Expanded(
              child: Text(
              _appBarTitles[_selectedIndex],
              textAlign: TextAlign.center,
              style: TextStyle(
                color: Theme.of(context).colorScheme.inversePrimary,
                fontSize: 28,
              ),
            ),
      ),
              ],
        ),
              actions: [
                MouseRegion(
                  onEnter: (_) {
                    setState(() {
                      _isHovered = true;
                    });
                  },
                  onExit: (_) {
                    setState(() {
                      _isHovered = false;
                    });
                  },
                  child: Padding(
                    padding: const EdgeInsets.only(right:20),
                  child: TextButton(
                    onPressed: () {
                      print("DU TRYCKTE SÖK"); //TODO: Lägg in searchfunktion här.
                    },
                    style: TextButton.styleFrom(
                      padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
                      backgroundColor: _isHovered
                        ? Theme.of(context).colorScheme.primary.withOpacity(0.5)
                          : Theme.of(context).colorScheme.surface.withOpacity(0.2),
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(12),
                      ),
                    ),
            child: Row(
              children: [
                Icon(Icons.search, color: Theme.of(context).colorScheme.inversePrimary),
                const SizedBox(width: 6),
                Text(
                  'Search',
                  style: TextStyle(
                    color: Theme.of(context).colorScheme.inversePrimary,
                    fontSize: 18,
                  ),
                ),
              ],
            ),
          ),
                ),
                ),
      ],
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
