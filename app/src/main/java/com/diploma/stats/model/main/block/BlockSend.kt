package com.diploma.stats.model.main.block

import com.google.gson.annotations.SerializedName

class BlockSend(tempList: MutableList<Int>){

    @SerializedName("userList")
    val list: MutableList<Int> = tempList

}