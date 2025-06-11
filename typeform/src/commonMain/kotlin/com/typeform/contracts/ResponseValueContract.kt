package com.typeform.contracts

import com.typeform.models.ResponseValue
import com.typeform.models.Upload
import com.typeform.schema.Choice
import com.typeform.serializers.DateSerializer
import java.util.Date
import kotlinx.serialization.Serializable

@Serializable
data class ResponseValueContract(
    val bool: Boolean?,
    val choice: Choice?,
    val choices: List<Choice>?,
    @Serializable(with = DateSerializer::class)
    val date: Date?,
    val int: Int?,
    val string: String?,
    val upload: Upload?,
) {
    constructor(responseValue: ResponseValue) : this(
        bool = responseValue.asBoolean(),
        choice = responseValue.asChoice(),
        choices = responseValue.asChoices(),
        date = responseValue.asDate(),
        int = responseValue.asInt(),
        string = responseValue.asString(),
        upload = responseValue.asUpload(),
    )

    fun toResponseValue(): ResponseValue? {
        return if (bool != null) {
            ResponseValue.BooleanValue(bool)
        } else if (choice != null) {
            ResponseValue.ChoiceValue(choice)
        } else if (choices != null) {
            ResponseValue.ChoicesValue(choices)
        } else if (date != null) {
            ResponseValue.DateValue(date)
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
