package com.devhjs.mysmokinglog.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.mysmokinglog.presentation.designsystem.AppTextStyles

@Composable
fun HomeButton(
    modifier: Modifier = Modifier,
    title: String,
    backgroundColor: Color,
    textColor: Color,
    onClick: ()-> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = backgroundColor, shape = RoundedCornerShape(10.dp))
            .clickable {
                onClick()
            }
            .padding(vertical = 20.dp)
            .clip(RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = AppTextStyles.largeTextBold.copy(fontSize = 18.sp, color = textColor),
        )
    }
}