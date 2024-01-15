package com.agilecrm_automation.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
@Getter
@Setter
@NoArgsConstructor
public class CreateContactResponsePojo {

    private String star_value;
    private String lead_score;
    private List<String> tags;
    private List<Map<String,String>> properties;
    private Long id;
    private String type;
    private Long created_time;
    private Long updated_time;
    private Long last_contacted;
    private Long last_emailed;
    private Long last_campaign_emaild;
    private Long last_called;
    private Long viewed_time;
    private Map<String,Long> viewed;
    private String klout_score;
    private List<Map<String,Object>> tagsWithTime;
    private List<String> campaignStatus;
    private String entity_type;
    private String source;
    private List<String> unsubscribeStatus;
    private List<String> emailBounceStatus;
    private Long formId;
    private List<String> browserId;
    private Long lead_source_id;
    private Long lead_status_id;
    private String is_lead_converted;
    private Long lead_converted_time;
    private String is_duplicate_existed;
    private Long trashed_time;
    private Long restored_time;
    private String is_duplicate_verification_failed;
    private String is_client_import;
    private String concurrent_save_allowed;
    private Map<String,Object> owner;
    private String contact_company_id;

}


