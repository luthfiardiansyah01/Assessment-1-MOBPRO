package org.d3if1136.kursusutbk.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if1136.kursusutbk.db.UtbkDao

class HistoriViewModel(private val db: UtbkDao) : ViewModel() {
    val data = db.getLastUtbk()
    fun hapusData() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            db.clearData()
        }
    }
}