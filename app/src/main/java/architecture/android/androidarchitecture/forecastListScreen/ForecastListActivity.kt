package architecture.android.androidarchitecture.forecastListScreen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import architecture.android.androidarchitecture.R
import architecture.android.androidarchitecture.data.api.ForecastApiImpl
import architecture.android.androidarchitecture.data.model.Forecast
import architecture.android.androidarchitecture.forecastListScreen.adapters.ForecastListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class ForecastListActivity : AppCompatActivity(), ForecastListContract.View {
    lateinit var forecastListPresenter: ForecastListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val forecastListUserCase = ForecastListUserCase(ForecastApiImpl())

        forecastListPresenter = ForecastListPresenter(this, forecastListUserCase)
        forecastListUserCase.userCaseOutput = forecastListPresenter

        forecastList.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        forecastListPresenter.onLoadForecast()
    }

    override fun showForecastList(forecasts: List<Forecast>) {
        val adapter = ForecastListAdapter(forecasts) {
            forecastListPresenter.onForecastClick(it)
        }
        forecastList.adapter = adapter
    }

    override fun goToForecastDetail(forecast: Forecast) {
        toast(forecast.date)
    }

    override fun getZipCode(): String {
        return "94043" //EditText
    }
}


