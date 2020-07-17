Desquared Calculator
-------------------------

This is the result of my assignment for Desquared company. **DesquaredCalc**. 

### Basic functionality.
Although the initial requirements were to build a simple calculator with a currency exchange feature, I decided to include some advanced capabilities as well due to the fact that they didn't require much additional work.
Furthermore the initial instructions I was given wanted me to utilize the [fixer.io](https://fixer.io/) API for the currency exchange but their free plan was way too limited (practically unusable) so I went for [exchangeratesapi.io](https://exchangeratesapi.io/) instead which is entirely free

### Tech stack used.

This app features the following technologies and coding practices.

- Kotlin
- Gradle Kotlin DSL scripts
- Clean architecture principles
- Module separation for each layer (domain, presentation, data). I decided to use modules for better separation of concerns and to demostrate how I would approach a large production grade app.
- Coroutines for asynchronous calls. Suspending functions, Flow and Channels.
- A Hybrid of MVVM-MVI approach. I use ViewModels with LiveData that emit `sealed` classes as states. The UI decides what to do based on the state received.
- Firebase Crashlytics for crash reporting
- Material Components library
- Splash screen
- And more... (I may keep updating this list if something important pops up to my mind)

### Screenshots



