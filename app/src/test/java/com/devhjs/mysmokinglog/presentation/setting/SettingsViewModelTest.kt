package com.devhjs.mysmokinglog.presentation.setting

import com.devhjs.mysmokinglog.core.util.Result
import com.devhjs.mysmokinglog.domain.model.UserSetting
import com.devhjs.mysmokinglog.domain.usecase.GetSettingsUseCase
import com.devhjs.mysmokinglog.domain.usecase.SaveSettingsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    private lateinit var viewModel: SettingsViewModel
    private val getSettingsUseCase: GetSettingsUseCase = mockk()
    private val saveSettingsUseCase: SaveSettingsUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()

    private val initialSettings = UserSetting(
        dailyLimit = 20,
        packPrice = 4500,
        cigarettesPerPackage = 20
    )

    @Before
    fun `초기화`() {
        Dispatchers.setMain(testDispatcher)
        
        coEvery { getSettingsUseCase() } returns Result.Success(initialSettings)
        
        viewModel = SettingsViewModel(getSettingsUseCase, saveSettingsUseCase)
    }

    @After
    fun `종료`() {
        Dispatchers.resetMain()
    }

    @Test
    fun `초기_로딩_시_설정을_가져와서_state를_업데이트하는지_테스트`() = runTest {
        // Given
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertEquals(20, state.dailyLimit)
        assertEquals(4500, state.packPrice)
        assertEquals(20, state.cigarettesPerPackage)
        assertEquals(false, state.isLoading)
        
        coVerify { getSettingsUseCase() }
    }

    @Test
    fun `일일_흡연량_변경_액션_테스트`() = runTest {
        // Given
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.onAction(SettingsAction.ChangeDailyLimit(30))

        // Then
        assertEquals(30, viewModel.state.value.dailyLimit)
    }

    @Test
    fun `가격_변경_액션_테스트`() = runTest {
        // Given
        testDispatcher.scheduler.advanceUntilIdle()
        coEvery { saveSettingsUseCase.execute(any()) } returns Result.Success(Unit)

        // When
        viewModel.onAction(SettingsAction.ChangePackPrice(5000))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(5000, viewModel.state.value.packPrice)
        coVerify {
            saveSettingsUseCase.execute(
                match { it.packPrice == 5000 && it.dailyLimit == 20 }
            ) 
        }
    }

    @Test
    fun `설정_저장_액션_테스트`() = runTest {
        // Given
        testDispatcher.scheduler.advanceUntilIdle()
        coEvery { saveSettingsUseCase.execute(any()) } returns Result.Success(Unit)
        
        viewModel.onAction(SettingsAction.ChangeDailyLimit(10))

        // When
        viewModel.onAction(SettingsAction.SaveSettings)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify {
            saveSettingsUseCase.execute(
                match { it.dailyLimit == 10 }
            ) 
        }
    }
}
