package com.diploma.stats.global.extension

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.diploma.stats.global.base.BaseFragment

//val BaseFragment.viewContainer: View get() = (activity as BaseActivity).fragmentContainer!!

val BaseFragment.appContext: Context get() = activity?.applicationContext!!

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun ImageView.setImageUrl(url: String?) =
        Glide.with(this.context.applicationContext)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)

fun ImageView.setCircleImageUrl(url: String?) =
        Glide.with(this.context.applicationContext)
                .load(url)
                .optionalCircleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)

fun ImageView.setImageUrl(uri: Uri?) =
        Glide.with(this.context.applicationContext)
                .load(uri)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)

fun ImageView.setCircleImageUrl(uri: Uri?) =
        Glide.with(this.context.applicationContext)
                .load(uri)
                .optionalCircleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
        LayoutInflater.from(context).inflate(layoutRes, this, false)

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
        beginTransaction().func().commit()

fun BaseFragment.close() = fragmentManager?.popBackStack()
