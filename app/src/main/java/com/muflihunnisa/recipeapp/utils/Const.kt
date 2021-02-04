package com.muflihunnisa.recipeapp.utils

object Const {
    val COLLECTION_PATH = "recipe"
    val PATH_NAME = "recipeName"

    fun setTimeStamp (): Long{
        val time = (-1 * System.currentTimeMillis())
        return time
    }
}