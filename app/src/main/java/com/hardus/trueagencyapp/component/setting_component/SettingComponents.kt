package com.hardus.trueagencyapp.component.setting_component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hardus.trueagencyapp.ui.theme.TrueAgencyAppTheme


@Composable
fun SettingComponents(
    iconStart: ImageVector,
    iconEnd: ImageVector,
    text: String,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(65.dp)
            .padding(start = 20.dp, end = 20.dp)
            .background(
                color = MaterialTheme.colorScheme.onSecondary,
                shape = RoundedCornerShape(5.dp)
            )
            .clickable { onNavigate() }
    ) {
        Icon(
            imageVector = iconStart, contentDescription = text, modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = text, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(2f)
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Icon(
            imageVector = iconEnd, contentDescription = text, modifier = Modifier.weight(1f)
        )

    }
    Divider(
        thickness = 2.dp,
        color = MaterialTheme.colorScheme.inverseOnSurface,
        modifier = Modifier.padding(start = 20.dp, end = 20.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun CheckSettingComponent() {
    TrueAgencyAppTheme {
        SettingComponents(
            iconStart = Icons.Default.Person,
            text = "test",
            iconEnd = Icons.Default.ArrowForwardIos,
            onNavigate = {},
            modifier = Modifier
        )
    }
}