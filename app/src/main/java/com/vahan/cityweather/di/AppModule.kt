package com.vahan.cityweather.di

import android.app.Application
import androidx.room.Room
import com.vahan.cityweather.app.AppDatabase
import com.vahan.cityweather.app.CityWeatherApp
import com.vahan.cityweather.app.CityWeatherInterceptor
import com.vahan.cityweather.app.CityWeatherRemoteApi
import com.vahan.cityweather.city.CityRepository
import com.vahan.cityweather.city.CityRepositoryImpl
import com.vahan.cityweather.ui.city.CitiesViewModel
import com.vahan.cityweather.ui.weather.WeatherViewModel
import com.vahan.cityweather.weather.local.CurrentWeatherDao
import com.vahan.cityweather.weather.local.DailyWeatherDao
import com.vahan.cityweather.weather.local.WeatherLocalDataSource
import com.vahan.cityweather.weather.local.WeatherLocalDataSourceImpl
import com.vahan.cityweather.weather.remote.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    fun provideApi(): CityWeatherRemoteApi {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(CityWeatherInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(CityWeatherApp.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .callFactory(client)
            .client(client)
            .build()
            .create(CityWeatherRemoteApi::class.java)
    }

    single<CityWeatherRemoteApi> { provideApi() }
    single<WeatherRemoteDataSource> { WeatherRemoteDataSourceImpl(get()) }
}

val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            CityWeatherApp.DB_NAME
        ).build()
    }

    single {
        provideDatabase(androidApplication())
    }

    fun provideCurrentWeatherDao(database: AppDatabase): CurrentWeatherDao {
        return database.currentWeatherDao()
    }

    single<CurrentWeatherDao> { provideCurrentWeatherDao(get()) }

    fun provideDailyWeatherDao(database: AppDatabase): DailyWeatherDao {
        return database.dailyWeatherDao()
    }

    single<DailyWeatherDao> { provideDailyWeatherDao(get()) }
    single<WeatherLocalDataSource> { WeatherLocalDataSourceImpl(get(), get()) }
}

val repositoryModule = module {
    single<CityRepository> { CityRepositoryImpl(get()) }
    single<WeatherRepository> { WeatherRepositoryImpl(get(), get()) }
}

val viewModelModule = module {
    viewModel { CitiesViewModel(get()) }
    viewModel { WeatherViewModel(get()) }
}