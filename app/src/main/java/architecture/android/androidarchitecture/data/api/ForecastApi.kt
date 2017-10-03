package architecture.android.androidarchitecture.data.api

import architecture.android.androidarchitecture.data.model.Forecast


interface ForecastApi {
    fun getForecasts(zipCode: String, callBack: CallBack<List<Forecast>>)

    interface CallBack<T> {
        fun onSucess(data: T)
        fun onError()
    }
}