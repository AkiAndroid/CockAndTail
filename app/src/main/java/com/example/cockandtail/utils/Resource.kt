package com.example.cockandtail.utils

data class Resource<out T>(val status: UIstatus, val data: T?, val message: String?) {

    companion object {

        fun <T> Success(data: T?): Resource<T> {
            return Resource(UIstatus.Success, data, null)
        }

        fun <T> Error(msg: String?, data: T?): Resource<T> {
            return Resource(UIstatus.Error, data, msg)
        }

        fun <T> Loading(data: T?): Resource<T> {
            return Resource(UIstatus.Loading, data, null)
        }

    }

}
