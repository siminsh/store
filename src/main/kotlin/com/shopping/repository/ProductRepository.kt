package com.shopping.repository

import com.shopping.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductRepository : JpaRepository<Product, UUID> {
    
    @Query("SELECT p FROM Product p WHERE p.cart.id = :cartId")
    fun findByCartId(cartId: UUID): List<Product>
    
    @Query("SELECT p FROM Product p WHERE p.cart.id = :cartId AND p.productId = :productId")
    fun findByCartIdAndProductId(cartId: UUID, productId: String): Product?
    
    @Query("DELETE FROM Product p WHERE p.cart.id = :cartId AND p.productId = :productId")
    fun deleteByCartIdAndProductId(cartId: UUID, productId: String)
}
