# ScanMeCalculator

Image to text app with calculator for simple arithmetic statement. This app allows the user to capture arithmetic expressions (i.e. `1+2`) either directly from
the built-in camera or from an image file picked by the user from the gallery. This project supports multiple product flavors.

## ✨ Demo

Main Screen | Camera Screen | Image Picker Screen | Result Screen
--- | --- | --- | ---
<img src="https://github.com/stef-ang/ScanMeCalculator/assets/6779288/6b78b6f6-fefd-478b-861a-0a03fced758f" width="220" /> | <img src="https://github.com/stef-ang/ScanMeCalculator/assets/6779288/3436c6e1-c835-4822-9d6a-c749a2cce000" width="220" /> | <img src="https://github.com/stef-ang/ScanMeCalculator/assets/6779288/29788a4b-76a0-4c5f-ab16-dc66a7ad121e" width="220" /> | <img src="https://github.com/stef-ang/ScanMeCalculator/assets/6779288/0d1a2b78-f184-476e-8101-a028b521c068" width="220" />

- As I mentioned, it supports multiple product flavors. Main and Result Screen represent variant with Green Theme, while Camera Screen and Main Screen with Image picker represent variant with Red Theme.
- Access image to Camera or Image Picker is controlled by product flavors.
- Main Screen shows history of arithmetic expression result, user can choose either local database or file with encryption as storage of history.
- When user chooses "use storage" to "File", history will be stored with encryption operation.

## 📚 Stacks

- [CameraX](https://developer.android.com/training/camerax) - Helping make camera app development easier.
- [MLKit Text Recognition](https://developers.google.com/ml-kit/vision/text-recognition/v2/android) - Recognizer text in images or video.
- [Coroutines](https://developer.android.com/kotlin/coroutines) - Performing asynchronous code with sequential manner.
- [Kotlin Flow](https://developer.android.com/kotlin/flow) - Reactive streams based on coroutines that can emit multiple values sequentially.
- [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - The DI framework which reduces the boilerplate.
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Presenter with its semi data persistence behavior.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Android modern toolkit for building native UI.
- [Compose Material 3](https://developer.android.com/jetpack/compose/designsystems/material3) - Helping me present Material Design.
- [Compose Navigation Component](https://developer.android.com/jetpack/compose/navigation) - For single-activity architecture with Compose.
- [Appcompat](https://developer.android.com/jetpack/androidx/releases/appcompat) - Camera preview doesn't support Compose yet, so I used non-Compose Activity for CameraActivity.
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - Data storage solution to store key-value pairs.
- [Room](https://developer.android.com/training/data-storage/room) - Save data in a local database and support to Kotlin Extensions and Coroutines.
- [Keystore](https://developer.android.com/training/articles/keystore) - Storing cryptographic keys in a secure container and use them for cryptographic operations.
- [LeakCanary](https://square.github.io/leakcanary) (Debug) - Memory leak detector.

## 📐 Architecture and Design Principles

This app adopts [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) with [Unidirectional flow (UDF)](https://en.wikipedia.org/wiki/Unidirectional_Data_Flow_(computer_science)) pattern. It follows [S.O.L.I.D](https://en.wikipedia.org/wiki/SOLID) and [The Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) to achieve maintainable, scalable and testable code base. Also the code base has been structured in multi-module project.
Thanks to [this architecture-template repository](https://github.com/android/architecture-templates) because initiation of this project used that template.

## 🍦 Product Flavors

This project has two flavor dimensions: `theme`, `mode`.
`theme` has two variants: `red` and `green`.
`mode` represents image access method: `camera` and `filesystem`.
Altogether it is possible to build 4 apks / variants:
1. app-red-filesystem
2. app-red-camera
3. app-green-filesystem
4. app-green-camera

## ✍️ Author

👤 **Stefanus Anggara**

* Twitter: <a href="https://twitter.com/Stef_Anggara" target="_blank"><img alt="Twitter: Stef_Anggara" src="https://img.shields.io/twitter/follow/Stef_Anggara.svg?style=social" /></a>
* Email: anggara.stefanus@gmail.com

Feel free to ping me 😉

## 📝 License

```
Copyright © 2023 - Stefanus Anggara

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
