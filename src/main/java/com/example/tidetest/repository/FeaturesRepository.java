package com.example.tidetest.repository;

import com.example.tidetest.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeaturesRepository extends JpaRepository<Feature, Long> {
}
