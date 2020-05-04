package ru.filtrabu.sharex.content.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_content.*
import kotlinx.android.synthetic.main.item_image.view.*
import ru.filtrabu.sharex.R
import ru.filtrabu.sharex.main.ui.MainActivity
import ru.filtrabu.sharex.main.ui.OuterRecyclerViewItemDecoration
import ru.filtrabu.sharex.main.ui.RecycleViewAdapter
import ru.filtrabu.sharex.main.ui.ViewObject

class ContentFragment : Fragment(R.layout.fragment_content) {

    data class ImageObject(val url: String) : ViewObject

    private val cd: CompositeDisposable = CompositeDisposable()

    private val viewModel: ContentViewModel by lazy {
        ViewModelProvider(this)
            .get(ContentViewModel::class.java)
    }

    private val shareListAdapter = RecycleViewAdapter()

    private val image = adapterDelegateLayoutContainer<ImageObject, ViewObject>(
        R.layout.item_image
    ) {
        bind {
            Picasso.get()
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
            .observeOn(Schedulers.computation())
            .subscribeWith(viewModel.intentObserver)
            .let { cd.add(it) }

        viewModel.state
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { state ->
                    // TODO: handle
                },
                { error ->
                    // TODO: handle error
                }
            )
            .let { cd.add(it) }
    }

    override fun onDestroy() {
        cd.clear()
        super.onDestroy()
    }
}
