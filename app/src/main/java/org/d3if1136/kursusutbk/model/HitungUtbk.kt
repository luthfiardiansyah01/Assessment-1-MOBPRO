package org.d3if1136.kursusutbk.model

import org.d3if1136.kursusutbk.db.UtbkEntity

fun UtbkEntity.hitungUtbk(): HasilUtbk{
    val weekOfMonth = 4
    val price = (weekOfMonth * time * 50000).toString().toDouble()
    val kategori = if (isStudy) {
        when {
            price < 249000 -> KategoriUtbk.REGULER
            price >= 299000 -> KategoriUtbk.BOOTCAMP
            else -> KategoriUtbk.INTENSIF
        }
    } else {
        when {
            price < 299000 -> KategoriUtbk.REGULER
            price >= 349000 -> KategoriUtbk.BOOTCAMP
            else -> KategoriUtbk.INTENSIF
        }
    }
    return HasilUtbk(price, kategori)
}