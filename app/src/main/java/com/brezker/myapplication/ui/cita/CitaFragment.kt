package com.brezker.myapplication.ui.cita

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brezker.myapplication.R

class CitaFragment : Fragment() {

    companion object {
        fun newInstance() = CitaFragment()
    }

    private lateinit var viewModel: CitaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cita, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CitaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}