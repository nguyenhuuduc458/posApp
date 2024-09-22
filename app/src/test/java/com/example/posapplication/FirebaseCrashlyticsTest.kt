package com.example.posapplication

import org.junit.Test

class FirebaseCrashlyticsTest {
    @Test
    fun `should show message in crashlytics`() {
        throw RuntimeException("Test crash")
        // only for testing firebase crash
    }
}
