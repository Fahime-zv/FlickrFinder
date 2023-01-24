package com.fahimezv.flickrfindr.presentaion.feature.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fahimezv.flickrfindr.core.model.image
import com.fahimezv.flickrfindr.databinding.InfoFragmentBinding
import com.fahimezv.flickrfindr.presentaion.common.ImageLoader
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class InfoFragment : Fragment() {

    private val args: InfoFragmentArgs by navArgs()

    //UI
    private var _binding: InfoFragmentBinding? = null

    private val binding get() = _binding!!

    //Inject View Model
    private val viewModel: InfoViewModel by viewModel {
        parametersOf(args.photo)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = InfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Load Image
        ImageLoader.with(requireContext()).load(args.photo.image).into(binding.fullPictureImageView)
        //set Name
        binding.nameTextView.text = args.photo.title
        //Setup Back ImageView
        setupOnBackImageView()
        //Setup Bookmark Button
        setupBookmarkButton()
        //Set State for BookmarkButton
        lifecycleScope.launch {
            viewModel.bookmarkFlow.collectLatest { state ->
                binding.bookMarkbutton.isChecked = state
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //****************************************
    //              View Creations           *
    //****************************************
    private fun setupOnBackImageView() = binding.backImageView.apply {
        setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupBookmarkButton() = binding.bookMarkbutton.apply {
        setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                viewModel.bookmark()
            else
                viewModel.unBookmark()
        }

    }


}