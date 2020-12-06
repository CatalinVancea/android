package com.ubb_pdm.catalin_vancea.todo.students

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.ubb_pdm.catalin_vancea.R
import com.ubb_pdm.catalin_vancea.auth.data.AuthRepository
import com.ubb_pdm.catalin_vancea.core.TAG
import kotlinx.android.synthetic.main.fragment_student_list.*

class StudentListFragment : Fragment() {
    private lateinit var studentListAdapter: StudentListAdapter
    private lateinit var studentsModel: StudentListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_student_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.v(TAG, "onActivityCreated")

        if (!AuthRepository.isLoggedIn) {
            findNavController().navigate(R.id.fragment_login)
            return;
        }

        setupStudentList()
        fab.setOnClickListener {
            Log.v(TAG, "add new student")
            findNavController().navigate(R.id.StudentEditFragment)
        }
    }

    private fun setupStudentList() {
        studentListAdapter = StudentListAdapter(this)
        student_list.adapter = studentListAdapter
        studentsModel = ViewModelProvider(this).get(StudentListViewModel::class.java)
        studentsModel.students.observe(viewLifecycleOwner, { students ->
            Log.v(TAG, "update students")
            studentListAdapter.students = students
        })
        studentsModel.loading.observe(viewLifecycleOwner, { loading ->
            Log.i(TAG, "update loading")
            progress.visibility = if (loading) View.VISIBLE else View.GONE
        })
        studentsModel.loadingError.observe(viewLifecycleOwner, { exception ->
            if (exception != null) {
                Log.i(TAG, "update loading error")
                val message = "Loading exception ${exception.message}"
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            }
        })
        studentsModel.refresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "onDestroy")
    }
}