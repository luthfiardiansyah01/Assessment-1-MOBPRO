package org.d3if1136.kursusutbk.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if1136.kursusutbk.model.Kursus
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/" +
        "luthfiardiansyah01/kursus-utbk/master/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface KursusApiService {
    @GET("static-api.json")
    suspend fun getKursus(): List<Kursus>
}

object KursusApi {
    val service: KursusApiService by lazy {
        retrofit.create(KursusApiService::class.java)
    }

    fun getKursusUrl(nama: String): String {
        return "$BASE_URL$nama.jpg"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }
