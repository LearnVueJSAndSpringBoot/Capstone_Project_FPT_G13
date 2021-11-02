package com.university.fpt.acf.service;

import com.university.fpt.acf.form.SearchAdvanceEmployeeForm;
import com.university.fpt.acf.form.SearchAdvanceSalaryAdminForm;
import com.university.fpt.acf.vo.GetAllAdvanceSalaryEmployeeVO;
import com.university.fpt.acf.vo.SearchAdvanceSalaryAdminVO;

import java.util.List;

public interface AdvanceSalaryAdminService {
    List<SearchAdvanceSalaryAdminVO> searchAdvanceSalaryAdmin(SearchAdvanceSalaryAdminForm searchForm);
    int totalSearch(SearchAdvanceSalaryAdminForm searchForm);
    SearchAdvanceSalaryAdminVO getDetailAdvanceSalaryAdmin(Long id);
    Boolean acceptAddvanceSalary();

}
