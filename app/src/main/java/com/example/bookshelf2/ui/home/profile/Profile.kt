package com.example.bookshelf2.ui.home.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookshelf2.ui.theme.BookShelfTheme

@Composable
fun Profile(
    isDarkMode: Boolean = false,
    onToggleTheme: (Boolean) -> Unit = {  },
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(color = BookShelfTheme.colors.cardColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Dark Mode",
                    modifier = Modifier.padding(end = 8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.weight(1f))

                Switch(
                    checked = isDarkMode,
                    onCheckedChange = onToggleTheme,
                    colors = SwitchDefaults.colors(
                        checkedBorderColor = Color.Black,
                       // checkedThumbColor = Color.White,
                       // checkedTrackColor = BookShelfTheme.colors.textInteractive,
                        uncheckedBorderColor = BookShelfTheme.colors.iconSecondary,
                        uncheckedThumbColor = BookShelfTheme.colors.iconSecondary,
                        uncheckedTrackColor = BookShelfTheme.colors.textInteractive
                    )
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ProfilePreview() {
    val isDarkMode by remember { mutableStateOf(true) }

    BookShelfTheme(darkTheme = isDarkMode) {
        Profile(
            isDarkMode = isDarkMode,
            modifier = Modifier
        )
    }
}
