package com.devhjs.mysmokinglog.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.devhjs.mysmokinglog.domain.repository.SmokingRepository
import com.devhjs.mysmokinglog.domain.repository.UserSettingRepository
import com.devhjs.mysmokinglog.presentation.stat.StatState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.text.DecimalFormat
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class GetStatUseCase @Inject constructor(
    private val smokingRepository: SmokingRepository,
    private val userSettingRepository: UserSettingRepository
) {
    suspend fun execute(): Flow<StatState> {
        val today = LocalDate.now()
        
        // 1. 가져올 데이터의 날짜 범위 설정 (과거 1년 ~ 내일)
        // 차트와 통계 계산을 위해 충분한 기간의 데이터를 가져옵니다.
        val startOfHistory = today.minusYears(1).toString()
        val endOfHistory = today.plusDays(1).toString()

        // 2. 데이터 Flow 결합 (흡연 기록 + 유저 설정)
        // 두 데이터 중 하나라도 변경되면 통계가 재계산되어 방출됩니다.
        return combine(
            smokingRepository.getSmokingEventsBetweenFlow(startOfHistory, endOfHistory),
            userSettingRepository.getSettingsFlow()
        ) { events, settings ->
            
            // 3. 데이터 준비
            // 타임스탬프 순으로 정렬하여 시계열 계산 정확도 확보
            val allEvents = events.sortedBy { it.timestamp }
            val packPrice = settings.packPrice
            
            // 4. 통계 계산 로직 시작
            
            // A. 이번 달 비용 및 흡연 개수 계산
            val startOfMonth = today.withDayOfMonth(1).atStartOfDay()
            val thisMonthEvents = allEvents.filter { 
                val eventTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(it.timestamp), ZoneId.systemDefault())
                eventTime.isAfter(startOfMonth) || eventTime.isEqual(startOfMonth)
            }
            
            val monthCount = thisMonthEvents.size
            // 비용 계산: (총 개비 수 / 20) * 한 갑 가격
            // 실제 갑 단위 구매가 아니라 개비 단위 소모 비용으로 추산
            val monthCost = (monthCount.toDouble() / 20 * packPrice).toInt()
            // 팩 카운트: 사용자에게 보여질 대략적인 '갑' 수 (올림 등 처리 없이 단순 나눗셈)
            val packCount = monthCount / 20 

            // B. 주간 흡연량 (막대 그래프 데이터)
            // 이번 주 월요일 ~ 일요일 데이터 집계
            val startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            val endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
            val weeklyCounts = MutableList(7) { 0 }
            
            allEvents.filter {
                val eventTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(it.timestamp), ZoneId.systemDefault())
                val eventDate = eventTime.toLocalDate()
                !eventDate.isBefore(startOfWeek) && !eventDate.isAfter(endOfWeek)
            }.forEach {
                val eventTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(it.timestamp), ZoneId.systemDefault())
                // 월요일(1) -> 인덱스 0 변환
                val dayIndex = eventTime.dayOfWeek.value - 1 
                if (dayIndex in 0..6) {
                    weeklyCounts[dayIndex]++
                }
            }

            // C. 오늘의 시간대별 흡연 분포 (점 그래프 데이터)
            val todayEvents = allEvents.filter {
                val eventTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(it.timestamp), ZoneId.systemDefault())
                eventTime.toLocalDate().isEqual(today)
            }
            val hourlyCounts = MutableList(24) { 0 }
            todayEvents.forEach {
                val eventTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(it.timestamp), ZoneId.systemDefault())
                val hour = eventTime.hour
                if (hour in 0..23) {
                    hourlyCounts[hour]++
                }
            }

            // D. 스트릭(연속 기록) 계산
            
            // 최장 금연 시간 (시간 단위)
            // 인접한 두 흡연 이벤트 사이의 최대 시간 간격을 찾음
            var maxGapHours = 0L
            if (allEvents.size >= 2) {
                for (i in 0 until allEvents.size - 1) {
                    val t1 = LocalDateTime.ofInstant(Instant.ofEpochMilli(allEvents[i].timestamp), ZoneId.systemDefault())
                    val t2 = LocalDateTime.ofInstant(Instant.ofEpochMilli(allEvents[i+1].timestamp), ZoneId.systemDefault())
                    val gap = ChronoUnit.HOURS.between(t1, t2)
                    if (gap > maxGapHours) maxGapHours = gap
                }
            }
            
            // 현재 금연 일수 (일 단위)
            // 오늘 날짜 - 마지막 흡연 날짜
            val lastEvent = allEvents.lastOrNull()
            val currentStreakDays = if (lastEvent != null) {
                val lastDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(lastEvent.timestamp), ZoneId.systemDefault()).toLocalDate()
                ChronoUnit.DAYS.between(lastDate, today).toInt()
            } else {
                0
            }

            // 최종 상태 객체 생성 및 방출
            StatState(
                streak = currentStreakDays,
                longestStreak = maxGapHours.toInt(),
                thisMonthCost = DecimalFormat("#,##0").format(monthCost),
                cigarettesTotalCount = monthCount,
                packCount = packCount,
                weeklyCigarettes = weeklyCounts,
                todayTime = hourlyCounts
            )
        }
    }
}
