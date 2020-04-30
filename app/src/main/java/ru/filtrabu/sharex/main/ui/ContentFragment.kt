package ru.filtrabu.sharex.main.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.Fragment
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_content.*
import kotlinx.android.synthetic.main.item_image.view.*
import ru.filtrabu.sharex.R

class ContentFragment : Fragment(R.layout.fragment_content) {

    data class ImageObject(val url: String) : ViewObject

    private val cd: CompositeDisposable = CompositeDisposable()

    private val shareListAdapter = RecycleViewAdapter()

    private val image = adapterDelegateLayoutContainer<ImageObject, ViewObject>(
        R.layout.item_image
    ) {
        bind {
            Picasso.get()
                // TODO: add placeholder, and placeholder for error
                .load(item.url)
                .error(R.drawable.ph_error)
                .placeholder(R.drawable.ph_sync)
                .fit()
                .into(containerView.imageView)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        shareListAdapter.delegatesManager
            .addDelegate(image)

        shareRecyclerView.apply {
            addItemDecoration(OuterRecyclerViewItemDecoration)
            this.adapter = this@ContentFragment.shareListAdapter
        }

        (requireActivity() as MainActivity).intentSubject
            .subscribe(
                { intent ->
                    when (intent.action) {
                        Intent.ACTION_SEND -> {
                            when {
                                intent.type == "text/plain" -> {
                                    intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                                        // FIXME: the url should be strictly acceptable by Picasso, need verification
                                        shareListAdapter.items =
                                            listOf(
                                                ImageObject(it),
                                                ImageObject(it),
                                                ImageObject(it)
                                            )
                                        shareListAdapter.notifyDataSetChanged()
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
                        /* TODO: implement
                        else -> showNothingToShare()
                         */
                    }
                },
                { error ->
                    // TODO("Handle errors")
                }
            ).let { cd.add(it) }
    }
}
