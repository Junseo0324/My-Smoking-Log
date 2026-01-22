package com.devhjs.mysmokinglog.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.mysmokinglog.domain.model.HealthMilestone
import com.devhjs.mysmokinglog.presentation.designsystem.AppColors
import com.devhjs.mysmokinglog.presentation.designsystem.AppTextStyles

@Composable
fun HealthMilestoneItem(
    milestone: HealthMilestone,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (milestone.isAchieved) AppColors.PrimaryColor.copy(alpha = 0.2f) else AppColors.Black15
    val contentColor = if (milestone.isAchieved) AppColors.PrimaryColor else AppColors.Gray

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(if (milestone.isAchieved) AppColors.PrimaryColor else AppColors.Gray)
        )
        
        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = milestone.title,
                style = AppTextStyles.normalTextBold.copy(color = if (milestone.isAchieved) AppColors.White else AppColors.Gray)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = milestone.description,
                style = AppTextStyles.smallTextRegular.copy(color = contentColor, fontSize = 13.sp)
            )
        }
    }
}
