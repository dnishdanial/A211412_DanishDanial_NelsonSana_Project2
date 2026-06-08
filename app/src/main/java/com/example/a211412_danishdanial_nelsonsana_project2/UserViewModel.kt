package com.example.a211412_danishdanial_nelsonsana_project2

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.a211412_danishdanial_nelsonsana_project2.data.BookingEntity
import com.example.a211412_danishdanial_nelsonsana_project2.data.BookingRepository
import com.example.a211412_danishdanial_nelsonsana_project2.data.KasihDatabase
import com.example.a211412_danishdanial_nelsonsana_project2.FoodApiService
import com.example.a211412_danishdanial_nelsonsana_project2.ProductDetails
import com.example.a211412_danishdanial_nelsonsana_project2.model.CommunityFoodItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class UserProfile(
    val name: String = "User",
    val rescuedMeals: Int = 5,
    val profileImage: Int = R.drawable.pp,
    val isVerified: Boolean = false
)

sealed interface ApiUiState {
    object Idle : ApiUiState
    object Loading : ApiUiState
    data class Success(val product: ProductDetails) : ApiUiState
    data class Error(val message: String) : ApiUiState
}

class UserViewModel(application: Application) : AndroidViewModel(application) {

    var userState by mutableStateOf(UserProfile())
        private set

    fun updateName(newName: String) {
        userState = userState.copy(
            name = newName,
            isVerified = true
        )
    }

    // =================================================================
    // PILLAR 1: DATA FROM THE INTERNET (RETROFIT WEB API) [cite: 22]
    // =================================================================
    var apiUiState: ApiUiState by mutableStateOf(ApiUiState.Idle)
        private set

    private val foodApiService = FoodApiService.create()

    fun fetchFoodDetailsFromApi(barcode: String) {
        if (barcode.isBlank()) {
            apiUiState = ApiUiState.Error("Barcode cannot be empty")
            return
        }

        viewModelScope.launch {
            apiUiState = ApiUiState.Loading
            try {
                val response = foodApiService.getProductByBarcode(barcode)
                if (response.status == 1 && response.product != null) {
                    apiUiState = ApiUiState.Success(response.product)
                } else {
                    apiUiState = ApiUiState.Error("Product not found in database.")
                }
            } catch (e: Exception) {
                apiUiState = ApiUiState.Error("Network error: ${e.localizedMessage ?: "Unknown Error"}")
            }
        }
    }

    fun resetApiState() {
        apiUiState = ApiUiState.Idle
    }

    // =================================================================
    // PILLAR 2: LOCAL PERSISTENCE (ROOM DATABASE)
    // =================================================================
    private val repository: BookingRepository
    val allBookings: StateFlow<List<BookingEntity>>

    init {
        val dao = KasihDatabase
            .getDatabase(application)
            .bookingDao()

        repository = BookingRepository(dao)

        allBookings = repository.allBookings
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }

    fun addBooking(food: String, name: String) {
        val refNum = (100000..999999)
            .random()
            .toString()

        viewModelScope.launch {
            repository.insertBooking(
                BookingEntity(
                    foodName = food,
                    userName = name,
                    referenceNumber = "REF$refNum"
                )
            )
        }
    }

    fun clearAllBookings() {
        viewModelScope.launch {
            repository.deleteAllBookings()
        }
    }

    // =================================================================
    // PILLAR 3: CLOUD INTEGRATION (FIREBASE FIRESTORE) [cite: 21]
    // =================================================================
    private val firestore = FirebaseFirestore.getInstance()

    fun uploadFoodToCommunityCloud(title: String, description: String, onComplete: (Boolean) -> Unit) {
        if (title.isBlank()) {
            onComplete(false)
            return
        }

        viewModelScope.launch {
            // Generate a secure unique Document ID inside the "community_food" collection path
            val docRef = firestore.collection("community_food").document()

            val cloudItem = CommunityFoodItem(
                id = docRef.id,
                title = title,
                description = description,
                storeName = "Donor: ${userState.name}" // Dynamically links the verified user's profile name!
            )

            // Asynchronously post the custom object straight into the remote Firestore cloud [cite: 31]
            docRef.set(cloudItem)
                .addOnSuccessListener {
                    onComplete(true)
                }
                .addOnFailureListener {
                    onComplete(false)
                }
        }
    }
}