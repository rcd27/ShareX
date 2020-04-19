package ru.filtrabu.sharex.core

import org.jsoup.Jsoup.parse
import org.jsoup.nodes.Document

object ImageFromUrlExtractor {

    /**
     * Accepts a HTML, and extracts all images contained in it,
     * returning their URLs
     */
    fun extractJPG(inputHTML: String): List<String> {
        val parseResult: Document = parse(inputHTML)

        // TODO: try getElementsByAttributeValueContains
        val filterJpgReferences = parseResult.getElementsByAttribute("href")
            .eachAttr("href")
            .filter { it.contains("jpg") }

        val filterJpgSources =
            parseResult.getElementsByTag("img")
                .eachAttr("src")
                .filter { it.contains("jpg") }

        return filterJpgReferences + filterJpgSources
    }
}
