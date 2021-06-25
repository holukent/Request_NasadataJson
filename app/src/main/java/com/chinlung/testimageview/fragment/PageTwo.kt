package com.chinlung.testimageview.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chinlung.testimageview.databinding.FragmentPageTwoBinding
import com.chinlung.testimageview.MyListAdapter
import com.chinlung.testimageview.R
import com.chinlung.testimageview.ViewModels


class PageTwo : Fragment() {

    private val viewModel: ViewModels by activityViewModels()
    private lateinit var binding: FragmentPageTwoBinding
    lateinit var mAdapter: MyListAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_page_two, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.setFilesList(requireContext())


        viewModel.test.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(),"${viewModel.test.value}", Toast.LENGTH_SHORT).show()
        }


        binding.lifecycleOwner = viewLifecycleOwner


        mAdapter = MyListAdapter(viewModel)

        binding.pagetwoRecycleView.apply {
            this.layoutManager = GridLayoutManager(requireContext(), 4)
            this.adapter = mAdapter
            this.setHasFixedSize(true)
        }


        viewModel.list.observe(viewLifecycleOwner) {
            if (viewModel.getRecyclerViewState("recyclerview") == null) {

                if (viewModel.filelist.value!!.isEmpty()) {
                    viewModel.updateList(requireContext())
                } else {
                    viewModel.updateList(
                        requireContext(), range =
                        viewModel.filelist.value!!.size
                    )

                }

            } else {
                binding.pagetwoRecycleView.layoutManager?.onRestoreInstanceState(
                    viewModel.getRecyclerViewState("recyclerview")
                )
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            binding.progressCircular.visibility = if (it == true) View.VISIBLE else View.GONE
        }

        viewModel.sublist.observe(viewLifecycleOwner) {
            mAdapter.submitList(viewModel.sublist.value)
        }


        binding.pagetwoRecycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == 0) {
                    val mLayoutManager = (recyclerView.layoutManager as GridLayoutManager)
                    if (mLayoutManager.findLastVisibleItemPosition() == mLayoutManager.itemCount - 1) {
                        viewModel.updateList(requireContext(), 32)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            }
        })
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveRecyclerView(
            "recyclerview",
            binding.pagetwoRecycleView.layoutManager?.onSaveInstanceState()
        )
    }
}