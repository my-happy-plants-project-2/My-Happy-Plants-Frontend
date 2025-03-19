import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mockito/mockito.dart';
import 'package:my_happy_plants_flutter/pages/home_page.dart';
import 'package:my_happy_plants_flutter/providers/plant_provider.dart';
import 'package:provider/provider.dart';
import 'package:my_happy_plants_flutter/pages/login_page.dart';
import 'package:my_happy_plants_flutter/providers/authentication_provider.dart';
import 'package:my_happy_plants_flutter/providers/login_provider.dart';

//@Author Pehr Nortén, Christian Storck
class MockLoginProvider extends Mock implements LoginProvider {
  @override
  Future<bool> login(String email, String password) {
    return super.noSuchMethod(
      Invocation.method(#login, [email, password]),
      returnValue: Future.value(false),
      returnValueForMissingStub: Future.value(false),
    );
  }
}

class MockAuthenticationProvider extends Mock
    implements AuthenticationProvider {
  @override
  Future<bool> createAccount(String userName, String email, String password) {
    return super.noSuchMethod(
      Invocation.method(#createAccount, [userName, email, password]),
      returnValue: Future.value(false),
      returnValueForMissingStub: Future.value(false),
    );
  }
}

void main() {
  late MockLoginProvider mockLoginProvider;
  late MockAuthenticationProvider mockAuthProvider;

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
        ChangeNotifierProvider<LoginProvider>.value(value: mockLoginProvider),
        ChangeNotifierProvider<AuthenticationProvider>.value(value: mockAuthProvider),
        ChangeNotifierProvider(create: (context) => PlantProvider()),
      ],
      child: MaterialApp(
          routes: {
            '/login_page': (context) => const LoginPage(),
            '/home_page': (context) => const HomePage(),
          },
        home: const LoginPage(),
      ),
    );
  }

  //UAC 08-F Error message, T-02
  group("Error message for Login", () {
    testWidgets('Displays error when email and password are empty (Login)',
        (WidgetTester tester) async {
      await tester.pumpWidget(createTestWidget());

      await tester.tap(find.widgetWithText(ElevatedButton, 'Login'));
      await tester.pumpAndSettle();

      expect(find.text('Email and password is empty!'), findsOneWidget);

      // Wait for 3 seconds (overlay removal time)
      await tester.pump(const Duration(seconds: 3));
    });

    testWidgets('Displays error when email is empty (Login)',
        (WidgetTester tester) async {
      await tester.pumpWidget(createTestWidget());

      await tester.enterText(find.byType(TextField).at(1), 'password123');
      await tester.tap(find.widgetWithText(ElevatedButton, 'Login'));
      await tester.pumpAndSettle();

      expect(find.text('Email is empty!'), findsOneWidget);

      // Wait for 3 seconds (overlay removal time)
      await tester.pump(const Duration(seconds: 3));
    });

    testWidgets('Displays error when password is empty (Login)',
        (WidgetTester tester) async {
      await tester.pumpWidget(createTestWidget());

      await tester.enterText(
          find.byType(TextField).at(0), 'testuser@gmail.com');
      await tester.tap(find.widgetWithText(ElevatedButton, 'Login'));
      await tester.pumpAndSettle();

      expect(find.text('Password is empty!'), findsOneWidget);

      // Wait for 3 seconds (overlay removal time)
      await tester.pump(const Duration(seconds: 3));
    });
  });

  //UAC 08.1-F Missing input, T-03
  group("Error message for missing input for Sign Up", () {
    testWidgets(
        'Displays error when everything filled out but passwords do not match (Sign Up)',
        (WidgetTester tester) async {
      await tester.pumpWidget(createTestWidget());

      await tester.tap(find.text('Create Account'));
      await tester.pumpAndSettle();

      await tester.enterText(find.byType(TextField).at(0), 'testuser');
      await tester.enterText(
          find.byType(TextField).at(1), 'testuser@gmail.com');
      await tester.enterText(find.byType(TextField).at(2), 'password123');
      await tester.enterText(find.byType(TextField).at(3), 'password321');
      await tester.tap(find.widgetWithText(ElevatedButton, 'Sign Up'));
      await tester.pumpAndSettle();

      expect(find.text('Password do not match'), findsOneWidget);

      // Wait for 3 seconds (overlay removal time)
      await tester.pump(const Duration(seconds: 3));
    });

    testWidgets('Displays error when nothing is filled out (Sign Up)',
        (WidgetTester tester) async {
      await tester.pumpWidget(createTestWidget());

      await tester.tap(find.text('Create Account'));
      await tester.pumpAndSettle();

      await tester.tap(find.widgetWithText(ElevatedButton, 'Sign Up'));
      await tester.pumpAndSettle();

      expect(find.text('All fields must be filled'), findsOneWidget);

      // Wait for 3 seconds (overlay removal time)
      await tester.pump(const Duration(seconds: 3));
    });

    testWidgets('Displays error when username is not filled out (Sign Up)',
        (WidgetTester tester) async {
      await tester.pumpWidget(createTestWidget());

      await tester.tap(find.text('Create Account'));
      await tester.pumpAndSettle();

      await tester.enterText(
          find.byType(TextField).at(1), 'testuser@gmail.com');
      await tester.enterText(find.byType(TextField).at(2), 'password123');
      await tester.enterText(find.byType(TextField).at(3), 'password321');
      await tester.tap(find.widgetWithText(ElevatedButton, 'Sign Up'));
      await tester.pumpAndSettle();

      expect(find.text('All fields must be filled'), findsOneWidget);

      // Wait for 3 seconds (overlay removal time)
      await tester.pump(const Duration(seconds: 3));
    });

    testWidgets('Displays error when email is not filled out (Sign Up)',
        (WidgetTester tester) async {
      await tester.pumpWidget(createTestWidget());

      await tester.tap(find.text('Create Account'));
      await tester.pumpAndSettle();

      await tester.enterText(find.byType(TextField).at(0), 'testuser');
      await tester.enterText(find.byType(TextField).at(2), 'password123');
      await tester.enterText(find.byType(TextField).at(3), 'password321');
      await tester.tap(find.widgetWithText(ElevatedButton, 'Sign Up'));
      await tester.pumpAndSettle();

      expect(find.text('All fields must be filled'), findsOneWidget);

      // Wait for 3 seconds (overlay removal time)
      await tester.pump(const Duration(seconds: 3));
    });

    testWidgets('Displays error when password is not filled out (Sign Up)',
        (WidgetTester tester) async {
      await tester.pumpWidget(createTestWidget());

      await tester.tap(find.text('Create Account'));
      await tester.pumpAndSettle();

      await tester.enterText(find.byType(TextField).at(0), 'testuser');
      await tester.enterText(
          find.byType(TextField).at(1), 'testuser@gmail.com');
      await tester.enterText(find.byType(TextField).at(3), 'password321');
      await tester.tap(find.widgetWithText(ElevatedButton, 'Sign Up'));
      await tester.pumpAndSettle();

      expect(find.text('All fields must be filled'), findsOneWidget);

      // Wait for 3 seconds (overlay removal time)
      await tester.pump(const Duration(seconds: 3));
    });

    testWidgets(
        'Displays error when confirm password is not filled out (Sign Up)',
        (WidgetTester tester) async {
      await tester.pumpWidget(createTestWidget());

      await tester.tap(find.text('Create Account'));
      await tester.pumpAndSettle();

      await tester.enterText(find.byType(TextField).at(0), 'testuser');
      await tester.enterText(
          find.byType(TextField).at(1), 'testuser@gmail.com');
      await tester.enterText(find.byType(TextField).at(2), 'password123');
      await tester.tap(find.widgetWithText(ElevatedButton, 'Sign Up'));
      await tester.pumpAndSettle();

      expect(find.text('All fields must be filled'), findsOneWidget);

      // Wait for 3 seconds (overlay removal time)
      await tester.pump(const Duration(seconds: 3));
    });
  });

//@Author Christian Storck
  //UAC 01-F Create Account, TC_1.0
  group('Creation of Account', () {
    testWidgets('Displays error when email already exists (Sign Up)',
            (WidgetTester tester) async {
          await tester.runAsync(() async {
            when(mockAuthProvider.createAccount('testuser','test@example.com' ,'pass1234'))
                .thenAnswer((_) async {
              return false;
            });

            await tester.pumpWidget(createTestWidget());

            await tester.tap(find.text('Create Account'));
            await tester.pumpAndSettle();

            await tester.enterText(find.byType(TextField).at(0), 'testuser');
            await tester.enterText(find.byType(TextField).at(1), 'test@example.com');
            await tester.enterText(find.byType(TextField).at(2), 'pass1234');
            await tester.enterText(find.byType(TextField).at(3), 'pass1234');

            await tester.tap(find.widgetWithText(ElevatedButton, 'Sign Up'));
            await tester.pumpAndSettle();
            await Future.delayed(Duration(seconds: 3));

            expect(find.text('Failed to create account'), findsOneWidget);
          });
        });
//@Author Christian Storck
    testWidgets('Redirects to login page when sign up is successful (Sign Up)',
            (WidgetTester tester) async {
          await tester.runAsync(() async {
            when(mockAuthProvider.createAccount('testuser','test@example.com' ,'pass1234'))
                .thenAnswer((_) async {
              return true;
            });

            await tester.pumpWidget(createTestWidget());

            await tester.tap(find.text('Create Account'));
            await tester.pumpAndSettle();

            await tester.enterText(find.byType(TextField).at(0), 'testuser');
            await tester.enterText(find.byType(TextField).at(1), 'test@example.com');
            await tester.enterText(find.byType(TextField).at(2), 'pass1234');
            await tester.enterText(find.byType(TextField).at(3), 'pass1234');

            await tester.tap(find.widgetWithText(ElevatedButton, 'Sign Up'));
            await tester.pumpAndSettle();
            await Future.delayed(Duration(seconds: 3));

            expect(find.byType(LoginPage), findsOneWidget);
          });
        });
  });
  //@Author Christian Storck, AND SOMEONE ELSE
  // UAC 04-F Login, TC_1.1
   group('Login to an account', () {
    //@Author Christian Storck, AND SOMEONE ELSE
    testWidgets('Displays error when login credentials are incorrect',
        (WidgetTester tester) async {
      await tester.runAsync(() async {
      when(mockLoginProvider.login('testuser@gmail.com', 'wrongpassword'))
          .thenAnswer((_) async => false);

      await tester.pumpWidget(createTestWidget());
      await tester.enterText(find.byType(TextField).at(0), 'testuser@gmail.com');
      await tester.enterText(find.byType(TextField).at(1), 'wrongpassword');
      await tester.tap(find.widgetWithText(ElevatedButton, 'Login'));
      await tester.pumpAndSettle();

      expect(find.text('Invalid credentials'), findsOneWidget);
      });
    });
  //@Author Christian Storck
    testWidgets('Redirects user to home page on successful login',
            (WidgetTester tester) async {
          await tester.runAsync(() async {
            when(mockLoginProvider.login('testuser@gmail.com', 'wrongpassword'))
                .thenAnswer((_) async => true);

            await tester.pumpWidget(createTestWidget());
            await tester.enterText(find.byType(TextField).at(0), 'testuser@gmail.com');
            await tester.enterText(find.byType(TextField).at(1), 'wrongpassword');
            await tester.tap(find.widgetWithText(ElevatedButton, 'Login'));
            await tester.pumpAndSettle();

            expect(find.byType(HomePage), findsOneWidget);
          });
        });
  });
}


