package com.typeform.example.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.typeform.example.ui.theme.ExampleTheme
import com.typeform.models.ResponseValue
import com.typeform.models.Responses
import com.typeform.schema.structure.Choice

@Composable
fun ResponsesView(
    responses: Responses,
) {
    Column {
        responses.forEach { (key, value) ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(
                    text = key,
                    modifier = Modifier.widthIn(min = 150.dp, max = 150.dp),
                    overflow = TextOverflow.MiddleEllipsis,
                    maxLines = 1,
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
                    is ResponseValue.ChoicesByReferenceValue -> {
                        ChoicesByReferenceView(value.value)
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
}

@Composable
fun ChoiceView(
    choice: Choice,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        Text(text = choice.id)
        Text(
            text = choice.ref,
            modifier = Modifier.widthIn(max = 100.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
        Text(text = choice.label)
    }
}

@Composable
fun ChoicesByReferenceView(
    choices: Map<String, List<Choice>>,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        choices.forEach { (key, list) ->
            Row {
                Text(
                    text = key,
                    modifier = Modifier.widthIn(max = 100.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                list.forEach { choice ->
                    Text(text = "✔️${choice.label}")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ResponsesViewPreview() {
    ExampleTheme {
        ResponsesView(
            responses = mapOf(
                "example-1" to ResponseValue.StringValue("Hello World!"),
                "example-2" to ResponseValue.BooleanValue(false),
                "something-a-bit-longer" to ResponseValue.ChoiceValue(
                    Choice(
                        id = "j8HD74ns83k",
                        ref = "AD521595-C697-415D-ACD9-F82861393904",
                        label = "Some Selection",
                    )
                )
            )
        )
    }
}
