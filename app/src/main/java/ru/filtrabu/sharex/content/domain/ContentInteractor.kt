package ru.filtrabu.sharex.content.domain

import io.reactivex.Single

object ContentInteractor {

    /** TODO
     * Если шарится урл, то это может быть ссылка:
     * - на картинку
     * - на html
     * - на видео
     * - на гифку
     * - ...
     *
     * Для начала проверить:
     * - HTML: если html, то распарсить весь контент: картинки, гифки, видео (opt: параграфы с текстом)
     * - JPG: венуть урл на картинку
     */
    fun onPlainTextReceived(data: String?): Single<String> = data?.let {
        Single.just("Any")
    } ?: Single.error(IllegalArgumentException("Input text is NULL"))
}
