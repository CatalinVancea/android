package com.ubb_pdm.catalin_vancea.todo.data

data class Student(
    val id: String,
    var name: String,
    var graduated: Boolean,
    var grade: Int,
    var enrollment: String
) {
    override fun toString(): String = id + name
}
