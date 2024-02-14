package no.uio.ifi.in2000.knuho.oblig2

import kotlinx.coroutines.runBlocking
import org.junit.Test
import no.uio.ifi.in2000.knuho.oblig2.data.alpacas.AlpacaPartiesDataSource



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun main() = runBlocking {
        val dataSource = AlpacaPartiesDataSource()
        val partyInfo = dataSource.fetchAlpacaParties()
        println(partyInfo)
    }
}