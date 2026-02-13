package com.example.weatherapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface WeatherHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather( weather: WeatherHistoryEntity)

    /**
     * Enforce business rule here so repository stays clean
     *
     * Removing of suspend - when using this Room runs the query once and stops
     *
     * Returning a Flow Room stays connected to the database and pushes a new list to ViewModel
     * every time the table change
     */
    //
    @Query("""
        SELECT * FROM weather_history
        ORDER BY timestamp DESC
    """)
    fun getWeatherLastFive(): Flow<List<WeatherHistoryEntity>>
    
    @Delete
    suspend fun deleteWeatherItem(item: WeatherHistoryEntity)

    @Query("DELETE FROM weather_history")
    suspend fun clearAll()


}