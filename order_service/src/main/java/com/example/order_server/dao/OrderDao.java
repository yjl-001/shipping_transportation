package com.example.order_server.dao;

import java.sql.Date;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDao {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime create_time;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime dest_time;
    private float price;
    private String status;
    private String begin_addr;
    private String dest_addr;
    private String now_addr;
    private Integer goodsId;
    private Integer companyId;
    private Integer driverId;

    private String goods_description;
    private String company_name;
    private String consigner_name;
    private String drivername;
    private String carId;

}
