package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.model.entity.Department;
import com.rayitosdesol.solarapp.model.dao.DepartmentDao;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class DepartmentController {

    private final DepartmentDao cityDao;

    public DepartmentController(DepartmentDao cityDao) {
        this.cityDao = cityDao;
    }

    @GetMapping("departments")
    public List<Department> getAllCities() {
        return cityDao.findAll();
    }
}