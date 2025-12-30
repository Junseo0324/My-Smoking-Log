package com.devhjs.mysmokinglog.presentation.home

import com.devhjs.mysmokinglog.core.util.Result
import com.devhjs.mysmokinglog.domain.model.TodaySmoking
import com.devhjs.mysmokinglog.domain.usecase.AddSmokingUseCase
import com.devhjs.mysmokinglog.domain.usecase.DeleteSmokingUseCase
import com.devhjs.mysmokinglog.domain.usecase.GetTodaySmokingInfoUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
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
    private val deleteSmokingUseCase: DeleteSmokingUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()


    private val _dataFlow = MutableStateFlow<Result<TodaySmoking, Throwable>>(Result.Success(TodaySmoking(0, 10, null)))

    @Before
    fun `초기화`() {
        Dispatchers.setMain(testDispatcher)

        // 초기 데이터 로드 Mock
        // Flow를 반환하도록 설정. MutableStateFlow를 사용하여 값을 변경하며 테스트.
        coEvery { getTodaySmokingInfoUseCase.execute() } returns _dataFlow

        viewModel = HomeViewModel(getTodaySmokingInfoUseCase, addSmokingUseCase, deleteSmokingUseCase)
    }

    @After
    fun `종료`() {
        Dispatchers.resetMain()
    }

    @Test
    fun `흡연_추가_액션이_유즈케이스를_호출하고_state가_자동으로_갱신되는지_테스트`() = runTest {
        // Given (준비)
        coEvery { addSmokingUseCase.execute() } returns Result.Success(Unit)
        
        // 초기 상태 확인
        val initialData = TodaySmoking(
            count = 0,
            dailyLimit = 10,
            lastSmokingTimestamp = null
        )
        _dataFlow.emit(Result.Success(initialData))
        testDispatcher.scheduler.advanceUntilIdle()
        
        assertEquals(0, viewModel.state.value.todayCount)

        // When (실행) - 흡연 추가
        viewModel.onAction(HomeAction.AddSmoking)
        testDispatcher.scheduler.advanceUntilIdle() // 코루틴 작업 대기(addSmoking 실행)

        // Then (검증)
        coVerify { addSmokingUseCase.execute() }
        coVerify { getTodaySmokingInfoUseCase.execute() } // Flow 수집 시작 확인

        // 데이터 변경 시뮬레이션 (DB 업데이트 -> Flow 방출)
        val updatedData = TodaySmoking(
            count = 1,
            dailyLimit = 10,
            lastSmokingTimestamp = 1000L
        )
        _dataFlow.emit(Result.Success(updatedData))
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(1, viewModel.state.value.todayCount)
        assertEquals(1000L, viewModel.state.value.lastSmokingTimestamp)
    }

    @Test
    fun `흡연_상태가_정상적으로_계산되는지_테스트`() = runTest {
        // Given
        val safeData = TodaySmoking(count = 5, dailyLimit = 10, lastSmokingTimestamp = null)
        _dataFlow.emit(Result.Success(safeData))
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(SmokingStatus.SAFE, viewModel.state.value.status)

        // Given
        val warningData = TodaySmoking(count = 8, dailyLimit = 10, lastSmokingTimestamp = null)
        _dataFlow.emit(Result.Success(warningData))
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(SmokingStatus.WARNING, viewModel.state.value.status)

        // Given
        val exceededData = TodaySmoking(count = 11, dailyLimit = 10, lastSmokingTimestamp = null)
        _dataFlow.emit(Result.Success(exceededData))
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(SmokingStatus.EXCEEDED, viewModel.state.value.status)
    }


    @Test
    fun `흡연_추가_시_되돌리기_버튼이_보이고_3초_후_사라지는지_테스트`() = runTest {
        // Given
        coEvery { addSmokingUseCase.execute() } returns Result.Success(Unit)

        // When
        viewModel.onAction(HomeAction.AddSmoking)
        testDispatcher.scheduler.runCurrent() // 코루틴 시작, delay 전까지만 실행

        // Then - 추가 직후 보임
        assertEquals(true, viewModel.state.value.isUndoVisible)

        // 3000ms (3초) 경과 후
        testDispatcher.scheduler.advanceTimeBy(3001)
        testDispatcher.scheduler.runCurrent()
        
        // Then - 사라짐
        assertEquals(false, viewModel.state.value.isUndoVisible)
    }

    @Test
    fun `흡연_삭제_액션이_유즈케이스를_호출하는지_테스트`() = runTest {
        // Given
        coEvery { deleteSmokingUseCase.execute() } returns Result.Success(Unit)
        
        // When
        viewModel.onAction(HomeAction.DeleteSmoking)
        testDispatcher.scheduler.advanceUntilIdle() // Flow debounce (throttleFirst) 대기
        
        // Then
        coVerify { deleteSmokingUseCase.execute() }
    }
}
