import 'package:flutter/material.dart';

class AppDrawer extends StatelessWidget {
  final int selectedIndex;
  final Function(int) onItemTapped;

  const AppDrawer({
    super.key,
    required this.selectedIndex,
    required this.onItemTapped,
  });

  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Column(
            children: [
              Container(
                height: 150,
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
                textColor: Theme.of(context).colorScheme.onSurface,
                iconColor: Theme.of(context).colorScheme.onSurface,
                selectedColor: Theme.of(context).colorScheme.secondary,
                selected: selectedIndex == 0,
                onTap: () {
                  onItemTapped(0);
                  Navigator.pop(context);
                },
              ),
              ListTile(
                title: const Text('Plant Library'),
                leading: const Icon(Icons.library_add),
                textColor: Theme.of(context).colorScheme.onSurface,
                iconColor: Theme.of(context).colorScheme.onSurface,
                selectedColor: Theme.of(context).colorScheme.secondary,
                selected: selectedIndex == 1,
                onTap: () {
                  onItemTapped(1);
                  Navigator.pop(context);
                },
              ),
              ListTile(
                title: const Text('Settings'),
                leading: const Icon(Icons.settings),
                textColor: Theme.of(context).colorScheme.onSurface,
                iconColor: Theme.of(context).colorScheme.onSurface,
                selectedColor: Theme.of(context).colorScheme.secondary,
                selected: selectedIndex == 2,
                onTap: () {
                  onItemTapped(2);
                  Navigator.pop(context);
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
                  textColor: Theme.of(context).colorScheme.onSurface,
                  iconColor: Theme.of(context).colorScheme.onSurface,
                  selectedColor: Theme.of(context).colorScheme.secondary,
                  onTap: () {
                    Navigator.pushReplacementNamed(context, '/login_page');
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
