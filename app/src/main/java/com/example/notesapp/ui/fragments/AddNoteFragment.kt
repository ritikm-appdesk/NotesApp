package com.example.notesapp.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R
import com.example.notesapp.database.AppDatabase
import com.example.notesapp.database.AppRepository
import com.example.notesapp.model.Notes
import com.example.notesapp.utils.Constants.Companion.getDate
import com.example.notesapp.viewmodel.NotesViewModel
import com.example.notesapp.viewmodel.NotesViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_note.*

class AddNoteFragment : Fragment(R.layout.fragment_add_note) {


    private lateinit var viewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = AppRepository(AppDatabase(requireActivity()))
        val viewModelFactory = NotesViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[NotesViewModel::class.java]
        var type = resources.getStringArray(R.array.type)[0]

        spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                type = resources.getStringArray(R.array.type)[position]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        ivBackButton.setOnClickListener {
            findNavController().popBackStack()
        }

        ivTick.setOnClickListener {
            if (!TextUtils.isEmpty(tvHeading2.text.toString()) && !TextUtils.isEmpty(etBody.text.toString())) {
                val head = tvHeading2.text.toString()
                val body = etBody.text.toString()
                val types = type
                val date = getDate()
                val note = Notes(null, head, body, date, types)
                viewModel.insert(note)
                Toast.makeText(activity, "Saved", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addNoteFragment_to_mainFragment)
            } else {
                Snackbar.make(view, "You have to Enter Something!", Snackbar.LENGTH_LONG).apply {
                    show()
                }
            }
        }
    }
}