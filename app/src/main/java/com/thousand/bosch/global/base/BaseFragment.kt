package com.thousand.bosch.global.base

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.arellomobile.mvp.MvpAppCompatFragment
import com.thousand.bosch.R
import com.thousand.bosch.global.extension.appContext
import com.thousand.bosch.global.utils.SafeClickListener
import com.thousand.bosch.views.main.presentation.activity.MainActivity
import com.google.android.material.snackbar.Snackbar

@Suppress("DEPRECATION")
abstract class BaseFragment : MvpAppCompatFragment(), BaseMvpView {

    companion object {
        private const val PROGRESS_TAG = "fragment_progress"
        private var pd: android.app.ProgressDialog? = null
    }

    abstract val layoutRes: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(layoutRes, container, false)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp(savedInstanceState)
        setStatusBar()
    }

    override fun onResume() {
        super.onResume()
        //setStatusBar()
    }

    protected fun refreshFragment(savedInstanceState: Bundle?) {
        setUp(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    protected fun setStatusBar(){
        val window: Window = activity!!.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = view!!.getBackgroundColor()
        Log.d("viewCheck", view.toString())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    fun View.getBackgroundColor() = (background as? ColorDrawable?)?.color ?: Color.TRANSPARENT

    protected fun showProgress(context: Context) {
        try {
            if (pd == null) {
                pd = android.app.ProgressDialog(context)
                pd!!.setMessage("Подождите")
                pd!!.setCancelable(false)
                pd!!.show()
            }
            if (!pd!!.isShowing()) {
                pd!!.show()
            }
        } catch (ie: IllegalArgumentException) {
            ie.printStackTrace()
        } catch (re: RuntimeException) {
            re.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }



    fun removeProgress() {
        try {
            if (pd != null) {
                if (pd!!.isShowing()) {
                    pd!!.dismiss()
                    pd = null
                }
            }
        } catch (ie: java.lang.IllegalArgumentException) {
            ie.printStackTrace()
        } catch (re: java.lang.RuntimeException) {
            re.printStackTrace()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    override fun closeKeyboard(activity: FragmentActivity?) {
        activity?.apply {
            currentFocus?.apply {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(windowToken, 0)
            }
        }
    }

    protected fun showProgressDialog(progress: Boolean) {
        if (!isAdded) return

        val fragment = childFragmentManager.findFragmentByTag(PROGRESS_TAG)
        if (fragment != null && !progress) {
            (fragment as ProgressDialog).dismissAllowingStateLoss()
            childFragmentManager.executePendingTransactions()
        } else if (fragment == null && progress) {
            ProgressDialog().show(
                childFragmentManager,
                PROGRESS_TAG
            )
            childFragmentManager.executePendingTransactions()
        }
    }

    protected fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
        val safeClickListener = SafeClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeClickListener)
    }

    abstract fun setUp(savedInstanceState: Bundle?)

    internal fun showProgressBar(show: Boolean) {
        if (activity is MainActivity)
            (activity as MainActivity).showProgressBar(show)
    }

    internal fun showMessage(@StringRes message: Int, view: View) =
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()

    internal fun showMessage(message: String, view: View) =
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()

    internal fun showMessageWithAction(
        @StringRes message: Int,
        @StringRes actionText: Int,
        view: View,
        action: () -> Any
    ) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(actionText) { action.invoke() }
        snackBar.setActionTextColor(
            ContextCompat.getColor(
                appContext,
                R.color.colorAccent
            )
        )
        snackBar.show()
    }
}