import Testing.Factory.create
import kotlinx.coroutines.*
import org.apache.commons.lang3.StringUtils

private val apiService = create()


fun main(): Unit {
    runBlocking {
        launch {
            apiService.getContent()
                .filter { it.type !="spin" }
                .filter { it.sku == "ssa" }
                .sortedBy { it.sort_index }
                .forEach { println(it.toString()) }
        }
    }
}

