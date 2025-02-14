import 'package:flutter/material.dart';

//@author Pehr Norten
class WaterBar extends StatelessWidget {
  final double value; // Value between 0.0 and 1.0
  const WaterBar({super.key, required this.value});

  @override
  Widget build(BuildContext context) {
    return Expanded(
      child: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 50),
        child: Stack(
          children: [
            // Background container, transparent with border
            Container(
              decoration: BoxDecoration(
                color: Colors.transparent,
                borderRadius: BorderRadius.circular(20),
                border: Border.all(
                  color: Colors.blue.shade900,
                  width: 1.2,
                ),
              ),
            ),
            // Filled container (the progress bar)
            FractionallySizedBox(
              widthFactor: value, // progress value
              child: Container(
                decoration: BoxDecoration(
                  color: Colors.blue.shade700
                      .withAlpha(200), // Blue for the filled part
                  borderRadius: BorderRadius.circular(20),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
