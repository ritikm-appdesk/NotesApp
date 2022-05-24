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
import androidx.navigation.fragment.navArgs
import com.example.notesapp.R
import com.example.notesapp.database.AppDatabase
import com.example.notesapp.database.AppRepository
import com.example.notesapp.model.Notes
import com.example.notesapp.viewmodel.NotesViewModel
import com.example.notesapp.viewmodel.NotesViewModelProviderFactory
import kotlinx.android.synthetic.main.notes_fragment.*

class NotesFragment : Fragment(R.layout.notes_fragment) {
    private lateinit var viewModel: NotesViewModel
    private val args: NotesFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notes_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = AppRepository(AppDatabase(requireActivity()))
        val viewModelFactory = NotesViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[NotesViewModel::class.java]
        val note = args.notes
        tvHeading.text = note.heading
        tvDate2.text = note.date
        tvBody.text = note.body
        idtype.text = note.type

        ivBack.setOnClickListener{
            findNavController().popBackStack()
        }

        ivEdit.setOnClickListener {
            switcher.showNext()
            switcheriv.showNext()
            switcher1.showNext()
            switcher2.showNext()
        }
        ivTick2.setOnClickListener {

            tvBody.text = etbody.text
            tvHeading.text = etHeading2.text
            var t = resources.getStringArray(R.array.type)[0]
            spinnerType2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    t = resources.getStringArray(R.array.type)[position]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
            idtype.text = t
            val head = tvHeading.text.toString()
            val body = tvBody.text.toString()
            val types = t
           ivTick2.setOnClickListener{
               if(!TextUtils.isEmpty(tvHeading.text.toString()) && !TextUtils.isEmpty(tvBody.text.toString())){
                   viewModel.update(Notes(note.id, head, body, note.date, types))
                   switcher.showNext()
                   switcheriv.showNext()
                   switcher1.showNext()
                   switcher2.showNext()
               }else{
                   Toast.makeText(requireContext(),"Not done",Toast.LENGTH_SHORT).show()
               }
           }
        }
    }
}