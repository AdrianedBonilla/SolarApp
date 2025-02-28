package com.rayitosdesol.solarapp.controller;

import com.rayitosdesol.solarapp.model.dao.DepartmentDao;
import com.rayitosdesol.solarapp.model.entity.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class DepartmentControllerTest {

    @Mock
    private DepartmentDao departmentDao;

    @InjectMocks
    private DepartmentController departmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDepartments() {
        List<Department> departments = new ArrayList<>();
        departments.add(Department.builder().name("City").build());

        when(departmentDao.findAll()).thenReturn(departments);

        ResponseEntity<Object> response = departmentController.getAllDepartaments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(departments, response.getBody());
    }
}