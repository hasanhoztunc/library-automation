package com.hasanoztunc.library_automation.features.publishinghouse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublishingHouseRepository extends JpaRepository<PublishingHouse, Long> {

    List<PublishingHouse> findByNameContainingIgnoreCase(String name);
}