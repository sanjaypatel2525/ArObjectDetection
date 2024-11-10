package com.example.rangolidetector;

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.rangolidetector.R
import com.example.rangolidetector.RangoliDetector
import com.google.ar.core.Anchor
import com.google.ar.core.HitResult
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import java.nio.ByteBuffer

class MainActivity : AppCompatActivity() {
    private lateinit var arFragment: ArFragment
    private lateinit var rangoliDetector: RangoliDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arFragment = supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment
        rangoliDetector = RangoliDetector(assets, "yolo11n_float16.tflite")

        arFragment.setOnTapArPlaneListener { hitResult, _, _ ->
            processHit(hitResult)
        }
    }

    private fun processHit(hitResult: HitResult) {
        // Simulate input and output buffer (adjust input size as needed for your model)
        val inputBuffer = ByteBuffer.allocateDirect(1024) // Replace 1024 with actual input size
        val outputBuffer = Array(1) { FloatArray(4) } // Replace with actual output shape

        rangoliDetector.runInference(inputBuffer, outputBuffer)

        // Assuming outputBuffer contains detection coordinates or confidence
        if (outputBuffer[0][0] > 0.5) { // Replace with your detection threshold
            addLabelToScene(hitResult.createAnchor())
        }
    }

    private fun addLabelToScene(anchor: Anchor) {
        ViewRenderable.builder()
            .setView(this, R.layout.rangoli_label)
            .build()
            .thenAccept { renderable ->
                val anchorNode = AnchorNode(anchor)
                anchorNode.renderable = renderable

                // Optionally, set label text dynamically
                val textView = renderable.view as TextView
                textView.text = "Rangoli Detected!"

                arFragment.arSceneView.scene.addChild(anchorNode)
            }
            .exceptionally { throwable ->
                // Handle any errors that occur during label creation
                throwable.printStackTrace()
                null
            }
    }
}