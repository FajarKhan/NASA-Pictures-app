package com.fajarkhan.nasapicapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fajarkhan.nasapicapp.model.PictureResponseModel
import com.fajarkhan.nasapicapp.repository.PictureRepository
import kotlinx.coroutines.launch

class PictureViewModel(private val pictureRepository: PictureRepository) : ViewModel() {

    //Here creating mutable version to pass the data
    private val _nasaLiveData: MutableLiveData<List<PictureResponseModel>> = MutableLiveData()

    //To observe that data we use this
    val nasaLiveData: LiveData<List<PictureResponseModel>> = _nasaLiveData


    fun getPictureData() {
        viewModelScope.launch {
            _nasaLiveData.postValue(pictureRepository.getPictureResponse())
        }
    }

}