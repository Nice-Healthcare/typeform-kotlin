package com.typeform.schema.questions

import kotlinx.serialization.Serializable

/**
 * Typeform offers the 3 most common date formats used worldwide:
 *
 * Month-Day-Year
 * Year-Month-Day
 * Day-Month-Year
 *
 * You can specify the date format you want to use, as well as the
 * separator character between days, months, and years.
 *
 * https://help.typeform.com/hc/en-us/articles/360052869051-Date-question
 */
@Serializable
data class DateStamp(
    val separator: String,
    val structure: String,
    val description: String?,
)
