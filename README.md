# typeform-kotlin

Multi-platform Kotlin library for parsing and displaying [Typeform](https://www.typeform.com) questionnaires.

If you're just getting started with Typeform, be sure to check out the [Developer Platform Documentation](https://www.typeform.com/developers/get-started/).

## Installation

This library is distributed using [JitPack](https://jitpack.io). Add it to your project via gradle:

```groovy
// Project Level - preferring `build.gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

// Project Level - preferring `settings.gradle`
dependencyResolutionManagement {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

// App/Module Gradle
dependencies {
    implementation 'com.github.Nice-Healthcare:typeform-kotlin:0.1.0'
}
```

## Modules

This repository contains multiple modules which comprise a complete toolset for decoding, logic parsing, and display of Typeform questionnaires.

### Common (Kotlin Native) (`:typeform:commonMain`)

This module contains the Typeform schema and additional models for navigating the logic and completion of a _form_. Start with the [`Form`](Typeform/src/commonMain/kotlin/com/typeform/schema/Form.kt) structure, which is the entity retrieved from the Typeform [API](https://www.typeform.com/developers/create/reference/retrieve-forms/).

Forms contain [`Field`](typeform/src/commonMain/kotlin/com/typeform/schema/Field.kt)s and [`Logic`](typeform/src/commonMain/kotlin/com/typeform/schema/Logic.kt) which act up the _fields_. Given a particular [`Position`](typeform/src/commonMain/kotlin/com/typeform/models/Position.kt) - _the screen presented or question being asked_ - and [`Responses`](typeform/src/commonMain/kotlin/com/typeform/models/Responses.kt) - _answers given to previous questions_ - the next _position_ can be determined.

```kotlin
val form = Form()
val position = Position.screen(form.firstScreen!!)
val responses: Map<Reference, ResponseValue> = mapOf()
val nextPosition = form.nextPosition(from = position, responses = responses)
```

### Android (`:typeform:androidMain`)

The **Android** source set is a **Jetpack Compose** implementation used to display and navigate through a Typeform questionnaire. It is designed to be self-contained and customizable to fit in with your apps design.

The [`FormView`](typeform/src/androidMain/kotlin/com/typeform/ui/structure/FormView.kt) navigation graph is initialized with a `Form` and a [`Conclusion`](typeform/src/androidMain/kotlin/com/typeform/ui/models/Conclusion.kt) function/lambda callback. In most cases, the _conclusion_ will include the _responses_ given by the individual questioned. (The only instance where they are not provided is the `canceled` case.)

```kotlin
@Composable
fun MyNavGraph(form: Form) {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = "typeform-form",
    ) {
        composable(route = "typeform-form") {
            FormView(
                form = form,
                conclusion = {
                    // handle conclusion
                },
            )
        }
    }
}
```

## Images

Image `Attachment`s are handled using the popular (**Coil**)[https://github.com/coil-kt/coil] library.
In order to display images, a `ImageLoader` must be provided in the `FormView` initializer. The Coil `SingletonImageLoader` provides an interface for retrieving an instance:

```kotlin
FormView(
  …
  imageLoader = SingletonImageLoader.get(LocalPlatformContext.current)
  …
)
```

To supply an `ImageLoader`, one must be configured by your application. Details can be found in the [Coil Documentation](https://coil-kt.github.io/coil/image_loaders/).
The example project in this repository uses Ktor, but OkHttp may be used as well.

```kotlin
class MainActivity {
  override fun onCreate(savedInstanceState: Bundle?) {
    setContent {
    setSingletonImageLoaderFactory { platformContext ->
      ImageLoader.Builder(platformContext)
        .components {
          add(
            KtorNetworkFetcherFactory(
              httpClient = {
                HttpClient()
              }
            )
          )
        }
        .build()
      }
      … // Your content
    }
  }
}
```

## Supported Question Types

> Note, this library is _incomplete_; not every question type or condition has been implemented. Want to help fix a bug or make improvements? Consider becoming a contributor!

All of the _structural_ components are supported: Welcome Screen, Ending, Statement, and Question Group. Learn more about Typeform [Question Types](https://www.typeform.com/help/a/question-types-360051789692/?attribution_user_id=1dbdf7d8-4d28-44f6-8536-d95cf65b0311). The currently supported types are:

* Date
* Dropdown
* File Upload
* Long Text
* Opinion Scale
* Multiple Choice
* Number
* Rating
* Short Text
* Yes/No

### File Upload

To enable the broadest support for file uploading a `UploadHelper` should be supplied to the `FormView` when initialized.
The helper is responsible for creating writable `Uri`s for camera capture and for creating `Bitmap` representations for display.
Your app manifest should include the following to support uploading:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android" xmlns:tools="http://schemas.android.com/tools">
  <uses-feature
    android:name="android.hardware.camera"
    android:required="false" />

  <uses-permission android:name="android.permission.CAMERA" />

  <application>
    <provider
      android:name="androidx.core.content.FileProvider"
      android:authorities="${applicationId}"
      android:grantUriPermissions="true"
      android:exported="false"
      tools:replace="android:authorities">
      <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/file_paths"/>
    </provider>
  </application>
</manifest>
```

## Customization

Every app is unique, so the ability to customize the look and feel of the questions presentation was built in to the library from the start.

### Settings

The [`Settings`](typeform/src/androidMain/kotlin/com/typeform/ui/models/Settings.kt) structure allows for the customization of several aspects of the libraries interface presentation & localization.

### MaterialTheme

Most other aspects use `MaterialTheme` to customize the appearance.

* **Title** style (`Screen.title`, `Field.title`)
  * `textStyle`: `MaterialTheme.typography.h5`
* **Subtitle** style (Applied to navigational buttons)
  * `textStyle`: `MaterialTheme.typography.subtitle1`
* **Body** style (Applied to any other text element.)
  * `textStyle`: `MaterialTheme.typography.body1`
* **Prompt** style (Applied to supplemental text blocks, like Date toggle.)
  * `textStyle`: `MaterialTheme.typography.body2`
* **Caption** style (`Field.properties.description`)
  * `textStyle`: `MaterialTheme.typography.caption`

The 'Exit' confirmation dialog _by default_ uses the `subtitle1` and `body1` styles for the title & text.
The dialog buttons use style `body1` but have color overrides applied:

* Cancel (return to form): `MaterialTheme.colors.primary`
* Exit (leave form): `MaterialTheme.colors.error`


### Headers & Footers

A _form_ can further be customized by providing an optional _header_. This is a completely custom view that will be displayed along with each _field_ (question).
