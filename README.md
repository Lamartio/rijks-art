# RijksArt 🎨

###### My portfolio application based on the the Rijksmuseum API

This app is intended to preview a decent yet simple native application for both Android and iOS that is built in one day. It has most of the components a medium application should have and is ready to be scaled to larger teams.

The app previews the art collection of the Rijksmuseum. The whole collection is presented in a list style gallery which loads content per 10 paintings.

When click upon a painting, the app will navigate, download and present the details of that painting.

## Shared code 
The debate seems to be never ending: Native, web, hybrid, cross-platform or multi-platform. All options have their place and I agree that apps should not be written twice just because it runs on another programming language. 

Yet I concluded that the platforms do not really like sharing their graphical rendering engine. Since the inception of mobile development HTML was promised to be it... How is that going? Then lets abstract the UI and write in one language: Xamarin, React Native, Flutter... Not really what we expected either. So I left the UI for what it is best: Android's Jetpack Compose and iOS's SwiftUI. 

The rest of the code is much simpler of nature and is easier to unify. That's where I believe the true 'write once run everywhere' lies, so I wrote all logic and services in the shared module. This module gets compile to an AndroidJar or Framework.  

The Framework is obj-c based and has limited headers for Swift, hence not all fancy Kotlin features are available on iOS. Biggest drawback that I experienced is the lack of `sealed class` support. Although I'm aware of the compiler plugins available for resolving this issue, it seems they come with their own problems too.

### Interfacing between modules
The shared module contains properties named `viewModel`, which is intended to be used the UI side (so native Android and iOS). Those properties are the interface between the shared code and the native code.

There are differences per platform which cannot be abstracted, since the platform just differ too much. To respect this, the `Platform` object is written per platform and contains the utilities that can be shared per platform. This is as well the entry point for the shared code.

### A little tour through the app
The application is built with a mindset that we develop first an application and next we develop the Android or iOS application. The platforms are basically the application+UI. Therefore any logic and tests should primarily be created in the `shared` module. Of course UI tests can only be
created in the platform specific projects. 

All three projects (shared, Android, & iOS) are designed in a composable fashion. Meaning that a developer that works on A has no interference with someone who works on the A. That goes for both UI as the logic. 

All modules contains classes that start with `Rijks`, which represent the root of the composition. In the `shared` module the `RijksState` is a good starting point for venturing into the code. This class is a fully transparent view of all the states the applications has. It is immutable and will be distributed to the platforms through a `MutableStateFlow`. Notice that all state is marked as internal and thereby not communicated to Android and iOS. 

For each `...State` there is an accompanying `...Actions` class representing all the mutations for the state. The mutations are facilitated through the [the Lux library](https://github.com/Lamartio/lux), but in short; the library prevents us having to use Kotlin's `copy()` in a nested form. In addition Lux has utility for handling asynchronous mutations, that include interruption of running asynchronous work, error handling and thread management for both platforms.

The `...Machine` classes are simply bundling the state and the actions and provide `compose` function to connect different machines together. Some machines are named `viewModel`, since they fulfill the exact role of a viewModel. They contain only the properties and actions that a specific view requires. Notice that they are the only one containing public properties and functions.

## Dependencies
For dependency inversion I don't see a reason to choose a library (like Koin or Kodein). Simply using Kotlin's features of `class delegation` and `receivers` is enough the apply dependencies and to scale to 100+ dependencies. In my opinion, that sounds reasonable for a medium to large application and the upside is that there is no compiler overhead.

## Services
The app relies on platform specific services: network and key-value storage. Both of these are available through multi mobile libraries and wrapped in a app-specific interface (see: `Rijskmuseum` and `Kvault`).

## Tests
Till now tests are lacking, but I have chosen for a centralized state management approach which I praise for its testability. See [the Lux library](https://github.com/Lamartio/lux) for how-to's.

## Utilities
A technique I like to employ to speed up development is quickly move generic functionality to a `utils.kt` file. When this file grows too big, I split it up in more appropriate files. This not meant to vex, but I noticed that I spend a lot of time in finding a good name for a utility and end up with 10 files with 1 utility.