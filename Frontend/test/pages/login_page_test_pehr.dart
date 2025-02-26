import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mockito/mockito.dart';
import 'package:provider/provider.dart';
import 'package:my_happy_plants_flutter/pages/login_page.dart';
import 'package:my_happy_plants_flutter/providers/authentication_provider.dart';
import 'package:my_happy_plants_flutter/providers/login_provider.dart';

class MockLoginProvider extends Mock implements LoginProvider {}

class MockAuthenticationProvider extends Mock
    implements AuthenticationProvider {}

void main() {
  late MockLoginProvider mockLoginProvider;
  late MockAuthenticationProvider mockAuthProvider;
  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    mockLoginProvider = MockLoginProvider();
    mockAuthProvider = MockAuthenticationProvider();
  });

  tearDown(() {
    reset(mockLoginProvider);
    reset(mockAuthProvider);
  });

  Widget createTestWidget() {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider<LoginProvider>(create: (_) => mockLoginProvider),
        ChangeNotifierProvider<AuthenticationProvider>(
            create: (_) => mockAuthProvider),
      ],
      child: const MaterialApp(
        home: LoginPage(),
      ),
    );
  }

  testWidgets('Renders LoginPage without error', (WidgetTester tester) async {
    await tester.pumpWidget(createTestWidget());
    await tester.pumpAndSettle();
    expect(find.text('Login'), findsAny);
  });
}
