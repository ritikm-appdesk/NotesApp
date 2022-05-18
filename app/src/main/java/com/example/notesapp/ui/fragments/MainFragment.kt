package com.example.notesapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.adapters.AppRVAdapter
import com.example.notesapp.database.AppDatabase
import com.example.notesapp.database.AppRepository
import com.example.notesapp.model.Notes
import com.example.notesapp.viewmodel.NotesViewModel
import com.example.notesapp.viewmodel.NotesViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main),AppRVAdapter.DeleteClickListener {

    private lateinit var rvAdapter: AppRVAdapter
    private lateinit var viewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = AppRepository(AppDatabase(requireActivity()))
        val viewModelFactory = NotesViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[NotesViewModel::class.java]
        viewModel.getAll().observe(viewLifecycleOwner){
            if (it.isEmpty()) {
                defaultid.visibility = View.VISIBLE
            } else {
                defaultid.visibility = View.VISIBLE
            }
            setupRV(it as MutableList<Notes>)
        }
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addNoteFragment)
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onItemClick(note: Notes) {
        viewModel.delete(note)
        rvAdapter.notifyDataSetChanged()
        Snackbar.make(requireView(), "Note Deleted!", Snackbar.LENGTH_LONG).apply {
            setAction("Undo") {
                viewModel.insert(note)
            }
            show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupRV(ls: MutableList<Notes>) {
        rvAdapter = AppRVAdapter(ls,this)
        if (ls.size == 0) {
            defaultid.visibility = View.VISIBLE
        } else {
            defaultid.visibility = View.GONE
        }

        id_rv.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        rvAdapter.notifyDataSetChanged()

        rvAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("notes", it)
            }
            findNavController().navigate(R.id.action_mainFragment_to_notesFragment, bundle)
        }
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = rvAdapter.ls[position]
                viewModel.delete(note)
                Snackbar.make(view!!, "Note Deleted!", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.insert(note)
                    }
                    show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(id_rv)
        }
    }
}