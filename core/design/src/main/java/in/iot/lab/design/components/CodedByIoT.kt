package `in`.iot.lab.design.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iot.lab.design.R


@Composable
fun CodedByIoT(modifier: Modifier) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.iot_logo_highres),
            contentDescription = "IoT Logo",
            Modifier.size(24.dp)
        )

        Spacer(Modifier.padding(horizontal = 3.dp))

        Text(
            text = "Coded with ❤️ and ☕ by IoT Lab",
            color = Color(0xFFF1F2EB),
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.inter_regular))
        )
    }
}