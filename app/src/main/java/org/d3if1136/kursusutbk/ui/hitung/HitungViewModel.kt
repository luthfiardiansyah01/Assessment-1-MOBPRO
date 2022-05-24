package org.d3if1136.kursusutbk.ui.hitung

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if1136.kursusutbk.db.UtbkDao
import org.d3if1136.kursusutbk.db.UtbkEntity
import org.d3if1136.kursusutbk.model.HasilUtbk
import org.d3if1136.kursusutbk.model.KategoriUtbk
import org.d3if1136.kursusutbk.model.hitungUtbk

class HitungViewModel(private val db: UtbkDao)  : ViewModel() {
    private val hasilUtbk = MutableLiveData<HasilUtbk?>()
    private val navigasi = MutableLiveData<KategoriUtbk?>()
    fun hitungKursus(time: Float, isStudy: Boolean, studyGroup: String, name: String, examGroup: String) {
        val dataUtbk = UtbkEntity(
            time = time,
            isStudy = isStudy,
            study_group = studyGroup,
            name = name,
            exam_group = examGroup
        )
        hasilUtbk.value = dataUtbk.hitungUtbk()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insert(dataUtbk)
            }
        }
    }

    fun getHasilUtbk(): LiveData<HasilUtbk?> = hasilUtbk

    fun mulaiNavigasi() {
        navigasi.value = hasilUtbk.value?.kategori
    }
    fun selesaiNavigasi() {
        navigasi.value = null
    }
    fun getNavigasi() : LiveData<KategoriUtbk?> = navigasi
}