package com.example.composepocapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composepocapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main)

        /*setContent {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .background(color = Color.Gray)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.happy_meal),
                    contentDescription = "Happy Meal",
                    modifier = Modifier.height(300.dp),
                    contentScale = ContentScale.Crop,
                )
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Happy Meal", fontSize = 26.sp)
                        Text(text = "Rs. 150", fontSize = 17.sp, color = Color.Green,modifier = Modifier.align(Alignment.CenterVertically))
                    }
                    *//*Text(text = "Happy Meal", fontSize = 26.sp)*//*
                    Spacer(modifier = Modifier.padding(top = 10.dp))
                    Text(text = "800 Calories", fontSize = 17.sp)
                    *//*Spacer(modifier = Modifier.padding(top = 10.dp))
                    Text(text = "Rs. 150", fontSize = 17.sp, color = Color.Green)*//*
                }
            }
        }*/

        /* setContent {
             Column (modifier = Modifier.padding(16.dp)
             ){
                 Text(text = "Hello Nachiket Salvi")
                 Spacer(modifier = Modifier.padding(top = 10.dp))
                 Button(onClick = {  }) {
                     Text(text = "A Button")
                 }
             }
         }*/
    }
}