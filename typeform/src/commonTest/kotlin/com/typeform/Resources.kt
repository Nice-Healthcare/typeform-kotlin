package com.typeform

expect class Resources {
    fun contentOfResource(named: String): ByteArray
}