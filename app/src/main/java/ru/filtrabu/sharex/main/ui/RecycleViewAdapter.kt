package ru.filtrabu.sharex.main.ui

import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

/**
 * Подойдёт для 99% всех [androidx.recyclerview.widget.RecyclerView]
 * Под капотом обвязка вокруг [androidx.recyclerview.widget.ListAdapter]
 *
 * see: https://github.com/sockeqwe/AdapterDelegates
 */

// TODO: для реализации анимаций при смене списка(сортировке), см. AsyncListDifferDelegationAdapter
class RecycleViewAdapter : ListDelegationAdapter<List<ViewObject>>(AdapterDelegatesManager()) {
    val delegatesManager: AdapterDelegatesManager<List<ViewObject>>
        get() = super.delegatesManager
}

/**
 * Маркер-интерфейс для передачи в делегат [RecycleViewAdapter].
 */
interface ViewObject
