package com.example.mobilele.repository;

import com.example.mobilele.models.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand,String> {
    @Query("SELECT DISTINCT b FROM Brand b")
    List<Brand> findAllBrands();

    Brand findByName(String brand);
}

