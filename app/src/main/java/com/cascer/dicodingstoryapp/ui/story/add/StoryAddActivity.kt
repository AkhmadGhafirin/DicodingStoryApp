package com.cascer.dicodingstoryapp.ui.story.add

import android.Manifest.permission.CAMERA
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cascer.dicodingstoryapp.databinding.ActivityStoryAddBinding
import com.cascer.dicodingstoryapp.ui.story.StoryViewModel
import com.cascer.dicodingstoryapp.ui.story.list.StoryActivity
import com.cascer.dicodingstoryapp.utils.getImageUri
import com.cascer.dicodingstoryapp.utils.gone
import com.cascer.dicodingstoryapp.utils.uriToFile
import com.cascer.dicodingstoryapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoryAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryAddBinding
    private val viewModel: StoryViewModel by viewModels()

    private var currentImageUri: Uri? = null

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
        }
    }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(this, CAMERA) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        with(binding) {
            if (!allPermissionsGranted()) permissionLauncher.launch(CAMERA)
            btnCamera.setOnClickListener {
                currentImageUri = getImageUri(this@StoryAddActivity)
                cameraLauncherResult.launch(currentImageUri)
            }
            btnGallery.setOnClickListener {
                galleryLauncherResult.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            btnAdd.setOnClickListener {
                if (currentImageUri == null) {
                    Toast.makeText(
                        this@StoryAddActivity, "Silahkan pilih gambar", Toast.LENGTH_SHORT
                    ).show()
                } else if (etDesc.text.toString().isEmpty()) {
                    Toast.makeText(
                        this@StoryAddActivity, "Silahkan isi description", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    currentImageUri?.let {
                        viewModel.uploadStory(
                            photo = uriToFile(it, this@StoryAddActivity),
                            desc = etDesc.text.toString()
                        )
                    }
                }
            }
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.ivAddPhoto.setImageURI(it)
        }
    }

    private fun setupViewModel() {
        with(viewModel) {
            isUploadSuccess.observe(this@StoryAddActivity) {
                val intent = Intent(this@StoryAddActivity, StoryActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }

            isLoading.observe(this@StoryAddActivity) {
                if (it) {
                    binding.progressbar.visible()
                    binding.btnAdd.isEnabled = false
                    binding.btnGallery.isEnabled = false
                    binding.btnCamera.isEnabled = false
                } else {
                    binding.progressbar.gone()
                    binding.btnAdd.isEnabled = true
                    binding.btnGallery.isEnabled = true
                    binding.btnCamera.isEnabled = true
                }
            }

            errorMsg.observe(this@StoryAddActivity) {
                Toast.makeText(this@StoryAddActivity, it, Toast.LENGTH_SHORT).show()
            }

            uploadMsg.observe(this@StoryAddActivity) {
                Toast.makeText(this@StoryAddActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val galleryLauncherResult =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            if (it != null) {
                currentImageUri = it
                showImage()
            }
        }

    private val cameraLauncherResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) showImage()
        }
}