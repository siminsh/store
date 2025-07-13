package com.shopping.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "shopping_carts")
data class Cart(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,
    
    @Column(nullable = false)
    val countryCode: String,
    
    @Column(nullable = false)
    val currency: String,
    
    @OneToMany(mappedBy = "cart", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonManagedReference
    val products: MutableList<Product> = mutableListOf(),
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    val created: LocalDateTime = LocalDateTime.now(),
    
    @UpdateTimestamp
    @Column(nullable = false)
    val updated: LocalDateTime = LocalDateTime.now()
)
