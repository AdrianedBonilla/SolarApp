package com.rayitosdesol.solarapp.model.dao;

import com.rayitosdesol.solarapp.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityDao extends JpaRepository<City, Long> {
    City findByName(String name);
}