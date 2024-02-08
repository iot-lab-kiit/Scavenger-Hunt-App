package `in`.iot.lab.design.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.theme.ScavengerHuntTheme

@Preview
@Composable
fun ButtonPreview() {
    ScavengerHuntTheme {
        Column {
            PrimaryButton(onClick = {}) {
                Text(text = "Button")
            }
            PrimaryButton(
                onClick = {},
                enabled = false
            ) {
                Text(text = "Button")
            }
            SecondaryButton(onClick = {}) {
                Text(text = "Button")
            }
            SecondaryButton(
                onClick = {},
                enabled = false
            ) {
                Text(text = "Button")
            }

            NextButton {

            }
        }
    }
}

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: RoundedCornerShape = RoundedCornerShape(percent = 50),
    contentPadding: PaddingValues = PaddingValues(25.dp, 0.dp),
    content: @Composable (RowScope.() -> Unit)
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        contentPadding = contentPadding,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
        ),
        shape = shape,
        content = content
    )
}


@Composable
fun SecondaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: RoundedCornerShape = RoundedCornerShape(percent = 50),
    contentPadding: PaddingValues = PaddingValues(25.dp, 0.dp),
    content: @Composable (RowScope.() -> Unit)
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        contentPadding = contentPadding,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        ),
        shape = shape,
        content = content
    )
}


@Composable
fun NextButton(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Filled.KeyboardArrowRight,
    contentDescription: String = "Next Button",
    onClick: () -> Unit
) {

    PrimaryButton(
        modifier = modifier.size(24.dp),
        onClick = onClick,
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )
    }
}