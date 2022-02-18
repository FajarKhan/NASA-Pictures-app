package com.fajarkhan.nasapicapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.fajarkhan.nasapicapp.model.PictureResponseModel
import com.fajarkhan.nasapicapp.repository.PictureRepository
import com.fajarkhan.nasapicapp.viewmodel.PictureViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PictureViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var pictureViewModel: PictureViewModel

    @MockK
    lateinit var repo: PictureRepository

    @Before
    fun setUpViewModel() {
        MockKAnnotations.init(this)
        // Given a fresh ViewModel
        pictureViewModel = PictureViewModel(repo)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `check if the expected response from the given fake response`() = runBlockingTest {
        val expectedResponse =
            MutableLiveData(listOf(PictureResponseModel("hi", "2", "ex", null, "a")))
        coEvery { repo.getPictureResponse() } returns (expectedResponse)
    }

}