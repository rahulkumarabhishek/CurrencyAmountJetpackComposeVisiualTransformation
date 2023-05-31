package com.example.editableuserinput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.math.max

class NumberCommaTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {

        val (integerPart, newAnnotatedText) = formatText(text)

        val offsetMapping = ThousandSeparatorOffsetMapping(
            originalIntegerLength = integerPart.length
        )

        return TransformedText(
            text = newAnnotatedText,
            offsetMapping = offsetMapping
        )
    }

    private fun formatText(text: AnnotatedString): Pair<CharSequence, AnnotatedString> {

        val symbols = DecimalFormatSymbols.getInstance(Locale.UK)
        val thousandsSeparator = symbols.groupingSeparator
        val decimalSeparator = symbols.decimalSeparator
        val currencySymbol = symbols.currencySymbol

        val originalText: String = text.text

        val integerPart = if (originalText.length > 2) {
            originalText.subSequence(0, originalText.length - 2)
        } else {
            "0"
        }
        var fractionPart = if (originalText.length >= 2) {
            originalText.subSequence(originalText.length - 2, originalText.length)
        } else {
            originalText
        }
        // Add zeros if the fraction part length is not 2
        if (fractionPart.length < 2) {
            fractionPart = fractionPart.padStart(2, '0')
        }

        val thousandsReplacementPattern = Regex("\\B(?=(?:\\d{3})+(?!\\d))")
        val formattedIntWithThousandsSeparator =
            integerPart.replace(
                thousandsReplacementPattern,
                thousandsSeparator.toString()
            )

        val formattedText = currencySymbol + " " +
                formattedIntWithThousandsSeparator + decimalSeparator + fractionPart
        val newAnnotatedText = AnnotatedString(
            formattedText,
            text.spanStyles,
            text.paragraphStyles
        )
        return Pair(integerPart, newAnnotatedText)
    }
}

class ThousandSeparatorOffsetMapping(
    val originalIntegerLength: Int) : OffsetMapping {
    val currencyOffset :Int = 2 //currencySymbol + " "
    val defaultValueOffset :Int = 4 // "0.00"
    val decimalSeparatorOffset :Int = 1 // "."
    //counting the extra characters shows after transformation
    /**
     * It is important to understand that we prefer for our cursor to remain stationary at
     * the end of the sum. So, if the input is empty, or we just inserted two digits,
     * our output string will always have minimum 4 characters (“0.00”),
     * that’s why we fixed originalToTransformed offset 0, 1, 2 -> 4.
     * */

    override fun originalToTransformed(offset: Int): Int =
        when (offset) {
            0, 1, 2 -> currencyOffset + defaultValueOffset
            else -> offset + currencyOffset + decimalSeparatorOffset + calculateThousandsSeparatorCount(originalIntegerLength)
        }

    /**
     * it must return a value between 0 and the original length
     * or it will return an exception
     *
     * */
    override fun transformedToOriginal(offset: Int): Int {
        if (offset == 0) {
            return 0
        }

        val myOffset: Int = offset - currencyOffset - decimalSeparatorOffset -
                calculateThousandsSeparatorCount(originalIntegerLength)
        if (myOffset < 0) {
            return 0
        }
        return myOffset
    }


    private fun calculateThousandsSeparatorCount(
        intDigitCount: Int
    ) = max((intDigitCount - 1) / 3, 0)
}