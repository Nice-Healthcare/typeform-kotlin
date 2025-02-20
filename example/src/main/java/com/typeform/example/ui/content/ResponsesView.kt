package com.typeform.example.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.typeform.example.ui.theme.ExampleTheme
import com.typeform.models.ResponseValue
import com.typeform.models.Responses
import com.typeform.schema.Choice

@Composable
fun ResponsesView(
    responses: Responses,
) {
    Row(
      horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        responses.forEach { (key, value) ->
            Text(
                text = key,
            )

            when (value) {
                is ResponseValue.BooleanValue -> {
                    Text(text = value.value.toString())
                }
                is ResponseValue.ChoiceValue -> {
                    ChoiceView(value.value)
                }
                is ResponseValue.ChoicesValue -> {
                    value.value.forEach {
                        ChoiceView(it)
                    }
                }
                is ResponseValue.DateValue -> {
                    Text(text = value.value.toString())
                }
                is ResponseValue.IntValue -> {
                    Text(text = value.value.toString())
                }
                is ResponseValue.StringValue -> {
                    Text(text = value.value)
                }
                is ResponseValue.UploadValue -> {
                    Text(text = value.value.mimeType)
                }
            }
        }
    }
}

@Composable
fun ChoiceView(
    choice: Choice,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        Text(text = choice.id)
        Text(text = choice.ref)
        Text(text = choice.label)
    }
}

@Preview(showBackground = true)
@Composable
private fun ResponsesViewPreview() {
    ExampleTheme {
        ResponsesView(
            responses = mutableMapOf(

            )
        )
    }
}
