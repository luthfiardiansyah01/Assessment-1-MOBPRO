package org.d3if1136.kursusutbk.ui.history

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if1136.kursusutbk.db.UtbkDao
import org.d3if1136.kursusutbk.model.Kursus
import org.d3if1136.kursusutbk.network.KursusApi

class HistoriViewModel(private val db: UtbkDao) : ViewModel() {
    private val json = MutableLiveData<List<Kursus>>()
    val data = db.getLastUtbk()
    fun hapusData() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            db.clearData()
        }
    }

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                json.postValue(KursusApi.service.getKursus())
            } catch (e: Exception) {
                Log.d("HistoriViewModel", "Failure: ${e.message}")
            }
        }
    }
}