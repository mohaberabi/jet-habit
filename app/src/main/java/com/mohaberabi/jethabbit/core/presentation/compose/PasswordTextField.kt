package com.mohaberabi.jethabbit.core.presentation.compose


import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.mohaberabi.jethabbit.R
import com.mohaberabi.jethabbit.core.presentation.design_system.theme.JetHabbitTheme


@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onChange: (String) -> Unit = {},
    error: Boolean = false,
    errorText: String? = null
) {

    var showPassword by remember {
        mutableStateOf(false)
    }


    var transformations =
        if (showPassword) VisualTransformation.None else PasswordVisualTransformation()

    AppTextField(
        value = value,
        errorText = errorText,
        error = error,
        onChanged = onChange,
        modifier = modifier,
        label = "Password",
        visualTransformations = transformations,
        placeHolder = "********",
        suffix = {
            Icon(
                modifier = Modifier
                    .clickable {
                        showPassword = !showPassword
                    },
                imageVector = if (showPassword) Icons.Default.Lock else Icons.Default.Lock,
                contentDescription = stringResource(R.string.change_password_visibility)
            )
        }

    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewPassowordTextField() {


    JetHabbitTheme {


        PasswordTextField(
            value = "sasdsad",
        )
    }
}