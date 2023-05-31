package com.example.editableuserinput

import BasicEditUserInput
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.editableuserinput.ui.theme.EditableUserInputTheme
import rememberEditableAmountInputState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EditableUserInputTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    //EditableUserInputTextField()

    BasicEditUserInput(rememberEditableAmountInputState())
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EditableUserInputTheme {
        Greeting("Android")
    }
}