/*import 'package:flutter/material.dart';

//@author Filip Claesson, Pehr Norten
class SettingsPage extends StatefulWidget {
  const SettingsPage({super.key});

  @override
  State<SettingsPage> createState() => _SettingsPageState();
}

class _SettingsPageState extends State<SettingsPage> {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Text('Settings Page'),
    );
  }
}

//TA BORT ALL KOD NEDANFÖR HÄR NÄR NI VILL JOBBA I SETTINGS OCH KONVERTERA IN DET OVAN, ALTERNATIVT KOLLA MED DEVGRUPP FÖR SPRINT 1
//@author Rick Astley
import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart';
import 'package:webview_windows/webview_windows.dart';
import 'package:url_launcher/url_launcher.dart';

class SettingsPage extends StatefulWidget {
  const SettingsPage({super.key});

  @override
  State<SettingsPage> createState() => _SettingsPageState();
}

class _SettingsPageState extends State<SettingsPage> {
  final _controller = WebviewController();
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    if (!kIsWeb && defaultTargetPlatform == TargetPlatform.windows) {
      _initWebView();
    }
  }

  void _initWebView() async {
    await _controller.initialize();
    await _controller.loadUrl('https://www.youtube.com/watch?v=dQw4w9WgXcQ');
    setState(() => _isLoading = false);
  }

  void _fallbackLaunch() async {
    const url = 'https://youtu.be/dQw4w9WgXcQ';
    if (await canLaunchUrl(Uri.parse(url))) {
      await launchUrl(Uri.parse(url));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Container(
          width: 300,
          height: 200,
          decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(15),
              boxShadow: [
                BoxShadow(
                    color: Colors.black,
                    spreadRadius: 3,
                    blurRadius: 7,
                    offset: const Offset(0, 3)),
              ]),
          child: ClipRRect(
            borderRadius: BorderRadius.circular(15),
            child: kIsWeb || defaultTargetPlatform != TargetPlatform.windows
                ? ElevatedButton(
                    onPressed: _fallbackLaunch,
                    child: const Text('Klicka för att visa settings'))
                : _isLoading
                    ? const Center(child: CircularProgressIndicator())
                    : Webview(_controller),
          ),
        ),
      ),
    );
  }
}

 */




import 'package:flutter/material.dart';

class SettingsPage extends StatefulWidget {
  const SettingsPage({super.key});

  @override
  State<SettingsPage> createState() => _SettingsPageState();
}

class _SettingsPageState extends State<SettingsPage>{
  void _confirmDeleteAccount() {
    showDialog(context: context, builder: (context) => AlertDialog(
      title: const Text ("Delete Account"),
      content: const Text ("Are you sure you want to delete your account?"), 
      actions: [
        TextButton(
          onPressed: () => Navigator.of(context).pop(),
          child: const Text("Cancel"),
        ),
        ElevatedButton(
            style: ElevatedButton.styleFrom(
              backgroundColor: Colors.green,
            ),
            onPressed: () {
              Navigator.of(context).pop();
              _deleteAccount();
            },
            child: const Text("Delete", style: TextStyle(color: Colors.white))
          ),
        ],
      ),
    );
  }
  void _deleteAccount(){
    //Databas kall för att radera konto, med errorMessage


    if (success){
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text("Account deleted successfully")),
      );
      Navigator.pushReplacementNamed(context, '/login_page');
    }else {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text("Failed to delete account")),
      );
    }
  }



  @override
  Widget build(BuildContext context){
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Image.asset('lib/assets/images/plants/parlor_palm.png',height: 150),
            const SizedBox(height: 20),
            ElevatedButton(
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.green,
                padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 12),
                shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
              ),
              onPressed: _confirmDeleteAccount,
              child: const Text("Delete Account", style: TextStyle(color: Colors.white)),
            ),
          ],
        ),
      ),
    );
  }
}











