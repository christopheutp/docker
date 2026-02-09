package com.training.shop.service;

import com.training.shop.model.Product;
import com.training.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    
    private final ProductRepository productRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    
    private static final String CACHE_KEY = "products:all";
    private static final Duration CACHE_TTL = Duration.ofMinutes(5);
    
    public List<Product> findAll() {
        try {
            @SuppressWarnings("unchecked")
            List<Product> cached = (List<Product>) redisTemplate.opsForValue().get(CACHE_KEY);
            if (cached != null) {
                log.debug("Cache HIT - products:all");
                return cached;
            }
        } catch (Exception e) {
            log.warn("Redis non disponible, lecture directe depuis DB");
        }
        
        log.debug("Cache MISS - lecture depuis DB");
        List<Product> products = productRepository.findAll();
        
        try {
            redisTemplate.opsForValue().set(CACHE_KEY, products, CACHE_TTL);
        } catch (Exception e) {
            log.warn("Impossible de mettre en cache");
        }
        
        return products;
    }
    
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
    
    public Product create(Product product) {
        Product saved = productRepository.save(product);
        invalidateCache();
        log.info("Produit créé: {} (ID: {})", saved.getName(), saved.getId());
        return saved;
    }
    
    public Product update(Long id, Product details) {
        return productRepository.findById(id)
            .map(product -> {
                product.setName(details.getName());
                product.setDescription(details.getDescription());
                product.setPrice(details.getPrice());
                product.setStock(details.getStock());
                product.setCategory(details.getCategory());
                Product updated = productRepository.save(product);
                invalidateCache();
                log.info("Produit mis à jour: {} (ID: {})", updated.getName(), id);
                return updated;
            })
            .orElseThrow(() -> new RuntimeException("Produit non trouvé: " + id));
    }
    
    public void delete(Long id) {
        productRepository.deleteById(id);
        invalidateCache();
        log.info("Produit supprimé (ID: {})", id);
    }
    
    public List<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }
    
    public List<Product> search(String q) {
        return productRepository.search(q);
    }
    
    public List<Product> findLowStock(Integer threshold) {
        return productRepository.findByStockLessThan(threshold);
    }
    
    private void invalidateCache() {
        try {
            redisTemplate.delete(CACHE_KEY);
            log.debug("Cache invalidé");
        } catch (Exception e) {
            log.warn("Impossible d'invalider le cache");
        }
    }
}
