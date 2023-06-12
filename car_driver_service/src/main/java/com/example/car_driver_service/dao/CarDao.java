package com.example.car_driver_service.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDao {
    private String id;
    private Integer company_id;
    private Integer driver_id;
    private Integer car_age;
    private Integer limit_weight;
    private String statue;
}