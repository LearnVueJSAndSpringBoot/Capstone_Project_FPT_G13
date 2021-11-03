package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.SearchSalaryForm;
import com.university.fpt.acf.repository.SalaryCustomRepository;
import com.university.fpt.acf.vo.AttendanceVO;
import com.university.fpt.acf.vo.SearchSalaryVO;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SalaryCustomRepositoryImpl extends CommonRepository implements SalaryCustomRepository {
    @Override
    public List<SearchSalaryVO> searchSalaryHistory(SearchSalaryForm searchSalaryForm) {
        StringBuilder sqlAcc = new StringBuilder("");
        Map<String, Object> paramsAcc = new HashMap<>();
        sqlAcc.append(" select new com.university.fpt.acf.vo.SearchSalaryVO(hs.id,hs.created_date,e.fullName,p.name,hs.countWork,hs.salary" +
                ",hs.bonus,hs.penalty,hs.advanceSalary,hs.totalMoney,hs.status) " +
                " from HistorySalary hs inner join hs.employee e inner join e.position p where hs.status = true and hs.deleted = false ");

        if (searchSalaryForm.getName() != null && !searchSalaryForm.getName().isEmpty()) {
            sqlAcc.append(" and LOWER(e.fullName) like :name ");
            paramsAcc.put("name", "%" + searchSalaryForm.getName() + "%");
        }

        if (searchSalaryForm.getIdPositons() != null && !searchSalaryForm.getIdPositons().isEmpty()) {
            sqlAcc.append(" and p.id in :idPosition ");
            paramsAcc.put("idPosition", searchSalaryForm.getIdPositons());
        }


        if (searchSalaryForm.getDate() != null && !searchSalaryForm.getDate().isEmpty()) {
            sqlAcc.append(" and  hs.created_date BETWEEN :dateStart and :dateEnd ");
            paramsAcc.put("dateStart", searchSalaryForm.getDate().get(0));
            paramsAcc.put("dateEnd", searchSalaryForm.getDate().get(1));
        }
        sqlAcc.append(" ORDER by hs.created_date desc ");
        TypedQuery<SearchSalaryVO> queryAcc = super.createQuery(sqlAcc.toString(), paramsAcc, SearchSalaryVO.class);
        queryAcc.setFirstResult((searchSalaryForm.getPageIndex() - 1)*searchSalaryForm.getPageSize());
        queryAcc.setMaxResults(searchSalaryForm.getPageSize());
        List<SearchSalaryVO> resultList = queryAcc.getResultList();
        return resultList;
    }

    @Override
    public int getTotalSearchSalaryHistory(SearchSalaryForm searchSalaryForm) {
        StringBuilder sqlAcc = new StringBuilder("");
        Map<String, Object> paramsAcc = new HashMap<>();
        sqlAcc.append(" select COUNT(*)  from HistorySalary hs inner join hs.employee e inner join e.position p " +
                "where hs.status = true and hs.deleted = false  ");

        if (searchSalaryForm.getName() != null && !searchSalaryForm.getName().isEmpty()) {
            sqlAcc.append(" and LOWER(e.fullName) like :name ");
            paramsAcc.put("name", "%" + searchSalaryForm.getName() + "%");
        }

        if (searchSalaryForm.getIdPositons() != null && !searchSalaryForm.getIdPositons().isEmpty()) {
            sqlAcc.append(" and p.id in :idPosition ");
            paramsAcc.put("idPosition", searchSalaryForm.getIdPositons());
        }


        if (searchSalaryForm.getDate() != null && !searchSalaryForm.getDate().isEmpty()) {
            sqlAcc.append(" and  hs.created_date BETWEEN :dateStart and :dateEnd ");
            paramsAcc.put("dateStart", searchSalaryForm.getDate().get(0));
            paramsAcc.put("dateEnd", searchSalaryForm.getDate().get(1));
        }

        TypedQuery<Long> queryAcc = super.createQuery(sqlAcc.toString(), paramsAcc, Long.class);
        return queryAcc.getSingleResult().intValue();
    }

    @Override
    public List<SearchSalaryVO> searchSalaryAccept(SearchSalaryForm searchSalaryForm) {
        StringBuilder sqlAcc = new StringBuilder("");
        Map<String, Object> paramsAcc = new HashMap<>();
        sqlAcc.append(" select new com.university.fpt.acf.vo.SearchSalaryVO(hs.id,hs.created_date,e.fullName,p.name,hs.countWork,hs.salary" +
                ",hs.bonus,hs.penalty,hs.advanceSalary,hs.totalMoney,hs.status) " +
                " from HistorySalary hs inner join hs.employee e inner join e.position p where hs.status = true and hs.deleted = false ");

        if (searchSalaryForm.getName() != null && !searchSalaryForm.getName().isEmpty()) {
            sqlAcc.append(" and LOWER(e.fullName) like :name ");
            paramsAcc.put("name", "%" + searchSalaryForm.getName() + "%");
        }

        if (searchSalaryForm.getIdPositons() != null && !searchSalaryForm.getIdPositons().isEmpty()) {
            sqlAcc.append(" and p.id in :idPosition ");
            paramsAcc.put("idPosition", searchSalaryForm.getIdPositons());
        }


        if (searchSalaryForm.getDate() != null && !searchSalaryForm.getDate().isEmpty()) {
            sqlAcc.append(" and  hs.created_date BETWEEN :dateStart and :dateEnd ");
            paramsAcc.put("dateStart", searchSalaryForm.getDate().get(0));
            paramsAcc.put("dateEnd", searchSalaryForm.getDate().get(1));
        }
        sqlAcc.append(" ORDER by hs.created_date desc ");
        TypedQuery<SearchSalaryVO> queryAcc = super.createQuery(sqlAcc.toString(), paramsAcc, SearchSalaryVO.class);
        queryAcc.setFirstResult((searchSalaryForm.getPageIndex() - 1)*searchSalaryForm.getPageSize());
        queryAcc.setMaxResults(searchSalaryForm.getPageSize());
        List<SearchSalaryVO> resultList = queryAcc.getResultList();
        return resultList;
    }

    @Override
    public int getTotalSearchSalaryAccept(SearchSalaryForm searchSalaryForm) {
        return 0;
    }
}
