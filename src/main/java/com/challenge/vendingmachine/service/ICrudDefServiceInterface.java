package com.challenge.vendingmachine.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICrudDefServiceInterface <T, ID>{

    T create(T t);
    T update(ID id, T t);
    void delete(ID id);
    List<T> findAll();
    Page<T> findAllPaged(Pageable pageable);
    T findById(ID id);

}
