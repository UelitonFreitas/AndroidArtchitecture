package architecture.android.androidarchitecture.forecastListScreen

import android.util.Log
import architecture.android.androidarchitecture.data.api.ForecastApi
import architecture.android.androidarchitecture.data.model.Forecast


class ForecastListPresenter(val view: ForecastListContract.View, val forecastAPI: ForecastApi) : ForecastListContract.UserInteraction {
    val tag = "ForecastListPresenter"

    override fun onLoadForecast() {
        forecastAPI.getForecasts("94043", object : ForecastApi.CallBack<List<Forecast>> {
            override fun onSucess(data: List<Forecast>) {
                view.showForecastList(data)
            }

            override fun onError() {
                Log.d(tag, "error on load data")
            }
        })
    }

    override fun onForecastClick(forecast: Forecast) {
        view.goToForecastDetail(forecast)
    }
}