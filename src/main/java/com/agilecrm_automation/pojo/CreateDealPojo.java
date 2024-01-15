package com.agilecrm_automation.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class CreateDealPojo {

    private Long id;
    private String name;
    private Float expected_value;
    private Float probability;
    private Long close_date;
    private String milestone;
    private List<Long> contact_ids;
    private List<Map<String,String>> custom_data;




}