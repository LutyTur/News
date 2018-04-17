# News
This Android app is a news aggregator that allows viewing articles from many different news sources.

## Prerequisites
The data source for News app is [News API](https://newsapi.org)
To run the app you need to obtain API key either for development or commercial purposes and save it as a string resource:
```
<string name="news_api_key">your api key</string>
```

## Build with
* [News API](https://newsapi.org) - news and articles are stored as JSON objects with structure explained [here](https://newsapi.org/docs/get-started)
* [Firebase](https://firebase.google.com/) - app is build with Firebase Authentication that allows users to log in with google account and Firebase Realtime Database to save articles in database
* [Retrofit](http://square.github.io/retrofit/) - allows to make queries for news sources and articles
* [Picasso](http://square.github.io/picasso/) - enables async loading of images from urls
* [Mosby library](https://github.com/sockeqwe/mosby) - enables clean model-view-presenter architecture
* [Butterknife](http://jakewharton.github.io/butterknife/) - library for binding views and listeners
* [Material design](https://material.io/) - icons source
* [Chrome](https://developer.chrome.com/multidevice/android/customtabs) - articles open in chrome customtabs

## License
```
Copyright 2018 Maciej Borzym

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
