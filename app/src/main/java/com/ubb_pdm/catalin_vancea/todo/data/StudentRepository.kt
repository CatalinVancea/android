package com.ubb_pdm.catalin_vancea.todo.data

import android.util.Log
import com.ubb_pdm.catalin_vancea.core.TAG
import com.ubb_pdm.catalin_vancea.todo.data.remote.StudentApi

object StudentRepository {
    private var cachedStudents: MutableList<Student>? = null;

    suspend fun loadAll(): List<Student> {
        Log.i(TAG, "loadAll")
        if (cachedStudents != null) {
            return cachedStudents as List<Student>;
        }
        cachedStudents = mutableListOf()
        val students = StudentApi.service.find()
        cachedStudents?.addAll(students)
        return cachedStudents as List<Student>
    }

    suspend fun load(studentId: String): Student {
        Log.i(TAG, "load")
        val student = cachedStudents?.find { it.id == studentId }
        if (student != null) {
            return student
        }
        return StudentApi.service.read(studentId)
    }

    suspend fun save(student: Student): Student {
        Log.i(TAG, "save")
        val createdStudent = StudentApi.service.create(student)
        cachedStudents?.add(createdStudent)
        return createdStudent
    }

    suspend fun update(student: Student): Student {
        Log.i(TAG, "update")
        val updatedStudent = StudentApi.service.update(student.id, student)
        val index = cachedStudents?.indexOfFirst { it.id == student.id }
        if (index != null) {
            cachedStudents?.set(index, updatedStudent)
        }
        return updatedStudent
    }
}