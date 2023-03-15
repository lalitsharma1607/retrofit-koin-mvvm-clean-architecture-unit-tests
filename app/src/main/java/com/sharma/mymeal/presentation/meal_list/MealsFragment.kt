package com.sharma.mymeal.presentation.meal_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.sharma.mymeal.databinding.FragmentCategoriesBinding
import com.sharma.mymeal.utils.SpacesItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return FragmentCategoriesBinding.inflate(
            inflater, container, false
        ).also {
            binding = it
            binding.lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealsViewModel.getMeals(arguments?.getString(NAME).orEmpty())

        binding.categoryList.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            addItemDecoration(SpacesItemDecoration(30, 2))
            adapter = this@MealsFragment.adapter
        }

        CoroutineScope(Dispatchers.Main).launch {
            mealsViewModel.meals.collect { state ->
                binding.apply {
                    progressBar.visibility =
                        if (state is MealListState.Loading) View.VISIBLE else View.GONE
                    errorText.visibility =
                        if (state is MealListState.Error) View.GONE else View.VISIBLE

                    if (state is MealListState.Data) {
                        categoryList.visibility = View.VISIBLE
                        adapter.addData(state.data.orEmpty())
                    } else categoryList.visibility = View.GONE
                }
            }
        }
    }
}