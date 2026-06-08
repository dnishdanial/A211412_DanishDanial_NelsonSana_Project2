package com.example.a211412_danishdanial_nelsonsana_project2

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

// 1. Define the Data Class structures based on the API JSON response
data class FoodProductResponse(
    val code: String,
    val product: ProductDetails?,
    val status: Int
)

data class ProductDetails(
    @SerializedName("product_name")
    val productName: String?,
    val categories: String?,
    @SerializedName("image_url")
    val imageUrl: String?
)

// 2. Define the Retrofit API Interface
interface FoodApiService {
    @GET("api/v2/product/{barcode}.json")
    suspend fun getProductByBarcode(
        @Path("barcode") barcode: String
    ): FoodProductResponse

    companion object {
        private const val BASE_URL = "https://world.openfoodfacts.org/"

        fun create(): FoodApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FoodApiService::class.java)
        }
    }
}