package com.fahimezv.flickrfindr.presentaion.feature.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahimezv.flickrfindr.R
import com.fahimezv.flickrfindr.databinding.SearchFragmentBinding
import com.fahimezv.flickrfindr.presentaion.common.adapter.CellDimension
import com.fahimezv.flickrfindr.presentaion.common.adapter.PhotoAdapter
import com.fahimezv.flickrfindr.presentaion.common.adapter.RecyclerViewDecorations
import com.fahimezv.flickrfindr.presentaion.common.extentions.dpToPx
import com.fahimezv.flickrfindr.presentaion.common.extentions.screenWidth
import com.fahimezv.flickrfindr.presentaion.extension.doOnSearch
import com.fahimezv.flickrfindr.presentaion.extension.hideKeyboard
import com.fahimezv.flickrfindr.presentaion.feature.search.sub.HistoryAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    //UI
    private var _binding: SearchFragmentBinding? = null

    private val binding get() = _binding!!

    //Adapter
    private  var photoAdapter: PhotoAdapter?=null
    private  var historyAdapter: HistoryAdapter?=null

    //Inject View Model
    private val viewModel: SearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Setup Photos Recycler View
        setupPhotosRecyclerView()
        //Setup History Recycler View
        setupHistoryRecyclerView()
        //Setup  Search View
        setupSearchEditText()
        //Setup bookmark Button
        setupBookmarkButton()
        // Observe UiState
        lifecycleScope.launch {
            viewModel.uiState.collectLatest { uiState ->
                binding.historyRecyclerView.isVisible = false
                when (uiState) {
                    is SearchUiState.Success -> {
                        binding.photosRecyclerView.isVisible = true
                        binding.loadingProgressbar.isVisible = false
                        photoAdapter?.submitData(uiState.photos)

                    }
                    SearchUiState.Loading -> {
                        binding.loadingProgressbar.isVisible = true
                        binding.photosRecyclerView.isVisible = false
                    }
                    SearchUiState.Error -> {
                        binding.apply {
                            loadingProgressbar.isVisible = false
                            emptyTextView.isVisible = true
                            emptyTextView.text = getString(R.string.anErrorOccurred)
                        }
                    }
                }
            }
        }
        // Observe History of terms
        lifecycleScope.launch {
            viewModel.terms.collectLatest {
                historyAdapter?.bind(it)
            }
        }
        //Adapter state
//        lifecycleScope.launchWhenCreated {
//            photoAdapter?.loadStateFlow?.collectLatest { loadStates ->
//                val refresher = loadStates.refresh
//                val displayEmptyMessage =  (refresher is LoadState.NotLoading && photoAdapter!!.itemCount == 0)
//                binding.emptyTextView.isVisible = displayEmptyMessage
//                binding.loadingProgressbar.isVisible=loadStates.refresh is LoadState.Loading
//            }
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        photoAdapter=null
        photoAdapter=null
        _binding = null
    }

    //****************************************
    //              View Creations           *
    //****************************************
    @SuppressLint("SuspiciousIndentation")
    private fun setupSearchEditText() = binding.searchEditText.apply {
        imeOptions = EditorInfo.IME_ACTION_SEARCH
        doOnSearch { term ->
            viewModel.search(term)
            binding.historyRecyclerView.isVisible = false
        }
        doAfterTextChanged { editableText ->
            editableText?.let { text ->
                binding.historyRecyclerView.isVisible = text.isNotEmpty() || viewModel.uiState.value !is SearchUiState.Success
            }
        }
        setOnFocusChangeListener { _, hasFocus ->
            binding.historyRecyclerView.isVisible = hasFocus
        }
    }

    private fun setupPhotosRecyclerView() = binding.photosRecyclerView.apply {
        val cellSize = CellDimension(
            widthRatio = 0.7F,
            width = 100.dpToPx,
            height = null,
            spacing = 10.dpToPx
        )
            .dimensions(screenWidth)

        addItemDecoration(
            RecyclerViewDecorations.GridSpacingItemDecoration(
                spacing = cellSize.spacing,
            )
        )

        clipToPadding = false
        setPaddingRelative(cellSize.spacing, cellSize.spacing, cellSize.spacing, cellSize.spacing)
        layoutManager = GridLayoutManager(context, cellSize.cols)

        //Setup Adapter
        photoAdapter = PhotoAdapter(
            cellSize.size,
            onPhotoClickListener = { photo ->
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToInfoFragment(photo = photo)
                )

            }
        )
        adapter = photoAdapter

    }

    private fun setupHistoryRecyclerView() = binding.historyRecyclerView.apply {

        clipToPadding = false
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        //Setup Adapter
        historyAdapter = HistoryAdapter(
            onItemClick = { term ->
                viewModel.search(term)
                binding.searchEditText.setText(term)
                binding.searchEditText.clearFocus()
                requireContext().hideKeyboard(windowToken)
            }
        )
        adapter = historyAdapter
    }


    private fun setupBookmarkButton() = binding.textViewBookmark.apply {
        setOnClickListener {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToBookmarkFragment())
        }
    }


}