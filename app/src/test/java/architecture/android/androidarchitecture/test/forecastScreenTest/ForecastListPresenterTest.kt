package architecture.android.androidarchitecture.test.forecastScreenTest

import architecture.android.androidarchitecture.data.api.ForecastApi
import architecture.android.androidarchitecture.data.model.Forecast
import architecture.android.androidarchitecture.forecastListScreen.ForecastListContract
import architecture.android.androidarchitecture.forecastListScreen.ForecastListPresenter
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.capture
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ForecastListPresenterTest {

    @Mock
    lateinit var view: ForecastListContract.View

    @Mock
    lateinit var forecastApi: ForecastApi

    @Captor
    lateinit var forecastApiCaptor: ArgumentCaptor<ForecastApi.CallBack<List<Forecast>>>

    lateinit var forecastListPresenter: ForecastListPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        forecastListPresenter = ForecastListPresenter(view, forecastApi)
    }

    @Test
    fun shouldLoadLoadForecastList(){
        val expectedForecast = listOf(
                Forecast("03/10/2017", "Sunny day", 30, 27, "u1"),
                Forecast("04/10/2017", "Sunny day", 31, 28, "u2"),
                Forecast("05/10/2017", "Sunny day", 32, 29, "u3"),
                Forecast("06/10/2017", "Sunny day", 33, 30, "u4")
        )

        forecastListPresenter.onLoadForecast()

        verify(forecastApi).getForecasts(any(), capture(forecastApiCaptor))
        forecastApiCaptor.value.onSucess(expectedForecast)

        verify(view).showForecastList(expectedForecast)
    }
}