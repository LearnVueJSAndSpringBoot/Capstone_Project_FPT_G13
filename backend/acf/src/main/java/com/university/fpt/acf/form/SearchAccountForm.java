package com.university.fpt.acf.form;

import lombok.Data;

@Data
public class SearchAccountForm {
    private String name;
    private Integer pageIndex;
    private Integer pageSize;
}
