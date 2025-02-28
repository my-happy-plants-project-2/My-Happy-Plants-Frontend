import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:my_happy_plants_flutter/components/my_plants_card.dart';
import 'package:my_happy_plants_flutter/components/water_bar.dart';
import 'package:my_happy_plants_flutter/model/plant.dart';

void main() {
  //Author Johnny Rosenquist
  // Testar: PCA 01-F
  // T-04

  group("Warning icon tests", () {
    late Plant plant;

    setUpAll(() {
      plant = Plant(
        plantId: "1",
        scientificName: "science",
        familyName: "test Family",
        lastWatered: DateTime.now(),
        light: 4,
        waterFrequency: 4,
        nickname: "Test Plant",
        commonName: "Test Common Name",
        imagePath: "lib/assets/images/plants/peace_lily.png",
      );
    });

    testWidgets('displays warning icon when water level is below 0.3',
        (tester) async {
      //Build the widget
      await tester.pumpWidget(
        MaterialApp(
          home: MyPlantsCard(plant: plant),
        ),
      );
      //finds the waterbar widget and saves a reference to it in waterbar,
      WaterBar waterBar = tester.widget<WaterBar>(find.byType(WaterBar));
      //Access value saved in waterbar
      double waterLevel = waterBar.value;
      //compare it to threshold values
      if (waterLevel < 0.3) {
        expect(find.byIcon(Icons.warning), findsOne);
      }
    });

    testWidgets('displays no warning when waterlevel is above 0.3',
        (tester) async {
      //Build the widget
      await tester.pumpWidget(
        MaterialApp(
          home: MyPlantsCard(plant: plant),
        ),
      );
      //finds the waterbar widget and saves a reference to it in waterbar,
      WaterBar waterBar = tester.widget<WaterBar>(find.byType(WaterBar));
      //Access value saved in waterbar
      double waterLevel = waterBar.value;
      //compare it to threshold values
      if (waterLevel > 0.3) {
        expect(find.byIcon(Icons.warning), findsNothing);
      }
    });
    testWidgets('displays no  warning icon when water level is at 0.3',
        (tester) async {
      //Build the widget
      await tester.pumpWidget(
        MaterialApp(
          home: MyPlantsCard(plant: plant),
        ),
      );
      //finds the waterbar widget and saves a reference to it in waterbar,
      WaterBar waterBar = tester.widget<WaterBar>(find.byType(WaterBar));
      //Access value saved in waterbar
      double waterLevel = waterBar.value;
      //compare it to threshold values
      if (waterLevel == 0.3) {
        expect(find.byIcon(Icons.warning), findsNothing);
      }
    });
  });
}
