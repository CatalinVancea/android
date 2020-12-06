package com.ubb_pdm.catalin_vancea.todo.students

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ubb_pdm.catalin_vancea.R
import com.ubb_pdm.catalin_vancea.core.TAG
import com.ubb_pdm.catalin_vancea.todo.data.Student
import com.ubb_pdm.catalin_vancea.todo.student.StudentEditFragment
import kotlinx.android.synthetic.main.view_student.view.*


class StudentListAdapter(
    private val fragment: Fragment
) : RecyclerView.Adapter<StudentListAdapter.ViewHolder>() {

    var students = emptyList<Student>()
        set(value) {
            field = value
            notifyDataSetChanged();
        }

    private var onStudentClick: View.OnClickListener;

    init {
        onStudentClick = View.OnClickListener { view ->
            val student = view.tag as Student
            fragment.findNavController().navigate(R.id.StudentEditFragment, Bundle().apply {
                Log.v(TAG, "-----------------------------------------------------------------------------------------------")
                Log.v(TAG, student.id.toString())
                putString(StudentEditFragment.STUDENT_ID, student.id.toString())
                putString(StudentEditFragment.STUDENT_NAME, student.name.toString())
                putString(StudentEditFragment.STUDENT_GRADE, student.grade.toString())
                putString(StudentEditFragment.STUDENT_GRADUATED, student.graduated.toString())
                putString(StudentEditFragment.STUDENT_ENROLLMENT, student.enrollment.toString())
                putString(StudentEditFragment.STUDENT_VERSION, student.version.toString())
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_student, parent, false)
        Log.v(TAG, "onCreateViewHolder")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.v(TAG, "onBindViewHolder $position")
        val student = students[position]
        holder.itemView.tag = student
        holder.textView.text = student.name
        holder.itemView.setOnClickListener(onStudentClick)
    }

    override fun getItemCount() = students.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.text
    }
}
