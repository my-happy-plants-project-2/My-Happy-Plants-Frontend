import 'package:flutter/material.dart';
import 'package:my_happy_plants_flutter/model/plant.dart';

//Displays plant image with water and light indicators
//@author Filip Claesson, Pehr Norten
class PlantImage extends StatelessWidget {
  final Plant plant;
  const PlantImage({super.key, required this.plant});

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 240,
      width: double.infinity,
      decoration: BoxDecoration(
        color: Colors.white.withAlpha(120),
        borderRadius: BorderRadius.circular(20),
      ),
      child: Stack(
        children: [
          Column(
            children: [
              Expanded(
                flex: 15,
                child: Center(
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Image.asset(
                      plant.imagePath,
                      fit: BoxFit.contain,
                    ),
                  ),
                ),
              ),
              const SizedBox(height: 15),
            ],
          ),
          // Indicators
          Positioned(
            top: 12,
            right: 12,
            child: _buildIndicatorContainer(),
          ),
        ],
      ),
    );
  }

  //Builds container for sun and water indicators
  Widget _buildIndicatorContainer() {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 8),
      decoration: BoxDecoration(
        color: Colors.white.withAlpha(180),
        borderRadius: BorderRadius.circular(20),
      ),
      child: Row(
        children: [
          _buildIndicator(
            icon: Icons.sunny,
            color: Colors.amber.shade700,
            value: plant.light.toString(),
          ),
          const SizedBox(width: 3),
          _buildIndicator(
            icon: Icons.water_drop,
            color: Colors.blue.shade700.withAlpha(200),
            value: plant.waterFrequency.toString(),
          ),
        ],
      ),
    );
  }

  //creates a single indicator with icon and value
  Widget _buildIndicator({
    required IconData icon,
    required Color color,
    required String value,
  }) {
    return Row(
      children: [
        Icon(icon, color: color, size: 20),
        const SizedBox(width: 2),
        Text(
          value,
          style: const TextStyle(
            fontSize: 16,
            fontFamily: 'Merienda',
          ),
        ),
      ],
    );
  }
}
