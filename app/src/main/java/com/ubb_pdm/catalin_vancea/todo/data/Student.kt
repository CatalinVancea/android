package com.ubb_pdm.catalin_vancea.todo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(


    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "graduated") var graduated: Boolean,
    @ColumnInfo(name = "grade") var grade: Int,
    @ColumnInfo(name = "enrollment") var enrollment: String
) {
    override fun toString(): String = id + name
}
