package com.challenge.vendingmachine.service.impl;

import com.challenge.vendingmachine.exception.EntityAlreadyExistException;
import com.challenge.vendingmachine.exception.EntityNotExistException;
import com.challenge.vendingmachine.model.Product;
import com.challenge.vendingmachine.repository.ProductRepository;
import com.challenge.vendingmachine.repository.UserRepository;
import com.challenge.vendingmachine.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Product create(Product product) throws EntityAlreadyExistException {
        log.info("Creating product {}", product);
        if (productRepository.findByProductNameIgnoreCase(product.getProductName()) != null) {
            throw new EntityAlreadyExistException("Product name " + product.getProductName() + " already exist");
        }
        if (product.getSeller() != null && product.getSeller().getId() != null && !userRepository.findById(product.getSeller().getId()).isPresent()) {
            throw new EntityNotExistException("Seller id " + product.getSeller().getId() + " invalid");
        }
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, Product product) throws EntityNotExistException {
        if (id == null || !productRepository.findById(id).isPresent())
            throw new EntityNotExistException("Invalid product id : " + id);
        log.info("Updating product {}", product);
        product.setId(id);

        if (product.getSeller() != null && product.getSeller().getId() != null && !userRepository.findById(product.getSeller().getId()).isPresent()) {
            throw new EntityNotExistException("Seller id " + product.getSeller().getId() + " invalid");
        }

        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) throws EntityNotExistException {
        Product p = findById(id);
        if (p != null) {
            log.info("Deleting product with id {}", id);
            productRepository.delete(p);
        } else
            throw new EntityNotExistException("Invalid product id: " + id);

    }

    @Override
    public List<Product> findAll() {
        log.info("find All Product");
        return productRepository.findAll();
    }

    @Override
    public Page<Product> findAllPaged(Pageable pageable) {
        log.info("find All Product paged");
        return productRepository.findAll(pageable);
    }

    @Override
    public Product findById(Long id) {
        log.info("find product by id {}", id);
        if (productRepository.findById(id).isPresent())
            return productRepository.findById(id).get();
        else
            throw new EntityNotExistException("Invalid product id: " + id);
    }

    @Override
    public Product findByProductName(String productName) {
        log.info("find product by name {}", productName);
        return productRepository.findByProductNameIgnoreCase(productName);
    }
}
