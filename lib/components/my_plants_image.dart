import 'package:flutter/material.dart';
import 'package:my_happy_plants_flutter/model/plant.dart';

class PlantImage extends StatelessWidget {
  const PlantImage({
    super.key,
    required this.plant,
  });

  final Plant plant;

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 240,
      width: double.infinity,
      decoration: BoxDecoration(
          color: Colors.white.withAlpha(120),
          borderRadius: BorderRadius.circular(20)),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Expanded(
            flex: 15,
            child: Image.asset(plant.imagePath),
          ),
          Expanded(
            child: Row(
              children: [
                SizedBox(
                  width: 60,
                ),
                Expanded(
                  child: Container(
                    decoration: BoxDecoration(
                        color: Colors.blue.shade700.withAlpha(200),
                        borderRadius: BorderRadius.circular(20)),
                  ),
                ),
                SizedBox(
                  width: 60,
                ),
              ],
            ),
          ),
          SizedBox(
            height: 15,
          ),
        ],
      ),
    );
  }
}
