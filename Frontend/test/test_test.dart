import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  testWidgets('Minimal test with no custom widgets',
      (WidgetTester tester) async {
    await tester.pumpWidget(
      const MaterialApp(
        home: Scaffold(
          body: Text('Hello'),
        ),
      ),
    );
    await tester.pumpAndSettle();
    expect(find.text('Hello'), findsOneWidget);
  });
}
