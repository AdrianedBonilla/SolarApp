package com.rayitosdesol.solarapp.model.dao;

import com.rayitosdesol.solarapp.model.entity.Department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentDao extends JpaRepository<Department, Long> {
    Department findByName(String name);
}