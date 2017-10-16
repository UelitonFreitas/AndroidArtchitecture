package architecture.android.androidarchitecture.data.api

import architecture.android.androidarchitecture.data.model.Forecast
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL
import java.text.DateFormat
import java.util.*


class ForecastApiImpl : ForecastApi {

    companion object {
        private val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
        private val BASE_URL = "http://api.openweathermap.org/data/2.5/" +
                "forecast/daily?mode=json&units=metric&cnt=7"
        private val COMPLETE_URL = "${BASE_URL}&APPID=${APP_ID}&q="
    }

    override fun getForecasts(zipCode: String, callBack: ForecastApi.CallBack<List<architecture.android.androidarchitecture.data.model.Forecast>>) {
        doAsync(exceptionHandler = {
            callBack.onError()
        }) {
            val forecastJsonStr = URL(COMPLETE_URL + zipCode).readText()
            val result = Gson().fromJson(forecastJsonStr, ForecastResult::class.java)
            uiThread {
                callBack.onSucess(result.list.map { convertForecastItemToAdapter(it) })
            }
        }
    }

    data class ForecastResult(val city: City, val list: List<Forecast>)

    data class City(val id: Long, val name: String, val coord: Coordinates, val country: String,
                    val population: Int)

    data class Coordinates(val lon: Float, val lat: Float)

    data class Forecast(val dt: Long, val temp: Temperature, val pressure: Float, val humidity: Int,
                        val weather: List<Weather>, val speed: Float, val deg: Int, val clouds: Int,
                        val rain: Float)

    data class Temperature(val day: Float, val min: Float, val max: Float, val night: Float,
                           val eve: Float, val morn: Float)

    data class Weather(val id: Long, val main: String, val description: String, val icon: String)

    private fun convertForecastItemToAdapter(forecast: Forecast): architecture.android.androidarchitecture.data.model.Forecast {
        return Forecast(convertDate(forecast.dt),
                forecast.weather[0].description, forecast.temp.max.toInt(),
                forecast.temp.min.toInt(),
                generateIconUrl(forecast.weather[0].icon))
    }

    private fun convertDate(date: Long): String {
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM,
                Locale.getDefault())
        return df.format(date * 1000)
    }

    private fun generateIconUrl(iconCode: String) = "http://openweathermap.org/img/w/$iconCode.png"
}