package com.springboot.model.route;

import lombok.Data;

@Data
public class QueryMsg {
    String bank;
     int page;
    int rows;
    int beginRecord;

    /*String datepicker_begin;
    String datepicker_end;
    int recordNum;*/
}
