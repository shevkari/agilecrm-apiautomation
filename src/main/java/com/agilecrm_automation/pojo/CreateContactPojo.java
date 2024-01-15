package com.agilecrm_automation.pojo;

import java.util.List;
import java.util.Map;

public class CreateContactPojo {

    private Long id;
    private String star_value;
    private String lead_score;
    private List<String> tags;
    private List<Map<String,String>> properties;

    public void setId(Long id){
        this.id=id;
    }
    public Long getId()
    {
        return id;
    }
    public void setStar_value(String star_value){
        this.star_value=star_value;
    }

    public String getStar_value(){
        return star_value;
    }

    public void setLead_score(String lead_score){
        this.lead_score=lead_score;
    }

    public String getLead_score(){
        return lead_score;
    }

    public void setTags(List<String>tags){
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
