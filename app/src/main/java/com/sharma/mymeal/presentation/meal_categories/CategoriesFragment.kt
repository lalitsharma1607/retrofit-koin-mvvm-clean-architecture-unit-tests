package com.sharma.mymeal.presentation.meal_categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import com.sharma.mymeal.R
import com.sharma.mymeal.databinding.FragmentCategoriesBinding
import com.sharma.mymeal.presentation.meal_list.MealsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class CategoriesFragment : Fragment(), OnItemClickListener {

    companion object {
        fun newInstance(): CategoriesFragment {
            return CategoriesFragment()
        }
    }

    private val categoryViewModel: CategoriesViewModel by viewModel()
    private lateinit var binding: FragmentCategoriesBinding
    private val adapter = CategoryAdapter(arrayListOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        return FragmentCategoriesBinding.inflate(
            inflater,
            container,
            false
        ).also {
            binding = it
            binding.lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycle.coroutineScope.launchWhenCreated {
            categoryViewModel.categories.collect {
                when {
                    it.isLoading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.categoryList.visibility = View.GONE
                        binding.errorText.visibility = View.GONE
                    }
                    it.error.isNotBlank() -> {
                        binding.progressBar.visibility = View.GONE
                        binding.categoryList.visibility = View.GONE
                        binding.errorText.visibility = View.VISIBLE
                    }
                    it.data?.isNotEmpty() == true -> {
                        binding.progressBar.visibility = View.GONE
                        binding.categoryList.visibility = View.VISIBLE
                        binding.errorText.visibility = View.GONE
                        adapter.addData(it.data)
                    }
                }
            }
        }

        binding.categoryList.adapter = adapter

    }

    override fun onItemClicked(item: String) {
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.add(
            R.id.container, MealsFragment.newInstance(item)
        )?.addToBackStack(MealsFragment::class.java.name)?.commit()
    }
}