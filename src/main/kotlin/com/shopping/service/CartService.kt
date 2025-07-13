package com.shopping.service

import com.shopping.dto.*
import com.shopping.entity.Cart
import com.shopping.entity.Product
import com.shopping.repository.CartRepository
import com.shopping.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional
class CartService(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository
) {
    
    fun createCart(cartRequest: CartRequest): CartResponse {
        val cart = Cart(
            countryCode = cartRequest.countryCode,
            currency = cartRequest.currency
        )
        
        val savedCart = cartRepository.save(cart)
        
        // Add products if provided
        cartRequest.products.forEach { productRequest ->
            addProductToCart(savedCart.id!!, productRequest)
        }
        
        return cartRepository.findByIdWithProducts(savedCart.id!!)?.let { 
            mapToCartResponse(it) 
        } ?: throw RuntimeException("Cart not found after creation")
    }
    
    fun getAllCarts(): List<CartResponse> {
        return cartRepository.findAllWithProducts().map { mapToCartResponse(it) }
    }
    
    fun getCartById(cartId: UUID): CartResponse {
        val cart = cartRepository.findByIdWithProducts(cartId)
            ?: throw RuntimeException("Cart not found with id: $cartId")
        return mapToCartResponse(cart)
    }
    
    fun addProductToCart(cartId: UUID, productRequest: ProductRequest): CartResponse {
        val cart = cartRepository.findById(cartId)
            .orElseThrow { RuntimeException("Cart not found with id: $cartId") }
        
        // Check if product already exists in cart
        val existingProduct = productRepository.findByCartIdAndProductId(cartId, productRequest.productId)
        if (existingProduct != null) {
            throw RuntimeException("Product ${productRequest.productId} already exists in cart")
        }
        
        val product = Product(
            productId = productRequest.productId,
            description = productRequest.description,
            category = productRequest.category,
            price = productRequest.price,
            cart = cart
        )
        
        productRepository.save(product)
        
        return getCartById(cartId)
    }
    
    fun getCartProducts(cartId: UUID): List<ProductResponse> {
        if (!cartRepository.existsById(cartId)) {
            throw RuntimeException("Cart not found with id: $cartId")
        }
        
        return productRepository.findByCartId(cartId).map { mapToProductResponse(it) }
    }
    
    fun deleteProductFromCart(cartId: UUID, productId: String): CartResponse {
        if (!cartRepository.existsById(cartId)) {
            throw RuntimeException("Cart not found with id: $cartId")
        }
        
        val product = productRepository.findByCartIdAndProductId(cartId, productId)
            ?: throw RuntimeException("Product $productId not found in cart $cartId")
        
        productRepository.delete(product)
        
        return getCartById(cartId)
    }
    
    private fun mapToCartResponse(cart: Cart): CartResponse {
        return CartResponse(
            id = cart.id!!,
            countryCode = cart.countryCode,
            currency = cart.currency,
            products = cart.products.map { mapToProductResponse(it) },
            created = cart.created,
            updated = cart.updated
        )
    }
    
    private fun mapToProductResponse(product: Product): ProductResponse {
        return ProductResponse(
            id = product.id,
            productId = product.productId,
            description = product.description,
            category = product.category,
            price = product.price,
            created = product.created,
            updated = product.updated
        )
    }
}
