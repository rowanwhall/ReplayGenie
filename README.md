# ReplayGenie
A work-in-progress repo for apps that query the Showdown API to populate and view a Firebase Datastore for replay data

This project contains two applications: PopulatorApp, which pages through the Showdown API to populate the Firebase Datastore, and ViewerApp, which is the actual user-facing ReplayGenie application to view replays. They also share code within SharedModule. Feel free to clone and populate your own Firebase Datastore, but note you will need your own google-services.json files in each application project.
