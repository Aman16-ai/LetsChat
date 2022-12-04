package com.example.letschat.data.service

import com.example.letschat.data.model.SpamMessageRequest
import com.example.letschat.data.model.SpamMessageResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.net.ResponseCache

const val BASE_URL = "https://spam-classificaition.up.railway.app/"

interface SpamClassificationApiInterface {

    @POST("predictApi/")
    suspend fun predictMessage(@Body spamMessageRequest: SpamMessageRequest) : Response<SpamMessageResponse>
}

object SpamClassificationService {
    val spamClassificationInstance : SpamClassificationApiInterface
    init {
        val retrofit :Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        spamClassificationInstance = retrofit.create(SpamClassificationApiInterface::class.java)
    }
}