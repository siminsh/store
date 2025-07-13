# Sample Test Data for Shopping Cart API

## Product Categories
- ELECTRONICS
- CLOTHING
- BOOKS
- HOME_GARDEN
- SPORTS
- TOYS
- HEALTH_BEAUTY
- AUTOMOTIVE
- FOOD_BEVERAGE
- OTHER

## Sample Cart Creation Requests

### Cart 1 - Electronics
```json
{
  "countryCode": "US",
  "currency": "USD",
  "products": [
    {
      "productId": "ELEC-001",
      "description": "Wireless Bluetooth Headphones",
      "category": "ELECTRONICS",
      "price": 99.99
    },
    {
      "productId": "ELEC-002",
      "description": "Gaming Mouse",
      "category": "ELECTRONICS",
      "price": 45.50
    }
  ]
}
```

### Cart 2 - Mixed Categories
```json
{
  "countryCode": "CA",
  "currency": "CAD",
  "products": [
    {
      "productId": "BOOK-001",
      "description": "Java Programming Guide",
      "category": "BOOKS",
      "price": 29.99
    },
    {
      "productId": "CLOTH-001",
      "description": "Cotton T-Shirt",
      "category": "CLOTHING",
      "price": 19.99
    }
  ]
}
```

### Cart 3 - Empty Cart
```json
{
  "countryCode": "UK",
  "currency": "GBP",
  "products": []
}
```

## Sample Product Addition Requests

### Electronics Product
```json
{
  "productId": "ELEC-003",
  "description": "Mechanical Keyboard",
  "category": "ELECTRONICS",
  "price": 129.99
}
```

### Sports Product
```json
{
  "productId": "SPORT-001",
  "description": "Basketball",
  "category": "SPORTS",
  "price": 24.99
}
```

### Home & Garden Product
```json
{
  "productId": "HOME-001",
  "description": "Indoor Plant Pot",
  "category": "HOME_GARDEN",
  "price": 15.99
}
```

## Testing Workflow

1. **Create Cart** → Save the returned cartId
2. **Get All Carts** → Verify your cart is listed
3. **Get Cart by ID** → Verify cart details
4. **Add Product** → Add new products to the cart
5. **Get Cart Products** → Verify products were added
6. **Remove Product** → Remove a product (use productId from response)
7. **Get Cart by ID** → Verify product was removed

## Error Testing

### Invalid Product Category
```json
{
  "productId": "INVALID-001",
  "description": "Invalid Product",
  "category": "INVALID_CATEGORY",
  "price": 10.00
}
```

### Missing Required Fields
```json
{
  "productId": "",
  "description": "Product without ID",
  "category": "ELECTRONICS",
  "price": 10.00
}
```

### Negative Price
```json
{
  "productId": "NEGATIVE-001",
  "description": "Negative Price Product",
  "category": "ELECTRONICS",
  "price": -10.00
}
```
