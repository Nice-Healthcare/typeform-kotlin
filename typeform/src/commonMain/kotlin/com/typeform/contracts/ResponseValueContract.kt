package com.typeform.contracts

import com.typeform.models.ResponseValue
import com.typeform.models.Upload
import com.typeform.schema.Choice
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class ResponseValueContract(
    val boolean: Boolean?,
    val choice: Choice?,
    val choices: List<Choice>?,
    val instant: Instant?,
    val int: Int?,
    val string: String?,
    val upload: Upload?,
) {
    constructor(responseValue: ResponseValue) : this(
        boolean = responseValue.asBoolean(),
        choice = responseValue.asChoice(),
        choices = responseValue.asChoices(),
        instant = responseValue.asInstant(),
        int = responseValue.asInt(),
        string = responseValue.asString(),
        upload = responseValue.asUpload(),
    )

    fun toResponseValue(): ResponseValue? {
        return if (boolean != null) {
            ResponseValue.BooleanValue(boolean)
        } else if (choice != null) {
            ResponseValue.ChoiceValue(choice)
        } else if (choices != null) {
            ResponseValue.ChoicesValue(choices)
        } else if (instant != null) {
            ResponseValue.InstantValue(instant)
        } else if (int != null) {
            ResponseValue.IntValue(int)
        } else if (string != null) {
            ResponseValue.StringValue(string)
        } else if (upload != null) {
            ResponseValue.UploadValue(upload)
        } else {
            null
        }
    }
}
