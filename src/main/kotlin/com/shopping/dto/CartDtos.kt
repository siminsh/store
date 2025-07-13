package com.shopping.dto

import com.shopping.entity.ProductCategory
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class CartRequest(
    @field:NotBlank(message = "Country code is required")
    val countryCode: String,
    
    @field:NotBlank(message = "Currency is required")
    val currency: String,
    
    val products: List<ProductRequest> = emptyList()
)

data class CartResponse(
    val id: UUID,
    val countryCode: String,
    val currency: String,
    val products: List<ProductResponse>,
    val created: LocalDateTime,
    val updated: LocalDateTime
)

data class ProductRequest(
    @field:NotBlank(message = "Product ID is required")
    val productId: String,
    
    @field:NotBlank(message = "Description is required")
    val description: String,
    
    @field:NotNull(message = "Category is required")
    val category: ProductCategory,
    
    @field:NotNull(message = "Price is required")
    @field:Positive(message = "Price must be positive")
    val price: BigDecimal
)

data class ProductResponse(
    val id: UUID?,
    val productId: String,
    val description: String,
    val category: ProductCategory,
    val price: BigDecimal,
    val created: LocalDateTime,
    val updated: LocalDateTime
)
