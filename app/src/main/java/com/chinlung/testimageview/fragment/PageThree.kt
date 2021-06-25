package com.chinlung.testimageview.fragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.chinlung.testimageview.R
import com.chinlung.testimageview.ViewModels
import com.chinlung.testimageview.databinding.FragmentPageThreeBinding
import com.chinlung.testimageview.model.NasaDataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PageThree : Fragment(R.layout.fragment_page_three) {

    lateinit var binding: FragmentPageThreeBinding

    val viewModels: ViewModels by activityViewModels()

    lateinit var nasaDataItem: NasaDataItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nasaDataItem = it.getParcelable("nasasataitem")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_page_three, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            fragmentThree = this@PageThree
        }


        lifecycleScope.launch(Dispatchers.Main) {
            binding.pageThreeProgress.visibility = View.VISIBLE
            viewModels.downloadimg(requireContext(), nasaDataItem, 100).join()
            binding.pageThreeProgress.visibility = View.GONE
            binding.pageThreeImage.setImageURI(
                Uri.parse("file://${requireActivity().filesDir}/${nasaDataItem.date}.jpg")
            )
        }

    }
}