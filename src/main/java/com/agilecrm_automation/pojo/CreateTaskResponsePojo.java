package com.agilecrm_automation.pojo;

import java.util.List;
import java.util.Map;

public class CreateTaskResponsePojo {

    public Long id;
    public String type;
    public Long due;
    public Long task_completed_time;
    public Long task_start_time;
    public Long created_time;
    public Boolean is_complete;
    public List<Object> contacts;
    public String entity_type;
    public List<String> notes;
    public List<String> note_ids;
    public Long progress;
    public String status;
    public List<Object> deal_ids;
    public Map<String,Object> taskOwner;
    public List<Object> deals;
}
