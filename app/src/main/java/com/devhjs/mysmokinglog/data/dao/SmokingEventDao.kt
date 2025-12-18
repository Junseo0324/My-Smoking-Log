package com.devhjs.mysmokinglog.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.devhjs.mysmokinglog.data.entity.SmokingEventEntity

@Dao
interface SmokingEventDao {

    // 기록
    @Insert
    suspend fun insert(event: SmokingEventEntity)

    // 되돌리기
    @Delete
    suspend fun delete(event: SmokingEventEntity)

    // 오늘 개수
    @Query("""
        SELECT COUNT(*) 
        FROM smoking_event 
        WHERE date = :date
    """)
    suspend fun getCountByDate(date: String): Int

    // 오늘 흡연 이벤트 목록
    @Query("""
        SELECT * 
        FROM smoking_event 
        WHERE date = :date 
        ORDER BY timestamp DESC
    """)
    suspend fun getEventsByDate(date: String): List<SmokingEventEntity>

    // 마지막 흡연
    @Query("""
        SELECT * 
        FROM smoking_event 
        ORDER BY timestamp DESC 
        LIMIT 1
    """)
    suspend fun getLastEvent(): SmokingEventEntity?

    // 기간별 이벤트 (주간/월간 통계)
    @Query("""
        SELECT * 
        FROM smoking_event
        WHERE date BETWEEN :startDate AND :endDate
        ORDER BY timestamp ASC
    """)
    suspend fun getEventsBetween(
        startDate: String,
        endDate: String
    ): List<SmokingEventEntity>
}
