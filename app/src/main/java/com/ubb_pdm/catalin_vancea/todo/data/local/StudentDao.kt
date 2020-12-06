package com.ubb_pdm.catalin_vancea.todo.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ubb_pdm.catalin_vancea.todo.data.Student

@Dao
interface StudentDao {
    @Query("SELECT * from students ORDER BY name ASC")
    fun getAll(): LiveData<List<Student>>

    @Query("SELECT * FROM students WHERE id=:id ")
    fun getById(id: String): LiveData<Student>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(student: Student)

    @Query("DELETE FROM students WHERE id=:id ")
    suspend fun delete(id: String)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(student: Student)

    @Query("DELETE FROM students")
    suspend fun deleteAll()
}