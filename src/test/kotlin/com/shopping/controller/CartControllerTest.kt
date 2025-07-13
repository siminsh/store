package com.shopping.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.shopping.dto.CartRequest
import com.shopping.dto.CartResponse
import com.shopping.dto.ProductRequest
import com.shopping.dto.ProductResponse
import com.shopping.entity.ProductCategory
import com.shopping.service.CartService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(CartController::class)
class CartControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var cartService: CartService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private val cartId = UUID.randomUUID()
    private val productId = "PROD-001"
    
    private val sampleProductRequest = ProductRequest(
        productId = productId,
        description = "Test Product",
        category = ProductCategory.ELECTRONICS,
        price = BigDecimal("99.99")
    )
    
    private val sampleProductResponse = ProductResponse(
        id = UUID.randomUUID(),
        productId = productId,
        description = "Test Product",
        category = ProductCategory.ELECTRONICS,
        price = BigDecimal("99.99"),
        created = LocalDateTime.now(),
        updated = LocalDateTime.now()
    )
    
    private val sampleCartRequest = CartRequest(
        countryCode = "US",
        currency = "USD",
        products = listOf(sampleProductRequest)
    )
    
    private val sampleCartResponse = CartResponse(
        id = cartId,
        countryCode = "US",
        currency = "USD",
        products = listOf(sampleProductResponse),
        created = LocalDateTime.now(),
        updated = LocalDateTime.now()
    )

    @Test
    fun `createCart should return created cart with 201 status`() {
        // Given
        given(cartService.createCart(any())).willReturn(sampleCartResponse)

        // When & Then
        mockMvc.perform(
            post("/api/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleCartRequest))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(cartId.toString()))
            .andExpect(jsonPath("$.countryCode").value("US"))
            .andExpect(jsonPath("$.currency").value("USD"))
            .andExpect(jsonPath("$.products").isArray)
            .andExpect(jsonPath("$.products[0].productId").value(productId))

        verify(cartService).createCart(any())
    }

    @Test
    fun `createCart should return 400 for invalid request`() {
        // Given
        val invalidRequest = CartRequest(
            countryCode = "", // Invalid: empty country code
            currency = "USD",
            products = emptyList()
        )

        // When & Then
        mockMvc.perform(
            post("/api/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest))
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `getAllCarts should return list of carts`() {
        // Given
        given(cartService.getAllCarts()).willReturn(listOf(sampleCartResponse))

        // When & Then
        mockMvc.perform(get("/api/carts"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$[0].id").value(cartId.toString()))
            .andExpect(jsonPath("$[0].countryCode").value("US"))

        verify(cartService).getAllCarts()
    }

    @Test
    fun `getCartById should return cart when found`() {
        // Given
        given(cartService.getCartById(cartId)).willReturn(sampleCartResponse)

        // When & Then
        mockMvc.perform(get("/api/carts/$cartId"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(cartId.toString()))
            .andExpect(jsonPath("$.countryCode").value("US"))

        verify(cartService).getCartById(cartId)
    }

    @Test
    fun `addProductToCart should add product and return updated cart`() {
        // Given
        val updatedCartResponse = sampleCartResponse.copy(
            products = sampleCartResponse.products + sampleProductResponse
        )
        given(cartService.addProductToCart(any(), any())).willReturn(updatedCartResponse)

        // When & Then
        mockMvc.perform(
            put("/api/carts/$cartId/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleProductRequest))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(cartId.toString()))
            .andExpect(jsonPath("$.products").isArray)

        verify(cartService).addProductToCart(cartId, sampleProductRequest)
    }

    @Test
    fun `addProductToCart should return 400 for invalid product`() {
        // Given
        val invalidProductRequest = ProductRequest(
            productId = "", // Invalid: empty product ID
            description = "Test Product",
            category = ProductCategory.ELECTRONICS,
            price = BigDecimal("99.99")
        )

        // When & Then
        mockMvc.perform(
            put("/api/carts/$cartId/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidProductRequest))
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `getCartProducts should return list of products`() {
        // Given
        given(cartService.getCartProducts(cartId)).willReturn(listOf(sampleProductResponse))

        // When & Then
        mockMvc.perform(get("/api/carts/$cartId/products"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$[0].productId").value(productId))

        verify(cartService).getCartProducts(cartId)
    }

    @Test
    fun `deleteProductFromCart should remove product and return updated cart`() {
        // Given
        val updatedCartResponse = sampleCartResponse.copy(products = emptyList())
        given(cartService.deleteProductFromCart(any(), any())).willReturn(updatedCartResponse)

        // When & Then
        mockMvc.perform(delete("/api/carts/$cartId/products/$productId"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(cartId.toString()))

        verify(cartService).deleteProductFromCart(cartId, productId)
    }

    @Test
    fun `should handle malformed JSON gracefully`() {
        // When & Then
        mockMvc.perform(
            post("/api/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ invalid json }")
        )
            .andExpect(status().isBadRequest)
    }
}
