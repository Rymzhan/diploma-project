package com.diploma.stats.global.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.MvpAppCompatDialogFragment
import com.diploma.stats.R
import com.google.android.material.snackbar.Snackbar

abstract class BaseDialogFragment : MvpAppCompatDialogFragment() {

    companion object {
        private const val PROGRESS_TAG = "fragment_progress"
    }

    abstract val layoutRes: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(layoutRes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp(savedInstanceState)
    }

    protected fun showProgressDialog(progress: Boolean) {
        if (!isAdded) return

        val fragment = childFragmentManager.findFragmentByTag(PROGRESS_TAG)
        if (fragment != null && !progress) {
            (fragment as ProgressDialog).dismissAllowingStateLoss()
            childFragmentManager.executePendingTransactions()
        } else if (fragment == null && progress) {
            ProgressDialog().show(childFragmentManager,
                PROGRESS_TAG
            )
            childFragmentManager.executePendingTransactions()
        }
    }

    abstract fun setUp(savedInstanceState: Bundle?)

    internal fun showMessage(@StringRes message: Int, view: View) =
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()

    internal fun showMessage(message: String,  view: View) =
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()

    internal fun showMessageWithAction(@StringRes message: Int, @StringRes actionText: Int,  view: View, action: () -> Any) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(actionText) { action.invoke() }
        context?.let {
            snackBar.setActionTextColor(
                ContextCompat.getColor(it,
                    R.color.colorAccent
                ))
        }
        snackBar.show()
    }


}