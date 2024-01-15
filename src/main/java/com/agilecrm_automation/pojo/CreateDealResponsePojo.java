package com.agilecrm_automation.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
@Getter
@Setter
@NoArgsConstructor
public class CreateDealResponsePojo {

    private String name;
    private Float expected_value;
    private Float probability;
    private Long close_date;
    private String milestone;
    private List<Long> contact_ids;
    private List<Map<String,String>> custom_data;
    private String colorName;
    private Long id;
    private Boolean apply_discount;
    private Float discount_value;
    private Float discount_amt;
    private String discount_type;
    private List<String> products;
    private String owner_id;
    private String created_time;
    private Long milestone_changed_time;
    private String entity_type;
    private List<String> notes;
    private List<String> note_ids;
    private Long note_created_time;
    private Long pipeline_id;
    private Boolean archived;
    private Long won_date;
    private Long lost_reason_id;
    private Long deal_source_id;
    private Float total_deal_value;
    private Long updated_time;
    private Boolean isCurrencyUpdateRequired;
    private Float currency_conversion_value;
    private List<String> tags;
    private List<String> tagsWithTime;
    private Map<String,Object> owner;
    private List<Map<String,Object>> contacts;
    private String description;


 }