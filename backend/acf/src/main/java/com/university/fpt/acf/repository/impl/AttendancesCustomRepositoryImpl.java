package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.AttendanceFrom;
import com.university.fpt.acf.form.SearchAccountForm;
import com.university.fpt.acf.repository.AccountCustomRepository;
import com.university.fpt.acf.repository.AttendancesCustomRepository;
import com.university.fpt.acf.vo.AttendanceVO;
import com.university.fpt.acf.vo.GetAllAccountVO;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AttendancesCustomRepositoryImpl extends CommonRepository implements AttendancesCustomRepository {
    @Override
    public List<AttendanceVO> getAllAttendance(AttendanceFrom attendanceFrom) {
        StringBuilder sqlAcc = new StringBuilder("");
        Map<String, Object> paramsAcc = new HashMap<>();
        sqlAcc.append(" select new com.university.fpt.acf.vo.AttendanceVO(t.date,e.id,e.fullName,t.type,t.note) from TimeKeep t inner join t.employee e where t.deleted = false ");
        if (attendanceFrom.getName() != null && !attendanceFrom.getName().isEmpty()) {
            sqlAcc.append(" and LOWER(e.fullName) like :name ");
            paramsAcc.put("name", "%" + attendanceFrom.getName().toLowerCase() + "%");
        }
        if (attendanceFrom.getDate() != null && !attendanceFrom.getDate().isEmpty()) {
            sqlAcc.append(" and  t.date BETWEEN :dateStart and :dateEnd");
            paramsAcc.put("dateStart", attendanceFrom.getDate().get(0));
            paramsAcc.put("dateEnd", attendanceFrom.getDate().get(1));
        }
        if(attendanceFrom.getType() != null && !attendanceFrom.getType().isEmpty()){
            sqlAcc.append(" and t.type  :type ");
            paramsAcc.put("type",attendanceFrom.getType());
        }
        TypedQuery<AttendanceVO> queryAcc = super.createQuery(sqlAcc.toString(), paramsAcc, AttendanceVO.class);
        queryAcc.setFirstResult(attendanceFrom.getPageIndex() - 1);
        queryAcc.setMaxResults(attendanceFrom.getPageSize());
        List<AttendanceVO> resultList = queryAcc.getResultList();
        return resultList;
    }

    @Override
    public int getTotalAttendances(AttendanceFrom attendanceFrom) {
        StringBuilder sqlAcc = new StringBuilder("");
        Map<String, Object> paramsAcc = new HashMap<>();
        sqlAcc.append(" select COUNT(*) from TimeKeep t inner join t.employee e where t.deleted = false ");
        if (attendanceFrom.getName() != null && !attendanceFrom.getName().isEmpty()) {
            sqlAcc.append(" and LOWER(e.fullName) like :name ");
            paramsAcc.put("name", "%" + attendanceFrom.getName().toLowerCase() + "%");
        }
        if (attendanceFrom.getDate() != null && !attendanceFrom.getDate().isEmpty()) {
            sqlAcc.append(" and  t.date BETWEEN :dateStart and :dateEnd");
            paramsAcc.put("dateStart", attendanceFrom.getDate().get(0));
            paramsAcc.put("dateEnd", attendanceFrom.getDate().get(1));
        }
        if(attendanceFrom.getType() != null && !attendanceFrom.getType().isEmpty()){
            sqlAcc.append(" and t.type  :type ");
            paramsAcc.put("type",attendanceFrom.getType());
        }
        TypedQuery<Long> queryAcc = super.createQuery(sqlAcc.toString(), paramsAcc, Long.class);
        return queryAcc.getResultList().size();
    }
}