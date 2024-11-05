package com.nalldev.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OnBoardingViewModel : ViewModel() {
    private val _page : MutableLiveData<Int> = MutableLiveData(1)
    val page : LiveData<Int> = _page

    fun nextPage() {
        _page.postValue(_page.value?.plus(1))
    }
}