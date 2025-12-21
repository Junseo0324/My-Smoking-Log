package com.devhjs.mysmokinglog.data.repository

import com.devhjs.mysmokinglog.data.dao.SmokingDao
import com.devhjs.mysmokinglog.data.entity.SmokingEntity
import com.devhjs.mysmokinglog.domain.model.Smoking
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SmokingRepositoryImplTest {

    private lateinit var repository: SmokingRepositoryImpl
    private val smokingDao: SmokingDao = mockk()

    @Before
    fun `초기화`() {
        repository = SmokingRepositoryImpl(smokingDao)
    }

    @Test
    fun `저장이_올바른_엔티티로_DAO를_호출하는지_테스트`() = runTest {
        // Given (준비)
        val smoking = Smoking(timestamp = 12345L, date = "2023-01-01")
        val expectedEntity = SmokingEntity(timestamp = 12345L, date = "2023-01-01")

        coEvery { smokingDao.insert(any()) } just Runs

        // When (실행)
        repository.insert(smoking)

        // Then (검증)
        coVerify { smokingDao.insert(expectedEntity) }
    }
}
