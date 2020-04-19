package ru.filtrabu.sharex.core

import arrow.core.NonEmptyList
import arrow.core.Option
import org.jsoup.Jsoup.parse
import org.jsoup.nodes.Document

object ImageFromUrlExtractor {

    /**
     * Accepts a HTML, and extracts all JPG files contained in it,
     * returning their URLs, if present
     *
     * @return [NonEmptyList] of URLs if there are some JPEG links or sources, otherwise - [arrow.core.None]
     */
    fun extractJPG(inputHTML: String): Option<NonEmptyList<String>> {
        val parseResult: Document = parse(inputHTML)

        // TODO: try getElementsByAttributeValueContains
        val jpegFilter: (String) -> Boolean =
            { it.contains("jpg") || it.contains("jpeg") }
        val filterJpgReferences = parseResult.getElementsByAttribute("href")
            .eachAttr("href")
            .filter(jpegFilter)

        val filterJpgSources =
            parseResult.getElementsByTag("img")
                .eachAttr("src")
                .filter(jpegFilter)

        val jpegs = filterJpgReferences + filterJpgSources
        return arrow.core.Nel.fromList(jpegs)
    }
}
