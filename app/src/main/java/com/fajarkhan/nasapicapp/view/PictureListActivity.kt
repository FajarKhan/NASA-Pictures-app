package com.fajarkhan.nasapicapp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fajarkhan.nasapicapp.R
import com.fajarkhan.nasapicapp.adapter.PictureListAdapter
import com.fajarkhan.nasapicapp.repository.PictureRepositoryImpl
import com.fajarkhan.nasapicapp.viewmodel.PictureViewModel
import com.fajarkhan.nasapicapp.viewmodel.PictureViewModelFactory
import kotlinx.android.synthetic.main.activity_picture_list.*
import kotlinx.android.synthetic.main.item_picture.*


class PictureListActivity : AppCompatActivity() {

    private val pictureListAdapter: PictureListAdapter by lazy {
        PictureListAdapter()
    }

    private lateinit var pictureViewModel: PictureViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_list)

        //Getting the instance of the Viewmodel
        pictureViewModel = ViewModelProvider(
            this,
            PictureViewModelFactory(
                PictureRepositoryImpl(this)
            )
        ).get(PictureViewModel::class.java)

        //Get the data from the viewModel
        pictureViewModel.getPictureData()

        rvPictureList.adapter = pictureListAdapter


        //Observing the data and applying the appropriate data condition
        pictureViewModel.nasaLiveData.observe(this, Observer {
            it?.let {
                pictureListAdapter.swapData(it)
            }
        })

        setUpListener()


    }

    private fun setUpListener() {
        pictureListAdapter.itemClickListener = this::handleItemClick
    }

    private fun handleItemClick(position: Int) {
        val intent = Intent(this, PictureDetailActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, photoThumbnail, getString(R.string.image_shared_element_animation))
        intent.putExtra("position", position)
        startActivity(intent, options.toBundle())
    }


}
