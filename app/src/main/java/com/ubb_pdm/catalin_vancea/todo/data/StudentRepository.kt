package com.ubb_pdm.catalin_vancea.todo.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.ubb_pdm.catalin_vancea.core.TAG
import com.ubb_pdm.catalin_vancea.todo.data.local.StudentDao
import com.ubb_pdm.catalin_vancea.todo.data.remote.StudentApi
import com.ubb_pdm.catalin_vancea.core.Result

class StudentRepository(private val studentDao: StudentDao) {
    val students = studentDao.getAll()

    suspend fun refresh(): Result<Boolean> {
        Log.i(TAG, "refresh")
        try {
            val students = StudentApi.service.find()
            for (student in students) {
                studentDao.insert(student)
            }
            return Result.Success(true)
        } catch(e: Exception) {
            return Result.Error(e)
        }
    }

    fun getById(studentId: String): LiveData<Student> {
        Log.i(TAG, "getById")
        return studentDao.getById(studentId)
    }

    suspend fun save(student: Student): Result<Student> {
        Log.i(TAG, "save")
        
        try {
            val createdStudent = StudentApi.service.create(student)
            studentDao.insert(createdStudent)
            return Result.Success(createdStudent)
        } catch(e: Exception) {
            return Result.Error(e)
        }
    }

    suspend fun update(student: Student): Result<Student> {
        Log.i(TAG, "update")
        try {
            val updatedStudent = StudentApi.service.update(student.id, student)
            studentDao.update(updatedStudent)
            return Result.Success(updatedStudent)
        } catch(e: Exception) {
            return Result.Error(e)
        }
    }
}