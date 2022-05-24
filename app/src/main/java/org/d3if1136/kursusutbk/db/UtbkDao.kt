package org.d3if1136.kursusutbk.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UtbkDao {
    @Insert
    fun insert(utbk: UtbkEntity)
    @Query("SELECT * FROM utbk ORDER BY id DESC")
    fun getLastUtbk(): LiveData<List<UtbkEntity?>>
    @Query("DELETE FROM utbk")
    fun clearData()

}