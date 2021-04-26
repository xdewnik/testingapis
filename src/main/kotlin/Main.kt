import GithubApiService.Factory.create
import kotlinx.coroutines.*
import org.apache.commons.lang3.StringUtils
import org.w3c.dom.Document
import java.io.File
import java.lang.Runnable
import java.nio.file.Path
import java.nio.file.Paths
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult


private val apiService = create()


fun main() {

//    val expected = "TableInfo{name='MediaFile', columns={serverStatus=Column{name='serverStatus', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue=''failed''}, synced=Column{name='synced', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='0'}, file_name=Column{name='file_name', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue=''''}, created_at=Column{name='created_at', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}, externalId=Column{name='externalId', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}, shareLink=Column{name='shareLink', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, sku_id=Column{name='sku_id', type='INTEGER', affinity='3', notNull=false, primaryKeyPosition=0, defaultValue='null'}, isVisible=Column{name='isVisible', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}, type=Column{name='type', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='null'}, url=Column{name='url', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, isCreateSpin=Column{name='isCreateSpin', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='1'}, path=Column{name='path', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, uid=Column{name='uid', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, updated_at=Column{name='updated_at', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}, bytes_size=Column{name='bytes_size', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='0'}, s3_key=Column{name='s3_key', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, isSelected=Column{name='isSelected', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}, attributes=Column{name='attributes', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, id=Column{name='id', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=1, defaultValue='null'}, sku=Column{name='sku', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, thumbs=Column{name='thumbs', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}}, foreignKeys=[], indices=[]}"
//    val found = "TableInfo{name='MediaFile', columns={serverStatus=Column{name='serverStatus', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='null'}, synced=Column{name='synced', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}, sortIndex=Column{name='sortIndex', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}, file_name=Column{name='file_name', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='null'}, created_at=Column{name='created_at', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}, externalId=Column{name='externalId', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}, shareLink=Column{name='shareLink', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, sku_id=Column{name='sku_id', type='INTEGER', affinity='3', notNull=false, primaryKeyPosition=0, defaultValue='null'}, isVisible=Column{name='isVisible', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}, type=Column{name='type', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='null'}, url=Column{name='url', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, isCreateSpin=Column{name='isCreateSpin', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='1'}, path=Column{name='path', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, uid=Column{name='uid', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, updated_at=Column{name='updated_at', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}, bytes_size=Column{name='bytes_size', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}, s3_key=Column{name='s3_key', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, isSelected=Column{name='isSelected', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}, attributes=Column{name='attributes', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, id=Column{name='id', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=1, defaultValue='null'}, sku=Column{name='sku', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, thumbs=Column{name='thumbs', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}}, foreignKeys=[], indices=[]}"
//    print(expected == found)
//    print(StringUtils.difference(expected, found))
//    print(
//        "eyJhbGciOiJSUzI1NiIsImtpZCI6Ijk2MGE3ZThlODM0MWVkNzUyZjEyYjE4NmZhMTI5NzMxZmUwYjA0YzAiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI4MzIzNjY3Njk2NC04ODN1YXNzZnF1NGJjcTdvbDI0ZDJxY3V0Y2Mwa290aC5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsImF1ZCI6IjgzMjM2Njc2OTY0LWdqNGw2c21lMjMzM3Y0cHZrOTJ1cmFmczRvNmV0b3R0LmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTAyMzc4MzAzNTk0MzY1MzMxNDE4IiwiZW1haWwiOiJpLmt1bGlrb3ZAZG8taXQuY28iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IklseWEgS3VsIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS8tU2l0VlEyN1ZKa1UvQUFBQUFBQUFBQUkvQUFBQUFBQUFBQUEvQU1adXVjbmFLQ25sTnRiTi1FQzY1SUJUWnJzM0hsTFFTdy9zOTYtYy9waG90by5qcGciLCJnaXZlbl9uYW1lIjoiSWx5YSIsImZhbWlseV9uYW1lIjoiS3VsIiwibG9jYWxlIjoidWsiLCJpYXQiOjE1ODk4MDgyNzksImV4cCI6MTU4OTgxMTg3OX0.iwqcFo7kDLp3oXf4r8bgnlLFQxj7rjGayGL78vxjmtjEZW5toqhvi62Mc56augGbXu0beHJyHUzhI1aiu0KVDVLCn594DVKeV4LS_1BakS1s3YFLYc5iNWAIi6AHZdWSJB9F_E6-MpdtqTBCmIwXvYWjw6YfjcL2e22NPyi01xNeZJEpFnOlLHiKehmzjLgcWdP2CO1L45kF-z_EInO8Udcg-kG9DlQrAf8FkxnAiIAfKJL2enN0U6iwT3PHm1ieaxcASylvemRnfr9VZ-dt_ADJC7Bn8WZRw-BjL1SSD6-t1GWNANGJeRpJ-NObfD0TqbGQPL804R7HiW9RRwmLFQ" == "eyJhbGciOiJSUzI1NiIsImtpZCI6Ijk2MGE3ZThlODM0MWVkNzUyZjEyYjE4NmZhMTI5NzMxZmUwYjA0YzAiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI4MzIzNjY3Njk2NC04ODN1YXNzZnF1NGJjcTdvbDI0ZDJxY3V0Y2Mwa290aC5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsImF1ZCI6IjgzMjM2Njc2OTY0LWdqNGw2c21lMjMzM3Y0cHZrOTJ1cmFmczRvNmV0b3R0LmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTA4MzYzMDQ3MTMzNTcyOTY2NzM3IiwiZW1haWwiOiJmcmF6b3IucWEwMDJAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5hbWUiOiJmcmF6b3IgcWEiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tLy13RUxQOEpfckVRcy9BQUFBQUFBQUFBSS9BQUFBQUFBQUFBQS9BTVp1dWNuWUFkMG5DZWVUMUNGaHZQb3B6ZlZUMFNQZXJ3L3M5Ni1jL3Bob3RvLmpwZyIsImdpdmVuX25hbWUiOiJmcmF6b3IiLCJmYW1pbHlfbmFtZSI6InFhIiwibG9jYWxlIjoicnUiLCJpYXQiOjE1ODk4MDcwNjcsImV4cCI6MTU4OTgxMDY2N30.ODCwaXcWdPN6F3pqMCEm7FqcQd2ZBkzgUVLysfTDIItnRD7OKqmDkCojh3ik0BkZKX4s9Dujpv6Ofl1TlmTtQveyQwdfwA7kBfMXBTHrwHvDx0LPJ6GpyS6TRzL50xQRoM-SYBjGTSqh-LhYz1H7CTLB9cMvnSjyzpX9jiJajy3lTOxGQIzaBoZzaFMdyV9Uy-1ZJPUeIGQiCZMddhs6FQHBy4ChGld7V9R7z7YDjLacukkQvpWq6KlkoTBEifwKVMP3lplOiGlxpTszv6gcVyIF5J7MtARuk_YI2itCafp4NFyKV_P_TBZ1iHRw0C5wB1cH4v6amwNqBH1m_AyrWA"
//    )
    val langToTranslate = listOf<String>(
        "ar",
        "bn",
        "de",
        "es",
        "fr",
        "hi",
        "it",
        "iw",
        "ko",
        "pt",
        "ru",
        "tr",
        "zh"
    )
//    val langToTranslate = listOf<String>("ko")

    Thread(Runnable {


        GlobalScope.launch {
            langToTranslate.map { langToTranslate ->
                async { translateLang(langToTranslate) }
            }.map { it.await() }

        }
        try {
            println("Does it work?")
            Thread.sleep(10000)
            println("Nope, it doesnt...again.")
        } catch (v: InterruptedException) {
            println(v)
        }
    }).start()
//    thread.start()


}

private suspend fun translateLang(lang: String): Unit {
    withContext(Dispatchers.IO) {
        val inputFile = File("/home/android-dev/translation/strings.xml")
        val dbFactory = DocumentBuilderFactory.newInstance()
        val dBuilder = dbFactory.newDocumentBuilder()
        val doc = dBuilder.parse(inputFile)
        doc.documentElement.normalize()
        println("Root element :" + doc.documentElement.nodeName)
        val nList = doc.getElementsByTagName("string")
        checkFileForExists(lang)
        val path = Paths.get("/home/android-dev/translation/strings_$lang.xml")
        val documentBuilderFactory = DocumentBuilderFactory.newInstance()
        val documentBuilder = documentBuilderFactory.newDocumentBuilder()
        val document = documentBuilder.newDocument()
        val root = document.createElement("resources")
        document.appendChild(root)
        XmlUtils.asList(nList).map {
            Pair(async {
                apiService.translate(it.firstChild.textContent, "en", lang)
            }, it)
        }
            .map { Pair(it.second, it.first.await()) }
            .map {
                val strings = document.createElement("string")
                strings.setAttribute("name", it.first.attributes.getNamedItem("name").textContent)
                strings.appendChild(document.createTextNode(it.second.data.translations[0].translatedText ?: ""))
                root.appendChild(strings)
                println(it.second.data.translations[0].translatedText ?: "")
            }.saveFile(document, path)

    }

}

private fun checkFileForExists(lang: String) {
    val file = File("/home/android-dev/translation/strings_$lang.xml")
    if (file.exists()) {
        file.delete()
    }
}

private suspend fun Any.saveFile(document: Document?, path: Path) {
    val transformerFactory = TransformerFactory.newInstance()
    val transformer = transformerFactory.newTransformer()
    val domSource = DOMSource(document)
    val streamResult = StreamResult(File(path.toString()))
    transformer.transform(domSource, streamResult)
}