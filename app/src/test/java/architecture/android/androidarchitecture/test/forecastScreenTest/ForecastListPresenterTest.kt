package architecture.android.androidarchitecture.test.forecastScreenTest

import architecture.android.androidarchitecture.data.model.Forecast
import architecture.android.androidarchitecture.forecastListScreen.ForecastListContract
import architecture.android.androidarchitecture.forecastListScreen.ForecastListPresenter
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ForecastListPresenterTest {

    @Mock
    lateinit var view: ForecastListContract.View

    @Mock
    lateinit var userCaseInput: ForecastListContract.UserCaseInput

    @Mock
    lateinit var userCaseOutput: ForecastListContract.UserCaseOutput

    lateinit var forecastListPresenter: ForecastListPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        forecastListPresenter = ForecastListPresenter(view, userCaseInput)
    }

    @Test
    fun shouldLoadForecastList(){
        val zipCode = "zipcoDE"

        `when`(view.getZipCode()).thenReturn(zipCode)

        forecastListPresenter.onLoadForecast()

        verify(userCaseInput).loadForecastList(eq(zipCode))
    }


    @Test
    fun shouldShowForecastList(){
        val expectedForecast = listOf(
                Forecast("03/10/2017", "Sunny day", 30, 27, "u1"),
                Forecast("04/10/2017", "Sunny day", 31, 28, "u2"),
                Forecast("05/10/2017", "Sunny day", 32, 29, "u3"),
                Forecast("06/10/2017", "Sunny day", 33, 30, "u4")
        )

        forecastListPresenter.onForecastListLoaded(expectedForecast)

        verify(view).showForecastList(expectedForecast)
    }
}