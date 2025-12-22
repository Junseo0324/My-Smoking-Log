package com.devhjs.mysmokinglog.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.mysmokinglog.ui.AppColors
import com.devhjs.mysmokinglog.ui.AppTextStyles


@Composable
fun InfoItem(text: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "•",
            style = AppTextStyles.normalTextRegular.copy(color = AppColors.Gray),
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = text,
            style = AppTextStyles.smallTextRegular.copy(color = AppColors.Gray, fontSize = 14.sp)
        )
    }
}