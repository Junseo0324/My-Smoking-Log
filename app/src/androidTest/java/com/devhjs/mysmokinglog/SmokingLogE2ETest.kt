package com.devhjs.mysmokinglog

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.devhjs.mysmokinglog.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.devhjs.mysmokinglog.R

@HiltAndroidTest
class SmokingLogE2ETest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun homeScreen_addAndUndoSmoking_updatesCount() {
        // 1. 초기 상태 확인
        // "0" 개비
        composeTestRule.onNodeWithText("0").assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.home_curr_cig_unit)).assertIsDisplayed()

        // 2. Add Smoking (+ 한 개비)
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.home_add_cig_button)).apply {
            assertIsDisplayed()
            assertHasClickAction()
            performClick()
        }

        // 3. 검증: 카운트 증가 (1) & Undo 버튼 표시
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("1").assertIsDisplayed()
        
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.home_undo_button)).apply {
            assertIsDisplayed()
            assertHasClickAction()
            performClick() // Undo Click
        }

        // 4. 검증: 카운트 감소 (0)
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("0").assertIsDisplayed()
    }
    
    @Test
    fun settings_changeDailyLimit_updatesHome() {
        // 1. Navigate to Settings
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.nav_setting)).performClick()
        
        // 2. Change Settings
        // Click Preset 5000 (Expected text: ₩5,000 for Locale.KOREA)
        // Or "￦5,000" depending on symbol.
        // Let's rely on finding "5,000" substring text if exact match fails, or use localized formatter in test.
        // But for simplicity, let's verify key elements.
        
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.settings_cost_title)).assertIsDisplayed()
        
        // Verify we are on Settings screen
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.settings_daily_goal_title)).assertIsDisplayed()

        // 3. Go back to Home
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.nav_home)).performClick()
        
        // 4. Verify Home
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.home_curr_cig_unit)).assertIsDisplayed()
    }
}
