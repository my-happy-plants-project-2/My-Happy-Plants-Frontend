import 'package:flutter/material.dart';

class PlantLibraryPage extends StatefulWidget {
  const PlantLibraryPage({super.key});

  @override
  State<PlantLibraryPage> createState() => _PlantLibraryPageState();
}

class _PlantLibraryPageState extends State<PlantLibraryPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Plant Library Page'),
      ),
      body: Center(
        child: Text('Plant Library Page'),
      ),
    );
  }
}
