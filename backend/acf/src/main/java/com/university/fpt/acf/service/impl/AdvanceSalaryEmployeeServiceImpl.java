package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.AdvaceSalary;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.entity.PersonaLeaveApplication;
import com.university.fpt.acf.form.AddAdvanceSalaryEmployeeForm;
import com.university.fpt.acf.form.SearchAdvanceEmployeeForm;
import com.university.fpt.acf.form.UpdateAdvanceSalaryEmployeeForm;
import com.university.fpt.acf.repository.AdvanceSalaryEmployeeCustomRepository;
import com.university.fpt.acf.repository.AdvanceSalaryEmployeeRepository;
import com.university.fpt.acf.repository.EmployeeRepository;
import com.university.fpt.acf.service.AdvanceSalaryEmployeeService;
import com.university.fpt.acf.vo.CompanyVO;
import com.university.fpt.acf.vo.DetailAdvanceSalaryEmployeeVO;
import com.university.fpt.acf.vo.GetAllAdvanceSalaryEmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdvanceSalaryEmployeeServiceImpl implements AdvanceSalaryEmployeeService {
    @Autowired
    private AdvanceSalaryEmployeeRepository advanceSalaryEmployeeRepository;
    @Autowired
    private AdvanceSalaryEmployeeCustomRepository advanceSalaryEmployeeCustomRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public List<GetAllAdvanceSalaryEmployeeVO> searchAdvanceSalaryEmployee(SearchAdvanceEmployeeForm searchForm) {
        List<GetAllAdvanceSalaryEmployeeVO> list = new ArrayList<>();
        try {
            list = advanceSalaryEmployeeCustomRepository.searchAdvanceSalary(searchForm);
        } catch (Exception e) {
            throw new RuntimeException("Error Advance Salary repository " + e.getMessage());
        }
        return  list;
    }

    @Override
    public int totalSearch(SearchAdvanceEmployeeForm searchForm) {
       int size;
        try {
            size = advanceSalaryEmployeeCustomRepository.totalSearch(searchForm);
        } catch (Exception e) {
            throw new RuntimeException("Error Advance Salary repository " + e.getMessage());
        }
        return  size;
    }

    @Override
    public Boolean addAdvanceSalaryEmployee(AddAdvanceSalaryEmployeeForm addForm) {
        boolean check = false;
        try{
            AdvaceSalary p = new AdvaceSalary();
            String em = employeeRepository.getFullNameById(addForm.getIdEmployee());
            if(em==null || em.isEmpty()){
                throw new Exception("Nhan vien ko ton tai");
            }
            p.setTitle(addForm.getTitle());
            p.setContent(addForm.getContent());
            p.setAdvaceSalary(addForm.getAdvanceSalary());
            Employee e = new Employee();
            e.setId(addForm.getIdEmployee());
            p.setEmployee(e);
            AccountSercurity accountSercurity = new AccountSercurity();
            p.setModified_by(accountSercurity.getUserName());
            p.setCreated_date(LocalDate.now());
            advanceSalaryEmployeeRepository.save(p);
            check=true;

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

    @Override
    public Boolean updateAdvanceSalaryEmployee(UpdateAdvanceSalaryEmployeeForm updateForm) {
        boolean check = false;
        try{
            AdvaceSalary p = advanceSalaryEmployeeRepository.getAdvanceSalaryById(updateForm.getId());
            String em = employeeRepository.getFullNameById(updateForm.getIdEmployee());
            if(em==null || em.isEmpty()){
                throw new Exception("Nhan vien ko ton tai");
            }
            p.setTitle(updateForm.getTitle());
            p.setContent(updateForm.getContent());
            p.setAdvaceSalary(updateForm.getAdvanceSalary());
            Employee e = new Employee();
            e.setId(updateForm.getIdEmployee());
            p.setEmployee(e);
            AccountSercurity accountSercurity = new AccountSercurity();
            p.setModified_by(accountSercurity.getUserName());
            p.setModified_date(LocalDate.now());
            advanceSalaryEmployeeRepository.save(p);
            check=true;

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

    @Override
    public Boolean deleteAdvanceSalaryEmployee(Long id) {
        boolean check = false;
        try{
            AdvaceSalary p = advanceSalaryEmployeeRepository.getAdvanceSalaryById(id);
            p.setDeleted(true);
            AccountSercurity accountSercurity = new AccountSercurity();
            p.setModified_by(accountSercurity.getUserName());
            p.setModified_date(LocalDate.now());
            advanceSalaryEmployeeRepository.save(p);
            check=true;

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

    @Override
    public DetailAdvanceSalaryEmployeeVO getDetailAdvanceSalaryEmployee(Long id) {
        DetailAdvanceSalaryEmployeeVO data = new DetailAdvanceSalaryEmployeeVO();
        try {
            data = advanceSalaryEmployeeRepository.getDetailAdvanceSalaryEmployeeByIdAplication(id);
        } catch (Exception e) {
            throw new RuntimeException("Error Advance Salary repository " + e.getMessage());
        }
        return  data;
    }
}