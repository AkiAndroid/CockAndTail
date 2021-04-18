package com.example.cockandtail.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cockandtail.Database.DBRepository
import com.example.cockandtail.Repository.MainRepository
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(
    private val mainRepository: MainRepository,
    private val dbRepository: DBRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mainRepository,dbRepository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}