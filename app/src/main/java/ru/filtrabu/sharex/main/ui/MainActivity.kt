package ru.filtrabu.sharex.main.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.FragmentActivity
import io.reactivex.subjects.BehaviorSubject
import ru.filtrabu.sharex.R

class MainActivity : FragmentActivity() {

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
                                // FIXME: the url should be strictly acceptable by Picasso, need verification
                                /*
                                shareListAdapter.items =
                                    listOf(ImageObject(it), ImageObject(it), ImageObject(it))
                                shareListAdapter.notifyDataSetChanged()
                                 */
                            }
                        }
                        intent.type?.startsWith("image") == true -> {
                            (intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri)?.let {
                                // TODO("Update UI to reflect image being shared")
                            }
                        }
                        else -> {
                        }
                    }
                }
                Intent.ACTION_SEND_MULTIPLE -> {
                    // TODO("Handle multiple ")
                }
                else -> showNothingToShare()
            }
        }, { error ->
            // TODO("Handle errors")
        })
    }

    @SuppressLint("SetTextI18n")
    private fun showNothingToShare() {
        // TODO("Handle nothing to share")
    }

    override fun onDestroy() {
        intentSubject.onComplete()
        super.onDestroy()
    }
}
