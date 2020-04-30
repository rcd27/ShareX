package ru.filtrabu.sharex.main.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_content.*
import kotlinx.android.synthetic.main.item_image.view.*
import ru.filtrabu.sharex.R

class ContentFragment : Fragment(R.layout.fragment_content) {

    data class ImageObject(val url: String) : ViewObject

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
    }
}
