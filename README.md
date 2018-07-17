# MVP-MVVM
Demo using kotlin and MVVM-MVP pattern to access the aws userpool and useridentity.

## Getting Started
You have to clone the app and import in android studio then sync and you will be able to run it, 
you should replace the keys in "UserPoolConstants".

## Project structure 

**1. data Layer**
- Contains all my data whether come from the backend or from local database.
- Repository pattern to access these data and provide interface between the domain layer and the data layer.
- the data layer for now use aws with no local 

**2. Presentation Layer**
- The presentation layer specific for android and it brings the data from the domain layer.
- MVP-MVVM pattern to structure the presentation layer and handle the logic of views from presneter not the activity.

## Libraries

1. Kotlin
2. RXjava2
3. Dagger2


