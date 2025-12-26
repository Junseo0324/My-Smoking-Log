package com.devhjs.mysmokinglog.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.mysmokinglog.ui.AppColors
import com.devhjs.mysmokinglog.ui.AppTextStyles

@Composable
fun StatCardHeader(
    title: String,
    state: String,
    description: String
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = AppTextStyles.titleTextBold.copy(fontSize = 18.sp, color = AppColors.White)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = state,
            style = AppTextStyles.headerTextBold.copy(color = AppColors.White),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis

        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = description,
            style = AppTextStyles.smallTextRegular.copy(color = AppColors.Gray80)
        )

    }
}