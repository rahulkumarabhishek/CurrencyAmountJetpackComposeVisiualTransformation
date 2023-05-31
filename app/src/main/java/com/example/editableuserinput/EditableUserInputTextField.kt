import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.editableuserinput.CurrencyAmountInputVisualTransformation
import com.example.editableuserinput.R
import com.example.editableuserinput.ui.theme.dimens

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BasicEditUserInput(state: EditableAmountInputState = rememberEditableAmountInputState("0")) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Card(
        Modifier.wrapContentHeight(),
        shape = RoundedCornerShape(MaterialTheme.dimens.dimens4dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.purple_200),
        )
    ) {
        Column(
            Modifier.padding(MaterialTheme.dimens.dimens16dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {

            Text("Investment amount")
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimens4dp))
            BasicTextField(
                value = state.text,
                onValueChange = {
                    state.updateText(it)
                    state.validate(it)
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    state.validate(state.text)
                    keyboardController?.hide()
                }),
                visualTransformation = CurrencyAmountInputVisualTransformation()
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimens4dp))
            if (state.isError) {
                Divider(
                    color = MaterialTheme.colorScheme.error,
                    thickness = 1.dp
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimens16dp))
                Text(
                    text = "The maximum amount that you can invest is £2,000,000",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            } else {
                Divider(
                    color = colorResource(id = R.color.grey_88),
                    thickness = 1.dp
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimens16dp))
                Text(
                    text = "Invest between \u00A3500.00 and \u00A32,000,000",
                    color = colorResource(id = R.color.grey_44),
                    style = MaterialTheme.typography.bodySmall
                )

            }
        }
    }

}

@Composable
fun rememberEditableAmountInputState(initialAmount: String = ""): EditableAmountInputState =
    remember(initialAmount) { EditableAmountInputState(initialAmount) }


class EditableAmountInputState(initialAmount: String = "") {
    var text by mutableStateOf(initialAmount)
        private set
    var isError by mutableStateOf(false)
        private set

    fun updateText(newText: String) {
        text = newText
    }

    fun validate(str: String) {
        isError = str.length > 10
    }

}

@Preview
@Composable
private fun BasicEditUserInputPreview() {
    BasicEditUserInput()
}
