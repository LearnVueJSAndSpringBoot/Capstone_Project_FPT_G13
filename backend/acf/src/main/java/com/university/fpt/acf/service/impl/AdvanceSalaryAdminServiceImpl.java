package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.AdvaceSalary;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.form.SearchAdvanceSalaryAdminForm;
import com.university.fpt.acf.repository.AdvanceSalaryAdminCustomRepository;
import com.university.fpt.acf.repository.AdvanceSalaryAdminRepository;
import com.university.fpt.acf.service.AdvanceSalaryAdminService;
import com.university.fpt.acf.service.AdvanceSalaryEmployeeService;
import com.university.fpt.acf.vo.SearchAdvanceSalaryAdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdvanceSalaryAdminServiceImpl implements AdvanceSalaryAdminService {
    @Autowired
    private AdvanceSalaryAdminCustomRepository adminCustomRepository;
    @Autowired
    private AdvanceSalaryAdminRepository adminRepository;
    @Override
    public List<SearchAdvanceSalaryAdminVO> searchAdvanceSalaryAdmin(SearchAdvanceSalaryAdminForm searchForm) {
        List<SearchAdvanceSalaryAdminVO> list = new ArrayList<>();
        try {
            list =adminCustomRepository.searchAdvanceSalary(searchForm);
        }catch (Exception e){
            throw new RuntimeException("Error Advance Salary repository " + e.getMessage());
        }
        return list;
    }

    @Override
    public int totalSearch(SearchAdvanceSalaryAdminForm searchForm) {
        int size;
        try {
            size = adminCustomRepository.totalSearchAdvance(searchForm);
        } catch (Exception e) {
            throw new RuntimeException("Error Advance Salary repository " + e.getMessage());
        }
        return  size;
    }

    @Override
    public SearchAdvanceSalaryAdminVO getDetailAdvanceSalaryAdmin(Long id) {
        SearchAdvanceSalaryAdminVO data ;
        try {
            data =adminRepository.getDetailById(id);
        }catch (Exception e){
            throw new RuntimeException("Error Advance Salary Detail repository " + e.getMessage());
        }
        return data;
    }

    @Override
    public Boolean acceptAddvanceSalary(Long id) {
        boolean check = false;
        try{
            AdvaceSalary data = adminRepository.getDetailAdvanceSalaryById(id);
            data.setAccept(true);
            // thieu setDate
            AccountSercurity accountSercurity = new AccountSercurity();
            data.setModified_by(accountSercurity.getUserName());
            data.setModified_date(LocalDate.now());
            adminRepository.save(data);
            check=true;

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }
}