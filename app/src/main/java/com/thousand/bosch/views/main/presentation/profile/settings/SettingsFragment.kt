package com.thousand.bosch.views.main.presentation.profile.settings

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide

import com.thousand.bosch.R
import com.thousand.bosch.global.utils.LocalStorage
import com.thousand.bosch.model.auth.login.User
import com.thousand.bosch.views.auth.di.AuthScope
import com.thousand.bosch.views.main.presentation.activity.MainActivity
import com.thousand.bosch.views.main.presentation.profile.black_list.BlackListFragment
import com.thousand.bosch.views.main.presentation.profile.block_user.BlockUserFragment
import com.thousand.bosch.views.main.presentation.profile.edit.ProfileEditFragment
import com.thousand.bosch.global.base.BaseFragment
import com.thousand.bosch.global.extension.addFragmentWithBackStack
import com.thousand.bosch.model.web_view.Data
import com.thousand.bosch.views.main.presentation.help.suggest_question.SuggestQuestionFragment
import com.thousand.bosch.views.main.presentation.profile.main.ProfileFragment
import com.thousand.bosch.views.main.presentation.web_view.WebViewFragment
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named


class SettingsFragment() : BaseFragment(), SettingsView {
    override val layoutRes: Int = R.layout.fragment_settings

    private var user: User? = null

    companion object {

        val TAG = "SettingsFragment"

        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }

    }

    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    @ProvidePresenter
    fun providePresenter(): SettingsPresenter {
        return getKoin().getOrCreateScope(
            AuthScope.SETTINGS_SCOPE,
            named(AuthScope.SETTINGS_SCOPE)
        ).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(AuthScope.SETTINGS_SCOPE)?.close()
        super.onDestroy()
    }

    override fun setUp(savedInstanceState: Bundle?) {
        switch1.isChecked = LocalStorage.getPush()
        presenter.getUserInfo()
        onClick()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onResume() {
        super.onResume()
        setStatusBar()
    }

    private fun onClick() {

        switch1.setOnClickListener {
            if(switch1.isChecked){
                LocalStorage.setPush(true)
                LocalStorage.setSound(true)
            }else{
                LocalStorage.setPush(false)
                LocalStorage.setSound(false)
            }
        }

        fromSettingsToProfile.setOnClickListener {
            activity?.onBackPressed()
        }

        blockUserButton.setSafeOnClickListener {
            activity?.supportFragmentManager?.addFragmentWithBackStack(
                R.id.settings_page,
                BlockUserFragment.newInstance(),
                BlockUserFragment.TAG
            )
        }

        blackListButton.setSafeOnClickListener {
            activity?.supportFragmentManager?.addFragmentWithBackStack(
                R.id.settings_page,
                BlackListFragment.newInstance(),
                BlackListFragment.TAG
            )
        }

        inviteFriendButton.setSafeOnClickListener {
            val myIntent = Intent(Intent.ACTION_SEND)
            myIntent.type = "text/plain"
            val shareBody = LocalStorage.getInvite()
            myIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(myIntent, "Поделиться с ссылкой"))
        }

        askQuestionButton.setSafeOnClickListener {
            requireActivity().supportFragmentManager.addFragmentWithBackStack(
                R.id.Container,
                SuggestQuestionFragment.newInstance(),
                SuggestQuestionFragment.TAG
            )
        }

        editProfileSettingsButton.setSafeOnClickListener {
            val fragment = ProfileEditFragment.newInstance(user!!)
            fragment.setCallback(ProfileFragment.newInstance())
            Log.i("onDestroy", "is init hope")
            if (fragment != null) {
                activity?.supportFragmentManager?.addFragmentWithBackStack(
                    R.id.Container,
                    fragment,
                    ProfileEditFragment.TAG
                )
            }

        }

        aboutAppButton.setSafeOnClickListener {
            presenter.getWebView()
        }


        logoutButton.setSafeOnClickListener {
            dialog()
        }

        deleteButton.setSafeOnClickListener {
            showDeleteDialog()
        }

        if (!LocalStorage.getImage().equals("1")) {
            Glide.with(context!!)
                .load(LocalStorage.getImage())
                .circleCrop()
                .into(settingsAvatar)
        }
        settingsAvatar.visibility = View.VISIBLE
        removeProgress()
    }

    override fun bindWebView(data: List<Data>) {
        showProgressBar(true)
        requireActivity().supportFragmentManager.addFragmentWithBackStack(
            R.id.Container,
            WebViewFragment.newInstance(data[5]),
            WebViewFragment.TAG
        )
    }

    fun showDeleteDialog() {
        val builder = AlertDialog.Builder(context)

        builder.setTitle("Удалить акаунт?")
        builder.setMessage("Вы действительно хотите удалить акаунт?")

        builder.setPositiveButton("Да") { dialog, _ ->

            showProgressDialog(true)
            presenter.deleteAccount()
            dialog.dismiss()
        }

        builder.setNegativeButton("Нет", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })

        val alert = builder.create()
        alert.show()
    }

    fun dialog() {
        val builder = AlertDialog.Builder(context)

        builder.setTitle("Выход")
        builder.setMessage("Вы уверены что хотите выйти?")

        builder.setPositiveButton("Да") { dialog, _ ->

            showProgressDialog(true)
            presenter.log_out()
            dialog.dismiss()
        }

        builder.setNegativeButton("Нет", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })

        val alert = builder.create()
        alert.show()
    }

    override fun bindUserInfo(user: User) {
        this.user = user
    }

    override fun logOut() {
        showProgressDialog(false)
        LocalStorage.deleteStorageDATA()
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK
                    or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    or Intent.FLAG_ACTIVITY_CLEAR_TASK
        )
        startActivity(intent)
    }

}
