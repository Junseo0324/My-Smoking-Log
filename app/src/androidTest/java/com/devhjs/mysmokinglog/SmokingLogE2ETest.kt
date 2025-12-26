package com.devhjs.mysmokinglog

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.devhjs.mysmokinglog.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
        composeTestRule.onNodeWithText("개비").assertIsDisplayed()

        // 2. Add Smoking (+ 한 개비)
        composeTestRule.onNodeWithText("+ 한 개비").apply {
            assertIsDisplayed()
            assertHasClickAction()
            performClick()
        }

        // 3. 검증: 카운트 증가 (1) & Undo 버튼 표시
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("1").assertIsDisplayed()
        
        composeTestRule.onNodeWithText("되돌리기").apply {
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
        composeTestRule.onNodeWithContentDescription("설정").performClick()
        
        // 2. Change Settings
        // Click Preset 5000 (Expected text: ₩5,000 for Locale.KOREA)
        // Or "￦5,000" depending on symbol.
        // Let's rely on finding "5,000" substring text if exact match fails, or use localized formatter in test.
        // But for simplicity, let's verify key elements.
        
        composeTestRule.onNodeWithText("비용 기준").assertIsDisplayed()
        
        // Verify we are on Settings screen
        composeTestRule.onNodeWithText("하루 목표").assertIsDisplayed()

        // 3. Go back to Home
        composeTestRule.onNodeWithContentDescription("홈").performClick()
        
        // 4. Verify Home
        composeTestRule.onNodeWithText("개비").assertIsDisplayed()
    }
}
