package com.devhjs.mysmokinglog

import com.devhjs.mysmokinglog.data.repository.fake.FakeSmokingRepository
import com.devhjs.mysmokinglog.data.repository.fake.FakeUserSettingRepository
import com.devhjs.mysmokinglog.domain.usecase.AddSmokingUseCase
import com.devhjs.mysmokinglog.domain.usecase.DeleteSmokingUseCase
import com.devhjs.mysmokinglog.domain.usecase.GetSettingsUseCase
import com.devhjs.mysmokinglog.domain.usecase.GetTodaySmokingInfoUseCase
import com.devhjs.mysmokinglog.domain.usecase.SaveSettingsUseCase
import com.devhjs.mysmokinglog.presentation.home.HomeAction
import com.devhjs.mysmokinglog.presentation.home.HomeViewModel
import com.devhjs.mysmokinglog.presentation.setting.SettingsAction
import com.devhjs.mysmokinglog.presentation.setting.SettingsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

@OptIn(ExperimentalCoroutinesApi::class)
class SmokingIntegrationTest {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var settingsViewModel: SettingsViewModel

    private lateinit var fakeSmokingRepository: FakeSmokingRepository
    private lateinit var fakeUserSettingRepository: FakeUserSettingRepository

    private val testDispatcher = StandardTestDispatcher()

    // Fixed time: 2024-01-01 12:00:00
    private val fixedClock = Clock.fixed(Instant.parse("2024-01-01T12:00:00Z"), ZoneId.systemDefault())

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        fakeSmokingRepository = FakeSmokingRepository()
        fakeUserSettingRepository = FakeUserSettingRepository()

        val addSmokingUseCase = AddSmokingUseCase(fakeSmokingRepository, fixedClock)
        val deleteSmokingUseCase = DeleteSmokingUseCase(fakeSmokingRepository)
        val getTodaySmokingInfoUseCase = GetTodaySmokingInfoUseCase(fakeSmokingRepository, fakeUserSettingRepository, fixedClock)

        val getSettingsUseCase = GetSettingsUseCase(fakeUserSettingRepository)
        val saveSettingsUseCase = SaveSettingsUseCase(fakeUserSettingRepository)

        homeViewModel = HomeViewModel(
            getTodaySmokingInfoUseCase,
            addSmokingUseCase,
            deleteSmokingUseCase
        )

        settingsViewModel = SettingsViewModel(
            getSettingsUseCase,
            saveSettingsUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `홈_화면에서_흡연_추가_및_삭제_그리고_설정_변경이_연동되어야_한다`() = runTest(testDispatcher) {
        // 1. 초기 상태 확인
        advanceUntilIdle() // ViewModel init block 실행 대기
        assertEquals(0, homeViewModel.state.value.todayCount)
        assertEquals(20, homeViewModel.state.value.dailyLimit)

        // 2. 홈: 흡연 추가
        homeViewModel.onAction(HomeAction.AddSmoking)
        testDispatcher.scheduler.advanceTimeBy(1000)

        // 검증: 카운트 증가, Undo 버튼 표시
        assertEquals(1, homeViewModel.state.value.todayCount)
        assertTrue(homeViewModel.state.value.isUndoVisible)

        // 3. 홈: 흡연 취소 (Undo)
        homeViewModel.onAction(HomeAction.DeleteSmoking)
        advanceUntilIdle() 
        
        // 검증: 카운트 감소
        assertEquals(0, homeViewModel.state.value.todayCount)

        // 다시 흡연 추가 (설정 연동 테스트를 위해)
        homeViewModel.onAction(HomeAction.AddSmoking)
        advanceUntilIdle()
        assertEquals(1, homeViewModel.state.value.todayCount)

        // 4. 설정: 하루 목표 변경 (20 -> 5)
        // 설정 화면 로드 대기
        advanceUntilIdle()
        assertEquals(20, settingsViewModel.state.value.dailyLimit)

        settingsViewModel.onAction(SettingsAction.ChangeDailyLimit(5))
        settingsViewModel.onAction(SettingsAction.SaveSettings)
        advanceUntilIdle()

        // 5. 홈: 변경된 설정 반영 확인
        homeViewModel.checkData()
        advanceUntilIdle()

        assertEquals(5, homeViewModel.state.value.dailyLimit)
        
        settingsViewModel.onAction(SettingsAction.ChangePackPrice(5000))
        advanceUntilIdle()
        val savedSettings = fakeUserSettingRepository.getSettings()
        assertEquals(5000, savedSettings.packPrice)
    }
}
