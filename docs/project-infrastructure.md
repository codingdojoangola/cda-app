# 1. Prerequisites

To contribute in this project, you need at least some familiarity with Java/Kotlin programming languages and Android framework. Git will be used for source control.
Being familiar with Android Studio is also recommended, but of course you can end up using any IDE of your preference.

# 2. Infrastructure Technology

## 2.1 Firebase
Firebase will be used as backend technology, it will handle tasks such as push notifications, database, file storage and user authentication.

## 2.2 Github API
Github API will be used to retrieve CDA members projects and tickets.

# 3. Code Guidelines
We'll use the english language to write this app, that includes Java/Kotlin code, comments, commits, file names, etc, except the text related to UI that is primarily written in portuguese.
Check the file 'project-and-code-guidelines.md' to learn about the code guideline that this project follow.

# 4. Directory structure:
In the source code, clear responsibilities must be kept between the modules, and that begins with the directory structure. We're using the schema below (note though that this can evolve as needed):

- network: classes that interacts with web APIs.
- data: classes that interacts with persistence (contracts, dbhelpers, sharedpreferences)
- ui: activities e fragments
- models: contains our business classes

Example
- ui
-- ui/main/MainActivity.java
-- ui/main/MainFragment.java
-- ui/settings/SettingsActivity.java
- models
-- models/Developer.java
-- models/Project.java
