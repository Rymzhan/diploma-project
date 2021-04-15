package com.thousand.bosch.views.main.presentation.game.main

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.thousand.bosch.R
import kotlinx.android.synthetic.main.fragment_main_game.*


class MainGameFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backToMain.setOnClickListener {
            activity?.onBackPressed()
        }
        cardView7.setOnClickListener {

        }
        cardView8.setOnClickListener {

        }
        cardView9.setOnClickListener {

        }
        cardView10.setOnClickListener {
            cardView10.setCardBackgroundColor(ContextCompat.getColor(context!!,R.color.green))
            textViewCardView10.setTextColor(Color.WHITE)
        }
    }
}