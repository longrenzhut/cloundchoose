package com.haiqi.base.http

import android.os.Build
import com.haiqi.base.Config
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.X509TrustManager

/**
 * Created by Administrator on 2017/9/7.
 */
object HttpProvider2{

    private val CONNECT_TIMEOUT = 8

     fun createRetrofit(url: String = ""): Retrofit{
        val baseUrl =  if(!url.isNullOrEmpty())
            url
        else
            if (Config.DEBUG) Config.BASE_URL_TEST else Config.BASE_URL

        return Retrofit.Builder().baseUrl(baseUrl)
                .client(initBuilder().build())
//              .addConverterFactory(JsonConverterFactory.create()) // 添加 json 转换器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加 RxJava 适配器
                .build()
    }


    private fun initBuilder(): OkHttpClient.Builder{
        val builder =  OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor()
//        loggingInterceptor.level =  HttpLoggingInterceptor.Level.BODY
        loggingInterceptor.level = if (Config.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        builder.addInterceptor(loggingInterceptor)
        .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            try {
                // 自定义一个信任所有证书的TrustManager，添加SSLSocketFactory的时候要用到
                val trustAllCert = object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }
                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }
                    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                        return arrayOf()
                    }
                }
                val sslSocketFactory = SSLSocketFactoryCompat(trustAllCert)
                builder.sslSocketFactory(sslSocketFactory, trustAllCert)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

        return builder
    }


     val mRetrofit by lazy { createRetrofit() }

    inline fun <reified T> createService(): T
            = mRetrofit.create(T::class.java)

    fun create(): BaseService{
        return mRetrofit.create(BaseService::class.java)
    }


}