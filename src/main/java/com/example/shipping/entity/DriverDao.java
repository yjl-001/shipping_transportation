package com.example.shipping.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDao {
    private Integer id;
    private String drivername;
    private String phone;
    private String sex;
    private Integer company_id;
    private Integer statue;
    private Integer age;
    private Integer driving_age;
    private String statue_desc;
}
