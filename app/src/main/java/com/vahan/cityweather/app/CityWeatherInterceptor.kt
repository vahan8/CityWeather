package com.vahan.cityweather.app

import com.vahan.cityweather.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

//Custom interceptor to add api key in request headers
class CityWeatherInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        // Adding api key not to provide it in every request as parameter
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("appid", BuildConfig.API_KEY)
            .build()

        // Request customization: add request headers
        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}
