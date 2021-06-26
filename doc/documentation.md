Cookbook Documentation
======================


## Intro

This is a documentation for Cookbook app created by [Robo Templates](https://robotemplates.com/). Cookbook is a native Android application. You can find here useful info how to configure, customize, build and publish your Cookbook app.

* [Cookbook on Codecanyon](https://codecanyon.net/item/cookbook-recipe-app-for-android/10747654)
* [Live demo on Google Play](https://play.google.com/store/apps/details?id=com.robotemplates.cookbook)
* [Video preview on YouTube](https://www.youtube.com/watch?v=dxpd-ryoTH4)


## Features

* Developed with Android Studio & Gradle
* Support for KitKat (Android 4.4) and newer
* Material design following Android Design Guidelines
* List of recipes
* Recipe detail screen (intro, ingredients, instruction)
* Simple no-server solution (no need to pay for backend and maintain it)
* Data (categories, recipes, ingredients) is stored in local SQLite database
* AdMob (adaptive banners and interstitial ad)
* Firebase Cloud Messaging (push notifications)
* Firebase Analytics
* GDPR compliant (European Union's General Data Protection Regulation)
* Splash screen (launch screen)
* Navigation drawer menu with categories
* Search for recipe with suggestion
* Favorite recipes
* Ingredients check list
* Recalculate quantity of ingredients by servings
* Convert calories to joules
* Kitchen timer
* Open web link of the recipe
* Share recipe or shopping list
* About dialog
* In-app review dialog
* Rate app on Google Play
* Privacy policy link
* Images can be loaded from the Internet or locally
* Caching images
* App works in offline mode
* Eight color themes (blue, brown, carrot, gray, green, indigo, red, yellow)
* Animations and effects
* Animated action bar
* Animated floating action button
* Parallax scrolling effect
* Quick return effect
* Ripple effect
* Responsive design and tablet support (portrait, landscape, handling orientation change)
* Support for vector drawables and high-resolution displays (xxxhdpi)
* Multi-language support
* Deep links
* Top quality clean code created by experienced senior Android developer
* Easy configuration
* Well documented
* Free support


## Android Studio & SDK

This chapter describes how to install Android Studio and Android SDK. You don't have to install Android Studio, but it's better. The project can be built without Android Studio, using Gradle and Android SDK. Gradle is a build system used for building a final APK file.

1. Install [Java JDK](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
2. Install [Android Studio with Android SDK](https://developer.android.com/studio/)
3. Run Android SDK Manager and [download necessary SDK packages](https://developer.android.com/studio/intro/update#sdk-manager), make sure that you have installed Android SDK Tools, Android SDK Platform-Tools, Android SDK Build-Tools, Android Emulator and Google USB Driver
4. Now you should be able to open/edit the Android project and build an APK


## Project Structure

Project has the following structure (directories are marked by square braces):

- [doc] - documentation
- [extras] - contains extras
- [extras]/[keystore]
- [extras]/[keystore]/cookbook.jks - keystore certificate for signing APK/AAB file
- [extras]/[keystore]/cookbook.properties - alias and password for keystore
- [gradle]
- [gradle]/[wrapper] - Gradle Wrapper
- [mobile] - main module
- [mobile]/[libs] - contains external libraries
- [mobile]/[src] - contains source code
- [mobile]/[src]/[main]
- [mobile]/[src]/[main]/[assets] - asset files (prepopulated database, images)
- [mobile]/[src]/[main]/[java] - java sources
- [mobile]/[src]/[main]/[res] - xml resources, drawables
- [mobile]/[src]/[main]/AndroidManifest.xml - manifest file
- [mobile]/build.gradle - main build script
- [mobile]/google-services.json - configuration file for Google Services and Firebase
- [mobile]/proguard-rules.pro - Proguard config (not used)
- .gitignore - Gitignore file
- build.gradle - parent build script
- gradle.properties - build script properties containing path to keystore
- gradlew - Gradle Wrapper (Unix)
- gradlew.bat - Gradle Wrapper (Windows)
- README.md - readme file
- settings.gradle - build settings containing list of modules
- utils.gradle - utilities for Gradle build script

Java packages:

- com.robotemplates.cookbook - contains application class and main config class
- com.robotemplates.cookbook.activity - contains activities representing screens
- com.robotemplates.cookbook.adapter - contains all adapters
- com.robotemplates.cookbook.ads - contains AdMob classes
- com.robotemplates.cookbook.content - contains content provider for search suggestions
- com.robotemplates.cookbook.database - contains database helper and tools for managing asynchronous database calls
- com.robotemplates.cookbook.database.dao - database access objects
- com.robotemplates.cookbook.database.data - data model wrapper
- com.robotemplates.cookbook.database.model - database models representing SQL tables
- com.robotemplates.cookbook.database.query - database queries
- com.robotemplates.cookbook.dialog - contains dialogs
- com.robotemplates.cookbook.fcm - contains services for FCM
- com.robotemplates.cookbook.fragment - contains fragments with main application logic
- com.robotemplates.cookbook.glide - contains Glide classes
- com.robotemplates.cookbook.listener - contains event listeners
- com.robotemplates.cookbook.utility - contains utilities
- com.robotemplates.cookbook.view - contains custom views and layouts
- com.robotemplates.cookbook.widget - contains decorations and other widgets


## Configuration

This chapter describes how to configure the project to be ready for publishing. All these steps are very important!

If you are stuck and need help, try to [search the problem in comments](https://codecanyon.net/item/cookbook-recipe-app-for-android/10747654/comments) on CodeCanyon. Most of the problems have already been discussed so there is a good chance that you will find the answer to your question there. You can also use [Stack Overflow](https://stackoverflow.com/) to find answers to your technical questions, resolve issues and bugs.


### 1. Import

Unzip the package and import/open the project in Android Studio. Choose "Import project" on Quick Start screen and select "cookbook-x.y.z" directory.

You can [switch to Project view](https://developer.android.com/studio/projects/#ProjectView) in the Project window on left if you want to see the actual file structure of the project including all files hidden from the Android view. Just select Project from the dropdown at the top of the Project window.

If you want, you can build and run the app right away to test it. Connect your device or [emulator](https://developer.android.com/studio/run/managing-avds) to your computer. Make sure you have installed all necessary [drivers](https://developer.android.com/studio/run/oem-usb) for your Android device and you also enabled [USB debugging in Developer options](https://developer.android.com/studio/run/device). To build and run the app in Android Studio, just click on Main menu -> Run -> Run 'mobile'. Choose a connected device and confirm.


### 2. Set Purchase Code

Open configuration file _mobile/src/main/java/com/robotemplates/cookbook/CookbookConfig.java_ and set value of `PURCHASE_CODE` constant to your own [Envato purchase code](https://help.market.envato.com/hc/en-us/articles/202822600-Where-Is-My-Purchase-Code-).

You are licensed to use this code to create one single end product for yourself or for one client (a "single application"). If you want to publish more than one app on Google Play, you have to buy more licenses. You have to buy a [License](https://codecanyon.net/licenses/terms/regular) for each end product. If you are going to sell your app, you will have to buy an [Extended License](https://codecanyon.net/licenses/terms/extended). The Extended License is required if end user must pay to use end product. Please read the [license terms](https://codecanyon.net/licenses/standard?license=regular) for more info.


### 3. Rename Application ID

Every Android app has a unique [application ID](https://developer.android.com/studio/build/application-id). This ID uniquely identifies your app on the device and in Google Play Store. If you want to upload a new version of your app, the application ID (and the [certificate you sign it with](https://developer.android.com/studio/publish/app-signing)) must be the same as the original APK. If you change the application ID, Google Play Store treats the APK as a completely different app. So once you publish your app, you should never change the application ID. Default application ID is "com.robotemplates.cookbook" so you have to rename it to something else.

1. Open the main build script _mobile/build.gradle_ and rename the value of `applicationId`. Just rewrite it to your own application ID, e.g. "com.mycompany.myapp".
2. If you try to build the project now, you will probably get "No matching client found for package name..." error. The reason for this is that declared package names in _mobile/google-services.json_ don't match to your own package name (application ID). We will setup Firebase and _google-services.json_ later. In the meantime, if you want to get rid of the error, just update values of all `package_name` attributes in _google-services.json_ to your own package name (application ID), e.g. "com.mycompany.myapp".
3. Synchronize the project. Main menu -> File -> Sync Project with Gradle Files.


### 4. Rename Application Name

Open _mobile/src/main/res/values/strings.xml_ and change "Cookbook" to your own name. Change _app\_name_ and _navigation\_header\_title_ strings.


### 5. Create Launcher Icon

Right click on _mobile/src/main/res_ directory -> New -> Image Asset -> Icon Type "Launcher Icons", Name "ic\_launcher", create the icon as you wish, you can set clipart, text, shape, color etc. -> Next -> Finish.

You can also change the icon by replacing _ic\_launcher.png_ file in _mipmap-mdpi_, _mipmap-hdpi_, _mipmap-xhdpi_, _mipmap-xxhdpi_, _mipmap-xxxhdpi_, _mipmap-anydpi-v26_ directories.

You can also change the splash screen logo. It is stored in _mobile/src/main/res/drawable-nodpi/splash.png_.

**Tip:** Another possibility is to create the launcher icons using [Android Asset Studio](https://romannurik.github.io/AndroidAssetStudio/icons-launcher.html).


### 6. Choose Color Theme

Open _mobile/src/main/AndroidManifest.xml_ and change the value of `application.android:theme` attribute. There are 8 themes you can use:

* Theme.Cookbook.Blue
* Theme.Cookbook.Brown
* Theme.Cookbook.Carrot
* Theme.Cookbook.Gray
* Theme.Cookbook.Green
* Theme.Cookbook.Indigo
* Theme.Cookbook.Red
* Theme.Cookbook.Yellow

You also have to modify MainActivity's theme. Main Activity uses a special theme with transparent status bar because of navigation drawer status overlay effect. It is an `activity.android:theme` attribute. Choose one of these themes:

* Theme.Cookbook.TransparentStatusBar.Blue
* Theme.Cookbook.TransparentStatusBar.Brown
* Theme.Cookbook.TransparentStatusBar.Carrot
* Theme.Cookbook.TransparentStatusBar.Gray
* Theme.Cookbook.TransparentStatusBar.Green
* Theme.Cookbook.TransparentStatusBar.Indigo
* Theme.Cookbook.TransparentStatusBar.Red
* Theme.Cookbook.TransparentStatusBar.Yellow


### 7. Prepare Database and Images

Data (categories, recipes, ingredients) is stored in a local SQLite database. Prepopulated database with recipes is stored in _mobile/src/main/assets/cookbook.db_. This prepopulated database is automatically copied on device storage on first run of the application and also if the database is updated (see below for more info about database update). Database is in SQLite 3.0 format and has the following structure (SQL script):

```sql
CREATE TABLE `categories` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `name` VARCHAR , `image` VARCHAR );
CREATE TABLE `recipes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `category_id` BIGINT , `name` VARCHAR , `intro` VARCHAR , `instruction` VARCHAR , `image` VARCHAR , `link` VARCHAR , `time` INTEGER , `servings` INTEGER , `calories` INTEGER , `favorite` SMALLINT );
CREATE INDEX `recipes_category_idx` ON `recipes` ( `category_id` );
CREATE TABLE `ingredients` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `recipe_id` BIGINT , `name` VARCHAR , `quantity` FLOAT , `unit` VARCHAR );
CREATE INDEX `ingredients_recipe_idx` ON `ingredients` ( `recipe_id` );
```

As you can see, there are 3 SQL tables (categories, recipes, ingredients) and 2 indexes (category\_id, recipe\_id) which are also foreign keys. This database schema corresponds to database models in the code. You can find models in _com.robotemplates.cookbook.database.model_ package.

This app contains a prepopulated database with demo data. Open the database file _mobile/src/main/assets/cookbook.db_ in any SQLite editor and modify data in the database as you need. You can add/remove/edit recipes, categories and ingredients. There are many [SQLite editors](https://www.sqlite.org/cvstrac/wiki?p=ManagementTools). We recommend [SQLite Studio](https://sqlitestudio.pl/) because it is free, open source, cross-platform, portable and intuitive. If you are working with SQLite Studio, don't forget to commit changes. Don't modify structure of the database, modify only data! Database tables have following columns:

categories:
* id (integer) - Unique primary key
* name (string) - Category name
* image (string) - URL of the category image. This field is optional and if it is empty or null, no category image is shown. URL should be in this format: _assets://categories/mycategory.png_. It points to _mobile/src/main/assets/categories_ folder where all category images should be stored.

recipes:
* id (integer) - Unique primary key
* category_id (integer) - Foreign key pointing to category id
* name (string) - Recipe name
* intro (string) - Introduction text on recipe detail screen. This field is optional and if it is empty or null, no text is shown.
* instruction (string) - Main instruction text for the recipe
* image (string) - URL of the recipe image. Image can be loaded from the Internet (URL with standard HTTP protocol) or locally from assets. Local URL should be in this format: _assets://recipes/myrecipe.jpg_. It points to _mobile/src/main/assets/recipes_ folder where all local recipe images should be stored.
* link (string) - URL of the web page. This field is optional and if it is empty or null, no web link is shown in the menu.
* time (integer) - Cooking time in minutes. This field is optional and if it is empty or null, no time is shown.
* servings (integer) - Number of servings. This field is optional and if it is empty or null, no servings info is shown.
* calories (integer) - Number of calories in kcal for 100 g of serving. This field is optional and if it is empty or null, no calories info is shown.
* favorite (boolean) - True/false value if the recipe is favorite. This field should stay 0 by default. This is the only column modified by the app. All other columns are read only.

ingredients:
* id (integer) - Unique primary key
* recipe_id (integer) - Foreign key pointing to recipe id
* name (string) - Ingredient name
* quantity (float) - Quantity of the ingredient. This field is optional and if it is empty or null, no quantity is shown.
* unit (string) - Physical unit of the quantity. This field is optional and if it is empty or null, no unit is shown.

There are two special categories: "All recipes" and "Favorites". Keep in mind that these categories are automatically added to the menu and don't have to be in the database. Categories are ordered by _id_, recipes are ordered alphabetically by _name_ and ingredients are ordered by _id_. Search query is looking for a match in _name_, _intro_ and _instruction_ fields. Searching is case insensitive.

If you modify prepopulated database in _assets_ folder, internal database on device storage will not be updated automatically. If you make any change in the prepopulated database, you have to increment database version. Open configuration file _mobile/src/main/java/com/robotemplates/cookbook/CookbookConfig.java_ and increment number in _DATABASE\_VERSION_ constant. Database helper detects that database data has been changed and copy the prepopulated database on device storage so data in the app will be updated. You have to increment database version every time when you want to publish a new build on Google Play and you have changed the data in prepopulated database. Data in _favorite_ column is automatically backed-up and then restored after the database is updated.

Name of the prepopulated database is defined in configuration file _mobile/src/main/java/com/robotemplates/cookbook/CookbookConfig.java_ in _DATABASE\_NAME_ constant. Database file name should correspond to the file in _mobile/src/main/assets_ directory. The database file should be stored in this directory and not in any sub-directory.

This paragraph will describe in more detail how it works. Note that there are 2 databases. The first one is prepopulated database which is located in _mobile/src/main/assets/cookbook.db_. This database has all data about your categories, recipes and ingredients. If you want to add/remove/edit data in your app, you should modify this database. The important thing is, that this database is baked in the APK/AAB file so when you build the APK/AAB file, this database is part of it. Prepopulated database is not directly used by your app. It is just a mirror and when you run the app for a first time (or after update), prepopulated database is copied into device. The second database is the real/internal database which is actually used directly by the app when you run it. This database is stored in device and it is automatically created from the prepopulated database – it is cloned/copied from it. If you want to use more translations in your app, you have to have more prepopulated databases – for each language. For example: default _cookbook.db_, _fr\_cookbook.db_ for French, _de\_cookbook.db_ for German etc. See "Multi-language support" chapter below for more info.


### 8. Setup Firebase Cloud Messaging and Analytics

Application uses [Firebase](https://firebase.google.com/) for Cloud Messaging, Analytics and AdMob. You need to create a new Firebase account if you don't have one. You have to create a new project in [Firebase Console](https://console.firebase.google.com/). Follow [Firebase documentation](https://firebase.google.com/docs/android/setup) to setup the project. After you add a new Firebase project, open it, select Analytics Dashboard and open wizard to add Firebase to your Android app. Fill in your package name (application ID) and download the _google-services.json_ config file. Copy this file into _mobile_ directory. That's it. You don't have to setup the Firebase SDK, it's already done. You can compose and send push notifications in the [Firebase Console](https://console.firebase.google.com/).

You can change the icon of push notification by replacing _ic\_stat\_notification.png_ files in _drawable-mdpi_, _drawable-hdpi_, _drawable-xhdpi_, _drawable-xxhdpi_, _drawable-xxxhdpi_ directories.


### 9. Setup AdMob

Open _mobile/src/main/res/values/admob.xml_ and set values of `admob_publisher_id` string to your own AdMob publisher id and `admob_app_id` string to your own AdMob app id. Default values are test publisher id and test app id. Keep the default values if you do not want to use AdMob. Set values of `admob_unit_id_recipe_list`, `admob_unit_id_recipe_detail` and `admob_unit_id_interstitial` strings to your own unit ids (banner ids and interstitial id). Leave these strings empty if you don't want to use AdMob. Empty means `<string name="admob_unit_id_recipe_list" translatable="false"></string>`.

You can also specify your test device id in `admob_test_device_id` string and use test mode when you are debugging the app. Requesting test ads is recommended when you are testing your application on a real device so you do not request invalid impressions. You can find your hashed device id in Android Monitor (Logcat) output by requesting an ad when debugging on your device. Open Android Monitor (Logcat) in Android Studio and look for "To get test ads on this device, call adRequest.addTestDevice("XXXXXX…");". You can use filter/search to find this information in the log. That XXX string is the hashed device id. This applies only to real devices, not emulators. Emulators are considered as test devices by default so you don't have to care about it.

Interstitial ad will be shown after each x openings of recipe detail. Frequency of showing AdMob interstitial ad can be changed. Open configuration file _mobile/src/main/java/com/robotemplates/cookbook/CookbookConfig.java_ and set value of `ADMOB_INTERSTITIAL_FREQUENCY` constant.

This app is GDPR (European Union's General Data Protection Regulation) compliant. GDPR consent form is shown to users located in the European Economic Area during the first launch of the app. Users can choose if they want to personalize ads or not. You can enable/disable GDPR consent form in the configuration file. Just open _mobile/src/main/java/com/robotemplates/cookbook/CookbookConfig.java_ and set `GDPR` constant to true/false. GDPR consent form requires a URL link to your privacy policy. You can specify the link in `PRIVACY_POLICY_URL` constant. Privacy policy link is also shown in the toolbar menu because Google Play requires developers to provide a valid privacy policy when the app requests or handles sensitive user or device information (e.g. advertising identifier). You can use [privacy policy generator](https://app-privacy-policy-generator.firebaseapp.com/).

Don't forget to link your app to Firebase in the [AdMob settings](https://apps.admob.com/).

**Note:** Sometimes happens that there is no ad shown in the app. There is nothing wrong, it is correct behavior. If you see _Failed to load ad: 0_ in the log, it means that the ad is newly created. It will take some time to fetch ads from Google servers. You just need to wait a few hours. If you see _Failed to load ad: 3_ in the log, it means that your ad request was successful but Google server was not able to provide any ads for the target user.


### 10. Create Signing Keystore

You need to create your own keystore to [sign an APK/AAB file](https://developer.android.com/studio/publish/app-signing) before [publishing the app on Google Play](https://developer.android.com/distribute/best-practices/launch/). You can create the keystore in [Android Studio](https://developer.android.com/studio/publish/app-signing#generate-key) or via [keytool utility](https://docs.oracle.com/javase/7/docs/technotes/tools/solaris/keytool.html).

1. Easiest way is to create the keystore directly in Android Studio. Main menu -> Build -> Generate Signed Bundle / APK -> APK -> Create new. Keystore file name has to be _cookbook.jks_ and must be stored in _extras/keystore_ directory. After you create the keystore file, you can just close the Generate Signed Bundle or APK dialog.
2. Make sure that newly created _cookbook.jks_ is stored in _extras/keystore_ directory.
3. Open _extras/keystore/cookbook.properties_ and set keystore alias and passwords which you chose in step 1.
4. Done. Remember that _cookbook.jks_ and _cookbook.properties_ are automatically read by Gradle script when creating a release APK via _assembleRelease_ command or AAB via _bundleRelease_ command. Paths to these files are defined in _gradle.properties_.

**Note:** If you want to create the keystore via keytool utility, run following command: `keytool -genkey -v -keystore cookbook.jks -alias <your_alias> -keyalg RSA -keysize 2048 -validity 36500` where `<your_alias>` is your alias name. For example your company name or app name. Keytool utility is part of Java JDK. On Windows, you can find it usually in _C:\Program Files\Java\jdkX.Y.Z\bin_. On Mac, you can find it usually in _/Library/Java/JavaVirtualMachines/jdkX.Y.Z/Contents/Home/bin_. If you create the keystore this way, don't forget for step 2 and 3 above.

**Important:** Don't lose the keystore file, otherwise you will not be able to publish new updates on Google Play. You must use the same certificate throughout the lifespan of your app in order for users to be able to install new versions as updates to the app. Optionally, you can use [Google Play App Signing](https://www.youtube.com/watch?v=PuaYhnGmeEk) to avoid losing your keystore.


## Customization

This chapter describes optional customizations of the app.


### In-app Review Dialog

In-app review dialog is shown after each x openings of recipe detail. Open _mobile/src/main/java/com/robotemplates/cookbook/CookbookConfig.java_ file if you want to configure the in-app review dialog. Frequency of showing the dialog can be set in `INAPP_REVIEW_DIALOG_FREQUENCY` constant. Set frequency to 0 if you don't want to show the in-app review dialog. Keep in mind that the in-app review dialog might not show up. It's not guaranteed. Google Play enforces a time-bound quota on how often a user can be shown the review dialog. Therefore in-app review dialog shouldn't be shown too often. See [Google Play In-App Review API](https://developer.android.com/guide/playcore/in-app-review) for more info.


### Splash Screen

Splash screen in this app is implemented as a [launch screen](https://material.io/design/communication/launch-screen.html) according to Material Design guidelines. Splash screen logo is stored in _mobile/src/main/res/drawable-nodpi/splash.png_.


### Custom Colors and Icons

You can customize colors in _mobile/src/main/res/values/colors.xml_.

There are 11 category icons. If you need to create an icon for category, it is recommended to use [Android Asset Studio](https://romannurik.github.io/AndroidAssetStudio/index.html). Use icons with highest DPI.


### Custom Banner Logo in Navigation Drawer Menu

There is a green table cloth background shown in the navigation drawer menu. You can easily change this background by replacing _banner.png_ file in _drawable-nodpi_ directory.


### About Dialog

If you want to change the text in About dialog, just open _mobile/src/main/res/values/strings.xml_ and edit _dialog\_about\_message_ string. Note that this text is in HTML format and it can also contains links.


### Multi-Language Support

Create a new directory _mobile/src/main/res/values-xx_ where _xx_ is an [ISO 639-1 code](https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) of the language you want to translate. For example "values-es" for Spanish, "values-fr" for French, "values-de" for German etc. Copy strings.xml from _mobile/src/main/res/values_ into the new directory. Now you can translate texts for specific languages. Language is automatically determined by the system device settings. If there is no match with _values-xx_ language, default language in _mobile/src/main/res/values_ is selected. See [Localizing with Resources](https://developer.android.com/guide/topics/resources/localization) for more info.

For database translation, you have to have more prepopulated databases for each language. For example: default _cookbook.db_, _fr\_cookbook.db_ for French, _de\_cookbook.db_ for German etc. Name of the database file has to be in this format: `<iso_code>_cookbook.db` where `<iso_code>` is an [ISO 639-1 code](https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) of the language you want to translate. Language is automatically determined by the system device settings. If there is no match with the language ISO code, default prepopulated database _cookbook.db_ is used. All database files should be stored in _mobile/src/main/assets_ directory.


### Deep Links

Application supports simple deep links which are specified in _mobile/src/main/AndroidManifest.xml_ as an intent filter. Links have this format: `http://host/pathPattern` or `https://host/pathPattern`. Open _mobile/src/main/res/values/strings.xml_ and change _app\_deep\_link\_host_ and _app\_deep\_link\_path_ as you need.

The pathPattern attribute specifies a complete path that is matched against the complete path in the Intent object. It can contain asterisk wildcard `*` that matches a sequence of 0 to many occurrences of the immediately preceding character. A period followed by an asterisk `.*` matches any sequence of 0 to many characters.


## Building & Publishing

This chapter describes how to build an APK/AAB (Android App Bundle) file using Gradle and prepare the app for publishing. Android Studio uses Gradle for building Android applications. Publishing an app in APK format is simpler. [AAB (Android App Bundle)](https://developer.android.com/guide/app-bundle/) is a new format that supports dynamic delivery, but defers APK generation and signing to Google Play. It requires additional [setup of signing keystore](https://support.google.com/googleplay/android-developer/answer/7384423) in Google Play.

You don't need to install Gradle on your system, because there is a [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html). The Wrapper is a batch script on Windows, and a shell script for other operating systems. When you start a Gradle build via the Wrapper, Gradle will be automatically downloaded and used to run the build.

1. Open the project in Android Studio.
2. Open the configuration file _mobile/src/main/java/com/robotemplates/cookbook/CookbookConfig.java_ and check if everything is set up correctly.
3. Open the main build script _mobile/build.gradle_ and check version constants.
4. Run `gradlew assemble` for APK or `gradlew bundle` for AAB in Android Studio terminal. Make sure you are running the command from the root directory of the project.
5. After the build is finished, the APK/AAB file should be available in _mobile/build/outputs/apk/release_ or _mobile/build/outputs/bundle/release_ directory.

**Note:** You can also [create the APK/AAB file](https://developer.android.com/studio/publish/app-signing#sign-apk) via Main menu -> Build -> Generate Signed Bundle / APK. If you want to do it this way, just choose your keystore, enter your alias, passwords and generate the file. After the build is finished, the APK/AAB file should be available in _mobile/release_ directory.

**Note:** You will also need a "local.properties" file to set the location of the SDK in the same way that the existing SDK requires, using the "sdk.dir" property. Example of "local.properties" on Windows: `sdk.dir=C:\\adt-bundle-windows\\sdk`. Alternatively, you can set an environment variable called "ANDROID\_HOME". Android Studio will take care of it.

**Tip:** Command `gradlew assemble` builds both - debug and release APK. You can use `gradlew assembleDebug` to build debug APK. You can use `gradlew assembleRelease` to build release APK. Debug APK is signed by a debug keystore. Release APK is signed by your own keystore, stored in _/extras/keystore_ directory. The same applies to `gradlew bundle` command which generates AAB.

**Signing process:** Keystore passwords are automatically loaded from property file during building the release APK/AAB file. Path to this file is defined in "keystore.properties" property in "gradle.properties" file.


### Versioning

Open the main build script _mobile/build.gradle_. There are 4 important constants for defining version code and version name.

* VERSION\_MAJOR
* VERSION\_MINOR
* VERSION\_PATCH
* VERSION\_BUILD

Keep in mind that versions have to be incremental. See [Version Your App](https://developer.android.com/studio/publish/versioning) in Android documentation for more info.


## Dependencies

* [Alfonz](https://github.com/petrnohejl/Alfonz)
* [Android Jetpack](https://developer.android.com/jetpack)
* [Firebase](https://firebase.google.com/)
* [Glide](https://github.com/bumptech/glide)
* [Google Services](https://developers.google.com/android/guides/overview)
* [OrmLite](http://ormlite.com/)
* [StickyScrollViewItems](https://github.com/emilsjolander/StickyScrollViewItems)


## Credits

Following images are used in the demo app:

* Goulash Soup, [CC BY 2.0](https://creativecommons.org/licenses/by/2.0/), https://www.flickr.com/photos/elsiehui/14706147387
* Apple Pie, [CC BY 2.0](https://creativecommons.org/licenses/by/2.0/), https://www.flickr.com/photos/jeffreyww/15160498676
* Perfect Sandwiches, [CC BY 2.0](https://creativecommons.org/licenses/by/2.0/), https://www.flickr.com/photos/jeffreyww/5696849258
* Orange Juice With Ginger, [CC BY 2.0](https://creativecommons.org/licenses/by/2.0/), https://www.flickr.com/photos/breville/8735921698
* Oven Roasted Potato Fries, [CC BY 2.0](https://creativecommons.org/licenses/by/2.0/), https://www.flickr.com/photos/gudlyf/3862654181
* Baked Biscuits, [CC BY 2.0](https://creativecommons.org/licenses/by/2.0/), https://www.flickr.com/photos/epw/2675532274
* Belgian Waffle, [CC BY 2.0](https://creativecommons.org/licenses/by/2.0/), https://www.flickr.com/photos/ralphandjenny/5434298641
* English Breakfast, [CC BY-ND 2.0](https://creativecommons.org/licenses/by-nd/2.0/), https://www.flickr.com/photos/dewolfert/3866881455
* Eggs With Crab Dip, [CC BY 2.0](https://creativecommons.org/licenses/by/2.0/), https://www.flickr.com/photos/jeffreyww/5291277352
* Hot Gingered Prawns, [CC BY 2.0](https://creativecommons.org/licenses/by/2.0/), https://www.flickr.com/photos/breville/8735236955
* Rings Calamari, [CC BY-SA 2.0](https://creativecommons.org/licenses/by-sa/2.0/), https://www.flickr.com/photos/saechang/4509691209
* Sushi Rolls, [CC BY 2.0](https://creativecommons.org/licenses/by/2.0/), https://www.flickr.com/photos/sql_samson/3977310279
* Tuna Nachos, [CC BY 2.0](https://creativecommons.org/licenses/by/2.0/), https://www.flickr.com/photos/dinesarasota/6791316679
* Curried Asparagus and Kaffir Lime Soup, [CC BY-SA 2.0](https://creativecommons.org/licenses/by-sa/2.0/), https://www.flickr.com/photos/dougbeckers/12870205414
* Matzoh Ball Soup, [CC BY-SA 2.0](https://creativecommons.org/licenses/by-sa/2.0/), https://www.flickr.com/photos/stuart_spivack/298130263
* Potato Soup, [CC BY 2.0](https://creativecommons.org/licenses/by/2.0/), https://www.flickr.com/photos/whitneyinchicago/4072293909
* Split Pea Soup, [CC BY-SA 2.0](https://creativecommons.org/licenses/by-sa/2.0/), https://www.flickr.com/photos/cogdog/11579080605
* Vegetable Soup, [CC BY-ND 2.0](https://creativecommons.org/licenses/by-nd/2.0/), https://www.flickr.com/photos/9439733@N02/2088701805
* Halushki, [CC BY-SA 2.0](https://creativecommons.org/licenses/by-sa/2.0/), https://www.flickr.com/photos/avlxyz/4567416647
* Orange Chicken With Bacon Fried Rice, [CC BY 2.0](https://creativecommons.org/licenses/by/2.0/), https://www.flickr.com/photos/jeffreyww/16241424778
* Posole and Quesadillas, [CC BY 2.0](https://creativecommons.org/licenses/by/2.0/), https://www.flickr.com/photos/jeffreyww/15865540490
* Spaghetti Carbonara, [CC BY 2.0](https://creativecommons.org/licenses/by/2.0/), https://www.flickr.com/photos/jeffreyww/13726596053
* Tuna Steak, [CC BY 2.0](https://creativecommons.org/licenses/by/2.0/), https://www.flickr.com/photos/elwillo/5993250162
* Summer Salad With Tomatoes and Basil, [CC BY-SA 2.0](https://creativecommons.org/licenses/by-sa/2.0/), https://www.flickr.com/photos/jasonsewall/6090556228
* Cupcake, [CC BY 2.0](https://creativecommons.org/licenses/by/2.0/), https://www.flickr.com/photos/jenumfamily/6911184740
* Key Lime Pie, [CC BY 2.0](https://creativecommons.org/licenses/by/2.0/), https://www.flickr.com/photos/ralphandjenny/5941648290
* Lemon Berry Trifle, [CC BY-SA 2.0](https://creativecommons.org/licenses/by-sa/2.0/), https://www.flickr.com/photos/brooke/2364980971
* Meringue Cake With Red Currant Curd Filling, [CC BY-SA 2.0](https://creativecommons.org/licenses/by-sa/2.0/), https://www.flickr.com/photos/69668444@N03/7801333926
* Strawberry Cake, [CC BY-SA 2.0](https://creativecommons.org/licenses/by-sa/2.0/), https://www.flickr.com/photos/martinhipp/4212723686
* Sunset Cocktail, [CC BY 2.0](https://creativecommons.org/licenses/by/2.0/), https://www.flickr.com/photos/rodeime/16157402619
* Tango Berry Smoothie, [CC BY 2.0](https://creativecommons.org/licenses/by/2.0/), https://www.flickr.com/photos/vegateam/5867754540


## Changelog

* Version 1.0.0
	* Initial release
* Version 1.1.0
	* Update SDK and libraries
* Version 1.2.0
	* Runtime permissions
	* Support for database translations
	* One config file for everything (Google Analytics, AdMob)
	* Update SDK and libraries
	* Huge refactoring of the code with many improvements and optimizations
* Version 1.2.1
	* Fix loading data when activity is restored
* Version 1.3.0
	* Backup and restore favorites when database is updated
	* Instruction text field now supports HTML
	* Update SDK and libraries
	* Refactoring of the code and optimizations
* Version 1.4.0
	* Fix suggestion provider authority
	* Update SDK and libraries
	* Refactoring of the code and optimizations
* Version 1.4.1
	* Support for Android Studio 3.0
* Version 1.5.0
	* Firebase Cloud Messaging (push notifications)
	* Firebase Analytics
	* AdMob interstitial ad
	* Hide AdMob banner on error
	* GDPR compliant (European Union's General Data Protection Regulation)
	* Deep links
	* Privacy policy link
	* Adaptive launcher icon
	* Fix lazy loading
	* Fix search suggestion on old Android
	* Update SDK and libraries
	* Refactoring of the code and optimizations
* Version 1.5.1
	* Don't cover AdMob ad with FAB
	* Animate AdMob banner
* Version 1.6.0
	* Splash screen
	* Preload interstitial ad and show immediately on item click
	* Strip HTML tags from shared recipe text
	* Support asterisk wildcard in deep link intent filter
	* Vector drawables
	* AdMob adaptive banner
	* AdMob test ads
	* Remove unnecessary permissions requests
	* Replace old libraries with new modern ones
	* Update SDK and libraries
	* Refactoring of the code and optimizations
* Version 1.7.0
	* Fix opening URL sent via push notification
	* Update SDK and libraries
* Version 1.8.0
	* In-app review dialog
	* Update SDK and libraries


## Developed by

[Robo Templates](https://robotemplates.com/)


## License

[Codecanyon license](http://codecanyon.net/licenses/standard)
