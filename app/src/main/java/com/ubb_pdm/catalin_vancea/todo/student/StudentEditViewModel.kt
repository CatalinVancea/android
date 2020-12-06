package com.ubb_pdm.catalin_vancea.todo.student

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ubb_pdm.catalin_vancea.core.TAG
import com.ubb_pdm.catalin_vancea.todo.data.Student
import com.ubb_pdm.catalin_vancea.todo.data.StudentRepository
import kotlinx.coroutines.launch

class StudentEditViewModel : ViewModel() {
    private val mutableStudent =
        MutableLiveData<Student>().apply { value = Student("", "", false, 0, "") }
    private val mutableFetching = MutableLiveData<Boolean>().apply { value = false }
    private val mutableCompleted = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val student: LiveData<Student> = mutableStudent
    val fetching: LiveData<Boolean> = mutableFetching
    val fetchingError: LiveData<Exception> = mutableException
    val completed: LiveData<Boolean> = mutableCompleted

    fun loadStudent(studentId: String) {
        viewModelScope.launch {
            Log.i(TAG, "loadStudent...")
            mutableFetching.value = true
            mutableException.value = null
            try {
                mutableStudent.value = StudentRepository.load(studentId)
                Log.i(TAG, "loadStudent succeeded")
                mutableFetching.value = false
            } catch (e: Exception) {
                Log.w(TAG, "loadStudent failed", e)
                mutableException.value = e
                mutableFetching.value = false
            }
        }
    }

    fun saveOrUpdateStudent(name: String, graduated: Boolean, grade: Int, enrollment: String) {
        viewModelScope.launch {
            Log.i(TAG, "saveOrUpdateStudent...");
            val student = mutableStudent.value ?: return@launch
            student.name = name
            student.graduated = graduated
            student.grade = grade
            student.enrollment = enrollment
            mutableFetching.value = true
            mutableException.value = null
            try {
                if (student.id.isNotEmpty()) {
                    mutableStudent.value = StudentRepository.update(student)
                } else {
                    mutableStudent.value = StudentRepository.save(student)
                }
                Log.i(TAG, "saveOrUpdateStudent succeeded");
                mutableCompleted.value = true
                mutableFetching.value = false
            } catch (e: Exception) {
                Log.w(TAG, "saveOrUpdateStudent failed", e);
                mutableException.value = e
                mutableFetching.value = false
            }
        }
    }

    fun ws() {


    }
}
