package com.devhjs.mysmokinglog.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.devhjs.mysmokinglog.data.entity.SmokingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SmokingDao {

    // 기록
    @Insert
    suspend fun insert(event: SmokingEntity)

    @Query("""
    DELETE FROM smoking_event
    WHERE timestamp = (
        SELECT timestamp
        FROM smoking_event
        ORDER BY timestamp DESC
        LIMIT 1
    )
""")
    suspend fun deleteLastEvent()


    // 오늘 흡연 이벤트 목록
    @Query("""
        SELECT * 
        FROM smoking_event 
        WHERE date = :date 
        ORDER BY timestamp DESC
    """)
    fun getEventsByDate(date: String): Flow<List<SmokingEntity>>

    // 마지막 흡연
    @Query("""
        SELECT * 
        FROM smoking_event 
        ORDER BY timestamp DESC 
        LIMIT 1
    """)
    fun getLastEvent(): Flow<SmokingEntity?>
    
    // 위젯용 Direct Access
    @Query("SELECT * FROM smoking_event WHERE date = :date ORDER BY timestamp DESC")
    suspend fun getEventsByDateList(date: String): List<SmokingEntity>

    @Query("SELECT * FROM smoking_event ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastEventItem(): SmokingEntity?

    @Query("SELECT * FROM smoking_event WHERE date BETWEEN :startDate AND :endDate")
    fun getEventsBetweenFlow(startDate: String, endDate: String): Flow<List<SmokingEntity>>
}

