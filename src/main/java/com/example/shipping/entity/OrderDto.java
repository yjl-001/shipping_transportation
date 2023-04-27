package com.example.shipping.entity;

import java.sql.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time;
    private float price;
    private String status;
    private String begin_addr;
    private String dest_addr;
    private String now_addr;
    private Integer goodsId;
    private Integer companyId;
    private Integer driverId;

    private String goods_description;
    private String company;
    private String driver;

}
