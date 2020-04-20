package ru.filtrabu.sharex.core

import arrow.core.NonEmptyList
import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import com.google.common.truth.Truth.assertThat
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors
import org.http4k.client.DualSyncAsyncHttpHandler
import org.http4k.client.OkHttp
import org.http4k.core.Method
import org.http4k.core.Request
import org.junit.Test
import ru.filtrabu.sharex.core.HTMLContentExtractor.extractJPG

class HTMLContentExtractorTest {

    @Test
    fun `JPEG extracting test`() {
        val testHtml: String =
            Files.lines(Paths.get("../dataset/vk-wall-post/photo-98877741_457248805.html"))
                .collect(Collectors.joining())

        when (val extractedResult: Option<NonEmptyList<String>> = extractJPG(testHtml)) {
            is Some -> {
                val expectedImageUrlList = listOf(
                    "https://sun9-58.userapi.com/c857536/v857536676/1d01c4/bJ2JJ_IBRv4.jpg",
                    "https://sun9-47.userapi.com/c857536/v857536676/1d01a9/hRg73LOpZdI.jpg",
                    "https://sun9-26.userapi.com/c855524/v855524371/d94ee/TQ-hV6zubK0.jpg?ava=1",
                    "https://sun9-62.userapi.com/c848624/v848624219/8deaf/K7IEFMxf3_o.jpg?ava=1",
                    "https://sun1-21.userapi.com/EMxGzDzOiQm06_KKW7REe9K8prki6X7GqA7_9g/9HdsScp-UJg.jpg?ava=1",
                    "https://sun9-41.userapi.com/c628016/v628016682/dfcd/mglK7-xKddc.jpg?ava=1"
                )

                assertThat(extractedResult.t.all).containsExactlyElementsIn(expectedImageUrlList)
            }
            is None -> {
                throw IllegalArgumentException("Extracted list must not be empty")
            }
        }
    }

    @Test
    fun `GIF extracting test`() {
        val urlToPikabuPostWithGIF = "https://pikabu.ru/story/posadka_derevev_7381394"
        val request = Request(Method.GET, urlToPikabuPostWithGIF)
        val httpHandler: DualSyncAsyncHttpHandler = OkHttp.invoke()
        val html = httpHandler.invoke(request).body.toString()

        when (val result: Option<NonEmptyList<String>> = HTMLContentExtractor.extractGIF(html)) {
            is Some -> {
                val expected = listOf("")
                assertThat(result.t.all).containsExactlyElementsIn(expected)
            }
            is None -> {
                throw IllegalArgumentException("Extracted list must be present")
            }
        }
    }
}
