package com.fahimezv.flickrfindr.presentaion.feature.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.fahimezv.flickrfindr.databinding.BookmarkFragmentBinding
import com.fahimezv.flickrfindr.presentaion.common.adapter.CellDimension
import com.fahimezv.flickrfindr.presentaion.common.adapter.PhotoAdapter
import com.fahimezv.flickrfindr.presentaion.common.adapter.RecyclerViewDecorations
import com.fahimezv.flickrfindr.presentaion.common.extentions.dpToPx
import com.fahimezv.flickrfindr.presentaion.common.extentions.screenWidth
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarkFragment:Fragment() {

    //UI
    private var _binding: BookmarkFragmentBinding? = null

    private val binding get() = _binding!!

    //Inject View Model
    private val viewModel: BookmarkViewModel by viewModel()

    //Adapter
    private lateinit var photoAdapter: PhotoAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = BookmarkFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Setup Bookmark RecyclerView
        setupBookmarksRecyclerView()
        //Setup Back ImageView
        setupOnBackImageView()
        //Observe data
        lifecycleScope.launch {
            viewModel.bookmarks.collectLatest {
                photoAdapter.submitData(it)
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupOnBackImageView() = binding.backImageView.apply {
        setOnClickListener {
            findNavController().navigateUp()
        }
    }
    //****************************************
    //              View Creations           *
    //****************************************

    private fun setupBookmarksRecyclerView() = binding.bookmarkRecyclerView.apply {
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
                    BookmarkFragmentDirections.actionBookmarkFragmentToInfoFragment(photo = photo)
                )

            }
        )
        adapter = photoAdapter

    }
}