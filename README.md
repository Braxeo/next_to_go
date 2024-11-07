Next To Go - Android Compose Multimodule Project
================================================

"Next To Go" is an Android app built with Jetpack Compose, leveraging a modular architecture to separate concerns and make development and testing easier. The app supports both light and dark modes, dynamic font changes, and handles network changes efficiently.

Features
--------

-   **Modular Architecture**: The app is divided into multiple modules, each handling different aspects of the app.
-   **Dark Mode & Light Mode**: Automatically switches between dark and light modes based on system settings.
-   **Dynamic Font Changes**: Supports customizable fonts.
-   **Network Change Handling**: Monitors and reacts to network changes.
-   **Unit and Instrumentation Tests**: Provides coverage for core features.

Project Structure
-----------------

The project uses a **multimodule** setup with the following core modules:

-   **App Module**: The main entry point of the app.
-   **Core Module**: Provides core features such as DI, networking and theming.
-   **Feature Modules**: Modularized feature sets for easy scalability.
-   **BuildSrc**: Custom build logic and shared dependencies.

The project has a **global lint configuration** that ensures consistent code quality across modules. Additional lint configurations in feature modules will merge with global settings, allowing specific overrides where needed.

Prerequisites
-------------

-   **Java 17**: The project uses Java 17 for building and running the app.
-   **Android Studio**: Ensure you have Android Studio installed with the necessary SDK versions and JDK setup.

Setup and Installation
----------------------

### 1\. Clone the repository:

`git clone https://github.com/your-username/next-to-go.git
cd next-to-go`

### 2\. Install dependencies:

Make sure you have **Java 17** installed and `jenv` set up if you're managing your Java versions using it.

### 3\. Sync Gradle:

Once the repository is cloned, sync the project with Gradle to download dependencies:

`./gradlew sync`

Building and Running the App
----------------------------

To build and run the app, follow these steps:

1.  **Build the app**: You can build the app using Gradle:

    `./gradlew build`

2.  **Run the app in Android Studio**:

    -   Open the project in **Android Studio**.
    -   Select the **app module** and click the **Run** button to install the app on an emulator or device.
3.  **Run the app via command line**: You can also run the app directly via Gradle:

    `./gradlew installDebug
    ./gradlew run`

Adding a New Module
-------------------

To add a new feature module to the project:

1.  Create a new module under the `features/` directory.
2.  Define the module's dependencies in the module's `build.gradle.kts`.
3.  Add the module in the root `settings.gradle.kts`:

    `include(":app", ":feature:myNewModule")`

4.  Write tests for the new module (unit and UI tests).

The modular structure ensures each module can be developed, tested, and deployed independently.

Running Tests
-------------

### 1\. Unit Tests

Unit tests can be run from Android Studio or via the command line:

-   **Run unit tests in Android Studio**: Right-click on the test class and select **Run**.
-   **Run unit tests via command line**:

`./gradlew testDebugUnitTest`

### 2\. Instrumentation Tests

Instrumentation tests are designed to run on an Android device or emulator. To run them:

-   **Run instrumentation tests in Android Studio**: Select the test and click **Run**.
-   **Run instrumentation tests via command line**:

`./gradlew connectedDebugAndroidTest`

### 3\. Linting

Lint checks are automatically run as part of the build process. To manually run lint checks:

`./gradlew lint`

-   The project uses a **global lint configuration** file located in the root of the project. Feature modules can override specific lint rules if necessary.
-   By default, lint errors will fail the build. You can modify this behavior by changing the `abortOnError` setting in the `build.gradle.kts` file:

    `lint {
        abortOnError false
    }`

Handling Network Changes
------------------------

The app listens for network changes and updates its UI accordingly, using a custom `NetworkChangeReceiver` that broadcasts changes to network connectivity. This ensures that the app can respond dynamically to network conditions.

UI Features
-----------

### 1\. Light and Dark Mode

The app supports **light** and **dark** themes, automatically switching based on the system-wide setting. Custom color schemes are defined for both themes.

### 2\. Font Changes

The app allows for dynamic font changes. Fonts are handled in a modular manner, ensuring that different features can define their own font sets.

Development Notes
-----------------

-   **Modular Structure**: The project is modular, meaning you can easily add or remove feature modules without affecting the core functionality. New modules should be added in the `settings.gradle.kts` and configured in the respective `build.gradle.kts` files.
-   **Global Lint Configuration**: The project uses a global lint configuration file that enforces consistent code quality. Additional lint files in feature modules can be used to add specific linting rules, which will merge with the global configuration.
-   **Java 17**: The project is built with Java 17. Ensure that you are using this version of Java to build and run the project.

License
-------

This project is unlicensed.
