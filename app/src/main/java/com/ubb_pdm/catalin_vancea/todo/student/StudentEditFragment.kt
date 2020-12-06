package com.ubb_pdm.catalin_vancea.todo.student

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.ubb_pdm.catalin_vancea.R
import com.ubb_pdm.catalin_vancea.core.TAG
import kotlinx.android.synthetic.main.fragment_student_edit.*

class StudentEditFragment : Fragment() {
    companion object {
        const val STUDENT_ID = "STUDENT_ID"
        const val STUDENT_NAME = "STUDENT_NAME"
        const val STUDENT_GRADE = "STUDENT_GRADE"
        const val STUDENT_GRADUATED = "STUDENT_GRADUATED"
        const val STUDENT_ENROLLMENT = "STUDENT_ENROLLMENT"
    }

    private lateinit var viewModel: StudentEditViewModel
    private var studentId: String? = null
    private var studentName: String? = null
    private var studentGrade: Int? = null
    private var studentGraduated: Boolean? = null
    private var studentEnrollment: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate")
        arguments?.let {
            if (it.containsKey(STUDENT_ID)) {
                studentId = it.getString(STUDENT_ID).toString()
                studentName = it.getString(STUDENT_NAME).toString()
                studentGrade = it.getInt(STUDENT_GRADE).toString().toInt()
                studentGraduated = it.getBoolean(STUDENT_GRADUATED).toString().toBoolean()
                studentEnrollment = it.getString(STUDENT_ENROLLMENT).toString()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.v(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_student_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.v(TAG, "onViewCreated")
        student_id.setText(studentId)
        student_name.setText(studentName)
        student_graduated.setText(studentGraduated.toString())
        student_grade.setText(studentGrade.toString())
        student_enrollment.setText(studentEnrollment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.v(TAG, "onActivityCreated")
        setupViewModel()
        fab.setOnClickListener {
            Log.v(TAG, "save student")
            viewModel.saveOrUpdateStudent(
                student_name.text.toString(),
                student_graduated.text.toString().toBoolean(),
                student_grade.text.toString().toInt(),
                student_enrollment.text.toString()
            )

        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(StudentEditViewModel::class.java)

        viewModel.student.observe(viewLifecycleOwner, Observer { student ->

            Log.v(TAG, "update student")
            student_name.setText(student.name)
            student_graduated.setText(student.graduated.toString())
            student_grade.setText(student.grade.toString())
            student_enrollment.setText(student.enrollment)
        })

        viewModel.fetching.observe(viewLifecycleOwner, Observer { fetching ->
            Log.v(TAG, "update fetching")
            progress.visibility = if (fetching) View.VISIBLE else View.GONE
        })

        viewModel.fetchingError.observe(viewLifecycleOwner, Observer { exception ->
            if (exception != null) {
                Log.v(TAG, "update fetching error")
                val message = "Fetching exception ${exception.message}"
                val parentActivity = activity?.parent
                if (parentActivity != null) {
                    Toast.makeText(parentActivity, message, Toast.LENGTH_SHORT).show()
                }
            }

        })

        viewModel.completed.observe(viewLifecycleOwner, Observer { completed ->
            if (completed) {
                Log.v(TAG, "completed, navigate back")
                findNavController().navigateUp()
            }
            hideKeyboard()
        })

        val id = studentId
        if (id != null) {
            viewModel.loadStudent(id)
        }
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}


