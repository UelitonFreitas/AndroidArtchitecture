package architecture.android.androidarchitecture.forecastListScreen

import android.util.Log
import architecture.android.androidarchitecture.data.model.Forecast


class ForecastListPresenter(val view: ForecastListContract.View,
                            val userCaseInput: ForecastListContract.UserCaseInput) :
        ForecastListContract.UserInteraction,
        ForecastListContract.UserCaseOutput {

    val tag = "ForecastListPresenter"

    override fun onForecastListLoaded(forecastList: List<Forecast>) {
        view.showForecastList(forecastList)
    }

    override fun onForecastListLoadError() {
        Log.d(tag, "error on load data")
    }

    override fun onLoadForecast() {
        val zipCode = view.getZipCode()
        userCaseInput.loadForecastList(zipCode)
    }

    override fun onForecastClick(forecast: Forecast) {
        view.goToForecastDetail(forecast)
    }
}