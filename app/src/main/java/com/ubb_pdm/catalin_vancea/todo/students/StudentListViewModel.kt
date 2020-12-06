package com.ubb_pdm.catalin_vancea.todo.students

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.ubb_pdm.catalin_vancea.core.TAG
import com.ubb_pdm.catalin_vancea.todo.data.Student
import com.ubb_pdm.catalin_vancea.todo.data.StudentRepository
import com.ubb_pdm.catalin_vancea.todo.data.local.TodoDatabase
import kotlinx.coroutines.launch
import com.ubb_pdm.catalin_vancea.core.Result

class StudentListViewModel(application: Application) : AndroidViewModel(application) {
    //private val mutableStudents = MutableLiveData<List<Student>>().apply { value = emptyList() }
    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val students: LiveData<List<Student>> //= mutableStudents
    val loading: LiveData<Boolean> = mutableLoading
    val loadingError: LiveData<Exception> = mutableException

    val studentRepository: StudentRepository

    init {
        val studentDao = TodoDatabase.getDatabase(application, viewModelScope).studentDao()
        studentRepository = StudentRepository(studentDao)
        students = studentRepository.students
    }

    fun refresh() {
        viewModelScope.launch {
            Log.v(TAG, "refresh...");
            mutableLoading.value = true
            mutableException.value = null
            when (val result = studentRepository.refresh()) {
                is Result.Success -> {
                    Log.d(TAG, "refresh succeeded");
                }
                is Result.Error -> {
                    Log.w(TAG, "refresh failed", result.exception);
                    mutableException.value = result.exception
                }
            }
            mutableLoading.value = false
        }
    }
}
