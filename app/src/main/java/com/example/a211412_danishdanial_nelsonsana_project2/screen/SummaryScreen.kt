package com.example.a211412_danishdanial_nelsonsana_project2.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.a211412_danishdanial_nelsonsana_project2.UserViewModel
import com.example.a211412_danishdanial_nelsonsana_project2.data.BookingEntity
import com.example.a211412_danishdanial_nelsonsana_project2.ui.theme.Project2Theme

@Composable
fun SummaryScreen(navController: NavController, viewModel: UserViewModel, innerPadding: PaddingValues) {
    // Collect the bookings from the ViewModel's StateFlow
    val bookingList by viewModel.allBookings.collectAsState()

    SummaryContent(
        navController = navController,
        bookingList = bookingList,
        innerPadding = innerPadding,
        onClearAll = { viewModel.clearAllBookings() }
    )
}

@Composable
fun SummaryContent(
    navController: NavController,
    bookingList: List<BookingEntity>,
    innerPadding: PaddingValues,
    onClearAll: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text("Booking Summary", fontSize = 24.sp, modifier = Modifier.weight(1f))
            
            if (bookingList.isNotEmpty()) {
                IconButton(onClick = onClearAll) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Clear All",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        if (bookingList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No bookings yet.", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(bookingList) { booking ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Food: ${booking.foodName}", 
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Reserved for: ${booking.userName}", 
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Ref: ${booking.referenceNumber}", 
                                style = MaterialTheme.typography.labelSmall, 
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SummaryScreenPreview() {
    Project2Theme {
        SummaryContent(
            navController = rememberNavController(),
            bookingList = listOf(
                BookingEntity(id = 1, foodName = "Chicken Rice", userName = "Danish Danial", referenceNumber = "REF123456"),
                BookingEntity(id = 2, foodName = "Nasi Lemak", userName = "Nelson Sana", referenceNumber = "REF654321")
            ),
            innerPadding = PaddingValues(0.dp),
            onClearAll = {}
        )
    }
}

