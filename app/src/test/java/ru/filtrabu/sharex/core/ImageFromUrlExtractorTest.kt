package ru.filtrabu.sharex.core

import com.google.common.truth.Truth
import org.junit.Test
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

class ImageFromUrlExtractorTest {

    @Test
    fun extract() {
        val testHtml: String =
            Files.lines(Paths.get("../dataset/vk-wall-post/photo-98877741_457248805.html"))
                .collect(Collectors.joining())
        val resultImageUrlList: List<String> = ImageFromUrlExtractor.extract(testHtml)
        val expectedImageUrlList = listOf(
            "https://sun9-58.userapi.com/c857536/v857536676/1d01c4/bJ2JJ_IBRv4.jpg",
            "https://sun9-47.userapi.com/c857536/v857536676/1d01a9/hRg73LOpZdI.jpg"
        )

        Truth.assertThat(resultImageUrlList).containsExactly(expectedImageUrlList)
    }
}