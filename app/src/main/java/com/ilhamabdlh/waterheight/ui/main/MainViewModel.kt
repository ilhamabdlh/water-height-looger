package com.ilhamabdlh.waterheight.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhamabdlh.waterheight.data.Post
import com.ilhamabdlh.waterheight.ui.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val post: LiveData<Post> get() = _post
    private var _post: MutableLiveData<Post> = MutableLiveData()

    fun getPost() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = Repository.getPost()
            if (result.isSuccessful) {
                _post.postValue(result.body())
            }
        }
    }
}