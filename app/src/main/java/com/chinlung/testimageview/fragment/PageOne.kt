package com.chinlung.testimageview.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.chinlung.testimageview.R
import com.chinlung.testimageview.ViewModels
import com.chinlung.testimageview.model.Api

class PageOne : Fragment(R.layout.fragment_page_one) {

    private val viewModel: ViewModels by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getRequest(requireContext(), Api.NASADATA)


        view.findViewById<Button>(R.id.btn_nextPage).setOnClickListener {
            findNavController().navigate(PageOneDirections.actionPageOneToPageTwo())
        }
    }
}
