package com.firebaseapp.mavenuptodate.mavenme.data.remote

import com.firebaseapp.mavenuptodate.mavenme.BuildConfig
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit.SECONDS

object ServiceGenerator {

    fun <S> createService(serviceClass: Class<S>, xml: Boolean = false): S {

        val defaultTimeOut = 60L

        val builder = OkHttpClient.Builder()
                .connectTimeout(defaultTimeOut, SECONDS)
                .writeTimeout(defaultTimeOut, SECONDS)
                .readTimeout(defaultTimeOut, SECONDS)
                .addNetworkInterceptor { it.proceed(it.request().newBuilder().build()) }

        val retrofitBuilder = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))

        val retrofit = if (xml) retrofitBuilder
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(Persister(AnnotationStrategy())))
                .build() else retrofitBuilder
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()

        return retrofit.create(serviceClass)

    }

}