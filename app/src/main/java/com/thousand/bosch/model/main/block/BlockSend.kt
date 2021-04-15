package com.thousand.bosch.model.main.block

import com.google.gson.annotations.SerializedName

class BlockSend(tempList: MutableList<Int>){

    @SerializedName("userList")
    val list: MutableList<Int> = tempList

}