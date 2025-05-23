package com.fit2081.fit2081_a3_caileen_34375783.UIScreen.NutriCoachViewModel.PicSumAPI

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PicSumViewModel : ViewModel()  {
    private val _imageUrl = MutableLiveData<String>()
        val imageUrl: LiveData<String> = _imageUrl

        init {
            fetchRandomImage() // Triggers once when VM is created
        }
        private fun fetchRandomImage() {
            _imageUrl.value = "https://picsum.photos/200"
        }
}