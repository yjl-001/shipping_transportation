package com.example.shipping.entity;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDto {
    private Integer id;
    private String goods_description;
    private String begin_addr;
    private String dest_addr;
    private float price;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date create_time;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dest_time;
    private Integer consigner_id;

}
