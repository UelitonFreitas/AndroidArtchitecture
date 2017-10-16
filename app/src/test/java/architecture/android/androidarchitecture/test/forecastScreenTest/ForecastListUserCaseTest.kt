package architecture.android.androidarchitecture.test.forecastScreenTest

import architecture.android.androidarchitecture.data.api.ForecastApi
import architecture.android.androidarchitecture.data.model.Forecast
import architecture.android.androidarchitecture.forecastListScreen.ForecastListContract
import architecture.android.androidarchitecture.forecastListScreen.ForecastListUserCase
import com.nhaarman.mockito_kotlin.capture
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ForecastListUserCaseTest {
    @Mock
    lateinit var forecastApi: ForecastApi

    @Mock
    lateinit var userCaseOutput: ForecastListContract.UserCaseOutput

    @Captor
    lateinit var forecastApiCaptor: ArgumentCaptor<ForecastApi.CallBack<List<Forecast>>>

    lateinit var forecastListUserCase: ForecastListUserCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        forecastListUserCase = ForecastListUserCase(forecastApi)
        forecastListUserCase.userCaseOutput = userCaseOutput
    }

    @Test
    fun shouldLoadForecastList(){
        val expectedForecast = listOf(
                Forecast("03/10/2017", "Sunny day", 30, 27, "u1"),
                Forecast("04/10/2017", "Sunny day", 31, 28, "u2"),
                Forecast("05/10/2017", "Sunny day", 32, 29, "u3"),
                Forecast("06/10/2017", "Sunny day", 33, 30, "u4")
        )

        val zipCode = "aCode"

        forecastListUserCase.loadForecastList(zipCode)

        verify(forecastApi).getForecasts(eq(zipCode), capture(forecastApiCaptor))
        forecastApiCaptor.value.onSucess(expectedForecast)

        verify(userCaseOutput).onForecastListLoaded(eq(expectedForecast))
    }
}