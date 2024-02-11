package `in`.iot.lab.teambuilding.view.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iot.lab.design.components.PrimaryButton
import `in`.iot.lab.design.components.SecondaryButton
import `in`.iot.lab.design.R


@Composable
fun ConfirmDialogUI(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes imageId: Int = R.drawable.confirm_png,
    onDismiss: () -> Unit,
    onContinue: () -> Unit
) {

    Card(shape = RoundedCornerShape(8.dp)) {
        Column(
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Local Image
            Image(
                painter = painterResource(id = imageId),
                contentDescription = null,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
            )


            // Text to Show
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = text,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )


            // Row containing Cancel and Try Again Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Cancel Button
                SecondaryButton(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1F),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        text = "Cancel",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }


                // Continue Button
                PrimaryButton(
                    onClick = onContinue,
                    modifier = Modifier.weight(1F),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        text = "Continue",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}