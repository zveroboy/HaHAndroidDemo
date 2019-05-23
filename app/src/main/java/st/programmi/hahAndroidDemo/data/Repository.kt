package st.programmi.hahAndroidDemo.data

import st.programmi.hahAndroidDemo.model.YandexWeather
import st.programmi.hahAndroidDemo.net.Api

class Repository {
    suspend fun getWeather(): YandexWeather.Json {
        return Api.getWeather()
    }
}