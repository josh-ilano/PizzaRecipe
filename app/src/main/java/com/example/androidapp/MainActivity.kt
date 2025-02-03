package com.example.androidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidapp.ui.theme.AndroidAppTheme
import androidx.compose.ui.unit.*
import androidx.compose.ui.res.painterResource
import com.example.androidapp.utils.pizzaInfo


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidAppTheme {
                Scaffold(
                    topBar = { TopBar() },
                    modifier = Modifier.fillMaxSize())
                {
                    innerPadding ->
                        IngredientsLayout(Modifier.padding(innerPadding))

                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            titleContentColor = MaterialTheme.colorScheme.tertiary,
        ),
        title = {
            Text("Pizza")
            }
    )
}

// the top 3 buttons the user can press
@Composable
fun TitleButton(title: String, color: Color, textToDisp: MutableState<String>, imageId: Int, modifier: Modifier) {
    Card(shape = CardDefaults.shape,
        colors = CardDefaults.cardColors(containerColor = color),
        modifier = modifier
            .clickable {  textToDisp.value = title} // turns it into a button
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()) {
            Text(
                text = title,
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Default
            )
            Image(
                painter = painterResource(id = imageId),
                contentDescription = "Pizza Dough",
                modifier = Modifier.size(70.dp),
            )
        }


    }


}

// The individual instructions that are displayed
@Composable
fun Item(arr: Array<String>, index: Int, numerical: Boolean) {

   var precedingText = if (numerical) (index + 1).toString() + ". " else "- "
    /* determines whether we use numerical bullets or general ones*/

    Text(
        text = precedingText + arr[index],
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily.Default
    )
}

// Displays a list of instructions
@Composable
fun SubIngredientLayout(arr: Array<String>, numerical: Boolean) {
    LazyColumn() {
        items(arr.size) { index ->
            Item(arr, index, numerical)
        }
    }
}


// This is what describes the list of instructions or ingredients below
@Composable
fun Header(str: String, modifier: Modifier) {
    Text(text=str,
        fontFamily = FontFamily.Cursive,
        fontSize = 30.sp,
        modifier = modifier)

}

@Composable
fun IngredientsLayout(modifier: Modifier) {

    val textToDisp = remember { mutableStateOf("Dough") }
    // variable that tracks changes from user press (Initial state is dough)

    Box (modifier = modifier /// root container has modifier with adjusted padding from top bar
        .background(Color(0xFFFDB1B1))
        .fillMaxSize()){
        Text(text = "\uD83C\uDF55",
            fontSize = 300.sp,
            modifier = Modifier.align(Alignment.Center)
                .alpha(0.25F))
        Column() { // Use of Column

            // Displays all the buttons, which show the corresponding ingredients and instructions
            Row() {
                TitleButton("Dough", Color(0xFFDAB900), textToDisp,
                    R.drawable.pizza_dough, Modifier
                        .weight(1f))
                TitleButton("Sauce", Color(0xFFFF0000), textToDisp, R.drawable.sauce, Modifier
                    .weight(1f))
                TitleButton("Toppings", Color(0xFFC8B6A6), textToDisp, R.drawable.toppings, Modifier.
                weight(1f))
            }


            HorizontalDivider(thickness = 5.dp)

            // options which determine what information to display
            if (textToDisp.value == "Dough") {
                Header("Recipe", Modifier.align(Alignment.CenterHorizontally))
                SubIngredientLayout(pizzaInfo.doughRecipe, false)
                HorizontalDivider(thickness = 2.dp)
                Header("Instructions", Modifier.align(Alignment.CenterHorizontally))
                SubIngredientLayout(pizzaInfo.doughInstructions, true)
            }
            else if(textToDisp.value == "Sauce") {
                Header("Recipe", Modifier.align(Alignment.CenterHorizontally))
                SubIngredientLayout(pizzaInfo.sauceRecipe, false)
                HorizontalDivider(thickness = 2.dp)
                Header("Instructions", Modifier.align(Alignment.CenterHorizontally))
                SubIngredientLayout(pizzaInfo.sauceInstructions, true)
            }
            else {
                Header("Recipe", Modifier.align(Alignment.CenterHorizontally))
                SubIngredientLayout(pizzaInfo.toppingsRecipe, false)
                HorizontalDivider(thickness = 2.dp)
                Header("Assembly Instructions", Modifier.align(Alignment.CenterHorizontally))
                SubIngredientLayout(pizzaInfo.assemblyInstructions, true)
                HorizontalDivider(thickness = 2.dp)
                Header("Baking Instructions", Modifier.align(Alignment.CenterHorizontally))
                SubIngredientLayout(pizzaInfo.bakingInstructions, true)
            }
            HorizontalDivider(thickness = 2.dp)

        }
    }


}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidAppTheme {
//        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
////            Top(Modifier.padding(innerPadding))
//        }

    }
}