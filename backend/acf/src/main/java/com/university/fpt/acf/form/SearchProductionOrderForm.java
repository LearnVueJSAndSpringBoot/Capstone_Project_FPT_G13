package com.university.fpt.acf.form;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SearchProductionOrderForm {
    private List<Long> listIdContact;
    private String nameProduction;
    private List<LocalDate> dateList;
    private Boolean status;
    private int pageIndex;
    private int pageSize;
}