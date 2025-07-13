package com.shopping.controller

import com.shopping.dto.CartRequest
import com.shopping.dto.CartResponse
import com.shopping.dto.ProductRequest
import com.shopping.dto.ProductResponse
import com.shopping.service.CartService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/carts")
class CartController(
    private val cartService: CartService
) {
    
    @PostMapping
    fun createCart(@Valid @RequestBody cartRequest: CartRequest): ResponseEntity<CartResponse> {
        val createdCart = cartService.createCart(cartRequest)
        return ResponseEntity(createdCart, HttpStatus.CREATED)
    }
    
    @GetMapping
    fun getAllCarts(): ResponseEntity<List<CartResponse>> {
        val carts = cartService.getAllCarts()
        return ResponseEntity.ok(carts)
    }
    
    @GetMapping("/{cartId}")
    fun getCartById(@PathVariable cartId: UUID): ResponseEntity<CartResponse> {
        val cart = cartService.getCartById(cartId)
        return ResponseEntity.ok(cart)
    }
    
    @PutMapping("/{cartId}/products")
    fun addProductToCart(
        @PathVariable cartId: UUID,
        @Valid @RequestBody productRequest: ProductRequest
    ): ResponseEntity<CartResponse> {
        val updatedCart = cartService.addProductToCart(cartId, productRequest)
        return ResponseEntity.ok(updatedCart)
    }
    
    @GetMapping("/{cartId}/products")
    fun getCartProducts(@PathVariable cartId: UUID): ResponseEntity<List<ProductResponse>> {
        val products = cartService.getCartProducts(cartId)
        return ResponseEntity.ok(products)
    }
    
    @DeleteMapping("/{cartId}/products/{productId}")
    fun deleteProductFromCart(
        @PathVariable cartId: UUID,
        @PathVariable productId: String
    ): ResponseEntity<CartResponse> {
        val updatedCart = cartService.deleteProductFromCart(cartId, productId)
        return ResponseEntity.ok(updatedCart)
    }
}
