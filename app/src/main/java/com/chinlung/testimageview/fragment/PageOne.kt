package com.chinlung.testimageview.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.chinlung.testimageview.MainActivity
import com.chinlung.testimageview.R
import com.chinlung.testimageview.ViewModels

class PageOne : Fragment(R.layout.fragment_page_one) {

    private val viewModel: ViewModels by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getRequest(requireContext(),MainActivity.NASADATA)




        view.findViewById<Button>(R.id.btn_nextPage).setOnClickListener {
            findNavController().navigate(PageOneDirections.actionPageOneToPageTwo())
        }
    }
}
