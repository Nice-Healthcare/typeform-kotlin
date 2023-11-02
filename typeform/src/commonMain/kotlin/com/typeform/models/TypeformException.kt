package com.typeform.models

sealed class TypeformException : Exception {
    private constructor() : super()
    private constructor(message: String) : super(message)
    private constructor(message: String, cause: Throwable) : super(message, cause)
    private constructor(cause: Throwable) : super(cause)

    object ResponseValueRequired : TypeformException("Field validations indicate a required response which is missing.")
    data class NextPosition(val from: Position) : TypeformException("The next position could not be determined.")
}
