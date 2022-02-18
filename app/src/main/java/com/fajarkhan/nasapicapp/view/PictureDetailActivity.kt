package com.fajarkhan.nasapicapp.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.widget.*
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
import kotlinx.android.synthetic.main.swipe_onborading_popup_layout.*

class PictureDetailActivity : AppCompatActivity() {


    private val detailsAdapter: DetailsPagerAdapter by lazy {
        DetailsPagerAdapter()
    }

    private val clickedPosition: Int by lazy {
        intent.getIntExtra("position", 0)
    }

    private lateinit var pictureViewModel: PictureViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_details)

        viewModelSetUp()

        loadDataAndUpdateUI()

        showSwipePopup()
    }

    private fun showSwipePopup(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.swipe_onborading_popup_layout)
        val yesBtn = dialog.findViewById(R.id.ok_button) as MaterialButton
        yesBtn.setOnClickListener {
            dialog.dismiss()
        }
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
