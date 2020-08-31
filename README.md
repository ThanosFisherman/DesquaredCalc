Desquared Calculator
-------------------------

It's a simple calculator with some Advanced capabilities and a currency exchange feature via [exchangeratesapi.io](https://exchangeratesapi.io/) API.

### Tech stack used.

This app features the following technologies and coding practices.

- Kotlin
- Gradle Kotlin DSL scripts
- Clean architecture principles
- Module separation for each layer (domain, presentation, data). I decided to use modules for better separation of concerns and to demostrate how I would approach a large production grade app.
- Coroutines for asynchronous calls. Suspending functions, Flow and Channels.
- MVVM approach. I use ViewModels with LiveData that emit `sealed` classes as states. The UI decides what to do based on the state received.
- Firebase Crashlytics for crash reporting
- Material Components library
- Splash screen
- Retrofit for API requests
- Moshi for content negotiation
- A modified version of [ExprK](https://github.com/Keelar/ExprK) for numerical expressions
- And more I guess.

Screenshots
---------------------

![image1](https://user-images.githubusercontent.com/4888330/87777222-c7d81000-c831-11ea-8cde-3e0e2a0e3322.png)
![image2](https://user-images.githubusercontent.com/4888330/87777267-dcb4a380-c831-11ea-897a-7074836d0559.png)
![image3](https://user-images.githubusercontent.com/4888330/87777305-e9d19280-c831-11ea-8992-507d662cf92f.png)


License
-------
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0.html)

    Copyright 2020 Thanos Psaridis

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    
