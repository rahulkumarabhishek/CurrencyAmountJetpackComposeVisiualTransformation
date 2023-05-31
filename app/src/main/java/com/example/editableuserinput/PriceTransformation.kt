package com.example.editableuserinput

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PriceTransformation(private val prefix: String = "₹ ") : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        var specialCharsCount = 0
        var leadingZeroCount = 0
        val zerosIndex = mutableListOf<Int>()
        val originalToTransformed = mutableListOf<Int>()
        val transformedToOriginal = mutableListOf<Int>()
        val output = prefix + text.text
        output.forEachIndexed { index, char ->
            if (output[index] == "₹".single() || output[index] == " ".single() || output[index] == ",".single()) {
                specialCharsCount++
            } else {
                originalToTransformed.add(index)
            }
            transformedToOriginal.add(index - specialCharsCount)
        }
        originalToTransformed.add(originalToTransformed.maxOrNull()?.plus(1) ?: 0)
        transformedToOriginal.add(transformedToOriginal.maxOrNull()?.plus(1) ?: 0)
        val offsetMapping = object : OffsetMapping {

            override fun originalToTransformed(offset: Int): Int {
                return originalToTransformed[offset]
            }

            override fun transformedToOriginal(offset: Int): Int {
                return transformedToOriginal[offset]
            }
        }
        return TransformedText(
            text = AnnotatedString(output),
            offsetMapping = offsetMapping
        )
    }
}