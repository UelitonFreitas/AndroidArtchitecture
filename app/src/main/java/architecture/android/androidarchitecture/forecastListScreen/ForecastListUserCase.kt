package architecture.android.androidarchitecture.forecastListScreen

import architecture.android.androidarchitecture.data.api.ForecastApi
import architecture.android.androidarchitecture.data.model.Forecast


class ForecastListUserCase(val forecastApi: ForecastApi) : ForecastListContract.UserCaseInput {

    var userCaseOutput: ForecastListContract.UserCaseOutput? = null

    override fun loadForecastList(zipCode: String) {
        forecastApi.getForecasts(zipCode, object : ForecastApi.CallBack<List<Forecast>> {
            override fun onSucess(data: List<Forecast>) {
                userCaseOutput!!.onForecastListLoaded(data)
            }

            override fun onError() {
                userCaseOutput!!.onForecastListLoadError()
            }

        })
    }
}