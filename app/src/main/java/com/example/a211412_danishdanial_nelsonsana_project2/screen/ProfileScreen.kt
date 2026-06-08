package com.example.a211412_danishdanial_nelsonsana_project2.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.a211412_danishdanial_nelsonsana_project2.UserViewModel
import com.example.a211412_danishdanial_nelsonsana_project2.ui.theme.Project2Theme

@Composable
fun ProfileScreen(navController: NavController, viewModel: UserViewModel, innerPadding: PaddingValues) {
    val user = viewModel.userState
    var nameInput by remember { mutableStateOf(user.name) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Profile", fontSize = 24.sp)
        
        Spacer(modifier = Modifier.height(24.dp))

        // Profile Image (using the resource from UserProfile)
        Image(
            painter = painterResource(id = user.profileImage),
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        var isEditing by remember { mutableStateOf(false) }

        if (!isEditing) {
            Text(text = user.name, fontSize = 20.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { isEditing = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Update Profile")
            }
        } else {
            OutlinedTextField(
                value = nameInput,
                onValueChange = { nameInput = it },
                label = { Text("Display Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    viewModel.updateName(nameInput)
                    isEditing = false
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Name")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Rescued Meals")
                Text(text = "${user.rescuedMeals}", style = MaterialTheme.typography.headlineSmall)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("summary") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text("View Booked Items")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    Project2Theme {
        ProfileScreen(
            navController = rememberNavController(),
            viewModel = viewModel(),
            innerPadding = PaddingValues(0.dp)
        )
    }
}

