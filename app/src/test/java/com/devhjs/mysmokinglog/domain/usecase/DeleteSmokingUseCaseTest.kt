package com.devhjs.mysmokinglog.domain.usecase

import com.devhjs.mysmokinglog.core.util.Result
import com.devhjs.mysmokinglog.domain.repository.SmokingRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DeleteSmokingUseCaseTest {

    private lateinit var deleteSmokingUseCase: DeleteSmokingUseCase
    private val smokingRepository: SmokingRepository = mockk()

    @Before
    fun `초기화`() {
        deleteSmokingUseCase = DeleteSmokingUseCase(smokingRepository)
    }

    @Test
    fun `기록_삭제_성공_시_Success_결과를_반환하는지_테스트`() = runTest {
        // Given
        coEvery { smokingRepository.delete() } just Runs

        // When
        val result = deleteSmokingUseCase.execute()

        // Then
        coVerify(exactly = 1) { smokingRepository.delete() }
        assertTrue(result is Result.Success)
    }

    @Test
    fun `기록_삭제_실패_시_Error_결과를_반환하는지_테스트`() = runTest {
        // Given
        val exception = RuntimeException("DB Error")
        coEvery { smokingRepository.delete() } throws exception

        // When
        val result = deleteSmokingUseCase.execute()

        // Then
        coVerify(exactly = 1) { smokingRepository.delete() }
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).error)
    }
}
