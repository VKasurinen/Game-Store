package com.vkasurinen.gamestore2.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vkasurinen.gamestore2.ui.theme.GameStore2Theme


@Composable
fun UtilityBox(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    value: String

    ) {

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(25.dp))
    ) {

        Box(
            modifier = Modifier
                .size(125.dp)
                .clip(RoundedCornerShape(20.dp))
                //.background(Color(0xFF0E161F)),
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center

        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    modifier = Modifier.size(50.dp),
                    imageVector = icon,
                    contentDescription = null
                )
                Text(
                    text = title,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = value,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.outline
                )
            }

        }

    }


}

@Preview(showBackground = true)
@Composable
fun UtilityBoxPreview() {

}