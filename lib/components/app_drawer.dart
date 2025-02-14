import 'package:flutter/material.dart';

//@author Filip Claesson, Pehr Norten
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
      // Add nested Column widgets to create the drawer layout with a header, list items, and a logout button at the bottom
      child: Column(
        mainAxisAlignment: MainAxisAlignment
            .spaceBetween, // Align the logout button to the bottom of the drawer
        children: [
          Column(
            children: [
              // Add a container for the app name
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
              // Add a ListTile for the My Plants page
              ListTile(
                title: const Text('My Plants'),
                leading: const Icon(Icons.local_florist),
                textColor: Theme.of(context).colorScheme.onSurface,
                iconColor: Theme.of(context).colorScheme.onSurface,
                selectedColor: Theme.of(context)
                    .colorScheme
                    .secondary, // Set the selected color to the secondary color
                selected: selectedIndex == 0, // Highlight the selected item
                onTap: () {
                  onItemTapped(
                      0); // Call the onItemTapped function with the index of the selected item to update the selected index and navigate to the correct page
                  Navigator.pop(context); // Close the drawer
                },
              ),
              // Add a ListTile for the Plant Library page
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
              // Add a ListTile for the Settings page
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
          // Add a ListTile for the Logout button
          Padding(
            padding: const EdgeInsets.only(bottom: 16),
            child: Column(
              mainAxisAlignment: MainAxisAlignment
                  .end, // Align the logout button to the bottom of the drawer
              children: [
                ListTile(
                  title: const Text('Logout'),
                  leading: const Icon(Icons.logout),
                  textColor: Theme.of(context).colorScheme.onSurface,
                  iconColor: Theme.of(context).colorScheme.onSurface,
                  selectedColor: Theme.of(context).colorScheme.secondary,
                  onTap: () {
                    Navigator.pushReplacementNamed(context,
                        '/login_page'); // Navigate to login page and remove all other routes from the stack
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
