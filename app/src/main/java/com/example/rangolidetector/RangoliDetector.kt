package com.example.rangolidetector

import android.content.res.AssetManager
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class RangoliDetector(assetManager: AssetManager, modelPath: String) {
    private val interpreter: Interpreter

    init {
        interpreter = Interpreter(loadModelFile(assetManager, modelPath))
    }

    @Throws(IOException::class)
    private fun loadModelFile(assetManager: AssetManager, modelPath: String): MappedByteBuffer {
        val fileDescriptor = assetManager.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun runInference(inputBuffer: ByteBuffer, outputBuffer: Array<FloatArray>) {
        interpreter.run(inputBuffer, outputBuffer)
    }
}