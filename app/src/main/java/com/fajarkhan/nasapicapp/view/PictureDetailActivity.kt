package com.fajarkhan.nasapicapp.view

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fajarkhan.nasapicapp.R
import com.fajarkhan.nasapicapp.adapter.DetailsPagerAdapter
import com.fajarkhan.nasapicapp.repository.PictureRepositoryImpl
import com.fajarkhan.nasapicapp.utils.ViewPagerTransformation
import com.fajarkhan.nasapicapp.viewmodel.PictureViewModel
import com.fajarkhan.nasapicapp.viewmodel.PictureViewModelFactory
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_picture_details.*

class PictureDetailActivity : AppCompatActivity() {


    private val detailsAdapter: DetailsPagerAdapter by lazy {
        DetailsPagerAdapter()
    }

    private val clickedPosition: Int by lazy {
        intent.getIntExtra("position", 0)
    }

    private lateinit var pictureViewModel: PictureViewModel
    private lateinit var shared: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_details)

        viewModelSetUp()

        loadDataAndUpdateUI()

        showSwipePopup()
    }

    private fun showSwipePopup() {
        shared =
            getSharedPreferences(getString(R.string.first_open_shared_pref), Context.MODE_PRIVATE)

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.swipe_onborading_popup_layout)
        val yesBtn = dialog.findViewById(R.id.ok_button) as MaterialButton
        yesBtn.setOnClickListener {

            val edit = shared.edit()
            edit.putBoolean(getString(R.string.first_open), true)
            edit.apply()
            dialog.dismiss()
        }

        //to check weather user clicked popup before
        if (!shared.getBoolean(getString(R.string.first_open), false))
            dialog.show()
    }


    private fun loadDataAndUpdateUI() {
        //Get the data from the viewModel
        pictureViewModel.getPictureData()

        vpDetailScreen.adapter = detailsAdapter
        vpDetailScreen.setPageTransformer(ViewPagerTransformation())


        //Observing the data and applying the appropriate data condition
        pictureViewModel.nasaLiveData.observe(this, Observer {
            it?.let {
                detailsAdapter.swapData(it)
                //Set smooth scroll to false so that it doesn't animate when selected at certain position
                vpDetailScreen.setCurrentItem(clickedPosition, false)
            }
        })
    }

    private fun viewModelSetUp() {
        //Getting the instance of the Viewmodel
        pictureViewModel = ViewModelProvider(
            this,
            PictureViewModelFactory(
                PictureRepositoryImpl(this)
            )
        ).get(PictureViewModel::class.java)
    }
}
