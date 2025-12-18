package com.typeform.example.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.typeform.example.ui.theme.ExampleTheme
import com.typeform.ui.models.Conclusion

@Composable
fun ConclusionView(
    modifier: Modifier = Modifier,
    conclusion: Conclusion?
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = "Conclusion",
                style = MaterialTheme.typography.titleLarge,
            )

            conclusion?.let {
                when (it) {
                    is Conclusion.Completed -> {
                        Text(text = "Completed")

                        ResponsesView(responses = it.responses)
                    }
                    is Conclusion.Abandoned -> {
                        Text(text = "Abandoned")

                        ResponsesView(responses = it.responses)
                    }
                    is Conclusion.Rejected -> {
                        Text(text = "Rejected")

                        ResponsesView(responses = it.responses)
                    }
                    is Conclusion.Canceled -> {
                        Text(text = "Canceled")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ConclusionViewPreview() {
    ExampleTheme {
        ConclusionView(
            conclusion = null,
        )
    }
}
