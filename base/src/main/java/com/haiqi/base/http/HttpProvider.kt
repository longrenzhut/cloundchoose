package com.haiqi.base.http
import android.os.Build
import com.haiqi.base.Config
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.X509TrustManager

/**
 */
class HttpProvider(val url: String = ""){

    private val builder: OkHttpClient.Builder by lazy{
        OkHttpClient.Builder()
    }

    private val CONNECT_TIMEOUT = 8
    var mRetrofit: Retrofit? = null
    init {
        val baseUrl: String =
                if(!url.isNullOrEmpty())
                    url
                else
                    if (Config.DEBUG) Config.BASE_URL_TEST else Config.BASE_URL
        initBuilder()
        //OkHttp3打印
        val loggingInterceptor = HttpLoggingInterceptor()
        //        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.level =  HttpLoggingInterceptor.Level.BODY
//        loggingInterceptor.level = if (Consts.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        builder.addInterceptor(loggingInterceptor)
        val client = builder.connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS).build()
        //设置缓存
        //    File httpCacheDirectory = new File(app.getBaseContext().getCacheDir(), "jhsqcache");
        //    Cache httpCache = new Cache(httpCacheDirectory, 50 * 1024 * 1024);
//        builder!!.addInterceptor(object :Interceptor{
//            override fun intercept(chain: Interceptor.Chain?): Response? {
//
//                return chain?.request()?.url()?.newBuilder()?.addQueryParameter("plat")
//            }
//        })
        mRetrofit = Retrofit.Builder().baseUrl(baseUrl).client(client)
//                .addConverterFactory(JsonConverterFactory.create()) // 添加 json 转换器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加 RxJava 适配器
                .build()
    }

    fun initBuilder() {
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
    }

    inline fun <reified T> createService(): T
            = mRetrofit!!.create(T::class.java)


    companion object {
        private val instance  by lazy { HttpProvider() }
        fun getHttpProvider(): HttpProvider
                = instance

        private var url = ""
        private val commonInstance by lazy { HttpProvider(url) }

        fun get(url: String): HttpProvider{
            this.url = url
            return commonInstance
        }
    }




    fun publicBody(fields: Map<String, Any>?): MultipartBody.Builder {
        val builder = MultipartBody.Builder()
                .setType(MultipartBody.ALTERNATIVE)
        if (null != fields && fields.size > 0) {
            for (key in fields.keys) {
                builder.addFormDataPart(key, fields[key].toString())
            }
        }
        return builder
    }
}