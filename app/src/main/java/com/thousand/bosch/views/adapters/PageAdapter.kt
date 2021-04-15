package com.thousand.bosch.views.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.thousand.bosch.views.main.presentation.game.results.main.country.CountryListFragment
import com.thousand.bosch.views.main.presentation.game.results.main.friends.FriendsTopListFragment
import com.thousand.bosch.views.main.presentation.game.results.main.top.TopListFragment

class PageAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                TopListFragment()
            }
            1 -> {
                FriendsTopListFragment()
            }
            2 -> {
                CountryListFragment()
            }
            else -> {
                TopListFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> {return "Общий рейтинг"}
            1 -> {return "Друзья"}
            2 -> {return "По странам"}
        }
        return super.getPageTitle(position)
    }

    override fun getCount(): Int {
        return 3
    }

}