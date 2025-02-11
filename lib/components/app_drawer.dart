import 'package:flutter/material.dart';

class AppDrawer extends StatelessWidget {
  const AppDrawer({super.key});

  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Column(
            children: [
              DrawerHeader(
                decoration: BoxDecoration(
                  color: Theme.of(context).colorScheme.primary,
                ),
                child: Center(
                  child: Text(
                    'My Happy Plants',
                    style: TextStyle(
                      color: Theme.of(context).colorScheme.inversePrimary,
                      fontSize: 24,
                    ),
                  ),
                ),
              ),
              ListTile(
                title: const Text('My Plants'),
                leading: const Icon(Icons.local_florist),
                textColor: Theme.of(context).colorScheme.inversePrimary,
                iconColor: Theme.of(context).colorScheme.inversePrimary,
                onTap: () {
                  Navigator.pop(context);
                },
              ),
              ListTile(
                title: const Text('Plant Library'),
                leading: const Icon(Icons.library_add),
                textColor: Theme.of(context).colorScheme.inversePrimary,
                iconColor: Theme.of(context).colorScheme.inversePrimary,
                onTap: () {
                  Navigator.pushNamed(context, '/plant_library_page');
                },
              ),
              ListTile(
                title: const Text('Settings'),
                leading: const Icon(Icons.settings),
                textColor: Theme.of(context).colorScheme.inversePrimary,
                iconColor: Theme.of(context).colorScheme.inversePrimary,
                onTap: () {
                  Navigator.pushNamed(context, '/settings_page');
                },
              ),
            ],
          ),
          Padding(
            padding: const EdgeInsets.only(bottom: 16),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                ListTile(
                  title: const Text('Logout'),
                  leading: const Icon(Icons.logout),
                  textColor: Theme.of(context).colorScheme.inversePrimary,
                  iconColor: Theme.of(context).colorScheme.inversePrimary,
                  onTap: () {
                    Navigator.pushNamed(context, '/login_page');
                  },
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
