package com.ubb_pdm.catalin_vancea.todo.students

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubb_pdm.catalin_vancea.core.TAG
import com.ubb_pdm.catalin_vancea.todo.data.Student
import com.ubb_pdm.catalin_vancea.todo.data.StudentRepository
import kotlinx.coroutines.launch


class StudentListViewModel : ViewModel() {
    private val mutableStudents = MutableLiveData<List<Student>>().apply { value = emptyList() }
    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val students: LiveData<List<Student>> = mutableStudents
    val loading: LiveData<Boolean> = mutableLoading
    val loadingError: LiveData<Exception> = mutableException

    fun createStudent(position: Int): Unit {
        val list = mutableListOf<Student>()
        list.addAll(mutableStudents.value!!)
        list.add(Student(position.toString(), "Student " + position, false, 0, "01/01/2020"))
        mutableStudents.value = list
    }

    fun loadStudents() {
        viewModelScope.launch {
            Log.v(TAG, "loadStudents...");
            mutableLoading.value = true
            mutableException.value = null
            try {
                mutableStudents.value = StudentRepository.loadAll()
                Log.d(TAG, "loadStudents succeeded");
                mutableLoading.value = false
            } catch (e: Exception) {
                Log.w(TAG, "loadStudents failed", e);
                mutableException.value = e
                mutableLoading.value = false
            }
        }
    }
}
