package ru.filtrabu.sharex.main.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import io.reactivex.subjects.BehaviorSubject
import ru.filtrabu.sharex.R

class MainActivity : FragmentActivity() {

    // TODO: test (maybe BehaviorRelay is better)
    val intentSubject: BehaviorSubject<Intent> = BehaviorSubject.create()

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        intent?.let {
            intentSubject.onNext(it)
        } ?: run {
            showNothingToShare()
        }
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
