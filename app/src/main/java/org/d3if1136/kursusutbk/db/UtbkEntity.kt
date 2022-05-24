package org.d3if1136.kursusutbk.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "utbk")
data class UtbkEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var tanggal: Long = System.currentTimeMillis(),
    var name: String,
    var study_group: String,
    var exam_group: String,
    var time: Float,
    var isStudy: Boolean

)
