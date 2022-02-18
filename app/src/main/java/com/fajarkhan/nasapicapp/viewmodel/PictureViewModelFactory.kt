package com.fajarkhan.nasapicapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fajarkhan.nasapicapp.repository.PictureRepository


/**
 * Passing an parameter in ViewModel requires you to create the ViewModelFactory class
 * to override the Base Implementation of the Android ViewmodelFactory without parameter*/
class PictureViewModelFactory(private val pictureRepository: PictureRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PictureViewModel(
            pictureRepository
        ) as T
    }
}