TouchLockView
===============
[![](https://jitpack.io/v/enofeb/touchlockview.svg)](https://jitpack.io/#enofeb/touchlockview)
<a href="http://developer.android.com/index.html" target="_blank"><img src="https://img.shields.io/badge/platform-android-green.svg"/></a>
<a href="https://android-arsenal.com/api?level=15" target="_blank"><img src="https://img.shields.io/badge/API-19%2B-green.svg?style=flat"/></a>
<a href="http://opensource.org/licenses/MIT" target="_blank"><img src="https://img.shields.io/badge/License-MIT-blue.svg?style=flat"/></a>

TouchLockView is a screen touch locker when the user does not want to interact the screen wrongly. For example, while watching video. Lottie animation was used for locked screen in this library.

# Simple Usage
1) Add TouchLockView to your xml.
```kotlin
    <com.enofeb.lockview.TouchLockView
        android:id="@+id/touchView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:visibility="gone"
        app:lockDisabledText="LOCK DISABLED"
        app:lockEnabledText="LOCK ENABLED" />
```

2) Set parent on long click listener. And start touchView.
```kotlin
    constrainLayout.setOnLongClickListener {
         touchView.start()
         return@setOnLongClickListener true
    }
```

## XML Attributes
```xml
    <attr name="lockEnabledText" format="string" />
    <attr name="lockDisabledText" format="string" />
    <attr name="lockTextColor" format="color" />
    <attr name="lockTextSize" format="dimension" />
    <attr name="animationSpeed" format="float" />
```

## Download
* Add Jitpack to your root `build.gradle` repositories.
```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

* Add TouchLockView to your module dependencies.
```groovy
dependencies {
    implementation 'com.github.enofeb:touchlockview:version'
}
```

## License
```
MIT License

Copyright (c) 2021 Enes Zor

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```