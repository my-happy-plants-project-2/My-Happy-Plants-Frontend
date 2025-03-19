# My Happy Plants II

My Happy Plants II is a web-based application that assists users in the care of their houseplants. Users create an account to which they are able to add and manage their plants. The water level of the plants are based on the date they were last watered and displayed to the user when logged in to serve as a reminder to water them. The application relies on the availability of a PostgreSQL database for storing user and application data. 

## Installation
The application consists of a webbased frontend that connects to the backend through an API
#### Step one:
Clone or download the repo available at: https://github.com/my-happy-plants-project-2/My-Happy-Plants-Frontend.git

#### Step two, starting the backend API:
* Add the .env file containing the database information to the My-Happy-Plants-Frontend/API folder.
* In your IDE of choice open My-Happy-Plants-Frontend/API as a project folder
* Install the dependencies listed in the pom.xml file
* Run the main file, this will start the server on localhost port: 8080

#### Step three, running the frontend:
* Running this application requires Flutter, Follow the install guide available at https://docs.flutter.dev/get-started/install
* In your IDE of choice open My-Happy-Plants-Frontend/Frontend as a project folder
* Ensure that you have the correct dependencies by running:
```
flutter pub get
```
Once the dependencies are in place, run
```
flutter run
``` 
And select the device you wish to run the application on.
This will start the frontend in a new window of the chosen application.

### Mobile devicies
The frontend of the application supports mobile devices and can be emulated through the use of Android studio or by adding  an Android plugin to your IDE. Note that should you choose to run the application on an emulated device the endpoint urls have to be changed from "http://localhost:8080" to "http://10.0.2.2:8080" in the provider classes located in the Frontend of the project. 

## Authors
Christian Storck, Filip Claesson, Ida Nordenswan, Johnny Rosenquist, Kasper Schröder, Pehr Nortén



