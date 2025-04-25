package cz.talich.ma.library.design.system

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MyPreviewTheme(
    content: @Composable () -> Unit,
) {
    MyTheme {
        Box(
            modifier = Modifier
                .defaultMinSize(
                    minWidth = 500.dp
                )
                .background(color = MyTheme.colorScheme.background)
        ) {
            content()
        }
    }
}