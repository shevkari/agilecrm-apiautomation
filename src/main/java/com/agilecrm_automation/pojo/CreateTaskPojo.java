package com.agilecrm_automation.pojo;

public class CreateTaskPojo {
    private String progress;
    private String subject;
    private String type;
    private Long due;
    private String task_ending_time;
    private String priority_type;
    private String status;
    private String[] contacts;
    public void setProgress(String progress){
        this.progress=progress;
    }
    public String getProgress(){
        return progress;
    }
    public void setSubject(String subject){
        this.subject=subject;
    }
    public String getSubject(){
        return subject;
    }
    public void setType(String type){
        this.type=type;
    }
    public String getType(){
        return type;
    }
    public void setDue(Long due){
        this.due=due;
    }
    public Long getDue(){
        return due;
    }
    public void setTask_ending_time(String task_ending_time){
        this.task_ending_time=task_ending_time;
    }
    public String getTask_ending_time(){
        return task_ending_time;
    }
    public void setPriority_type(String priority_type){
        this.priority_type=priority_type;
    }
    public String getPriority_type(){
        return priority_type;
    }
    public void setStatus(String status){
        this.status=status;
    }
    public String getStatus(){
        return status;
    }
    public void setContacts(String[] contacts){
        this.contacts=contacts;
    }
    public String[] getContacts(){
        return contacts;
    }
}
