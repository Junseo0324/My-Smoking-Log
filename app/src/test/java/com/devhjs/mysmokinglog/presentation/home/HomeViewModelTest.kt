package com.devhjs.mysmokinglog.presentation.home

import com.devhjs.mysmokinglog.core.util.Result
import com.devhjs.mysmokinglog.domain.model.TodaySmoking
import com.devhjs.mysmokinglog.domain.usecase.AddSmokingUseCase
import com.devhjs.mysmokinglog.domain.usecase.GetTodaySmokingInfoUseCase
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
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private val getTodaySmokingInfoUseCase: GetTodaySmokingInfoUseCase = mockk()
    private val addSmokingUseCase: AddSmokingUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()



    @Before
    fun `초기화`() {
        Dispatchers.setMain(testDispatcher)
        
        // 초기 데이터 로드 Mock
        val initialData = TodaySmoking(
            count = 0,
            dailyLimit = 10,
            lastSmokingTime = "None"
        )
        coEvery { getTodaySmokingInfoUseCase.execute() } returns Result.Success(initialData)

        viewModel = HomeViewModel(getTodaySmokingInfoUseCase, addSmokingUseCase)
    }

    @After
    fun `종료`() {
        Dispatchers.resetMain()
    }

    @Test
    fun `흡연_추가_액션이_유즈케이스를_호출하고_데이터를_갱신하는지_테스트`() = runTest {
        // Given (준비)
        coEvery { addSmokingUseCase.execute() } returns Result.Success(Unit)
        
        val updatedData = TodaySmoking(
            count = 1,
            dailyLimit = 10,
            lastSmokingTime = "방금 전"
        )
        
        // 두 번째 호출(갱신)에 대한 mock 설정. 

        val initialData = TodaySmoking(
            count = 0,
            dailyLimit = 10,
            lastSmokingTime = "None"
        )
        
        coEvery { getTodaySmokingInfoUseCase.execute() } returns Result.Success(initialData) andThen Result.Success(updatedData)

        coEvery { getTodaySmokingInfoUseCase.execute() } returns Result.Success(updatedData)

        // When (실행)
        viewModel.onAction(HomeAction.AddSmoking)
        testDispatcher.scheduler.advanceUntilIdle() // 코루틴 작업 대기

        // Then (검증)
        coVerify { addSmokingUseCase.execute() }
        coVerify(atLeast = 1) { getTodaySmokingInfoUseCase.execute() }
        
        assertEquals(1, viewModel.state.value.todayCount)
        assertEquals("방금 전", viewModel.state.value.lastSmokingTime)
    }
}
