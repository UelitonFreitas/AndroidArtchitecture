package architecture.android.androidarchitecture.forecastListScreen

import architecture.android.androidarchitecture.data.model.Forecast


interface ForecastListContract {
    interface View {
        fun showForecastList(forecastList: List<Forecast>)
        fun goToForecastDetail(forecast: Forecast)
        fun getZipCode(): String
    }

    interface UserInteraction {
        fun onLoadForecast()
        fun onForecastClick(forecast: Forecast)
    }

    interface UserCaseInput{
        fun loadForecastList(zipCode: String)
    }

    interface UserCaseOutput{
        fun onForecastListLoaded(forecastList: List<Forecast>)
        fun onForecastListLoadError()
    }
}