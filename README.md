# ZumperSolution
Please download and run the android application on any device with Android version 4.0 and above.
Please give the Permission for Location services and Phone call.
The location services is used to determine the current location of the user, while the phone call service is used to make a call from the Restaurant contact information.
The Google API key for using Maps is already written in the google_api_key.xml (res -> values -> google_api_key.xml) Mostly, there wont be a requirement of modifying it but in case it does this is where you can find it,
this can be know when the map is not displayed or when the List View is empty.
I have implemented the concept using MVC to the maximum extent I could.
I have assumed that the Google Play services is present, also, I have for demostration purpose only downloaded one image per restaurant.
When we hit the limit of number of downloads allowed by google we might see image and error 403.
I have implemented the whole code in Android 3.0.
There are a few more implementations which I could not do because of the time constraint.
I have implemented The MAin Activity which has two buttons which are used to go to MapScreen Activity or ListScreen Activity, where MapScreen Loads MapView with a marker of your location in Red and the restaurants located near by you with a radius of 2,000 mtrs.
The default value of lattitude and longitude as San Francisco, The marker shows the Title and the snippet shows address of the place.
In the list View the image, name and ratings and in the detail view, there is a recycler view in horizontal view which shows reviews.
