package com.example.a211412_danishdanial_nelsonsana_project2.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.a211412_danishdanial_nelsonsana_project2.ApiUiState
import com.example.a211412_danishdanial_nelsonsana_project2.UserViewModel

@Composable
fun AddScreen(navController: NavController, viewModel: UserViewModel, innerPadding: PaddingValues) {
    var barcodeInput by remember { mutableStateOf("") }
    var foodName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // NEW: Local state to track Firebase Firestore upload progress
    var isUploadingToCloud by remember { mutableStateOf(false) }

    // 1. Observe the Web API UI State from the ViewModel
    val apiState = viewModel.apiUiState

    // 2. Side-Effect: Automatically populate fields when the API fetches successfully
    LaunchedEffect(apiState) {
        if (apiState is ApiUiState.Success) {
            // Updated to match your exact model mapping structure
            foodName = apiState.product.productName ?: ""
            description = "Category: ${apiState.product.categories ?: "Unknown Food Group"}"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        Text(text = "Add New Food Item", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        // 3. Web API Trigger Field (Barcode Row)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = barcodeInput,
                onValueChange = { barcodeInput = it },
                label = { Text("Enter Food Barcode") },
                placeholder = { Text("e.g., 5449000000996") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                enabled = !isUploadingToCloud // Lock inputs during final submission
            )
            Button(
                onClick = { viewModel.fetchFoodDetailsFromApi(barcodeInput) },
                modifier = Modifier.height(56.dp),
                enabled = !isUploadingToCloud
            ) {
                Text("Fetch")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 4. Conditional Rendering based on Web API Async Network States
        when (apiState) {
            is ApiUiState.Loading -> {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
            }
            is ApiUiState.Error -> {
                Text(
                    text = apiState.message,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            is ApiUiState.Success -> {
                Text(
                    text = "Live data fetched from internet successfully!",
                    color = Color(0xFF4CAF50), // Green color indicator
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            is ApiUiState.Idle -> { /* Do nothing */ }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 5. Standard Form Fields (Can be overridden by Web API data)
        OutlinedTextField(
            value = foodName,
            onValueChange = { foodName = it },
            label = { Text("Food Name") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isUploadingToCloud
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isUploadingToCloud
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 6. Updated Submit Button linking into Cloud Integration (Firebase Firestore)
        Button(
            onClick = {
                if (foodName.isNotBlank()) {
                    isUploadingToCloud = true // Trigger loading UI indicator

                    // Fire the Firestore upload sequence
                    viewModel.uploadFoodToCommunityCloud(
                        title = foodName,
                        description = description
                    ) { success ->
                        isUploadingToCloud = false
                        if (success) {
                            viewModel.resetApiState() // Reset Retrofit network states cleanly
                            navController.popBackStack() // Safely navigate back to Home
                        } else {
                            // Optional: Implement an error dialog or toast notifications if sync fails
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            // Ensure button locks if text is empty OR if upload is actively processing
            enabled = foodName.isNotBlank() && !isUploadingToCloud
        ) {
            if (isUploadingToCloud) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp, color = Color.White)
                    Text("Sharing to Cloud...")
                }
            } else {
                Text("Share Free Food Item")
            }
        }
    }
}