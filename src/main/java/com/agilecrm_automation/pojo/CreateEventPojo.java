package com.agilecrm_automation.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateEventPojo {
            private String title;
            private Boolean allDay;
            private String color;
            private Long start;
            private Long end;
            private List<Long> contacts;
}
