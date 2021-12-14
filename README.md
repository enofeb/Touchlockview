TouchLockView
===============
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