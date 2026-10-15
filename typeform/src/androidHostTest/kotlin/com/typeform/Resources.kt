package com.typeform

actual class Resources {
    actual fun contentOfResource(named: String): ByteArray {
        return ClassLoader.getSystemResourceAsStream(named).use { stream ->
            stream.use {
                it.readBytes()
            }
        }
    }
}
