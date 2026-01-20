package com.devhjs.mysmokinglog.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devhjs.mysmokinglog.presentation.designsystem.AppColors

@Composable
fun SmokingLogCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.Black10, RoundedCornerShape(16.dp))
            .border(1.dp, AppColors.Black15, RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        content()
    }
}
