package com.example.editableuserinput

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.DecimalFormatSymbols

class CurrencyMaskTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText = maskFilter(text)

    private fun maskFilter(text: AnnotatedString): TransformedText {
        // £ 200000
        var out = ""
        for (i in text.text.indices) {
            if (i == 0) out += "£ "
            out += toLocalizedDecimal(text.text[i].toString())
        }

        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 0) return offset
                return out.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 2) return offset
                return out.length - 2
            }
        }

        return TransformedText(AnnotatedString(out), numberOffsetTranslator)
    }


    /**
     * Formats input to a decimal. Leaves the only separator (or none), which matches [separator].
     * Examples:
     * 1. [s]="12.12", [separator]=',' -> result= "12,12"
     * 2. [s]="12.12", [separator]='.' -> result= "12.12"
     * 4. [s]="12,12", [separator]='.' -> result= "12.12"
     * 5. [s]="12,12,,..,,,,,34..,", [separator]=',' -> result= "12,1234"
     * 6. [s]="12.12,,..,,,,,34..,", [separator]='.' -> result= "12.1234"
     * 7. [s]="5" -> result= "5"
     */
    private fun toLocalizedDecimal(s: String): String {
        if(s=="." || s==","){
            return ""
        }
        val separator = DecimalFormatSymbols.getInstance().decimalSeparator
        val cleared = s.replace(",", ".")
        val splitted = cleared.split('.').filter { it.isNotBlank() }
        return when (splitted.size) {
            0 -> s
            1 -> cleared.replace('.', separator).replaceAfter(separator, "")
            2 -> splitted.joinToString(separator.toString())
            else -> splitted[0]
                .plus(separator)
                .plus(splitted.subList(1, splitted.size - 1).joinToString(""))
        }
    }
}