import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:my_happy_plants_flutter/components/plant_library_addialog.dart';
import 'package:my_happy_plants_flutter/model/plant.dart';

//Testar krav:
//COL 01-F  Add Plant To collection
//COL 04-F Naming of plant

void main() {
  testWidgets(
    'PlantLibraryAddDialog saves the correct nickname when Save is clicked',
    (tester) async {
      // Step 1: Set up the test environment
      final controller = TextEditingController();
      final plant = Plant(
        plantId: '1',
        commonName: 'Monstera',
        scientificName: 'Monstera deliciosa',
        familyName: 'Araceae',
        imagePath: 'lib/assets/images/plants/monstera.png',
        nickname: 'Vera',
        lastWatered: DateTime.now(),
        waterFrequency: 6,
        light: 7,
      );
      bool onSaveCalled = false;
      String? savedNickname;

      // Define onSave to capture the nickname and close the dialog
      void onSave(BuildContext dialogContext) {
        onSaveCalled = true;
        savedNickname = controller.text;
        Navigator.of(dialogContext).pop();
      }

      void onCancel() {}

      // Step 2: Pump the widget tree
      await tester.pumpWidget(
        MaterialApp(
          home: Scaffold(
            body: Builder(
              builder: (BuildContext context) => ElevatedButton(
                onPressed: () {
                  showDialog(
                    context: context,
                    builder: (dialogContext) => PlantLibraryAddDialog(
                      //Here we put the PlantLibraryAddDialog for the testing
                      controller: controller,
                      plant: plant,
                      onSave: () =>
                          onSave(dialogContext), //creates it when pressed save
                      onCancel: onCancel,
                    ),
                  );
                },
                child: const Text('Input nickname...'),
              ),
            ),
          ),
        ),
      );

      // Step 3: Simulate showing the dialog
      await tester.tap(find.text('Input nickname...'));
      await tester.pumpAndSettle();
      expect(find.byType(AlertDialog), findsOneWidget);

      // Step 4: Simulate user input
      await tester.enterText(find.byType(TextField), 'My Test Plant');
      expect(controller.text, 'My Test Plant');

      // Step 5: Simulate clicking "Save"
      await tester.tap(find.text('Save'));
      await tester.pumpAndSettle();

      // Step 6: Verify the dialog is dismissed and results are correct
      expect(find.byType(AlertDialog), findsNothing);
      expect(onSaveCalled, true);
      expect(savedNickname, 'My Test Plant');
    },
  );
}
