package com.sharma.mymeal.presentation.meal_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.GridLayoutManager
import com.sharma.mymeal.databinding.FragmentCategoriesBinding
import com.sharma.mymeal.utils.SpacesItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class MealsFragment : Fragment() {

    companion object {
        private const val NAME = "name"
        fun newInstance(item: String): MealsFragment {
            val bundle = Bundle().apply { putString(NAME, item) }
            return MealsFragment().apply { arguments = bundle }
        }
    }

    private val mealsViewModel: MealsViewModel by viewModel()
    private lateinit var binding: FragmentCategoriesBinding
    private val adapter = MealAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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
        mealsViewModel.getMeals(arguments?.getString(NAME).orEmpty())
        lifecycle.coroutineScope.launchWhenCreated {
            mealsViewModel.meals.collect {
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
        binding.categoryList.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.categoryList.addItemDecoration(SpacesItemDecoration(30, 2))
        binding.categoryList.adapter = adapter

    }
}