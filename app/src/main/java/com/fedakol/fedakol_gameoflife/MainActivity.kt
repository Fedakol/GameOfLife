package com.fedakol.fedakol_gameoflife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fedakol.fedakol_gameoflife.ui.theme.FedakolGameOfLifeTheme
import com.fedakol.fedakol_gameoflife.ui.theme.FedakolGameOfLifeTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FedakolGameOfLifeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TheGameOfLife()
                }
            }
        }
    }
}

@Composable
fun TheGameOfLife() {
    fun CellChooser(): String {
        return if (Random.nextBoolean()) "живая" else "мертвая"
    }
    // Function to get the corresponding text based on cell type
    fun CellSubText(CellType: String): String {
        return when (CellType) {
            "живая" -> "и уже плодится"
            "мертвая" -> "прям совсем"
            "жизнь зародилась" -> "время выживания"
            "жизнь погибла" -> "не повезло"
            else -> "ты как умудрился сломать клетки?"
        }
    }

    // Function to get the corresponding icon based on cell type
    fun CellIcon(CellType: String): ImageVector {
        return when (CellType) {
            "живая" -> Icons.Default.Check 
            "мертвая" -> Icons.Default.Close 
            "жизнь зародилась" -> Icons.Default.AddCircle 
            "жизнь погибла" -> Icons.Default.Delete 
            else -> Icons.Default.Warning 
        }
    }

    val cards = remember { mutableStateListOf<String>() }
    val lastThreeCells = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .padding(top = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Клеточное наполнение",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 5.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .weight(1f, false)
        ) {
            cards.forEach { cardContent ->
                Card(
                    modifier = Modifier
                        .padding(all = 5.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = CellIcon(cardContent),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                        )
                        Column {
                            Text(text = cardContent, fontSize = 20.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = CellSubText(cardContent), fontSize = 16.sp)
                        }
                    }
                }
            }
        }
        Button(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            onClick = {
                // Add a new cell
                val cellType = CellChooser()
                cards.add(cellType)

                // Update the last three cells list
                lastThreeCells.add(cellType)
                if (lastThreeCells.size > 3) {
                    lastThreeCells.removeAt(0)
                }

                // Check if three cells are of the same type
                if (lastThreeCells.size == 3) {
                    if (lastThreeCells.all { it == "живая" }) {
                        cards.add("жизнь зародилась")
                        lastThreeCells.clear()  // Reset after special card is added
                    } else if (lastThreeCells.all { it == "мертвая" }) {
                        cards.add("жизнь погибла")
                        lastThreeCells.clear()  // Reset after special card is added
                    }
                }
            }
        ) {
            Text(text = "Сотворить")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FedakolGameOfLifeTheme {
        TheGameOfLife()
    }
}
