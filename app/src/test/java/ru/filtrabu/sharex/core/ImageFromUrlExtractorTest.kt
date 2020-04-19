package ru.filtrabu.sharex.core

import arrow.core.NonEmptyList
import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import com.google.common.truth.Truth.assertThat
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors
import org.junit.Test
import ru.filtrabu.sharex.core.ImageFromUrlExtractor.extractJPG

class ImageFromUrlExtractorTest {

    @Test
    fun extract() {
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
}
