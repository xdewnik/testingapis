import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class ExecuteRemoteDataSource(private val api: Testing) {



}

abstract class ExecuteData(open val calcId: String, open val args: Arguments)


data class TestingData(override val args: Arguments, val test: String = "testing value") : ExecuteData("_CALC1", args)