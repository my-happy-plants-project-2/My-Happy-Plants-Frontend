import 'package:flutter/material.dart';
import 'package:my_happy_plants_flutter/components/app_drawer.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('My Plants Page'),
        centerTitle: true,
      ),
      drawer: AppDrawer(),
      body: Center(
        child: Text('My Plants Page'),
      ),
    );
  }
}
