package com.ilhamabdlh.waterheight.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhamabdlh.waterheight.data.Post
import com.ilhamabdlh.waterheight.ui.Repository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    val height: LiveData<Post> get() = _height
    private var _height: MutableLiveData<Post> = MutableLiveData()

    private val dummyPost = Post(
        id = 1L,
        name = "Post Pemantauan Fakultas Teknik",
        height = 0,
        isEnabled = true
    )

    fun getHeight() {
        viewModelScope.launch(IO) {
            val result = Repository.getHeight()
            if (result.isSuccessful) {
                dummyPost.height = result.body()?.height ?: 0
                _height.postValue(dummyPost)
            }
        }
    }
}