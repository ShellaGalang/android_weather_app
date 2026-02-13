package com.example.weatherapp.ui.current.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.DeepTeal

@Composable
fun SearchBarUI(
    city: String,
    onChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = city,
            onValueChange = onChange,
            label = {
                Text(
                    text = "Enter city name",
                    color = DeepTeal.copy(alpha = 0.7f)
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White.copy(alpha = 0.3f),
                unfocusedContainerColor = Color.White.copy(alpha = 0.15f),

                focusedTextColor = DeepTeal,
                unfocusedTextColor = DeepTeal,

                focusedBorderColor = DeepTeal.copy(alpha = 0.5f),
                unfocusedBorderColor = DeepTeal.copy(alpha = 0.2f),

                cursorColor = DeepTeal
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.weight(1f),
            // Set Keyboard to Search mode
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            // Trigger the search when the keyboard button is pressed
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClick()
                    focusManager.clearFocus() // Closes the keyboard
                }
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = {
                onSearchClick()
                focusManager.clearFocus()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = DeepTeal,
                contentColor = Color.White
            ),
            shape = (RoundedCornerShape(16.dp)),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
            modifier = Modifier.height(50.dp)
        ) {
            Text(
                text = stringResource(R.string.btn_search_label),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview
@Composable
fun SearchBarUIPreview() {
    var city = "Malolos"

    SearchBarUI(
        city = city,
        onChange = { city = it },
        onSearchClick = { }
    )
}


