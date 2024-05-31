package com.example.flashlight

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flashlight.ui.theme.FlashLightTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlashLightTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(LocalContext.current)
                }
            }
        }
    }
}

@Composable
fun Greeting(context: Context) {

    var torchStatus by remember {
        mutableStateOf(false)
    }

    var notification by remember {
        mutableStateOf("Off")

    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(all = 20.dp) , verticalArrangement = Arrangement.Center , horizontalAlignment = Alignment.CenterHorizontally) {

        // Text(text = "torch is $notification", fontWeight = FontWeight.ExtraBold)
            Switch(checked = torchStatus, onCheckedChange ={torchStatus = it
                lateinit var cameraID : String
                var camerManager: CameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
                try {

                    cameraID = camerManager.cameraIdList[0]
                }
                catch (e: Exception){

                    e.printStackTrace()
                }
                if(torchStatus){
                        try {
                            camerManager.setTorchMode(cameraID , true)
                            Toast.makeText(context , "Torch Turned On..." , Toast.LENGTH_LONG).show()
                            notification = "on"
                        }catch (e: Exception){
                            e.printStackTrace()
                        }
                }
                else {
                    try {
                        camerManager.setTorchMode(cameraID , false)
                        Toast.makeText(context , "Torch is OFF .." , Toast.LENGTH_LONG).show()
                        notification = "off"
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }

            } )

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FlashLightTheme {
       Greeting(LocalContext.current)
    }
}