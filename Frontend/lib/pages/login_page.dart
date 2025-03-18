import 'package:flutter/material.dart';
import 'package:my_happy_plants_flutter/components/error_message_overlay.dart';
import 'package:my_happy_plants_flutter/providers/authentication_provider.dart';
import 'package:my_happy_plants_flutter/providers/login_provider.dart';
import 'package:provider/provider.dart';

//@author Filip Claesson, Pehr Norten, Christian Storck, Ida Nordenswan
class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<StatefulWidget> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final TextEditingController _userNameController = TextEditingController();
  final TextEditingController _passwordVerifyController =
      TextEditingController();

  bool _isSignUpMode = false;

  void _toggleLoginMode() { //Changes state depending on if you want to login or create account.
    setState(() {
      _isSignUpMode = !_isSignUpMode;
    });
  }

  void _login(BuildContext context) async {
    final loginProvider = context.read<LoginProvider>();
    final email = _emailController.text.trim();
    final password = _passwordController.text.trim();

    // Check if email or password is empty, if so show a snackbar and return.
    if (email.isEmpty && password.isEmpty) {
      errorMessageOverlay(context, "Email and password is empty!");
      return;
    } else if (email.isEmpty) {
      errorMessageOverlay(context, "Email is empty!");
      return;
    } else if (password.isEmpty) {
      errorMessageOverlay(context, "Password is empty!");
      return;
    }

    final success = await loginProvider.login(email, password);

    if (success) { // Redirects to the homepage if login is successfull
      Navigator.pushNamed(context, '/home_page');
    } else {
      errorMessageOverlay(context, "Invalid credentials");
      Navigator.pushNamed(context, '/login_page');
    }
  }

  void _signUp(BuildContext context) async { //Takes user input and sends it to the "AuthenticationProvider". Creates account if success.
    final authProvider = context.read<AuthenticationProvider>();
    final userName = _userNameController.text.trim();
    final email = _emailController.text.trim();
    final password = _passwordController.text.trim();
    final confirmPassword = _passwordVerifyController.text.trim();

    if (userName.isEmpty ||
        email.isEmpty ||
        password.isEmpty ||
        confirmPassword.isEmpty) {
      errorMessageOverlay(context, "All fields must be filled");
      return;
    }

    if (password != confirmPassword) {
      errorMessageOverlay(context, "Password do not match");
      return;
    }

    final success = await authProvider.createAccount(userName, email, password);

    if (success) {
      Navigator.pushNamed(context, '/login_page');
    } else {
      errorMessageOverlay(context, "Failed to create account");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.transparent, //Background
      body: Container(
        decoration: BoxDecoration(
          gradient: LinearGradient(
            begin: Alignment.topLeft,
            end: Alignment.bottomRight,
            colors: [
              Color.fromARGB(192, 156, 224, 156), //Muted light green
              Color.fromARGB(166, 255, 255, 200), //Muted light yellow
            ],
          ),
        ),

      child: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Center(
          //Added this to center everything
          child: SingleChildScrollView(
            child: Container(
              width: 450,
              padding: const EdgeInsets.all(25),
              decoration: BoxDecoration(
                color: Theme.of(context).colorScheme.primary,
                borderRadius: BorderRadius.circular(20),
                boxShadow: [
                  BoxShadow(
                    color: Colors.green.withOpacity(0.3),
                    spreadRadius: 7,
                    blurRadius: 7,
                    offset: Offset(0, 3), //Shadow position
                  ),
                ],
              ),
              child: Column(
                mainAxisSize: MainAxisSize.min,
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text(
                    _isSignUpMode ? "Create Account" : "Login",
                    style: TextStyle(
                      fontSize: 28,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  const SizedBox(height: 25),
                  if (_isSignUpMode)
                    _buildTextField(
                        "Username", Icons.person, _userNameController, false),
                  _buildTextField(
                      "Email", Icons.email, _emailController, false),
                  _buildTextField(
                      "Password", Icons.lock, _passwordController, true),
                  if (_isSignUpMode)
                    _buildTextField("Confirm Password", Icons.lock,
                        _passwordVerifyController, true),
                  const SizedBox(height: 5),
                  if (!_isSignUpMode)
                    Align(
                      alignment: Alignment.centerRight,
                      child: TextButton(
                        onPressed: () {
                          //TODO:  Add forgot password.
                        },
                        child: const Text(
                          "Forgot Password?",
                          style: TextStyle(color: Colors.white70, fontSize: 14),
                        ),
                      ),
                    ),
                  const SizedBox(height: 20),
                  ElevatedButton(
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Theme.of(context).colorScheme.primary,
                      foregroundColor: Colors.white,
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(20),
                      ),
                      padding: const EdgeInsets.symmetric(
                          vertical: 14, horizontal: 50),
                    ),
                    onPressed: _isSignUpMode
                        ? () => _signUp(context)
                        : () => _login(
                            context),
                    child: Text(
                      _isSignUpMode ? "Sign Up" : "Login",
                      style: const TextStyle(fontSize: 18),
                    ),
                  ),
                  const SizedBox(height: 20),
                  TextButton(
                    onPressed: _toggleLoginMode,
                    child: Text(
                      _isSignUpMode
                          ? "Already have an account? Login"
                          : "Create Account",
                      style: TextStyle(color: Colors.white70, fontSize: 16),
                    ),
                  )
                ],
              ),
            ),
          ),
        ),
      ),
      ),
    );
  }

  Widget _buildTextField(String hint, IconData icon, //Widget for the textfields.
      TextEditingController controller, bool isPassword) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 10),
      child: TextField(
        controller: controller,
        obscureText: isPassword,
        decoration: InputDecoration(
          hintText: hint,
          prefixIcon: Icon(icon),
          filled: true,
          fillColor: Colors.white.withAlpha(200),
          border: OutlineInputBorder(
            borderRadius: BorderRadius.circular(15),
            borderSide: BorderSide.none,
          ),
        ),
      ),
    );
  }
}


