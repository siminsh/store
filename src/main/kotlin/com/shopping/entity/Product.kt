package com.shopping.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,
    
    @Column(nullable = false)
    val productId: String,
    
    @Column(nullable = false)
    val description: String,
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val category: ProductCategory,
    
    @Column(nullable = false, precision = 19, scale = 2)
    val price: BigDecimal,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    val cart: Cart? = null,
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    val created: LocalDateTime = LocalDateTime.now(),
    
    @UpdateTimestamp
    @Column(nullable = false)
    val updated: LocalDateTime = LocalDateTime.now()
)

enum class ProductCategory {
    ELECTRONICS,
    CLOTHING,
    BOOKS,
    HOME_GARDEN,
    SPORTS,
    TOYS,
    HEALTH_BEAUTY,
    AUTOMOTIVE,
    FOOD_BEVERAGE,
    OTHER
}
