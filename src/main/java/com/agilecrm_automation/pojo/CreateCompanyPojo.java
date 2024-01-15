package com.agilecrm_automation.pojo;

import java.util.List;
import java.util.Map;

public class CreateCompanyPojo {

    private Long id;
    private String type;
    private int star_value;
    private int lead_score;
    private List<String> tags;
    private List<Map<String,String>> properties;

    public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return id;
    }

    public void setType(String type){
        this.type=type;
    }

    public String getType(){
        return type;
    }

    public void setStar_value(int star_value){
        this.star_value=star_value;
    }
    public int getStar_value(){
        return star_value;
    }
    public void setLead_score(int lead_score) {
        this.lead_score = lead_score;
    }

    public int getLead_score(){
        return lead_score;
    }

    public void setTags(List<String> tags){
        this.tags=tags;
    }

    public List<String> getTags(){
        return tags;
    }

    public void setProperties(List<Map<String,String>> properties){
        this.properties=properties;
    }

    public List<Map<String,String>> getProperties(){
        return properties;
    }
}
