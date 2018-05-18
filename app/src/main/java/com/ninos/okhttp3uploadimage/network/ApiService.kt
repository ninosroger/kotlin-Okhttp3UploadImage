package com.ninos.okhttp3uploadimage.network

import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Part
import rx.Observable

/**
 * Created by ninos on 2017/5/26.
 */
interface ApiService {
    @FormUrlEncoded
    @POST("/demo")
    fun demoRequest(@Part image: MultipartBody.Part, @Part other: MultipartBody.Part): Observable<String>
}
