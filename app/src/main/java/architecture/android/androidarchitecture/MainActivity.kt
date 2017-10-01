package architecture.android.androidarchitecture

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import architecture.android.androidarchitecture.MainActivity.Forecast
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_forecast.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.net.URL
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        forecastList.layoutManager = LinearLayoutManager(this)

        doAsync {
            val result = ForecastApi().getForecasts("94043")
            uiThread {
                val adapter = ForecastListAdapter(result) { toast(it.date) }
                forecastList.adapter = adapter
            }
        }
    }

    inner class ForecastListAdapter(private val weekForecast: List<Forecast>,
                                    private val itemClick: (Forecast) -> Unit) :
            RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_forecast, parent, false)
            return ViewHolder(view, itemClick)
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            holder?.bindForecast(weekForecast[position])
        }

        override fun getItemCount() = weekForecast.size

        inner class ViewHolder(private val view: View, private val itemClick: (Forecast) -> Unit) :
                RecyclerView.ViewHolder(view) {

            fun bindForecast(forecast: Forecast) {
                with(forecast) {
                    Picasso.with(itemView.context).load(iconUrl).into(itemView.icon)
                    itemView.date.text = date
                    itemView.maxTemperature.text = "${high.toString()}º"
                    itemView.minTemperature.text = "${low.toString()}º"
                    view.setOnClickListener { itemClick(this) }
                }
            }
        }
    }

    data class Forecast(val date: String, val description: String, val high: Int,
                        val low: Int,
                        val iconUrl: String)

    class ForecastApi{

        companion object{
            private val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
            private val BASE_URL = "http://api.openweathermap.org/data/2.5/" +
                    "forecast/daily?mode=json&units=metric&cnt=7"
            private val COMPLETE_URL = "$BASE_URL&APPID=$APP_ID&q="
        }

        fun getForecasts(zipCode: String): List<MainActivity.Forecast>{
            val forecastJsonStr = URL(COMPLETE_URL+zipCode).readText()
            val result = Gson().fromJson(forecastJsonStr, ForecastResult::class.java)

            return result.list.map { convertForecastItemToAdapter(it) }
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

        private fun convertForecastItemToAdapter(forecast: ForecastApi.Forecast): MainActivity.Forecast {
            return  Forecast(convertDate(forecast.dt),
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
}


