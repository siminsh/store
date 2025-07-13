package com.shopping.repository

import com.shopping.entity.Cart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CartRepository : JpaRepository<Cart, UUID> {
    
    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.products WHERE c.id = :id")
    fun findByIdWithProducts(id: UUID): Cart?
    
    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.products")
    fun findAllWithProducts(): List<Cart>
}
