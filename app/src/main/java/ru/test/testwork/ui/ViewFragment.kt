package ru.test.testwork.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.test.testwork.R
import ru.test.testwork.api.ResponseInfoDto
import ru.test.testwork.databinding.FragmentViewBinding

@AndroidEntryPoint
class ViewFragment : Fragment() {

    private var _binding: FragmentViewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getInfo()
        _binding = FragmentViewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stateLoadingListener()

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.infoFlow.collect { info ->
                    if (info != null) {
                        setInfo(info)
                    }
                }
            }
        }

        binding.backArrowBtn.setOnClickListener {
            Toast.makeText(this.context, "back button pressed", Toast.LENGTH_SHORT).show()
        }

        binding.enhanceBtn.setOnClickListener {
            Toast.makeText(this.context, "enhance button pressed", Toast.LENGTH_SHORT).show()
        }

    }

    private fun stateLoadingListener() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadInfoState.collect { state ->
                    when (state) {
                        is StateLoading.Loading -> {
                            binding.apply {
                                progressGroup.isVisible = true
                                loadingProgress.isVisible = true
                                loadingRefreshBtn.isVisible = false

                                imageGroup.isVisible = false
                                myScrollView.isVisible = false
                            }
                        }

                        is StateLoading.Success -> {
                            binding.apply {
                                progressGroup.isVisible = false
                                loadingProgress.isVisible = false
                                loadingRefreshBtn.isVisible = false

                                imageGroup.isVisible = true
                                myScrollView.isVisible = true
                            }
                        }

                        else -> {
                            binding.apply {
                                progressGroup.isVisible = true
                                loadingProgress.isVisible = false
                                loadingRefreshBtn.setOnClickListener { viewModel.getInfo() }

                                imageGroup.isVisible = false
                                myScrollView.isVisible = false
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setInfo(info: ResponseInfoDto) {
        binding.apply {
            imageView.loadImage(info.primaryImageSmall!!)
            title.text = info.title
            beginEndDate.text =
                String.format(getString(R.string.year), info.objectBeginDate, info.objectEndDate)

            if (info.artistRole != "") {
                artistRole.text = info.artistRole
                artistNameBio.text = String.format(
                    getString(R.string.bio),
                    info.artistDisplayName,
                    info.artistDisplayBio
                )
            } else {
                artistRole.isVisible = false
                artistNameBio.isVisible = false
            }

            if (info.culture != "") {
                cultureText.text = info.culture
            } else {
                cultureGroup.isVisible = false
            }

            if (info.department != "") {
                departmentText.text = info.department
            } else {
                departmentGroup.isVisible = false
            }

            if (info.medium != null) {
                mediumText.text = info.medium
            } else {
                mediumGroup.isVisible = false
            }

            if (info.dimensions != null) {
                dimensionsText.text = info.dimensions
            } else {
                dimensionsGroup.isVisible = false
            }
        }
    }


    fun ImageView.loadImage(imageURL: String) {
        Glide
            .with(this)
            .load(imageURL)
            .placeholder(R.drawable.ic_launcher_background)
            .into(this)
    }
}