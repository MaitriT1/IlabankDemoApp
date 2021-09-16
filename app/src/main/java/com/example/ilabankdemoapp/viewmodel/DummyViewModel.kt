package com.example.ilabankdemoapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ilabankdemoapp.R
import com.example.ilabankdemoapp.model.ImageDetail
import java.text.FieldPosition
import kotlin.coroutines.CoroutineContext

class DummyViewModel : ViewModel() {

    var dummmyData = MutableLiveData<ArrayList<ImageDetail>>()
    var data = ArrayList<ImageDetail>()

    fun fetchDymmyData() {
        data.add(
            ImageDetail(
                R.drawable.bird,
                null,
                null,
                mutableListOf("Eagle", "Peocock", "Owl", "Pigeon", "Sparrow", "Crow")
            )
        )
        data.add(
            ImageDetail(
                R.drawable.animal,
                null,
                null,
                mutableListOf("Lion", "Tiger", "Bear", "Crocodile", "Wolf", "Fox")
            )
        )
        data.add(
            ImageDetail(
                R.drawable.flower,
                null,
                null,
                mutableListOf("Rose", "Jasmine", "Sunflower", "Lotus", "Lavender", "Marigold","Hibiscus","Millingtonia Hortensis")
            )
        )
        dummmyData.value = data
    }
}