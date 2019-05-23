package st.programmi.hahAndroidDemo.net

import android.net.Uri
import android.util.Log
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import st.programmi.hahAndroidDemo.model.YandexWeather
import java.net.HttpURLConnection
import java.net.URL

class Api {
    companion object {
        @JvmStatic
        suspend fun getWeather(): YandexWeather.Json {
            return withContext(Dispatchers.IO) {
//                Thread.sleep(5000) //coroutine test
                val lon = 30.3350986 //SPb
                val lat = 59.9342802 //SPb
                val urlString = "https://api.weather.yandex.ru/v1/forecast?lon=$lon&lat=$lat"
                val uri = Uri.parse(urlString)
                val gson = GsonBuilder().disableHtmlEscaping().create()
                val connection = URL(uri.toString()).openConnection() as HttpURLConnection
                connection.setRequestProperty("X-Yandex-API-Key", "b9d48468-bb80-4206-bcec-769c34085b9d")
                connection.connect()
                val text = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
                Log.d("GetAsyncTask", text)
                val response = gson.fromJson(text, YandexWeather.Json::class.java)
                Log.d("GetAsyncTask", response.toString())
                connection.disconnect()
                return@withContext response
            }
        }
    }
}