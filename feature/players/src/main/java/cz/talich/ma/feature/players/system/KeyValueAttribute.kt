package cz.talich.ma.feature.players.system

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.talich.ma.library.design.system.MyTheme

@Composable
fun KeyValueAttribute(
    text: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = modifier,
    ) {
        Text(
            text = text,
            style = MyTheme.typography.labelLarge,
        )
        Text(
            text = value,
            style = MyTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(start = 10.dp)
        )
    }
}
