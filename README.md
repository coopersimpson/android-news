# Android News App
Simple app that loads 10 articles from an API, user has options to configure different parameters to load additional articles.

## Architecture overview
This app uses the [Model-view-viewmodel (MVVM)](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) architectural pattern.

## Libraries used and rationale
- [Jetpack Compose](https://developer.android.com/compose) with [material3](https://m3.material.io/develop/android/jetpack-compose) components
  - Modern toolkit for building native UI on Android
  - Used for this app to implement best and modern practice, material3 components provide easy to use and clean interfaces out of the box
- [hilt/dagger](https://dagger.dev/hilt/)
  - Dependency injection library for Android built on top of Dagger
  - Allows for simplified way to provide and manage dependencies in the app, works automatically with ViewModels in a lifecycle-aware and test-friendly way
- [http3/retrofit2](https://square.github.io/retrofit/)
  - HTTP client for Android
  - Used to consume the newsdata API and network logging
- [Room](https://developer.android.com/training/data-storage/room)
  - Persistence library that provides abstraction layer over SQLite
  - Used for caching article data
  - A bit overkill for the needs of this project, better off with [SharedPreferences](https://developer.android.com/training/data-storage/shared-preferences) (legacy) or [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) (jetpack replacement for SharedPreferences)
- [coil](https://coil-kt.github.io/coil/)
  - Easy to use image loading library, loads asynchronously and can cache images
  - Used for asynchronously loading and displaying article images from the API

## How to build
- Clone the repo `git clone git@github.com:coopersimpson/android-news.git`
- Sync and build gradle file
- Visit [newsdata.io](https://newsdata.io/) sign up for free account and get a free API key
- Add `API_KEY=<YOUR_API_KEY>` to `/local.properties`

## Resources
API Endpoint - https://newsdata.io/api/1/latest?apikey=YOUR_API_KEY

See documentation - https://newsdata.io/documentation