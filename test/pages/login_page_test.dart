import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:my_happy_plants_flutter/pages/home_page.dart';
import 'package:my_happy_plants_flutter/pages/login_page.dart';


void main() {
  testWidgets("Test click login button navigates to HomePage", (WidgetTester tester) async {
    // Build the LoginPage widget
    await tester.pumpWidget(MaterialApp(
      home: LoginPage(),
      routes: {
        '/home_page': (context) => HomePage(),
      },
    ));

    //Looks for a button labeled "Login"
    var button = find.text("Login");

    //Taps the login button and waits for the animations to complete
    await tester.tap(button);
    await tester.pumpAndSettle();
    //Checks if a widget of type HomePage is present
    expect(find.byType(HomePage), findsOneWidget);
  });
}