package com.example.editableuserinput

import android.text.Editable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.util.Currency
import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.min

class AmountVisualTransformation : VisualTransformation {
    private  val DEFAULT_GROUPING_SEPARATOR = ","
    private val CURRENCY_LOCALE = Locale.UK
    private var currencyCode: String? = null
    var currencySymbol: String? = null
        private set
    private var matcher: Matcher?  = buildRegex("£")
    private var lastValidText: String? = null

    override fun filter(text: AnnotatedString): TransformedText {
        matcher?.reset(text.toString().replace(DEFAULT_GROUPING_SEPARATOR, ""))
            lastValidText = text.toString()
        return TransformedText(text = AnnotatedString(lastValidText?:""), offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
               return text.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                return  text.length
            }

        })
    }

    private fun format(s: CharSequence, cursor: Int): String {
        return ""
    }

    fun setCurrency(currencyCode: String) {
        if (this.currencyCode != currencyCode) {
            this.currencyCode = currencyCode
            currencySymbol = formatCurrency(currencyCode)
            lastValidText = currencySymbol
            matcher = buildRegex(currencySymbol)
        }
    }

    fun formatCurrency(currencyCode: String?): String {
        return if (currencyCode.isNullOrEmpty().not()) {
            Currency.getInstance(currencyCode).getSymbol(CURRENCY_LOCALE)
        } else {
            ""
        }
    }

    private fun buildRegex(currencySymbol: String?): Matcher {
        return Pattern.compile(
            "^" + Pattern.quote(
                currencySymbol ?: ""
            ) + "(\\d{0,9}(\\.\\d{0,2})?)$"
        ).matcher("")
    }

   /* fun afterTextChanged(s: String) {
        matcher?.reset(s.replace(DEFAULT_GROUPING_SEPARATOR, ""))
        if (matcher?.matches() == false) {
            val offset = s.length + (lastValidText?.length ?: 0)
            setText(lastValidText)
            setSelection(min(offset, lastValidText?.length ?: 0))
        } else {
            if (selectionStart == 0 && text.toString().length > currencySymbol?.length ?: 0) {
                setSelection(currencySymbol?.length ?: 0)
            }
            lastValidText = text.toString()
        }
    }*/

}