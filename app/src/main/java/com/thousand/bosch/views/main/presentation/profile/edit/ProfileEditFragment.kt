package com.thousand.bosch.views.main.presentation.profile.edit

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.thousand.bosch.R
import com.thousand.bosch.global.extension.replaceFragment
import com.thousand.bosch.global.extension.setCircleImageUrl
import com.thousand.bosch.global.utils.LocalStorage
import com.thousand.bosch.model.auth.login.User
import com.thousand.bosch.views.auth.di.AuthScope
import com.thousand.bosch.views.main.presentation.profile.friends.details.FriendsDetailsFragment
import com.thousand.bosch.views.main.presentation.profile.main.ProfileFragment
import com.thousand.bosch.global.base.BaseFragment
import com.thousand.bosch.model.list.Cities
import com.thousand.bosch.model.list.Countries
import kotlinx.android.synthetic.main.fragment_profile_edit.*
import kotlinx.android.synthetic.main.fragment_profile_edit.backToMain
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import permissions.dispatcher.NeedsPermission


class ProfileEditFragment() : BaseFragment(), ProfileEditView {
    override val layoutRes: Int = R.layout.fragment_profile_edit
    var password: String? = null

    private var workPlace: String = ""

    private var countryId: Int? = null
    private var cityId: Int? = null

    private val firstList: MutableList<Int> = mutableListOf()
    private val secondList: MutableList<String> = mutableListOf()
    private val tempList1: MutableList<Int> = mutableListOf()
    private val tempList2: MutableList<String> = mutableListOf()

    companion object {
        private const val PICK_IMAGE_PASSPORT = 1
        val TAG = "ProfileEditFragment"
        const val userInfo = "user"
        fun newInstance(user: User): ProfileEditFragment {
            val fragment = ProfileEditFragment()
            val args = Bundle()
            args.putParcelable(userInfo, user)
            fragment.arguments = args
            return fragment
        }
    }

    var image_uri: Uri? = null
    private val IMAGE_CAPTURE_CODE = 1001

    @InjectPresenter
    lateinit var presenter: ProfileEditPresenter

    @ProvidePresenter
    fun providePresenter(): ProfileEditPresenter {
        return getKoin().getOrCreateScope(
            AuthScope.PROFILE_EDIT_SCOPE,
            named(AuthScope.PROFILE_EDIT_SCOPE)
        ).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(AuthScope.PROFILE_EDIT_SCOPE)?.close()

        super.onDestroy()


    }

    override fun setUp(savedInstanceState: Bundle?) {
        onClickListener()
        bindView()
    }

    private fun onClickListener() {
        editAvatarButton.setSafeOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (activity?.checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED ||
                    context?.let { it1 ->
                        ContextCompat.checkSelfPermission(
                            it1,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    }
                    == PackageManager.PERMISSION_DENIED
                ) {
                    //permission was not enabled
                    val permission = arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    //show popup to request permission
                    requestPermissions(permission, 1000)
                } else {
                    dialog()
                }
            } else {
                dialog()
            }
        }
        backToMain.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        saveEditButton.setSafeOnClickListener {
            ifPasswordEmpty()

            if (loginEditText.text.toString().isEmpty()) {
                loginEditText.error = "Заполните это поле"
            }
            if (nameEditText.text.toString().isEmpty()) {
                nameEditText.error = "Заполните это поле"
            }
            if (surnameEditText.text.toString().isEmpty()) {
                surnameEditText.error = "Заполните это поле"
            }
            if (editOrgNameText.text.toString().isEmpty()) {
                editOrgNameText.error = "Заполните это поле"
            }
            if (workPlace == "") {
                Toast.makeText(requireContext(), "Выберите место работы!", Toast.LENGTH_SHORT)
                    .show()
            }
            if (countryId == null) {
                Toast.makeText(requireContext(), "Выберите страну!", Toast.LENGTH_SHORT).show()
            }
            if (cityId == null) {
                Toast.makeText(requireContext(), "Выберите город!", Toast.LENGTH_SHORT).show()
            }
            showProgress(context!!)
            presenter.updateProfile(
                loginEditText.text.toString(),
                nameEditText.text.toString(),
                surnameEditText.text.toString(),
                editOrgNameText.text.toString(),
                workPlace,
                countryId!!,
                cityId!!,
                password
            )
        }

    }

    private fun ifPasswordEmpty() {
        if ((editNewPasswordText.text.toString().isNotEmpty() && editNewPasswordConfirmText.text.toString().isEmpty()) ||
                (editNewPasswordText.text.toString().isEmpty() && editNewPasswordConfirmText.text.toString().isNotEmpty())){
            Toast.makeText(requireContext(),"Заполните пароль!", Toast.LENGTH_SHORT).show()
        }else if((editNewPasswordText.text.toString().isNotEmpty() && editNewPasswordConfirmText.text.toString().isNotEmpty())){
            if (editNewPasswordConfirmText.text.toString().isEmpty()){
                Toast.makeText(requireContext(),"Подтвердите пароль!", Toast.LENGTH_SHORT).show()
            }else if (editNewPasswordText.text.toString()!=editNewPasswordConfirmText.text.toString()){
                Toast.makeText(requireContext(),"Пароли не совпадают!", Toast.LENGTH_SHORT).show()
            } else if (editNewPasswordConfirmText.text.toString() == editNewPasswordText.text.toString()){
                password = editNewPasswordText.text.toString()
            }
        }
    }

    private fun bindView() {
        val currentUser: User = arguments?.getParcelable(FriendsDetailsFragment.userInfo)!!
        presenter.getCountries()
        presenter.getCities(currentUser.country_id)
        bindWorkplaceSpinner(currentUser.workplace)
        loginEditText.setText(currentUser.login)
        nameEditText.setText(currentUser.first_name)
        surnameEditText.setText(currentUser.last_name)
        editOrgNameText.setText(currentUser.organization)
        oldPasswordText.text = LocalStorage.getPassword()
        if (currentUser.image != null) {
            editProfileAvatar.setCircleImageUrl(currentUser.image)
        }
        editProfileAvatar.visibility = View.VISIBLE
    }


    private fun bindWorkplaceSpinner(value: String) {
        workPlace = value
        val spinner1: Spinner = workplaceSpinner1
        val tempList: MutableList<String> = mutableListOf()
        tempList.add("Выберите место работы")
        tempList.add("Торгующая о.")
        tempList.add("Строительная к.")
        val adapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.spinner_item,
            tempList
        )

        Log.d("WorkPlaceCheck", value)

        spinner1.adapter = adapter

        for (i in tempList.indices) {
            if (tempList[i] == value) {
                spinner1.setSelection(i)
                break
            }
        }

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    workPlace = tempList[position]
                } else if (position == 0) {
                    workPlace = ""
                }
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //called when user presses ALLOW or DENY from Permission Request Popup
        when (requestCode) {
            1000 -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup was granted
                    dialog()
                } else {
                    //permission from popup was denied
                    context?.apply {
                        Toast.makeText(this, "Доступ запрещён!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_IMAGE_PASSPORT -> {
                    data?.let { intent ->
                        intent.data?.apply {
                            editProfileAvatar.setCircleImageUrl(this)
                            Glide.with(this@ProfileEditFragment)
                                .asBitmap()
                                .load(this)
                                .into(object : CustomTarget<Bitmap>() {
                                    override fun onLoadCleared(placeholder: Drawable?) {}

                                    override fun onResourceReady(
                                        resource: Bitmap,
                                        transition: Transition<in Bitmap>?
                                    ) {
                                        presenter.setPhoto(resource)
                                    }
                                })
                        }
                    }
                }
                1001 -> {
                    if (resultCode == Activity.RESULT_OK) {
                        editProfileAvatar.setCircleImageUrl(image_uri)
                        Glide.with(this@ProfileEditFragment)
                            .asBitmap()
                            .load(image_uri)
                            .into(object : CustomTarget<Bitmap>() {
                                override fun onLoadCleared(placeholder: Drawable?) {}

                                override fun onResourceReady(
                                    resource: Bitmap,
                                    transition: Transition<in Bitmap>?
                                ) {
                                    presenter.setPhoto(resource)
                                }
                            })
                    }
                }

            }
        }
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun getPhotoFromGallery(code: Int) {
        context?.let {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, code)
        }
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri =
            activity?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    private fun dialog() {
        val alertDB = AlertDialog.Builder(this.activity!!)
        val view = this.layoutInflater.inflate(R.layout.dialog_choose, null, false)
        alertDB.setView(view)

        val alertDialog = alertDB.create()
        alertDialog.setCancelable(true)

        view.findViewById<Button>(R.id.btn1).setOnClickListener {
            openCamera()
            alertDialog.dismiss()
        }
        view.findViewById<Button>(R.id.btn2).setOnClickListener {
            getPhotoFromGallery(PICK_IMAGE_PASSPORT)
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    override fun openProfileFrag() {
        removeProgress()
        showProgress(requireContext())
        activity?.supportFragmentManager?.replaceFragment(
            R.id.Container,
            ProfileFragment.newInstance(),
            ProfileFragment.TAG
        )
        if (::callback.isInitialized) {
            callback.onChanged()
            Log.i("onDestroy", "isInitialized")
        } else {
            Log.i("onDestroy", " not isInitialized")
        }
    }

    override fun showError(mes: String) {
        removeProgress()
        showMessage(mes, requireView())
    }

    override fun bindCountry(countries: Countries) {
        val currentUser: User = arguments?.getParcelable(FriendsDetailsFragment.userInfo)!!
        countryId = currentUser.country_id
        firstList.clear()
        secondList.clear()
        val spinner: Spinner = countrySpinner1
        secondList.add("Выберите страну")
        for (i in countries.indices) {
            firstList.add(countries[i].id)
            secondList.add(countries[i].title)
        }

        val adapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.spinner_item,
            secondList
        )
        spinner.adapter = adapter

        for (i in firstList.indices) {
            if (currentUser.country_id == firstList[i]) {
                spinner.setSelection(i+1)
                break
            }
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    countryId = firstList[position - 1]
                    presenter.getCities(countryId!!)
                }
            }
        }

    }

    override fun bindCity(cities: Cities) {
        val currentUser: User = arguments?.getParcelable(FriendsDetailsFragment.userInfo)!!
        cityId = currentUser.city_id
        val spinner: Spinner = citySpinner1
        tempList1.clear()
        tempList2.clear()
        tempList2.add("Выберите город")
        for (i in cities.indices) {
            tempList1.add(cities[i].id)
            tempList2.add(cities[i].title)
        }
        val adapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.spinner_item,
            tempList2
        )
        spinner.adapter = adapter

        for (i in tempList1.indices) {
            if (currentUser.city_id == tempList1[i]) {
                spinner.setSelection(i+1)
                break
            }
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    cityId = tempList1[position - 1]
                }
            }
        }
    }

    private lateinit var callback: Callback


    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    interface Callback {
        fun onChanged()
    }
}