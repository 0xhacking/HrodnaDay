package com.github.kiolk.hrodnaday

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.github.kiolk.hrodnaday", appContext.packageName)
    }

    @Test
    fun checkDateConverter(){
        val date : Long = 1520454462000
        val appContext = InstrumentationRegistry.getTargetContext()
        val result = convertEpochTime(date, appContext)
        assertEquals("ssss", result)
    }
}
