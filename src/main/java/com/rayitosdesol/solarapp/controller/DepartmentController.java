package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.model.entity.Department;
import com.rayitosdesol.solarapp.model.dao.DepartmentDao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class DepartmentController {

    private final DepartmentDao departmentDao;

    public DepartmentController(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @GetMapping("departments")
    public ResponseEntity<Object> getAllDepartaments() {
            List<Department> departments = departmentDao.findAll();
            return ResponseEntity.ok(departments);
    }
}