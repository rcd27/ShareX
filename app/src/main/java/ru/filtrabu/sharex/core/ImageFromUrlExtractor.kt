package ru.filtrabu.sharex.core

import org.jsoup.Jsoup.parse
import org.jsoup.nodes.Document

object ImageFromUrlExtractor {

    /**
     * Accepts a HTML, and extracts all images contained in it,
     * returning their URLs
     */
    fun extract(inputHTML: String): List<String> {
        val parseResult: Document = parse(inputHTML)
        // TODO: implement
        return emptyList()
    }
}