package ru.filtrabu.sharex.content.ui

import android.content.Intent
import android.net.Uri
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import io.reactivex.observers.DisposableObserver
import io.reactivex.subjects.BehaviorSubject
import ru.filtrabu.sharex.content.domain.ContentInteractor

class ContentViewModel : ViewModel() {

    val state: BehaviorSubject<ContentViewState> = BehaviorSubject.create()

    private val contentInteractor: ContentInteractor = ContentInteractor

    val intentObserver = object : DisposableObserver<Intent>() {
        override fun onComplete() {}

        override fun onNext(intent: Intent) {
            when (intent.action) {
                Intent.ACTION_SEND -> {
                    when {
                        intent.type == "text/plain" -> {
                            val data: String? = intent.getStringExtra(Intent.EXTRA_TEXT)

                            contentInteractor.onPlainTextReceived(data)
                                .subscribe(
                                    { state.onNext(ContentExtractedFromSharedText(it)) },
                                    {
                                        state.onNext(
                                            ErrorHandlingSharedText(
                                                it.message ?: "No error message"
                                            )
                                        )
                                    }
                                )
                        }
                        intent.type?.startsWith("image") == true -> {
                            (intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri)?.let {
                                // TODO("Update UI to reflect image being shared")
                            }
                        }
                        else -> {
                            // TODO: handle
                        }
                    }
                }
                Intent.ACTION_SEND_MULTIPLE -> {
                    // TODO("Handle multiple ")
                }
                else -> state.onNext(NoContentToShow)
            }
        }

        override fun onError(e: Throwable) {
            state.onError(e)
        }
    }
}

sealed class ContentViewState
data class ErrorHandlingSharedText(val errorMessage: String) : ContentViewState()
data class ContentExtractedFromSharedText(val content: Any) : ContentViewState()
object NoContentToShow : ContentViewState()
