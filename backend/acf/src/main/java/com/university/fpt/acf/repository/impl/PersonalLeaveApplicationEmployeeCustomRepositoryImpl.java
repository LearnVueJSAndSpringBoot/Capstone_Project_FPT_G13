package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.SearchPersonalApplicationEmployeeForm;
import com.university.fpt.acf.repository.PersonalLeaveApplicationEmployeeCustomRepository;
import com.university.fpt.acf.vo.SearchPersonalApplicationEmployeeVO;
import com.university.fpt.acf.vo.SearchPersonalLeaveApplicationAdminVO;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PersonalLeaveApplicationEmployeeCustomRepositoryImpl extends CommonRepository implements PersonalLeaveApplicationEmployeeCustomRepository {
    @Override
    public List<SearchPersonalApplicationEmployeeVO> searchPerLeaApplicationEmployee(SearchPersonalApplicationEmployeeForm searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" select new com.university.fpt.acf.vo.SearchPersonalApplicationEmployeeVO(p.id,p.fileAttach,p.title,p.comment,p.content,p.accept) from PersonaLeaveApplication p where p.employee.id=:id ");
        params.put("id",searchForm.getIdEmployee());
        if(searchForm.getStatus() != null ){
            sql.append(" and p.accept=:status ");
            params.put("status",searchForm.getStatus());
        }
        if(searchForm.getTitle() != null && !searchForm.getTitle().isEmpty()){
            sql.append(" and LOWER(p.title) like :title ");
            params.put("title","%"+searchForm.getTitle().toLowerCase()+"%");
        }
        if (searchForm.getDate() != null && !searchForm.getDate().isEmpty()) {
            sql.append(" and  p.date BETWEEN :dateStart and :dateEnd ");
            params.put("dateStart", searchForm.getDate().get(0));
            params.put("dateEnd", searchForm.getDate().get(1));
        }
        sql.append(" ORDER by p.id desc ");
        TypedQuery<SearchPersonalApplicationEmployeeVO> query = super.createQuery(sql.toString(), params, SearchPersonalApplicationEmployeeVO.class);
        query.setFirstResult((searchForm.getPageIndex() - 1)*searchForm.getPageSize());
        query.setMaxResults(searchForm.getPageSize());
        return query.getResultList();
    }

    @Override
    public int totalSearch(SearchPersonalApplicationEmployeeForm searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" select COUNT(*) from PersonaLeaveApplication p where p.employee.id=:id ");
        params.put("id",searchForm.getIdEmployee());
        if(searchForm.getStatus() != null ){
            sql.append(" and p.accept=:status ");
            params.put("status",searchForm.getStatus());
        }
        if(searchForm.getTitle() != null && !searchForm.getTitle().isEmpty()){
            sql.append(" and LOWER(p.title) like :title ");
            params.put("title","%"+searchForm.getTitle().toLowerCase()+"%");
        }
        if (searchForm.getDate() != null && !searchForm.getDate().isEmpty()) {
            sql.append(" and  p.date BETWEEN :dateStart and :dateEnd ");
            params.put("dateStart", searchForm.getDate().get(0));
            params.put("dateEnd", searchForm.getDate().get(1));
        }
        sql.append(" ORDER by p.id desc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(), params, Long.class);
        return query.getSingleResult().intValue();
    }
}