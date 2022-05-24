package org.d3if1136.kursusutbk.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UtbkEntity::class], version = 2, exportSchema = false)
abstract class UtbkDb : RoomDatabase(){
    abstract val dao: UtbkDao
    companion object {
        @Volatile
        private var INSTANCE: UtbkDb? = null
        fun getInstance(context: Context): UtbkDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UtbkDb::class.java,
                        "utbk.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
