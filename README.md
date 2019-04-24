![banner](https://github.com/truongpx396/ckcportfolio/blob/master/screenshots/blockchain.jpg)

# Android - CKC Portfolio - Kotlin (alpha) [![Build Status](https://api.travis-ci.org/truongpx396/ckcportfolio.svg?branch=master)](https://travis-ci.org/truongpx396/ckcportfolio)
CKC stands for "Clean architecture - Kotlin - Cryptocurrency". This project is just for learning purpose, and still being under development, I was inspired by blockchain technology for initial motivation in doing this project.
* The design of app is mimicked from [Blockfolio App][7].
* The architecture is based on android clean architecture principles which was implemented and shared by Fernando Cejas. A special thanks to Fernando Cejas, he did a great job there, take a look in [Architecture Android Reload][8].
* Written in kotlin, a very powerful and concise progamming language in my opinion. 
* Being powered up by Android jetpack and take advantage of android architecture components features, these knowledge I've learned from [Android Sunflower App][9] .

[7]: https://blockfolio.com/
[8]: https://fernandocejas.com/2018/05/07/architecting-android-reloaded/
[9]: https://github.com/googlesamples/android-sunflower

# Introduction

## Screenshots
![](https://github.com/truongpx396/ckcportfolio/blob/master/screenshots/app.jpg)


## Clean Architecture
![https://fernandocejas.com/2018/05/07/architecting-android-reloaded/](https://github.com/android10/Sample-Data/blob/master/Android-CleanArchitecture-Kotlin/architecture/clean_architecture_reloaded_main.png)

## Android - Jetpack - Java
------------
Android Jetpack is a set of components, tools and guidance to make great Android apps. They bring
together the existing Support Library and Architecture Components and arranges them into four
categories:

![Android Jetpack](https://github.com/googlesamples/android-sunflower/blob/master/screenshots/jetpack_donut.png)

Libraries Used
--------------
* [Foundation][0] - Components for core system capabilities, Kotlin extensions and support for
  multidex and automated testing.
  * [AppCompat][1] - Degrade gracefully on older versions of Android.
  * [Android KTX][2] - Write more concise, idiomatic Kotlin code.
  * [Test][4] - An Android testing framework for unit and runtime UI tests.
* [Architecture][10] - A collection of libraries that help design robust, testable, and
  maintainable apps. Start with classes for managing UI component lifecycle and handling data
  persistence.
  * [Data Binding][11] - Declaratively bind observable data to UI elements.
  * [Lifecycles][12] - Create a UI that automatically responds to lifecycle events.
  * [LiveData][13] - Build data objects that notify views when the underlying database changes.
  * [Navigation][14] - Handle everything needed for in-app navigation.
  * [Room][16] - Access app's SQLite database with in-app objects and compile-time checks.
  * [ViewModel][17] - Store UI-related data that isn't destroyed on app rotations. Easily schedule
     asynchronous tasks for optimal execution.
  * [WorkManager][18] - Manage Android background jobs.
* [UI][30] - Details on why and how to use UI Components in apps - together or separate
  * [Fragment][34] - A basic unit of composable UI.
  * [Layout][35] - Lay out widgets using different algorithms.
* Third party
  * [Retrofit][92] for network requests
  * [Dagger2][93] for dependency injection
  * [Firebase][94] for cloud services
  * [Glide][90] for image loading
  * [Kotlin Coroutines][91] for managing background threads with simplified code and reducing needs for callbacks
  * [Mockito][95] for mock test data
  * [Roboletric][96] for test android components without the need for an emulator device

  
[0]: https://developer.android.com/jetpack/foundation/
[1]: https://developer.android.com/topic/libraries/support-library/packages#v7-appcompat
[2]: https://developer.android.com/kotlin/ktx
[4]: https://developer.android.com/training/testing/
[10]: https://developer.android.com/jetpack/arch/
[11]: https://developer.android.com/topic/libraries/data-binding/
[12]: https://developer.android.com/topic/libraries/architecture/lifecycle
[13]: https://developer.android.com/topic/libraries/architecture/livedata
[14]: https://developer.android.com/topic/libraries/architecture/navigation/
[16]: https://developer.android.com/topic/libraries/architecture/room
[17]: https://developer.android.com/topic/libraries/architecture/viewmodel
[18]: https://developer.android.com/topic/libraries/architecture/workmanager
[30]: https://developer.android.com/jetpack/ui/
[31]: https://developer.android.com/training/animation/
[34]: https://developer.android.com/guide/components/fragments
[35]: https://developer.android.com/guide/topics/ui/declaring-layout
[90]: https://bumptech.github.io/glide/
[91]: https://kotlinlang.org/docs/reference/coroutines-overview.html
[92]: https://github.com/square/retrofit
[93]: https://github.com/google/dagger
[94]: https://firebase.google.com
[95]: https://github.com/mockito/mockito
[96]: https://github.com/robolectric/robolectric

## Other material worth reading:

[Android Architecture](https://github.com/googlesamples/android-architecture)

[Architecting Android…The clean way?](http://fernandocejas.com/2014/09/03/architecting-android-the-clean-way/)

[Tasting Dagger 2 on Android](http://fernandocejas.com/2015/04/11/tasting-dagger-2-on-android/)

[Best coding practices, tips and more for Android](https://medium.com/mindorks/best-coding-practices-tips-and-more-for-android-4ec03c7eeb2c)

[Android Development : Some of the best practices](https://android.jlelse.eu/android-development-some-of-the-best-practices-27722c685b6a)

[Android Code Convention Guidelines](https://github.com/ribot/android-guidelines/blob/master/project_and_code_guidelines.md)



## Local Development
This project uses the Gradle build system. To build this project, use the
`gradlew build` command or use "Import Project" in Android Studio.

Here are some other useful Gradle/adb commands for executing this example:

 * `./gradlew deployDebug` - Builds and install the debug apk on the current connected device.
 * `./gradlew runUnitTests` - Execute all unit tests.
 
## Discussions
Refer to the issues section: https://github.com/truongpx396/ckcportfolio/issues
 

## License

    Copyright 2019 truongpx

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
