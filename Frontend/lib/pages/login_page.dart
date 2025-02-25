import 'package:flutter/material.dart';
import 'package:my_happy_plants_flutter/components/error_message_overlay.dart';
import 'package:my_happy_plants_flutter/providers/login_provider.dart';
import 'package:provider/provider.dart';

//@author Filip Claesson, Pehr Norten
class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<StatefulWidget> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();

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

    //TODO: Här nere ligger en potentiell login metod, den är bortkommenterad för att jag inte har en login logik fixad.
    /*
    final success = await loginProvider.login(email, password);

    if(success) {
      Navigator.pushNamed(context, '/home_page');
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text("Invalid credentials!")),
      );
    }
     */
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: Center(
        child: Container(
          width: 450,
          height: 450,
          padding: const EdgeInsets.all(25),
          decoration: BoxDecoration(
            color: Theme.of(context).colorScheme.primary,
            borderRadius: BorderRadius.circular(20),
          ),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              const Text(
                "Login",
                style: TextStyle(
                  fontSize: 28,
                  fontWeight: FontWeight.bold,
                ),
              ),
              const SizedBox(height: 25),
              _buildTextField("Email", Icons.email, _emailController, false),
              const SizedBox(height: 15),
              _buildTextField(
                  "Password", Icons.lock, _passwordController, true),
              const SizedBox(height: 5),
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
                  padding:
                      const EdgeInsets.symmetric(vertical: 14, horizontal: 50),
                ),
                onPressed: () => _login(
                    context), // ------- Detta är troligtvis så login kommer funka senare.
                //TODO: Här ska login funktionen vara. Den ska checka ditt lösenord osv. Nu kommer du alltid in.
                //Navigator.pushNamed(context, '/home_page');

                child: const Text(
                  "Login",
                  style: TextStyle(fontSize: 18),
                ),
              ),
              const Spacer(),
              TextButton(
                onPressed: () {
                  //TODO: Add create account logic.
                },
                child: const Text(
                  "Create Account",
                  style: TextStyle(color: Colors.white70, fontSize: 16),
                ),
              )
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildTextField(String hint, IconData icon,
      TextEditingController controller, bool isPassword) {
    return TextField(
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
    );
  }
}
