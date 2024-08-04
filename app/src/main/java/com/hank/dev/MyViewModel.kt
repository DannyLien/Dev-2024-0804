package com.hank.dev

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class MyViewModel : ViewModel() {
    private val TAG: String? = MyViewModel::class.java.name
    val words = MutableLiveData<List<Word>>()

    fun readJSON() {
        viewModelScope.launch(Dispatchers.IO) {
            val json = URL("https://api.jsonserve.com/pcLzBT").readText()
            Log.d(TAG, "onCreate: ${json}")
//            parseJSON(json)
            val data = Gson().fromJson(json, Words::class.java)
//            words.words.forEach { w ->
//                Log.d(TAG, "onCreate: ${w.name} / ${w.means}")
//            }
            words.postValue(data.words)

        }

    }

}