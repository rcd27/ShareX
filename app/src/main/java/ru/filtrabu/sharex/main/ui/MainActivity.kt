package ru.filtrabu.sharex.main.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_main.*
import ru.filtrabu.sharex.R

class MainActivity : Activity() {

    // TODO: test (maybe BehaviorRelay is better)
    private val intentSubject: BehaviorSubject<Intent> = BehaviorSubject.create()

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        intent?.let {
            intentSubject.onNext(it)
        } ?: run {
            showNothingToShare()
        }

        intentSubject.subscribe({ intent ->
            when (intent.action) {
                Intent.ACTION_SEND -> {
                    when {
                        intent.type == "text/plain" -> {
                            intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                                debugTextView.text = it
                            }
                        }
                        intent.type?.startsWith("image") == true -> {
                            (intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri)?.let {
                                TODO("Update UI to reflect image being shared")
                            }
                        }
                        else -> {
                            debugTextView.text = "Unsupported Intent type: ${intent.type}"
                        }
                    }
                }
                Intent.ACTION_SEND_MULTIPLE -> {
                    TODO("Handle multiple ")
                }
                else -> showNothingToShare()
            }
        }, { error ->
            debugTextView.text = "Error while rakafakamaka: ${error.message}"
        })
    }

    @SuppressLint("SetTextI18n")
    private fun showNothingToShare() {
        debugTextView.text = "Nothing to share..."
    }

    override fun onDestroy() {
        intentSubject.onComplete()
        super.onDestroy()
    }
}
