package com.thousand.bosch.views.main.presentation.game.category.first

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.thousand.bosch.R
import com.thousand.bosch.global.utils.LocalStorage
import com.thousand.bosch.model.main.game.categories.RandomCategories
import com.thousand.bosch.views.auth.di.AuthScope
import com.thousand.bosch.global.base.BaseFragment
import com.thousand.bosch.views.adapters.CategoryAdapter
import kotlinx.android.synthetic.main.fragment_category_select.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named


class CategorySelectFragment() : BaseFragment(),
    CategorySelectView {

    override val layoutRes: Int = R.layout.fragment_category_select

    @InjectPresenter
    lateinit var presenter: CategorySelectPresenter

    @ProvidePresenter
    fun providePresenter(): CategorySelectPresenter {
        getKoin().getScopeOrNull(AuthScope.CATEGORIES_SELECT)?.close()
        return getKoin().getOrCreateScope(
            AuthScope.CATEGORIES_SELECT,
            named(AuthScope.CATEGORIES_SELECT)
        ).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(AuthScope.CATEGORIES_SELECT)?.close()
        super.onDestroy()
    }

    companion object {
        val TAG = "CategorySelectFragment"
        const val guestTitle = "guestName"
        const val guestImage = "guestImage"
        const val gameId = "gameId"
        const val friendId = "friendId"
        const val curRound = "curRound"
        fun newInstance(
            gameId1: Int?,
            guestName1: String?,
            guestImage1: String?,
            guestId: Int?,
            currentR: Int
        ): CategorySelectFragment {
            val fragment =
                CategorySelectFragment()
            val args = Bundle()
            args.putString(guestTitle, guestName1)
            args.putString(guestImage, guestImage1)
            args.putInt(gameId, gameId1!!)
            args.putInt(friendId, guestId!!)
            args.putInt(curRound, currentR)
            fragment.arguments = args
            return fragment
        }
    }

    override fun setUp(savedInstanceState: Bundle?) {
        showProgress(context!!)
        bindView()
        presenter.randomCategories()
    }


    private fun bindView() {
        if (LocalStorage.getImage() != "1") {
            Glide.with(context!!)
                .load(LocalStorage.getImage())
                .circleCrop()
                .into(creatorAvatar)
        }
        //creatorName.text = LocalStorage.getName() + " " + LocalStorage.getSurname()
        creatorName.text = LocalStorage.getLogin()
        if (arguments?.getString(guestImage) != null) {
            Glide.with(context!!)
                .load(arguments?.getString(guestImage))
                .circleCrop()
                .into(guestAvatar)
        }
        guestName.text = arguments?.getString(guestTitle)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun setCategories(response: RandomCategories) {
        val adapter = CategoryAdapter(
            { showProgress(requireContext()) },
            arguments?.getInt(gameId),
            arguments?.getInt(friendId)!!,
            requireActivity(), requireContext(),arguments?.getInt(curRound)!!
        )
        adapter.setDataList(response)
        adapter.notifyDataSetChanged()
        val numberOfColumns = 2
        val gridLayoutManager =
            GridLayoutManager(requireContext(), numberOfColumns, RecyclerView.VERTICAL, false)
        categoryRecycler.layoutManager = gridLayoutManager
        categoryRecycler.setHasFixedSize(true)
        categoryRecycler.adapter = adapter
        removeProgress()
    }

}