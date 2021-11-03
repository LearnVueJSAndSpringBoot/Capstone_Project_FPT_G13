package com.university.fpt.acf.form;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SearchBonusAdminForm {
    private String title;
    private List<LocalDate> date;
    private Boolean status;
    private Integer pageIndex;
    private Integer pageSize;
}
