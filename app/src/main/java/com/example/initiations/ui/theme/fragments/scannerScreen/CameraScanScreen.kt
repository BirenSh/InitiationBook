package com.example.initiations.ui.theme.fragments.scannerScreen

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.File
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.example.initiations.di.viewmodols.LoginViewModel
import com.example.initiations.di.viewmodols.SharedViewModel
import com.example.initiations.util.ImageUtil

class CameraScanScreen(
//    val onResult: (Map<String, String>) -> Unit
) :Screen{
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        val sharedViewModel: SharedViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
            var isProcessing by remember { mutableStateOf(false) }

            CameraCaptureCompose { bitmap ->
                if (!isProcessing) {
                    isProcessing = true
                    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                    val enhancedBitmap = ImageUtil.enhanceBitmap(bitmap)
                    val image = InputImage.fromBitmap(enhancedBitmap, 0)
                    recognizer.process(image)
                        .addOnSuccessListener { visionText ->
                            val parsed = ImageUtil.parseOcrResult(visionText.text)
                            println("===parsed: $parsed")
                            sharedViewModel.setResult(parsed)
                            navigator?.pop()


                        }

                        .addOnFailureListener { e ->
                            Log.e("MLKit", "OCR failed: ${e.message}", e)
                        }

                }

            }

    }

}
@Composable
fun CameraCaptureCompose(
    onImageCaptured: (Bitmap) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val previewView = remember { PreviewView(context) }
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }

    // ✅ Cropper launcher setup
    val cropLauncher = rememberLauncherForActivityResult(
        contract = CropImageContract(),
        onResult = { result ->
            if (result.isSuccessful) {
                val uri = result.uriContent
                uri?.let {
                    val croppedBitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                    onImageCaptured(croppedBitmap)
                }
            } else {
                Log.e("Cropper", "Crop failed: ${result.error}")
            }
        }
    )

    // 📷 Show camera preview
    AndroidView(
        factory = { previewView },
        modifier = Modifier.fillMaxSize()
    )

    LaunchedEffect(Unit) {
        val cameraProvider = cameraProviderFuture.get()

        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        val capture = ImageCapture.Builder().build()
        imageCapture = capture

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            capture
        )
    }

    // 📸 Capture Button
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 40.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(onClick = {
            val photoFile = File.createTempFile("scan_", ".jpg", context.cacheDir)
            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

            imageCapture?.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        val uri = Uri.fromFile(photoFile)

                        // ✅ Launch cropper with options
                        val cropOptions = CropImageContractOptions(
                            uri,
                            CropImageOptions().apply {
                                guidelines = CropImageView.Guidelines.ON
                                fixAspectRatio = false
                                showCropOverlay = true
                                autoZoomEnabled = true
                            }
                        )
                        cropLauncher.launch(cropOptions)
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Log.e("CameraX", "Capture failed: ${exception.message}", exception)
                    }
                }
            )
        }) {
            Text("Capture")
        }
    }
}




