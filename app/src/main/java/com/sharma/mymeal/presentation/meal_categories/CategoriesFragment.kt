package com.sharma.mymeal.presentation.meal_categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sharma.mymeal.R
import com.sharma.mymeal.databinding.FragmentCategoriesBinding
import com.sharma.mymeal.presentation.meal_list.MealsFragment
import kotlinx.coroutines.launch
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
            inflater, container, false
        ).also {
            binding = it
            binding.lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                categoryViewModel.categories.collect { state ->
                    binding.apply {
                        progressBar.visibility =
                            if (state is CategoryListState.Loading) View.VISIBLE else View.GONE
                        errorText.visibility =
                            if (state is CategoryListState.Error) View.GONE else View.VISIBLE

                        if (state is CategoryListState.Data) {
                            categoryList.visibility = View.VISIBLE
                            adapter.addData(state.data)
                        } else categoryList.visibility = View.GONE
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