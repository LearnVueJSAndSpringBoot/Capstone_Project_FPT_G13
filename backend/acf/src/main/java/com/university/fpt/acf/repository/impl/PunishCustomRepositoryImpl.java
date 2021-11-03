package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.SearchBonusAdminForm;
import com.university.fpt.acf.repository.PunishCustomRepository;
import com.university.fpt.acf.vo.SearchBonusAdminVO;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PunishCustomRepositoryImpl extends CommonRepository implements PunishCustomRepository {
    @Override
    public List<SearchBonusAdminVO> searchPunish(SearchBonusAdminForm searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select  new com.university.fpt.acf.vo.SearchBonusAdminVO(b.id,b.title,b.reason,b.money,b.status,b.effectiveDate) from BonusPenalty b where b.deleted=false and b.bonus = false");

        if(searchForm.getTitle()!=null){
            sql.append(" and LOWER(a.title) like :title ");
            params.put("title","%"+searchForm.getTitle().toLowerCase()+"%");
        }
        if(searchForm.getStatus()!=null){
            sql.append(" and LOWER(b.status) =:status ");
            params.put("status",searchForm.getStatus());
        }
        if (searchForm.getDate() != null && !searchForm.getDate().isEmpty()) {
            sql.append(" and  b.effective_date BETWEEN :dateStart and :dateEnd ");
            params.put("dateStart", searchForm.getDate().get(0));
            params.put("dateEnd", searchForm.getDate().get(1));
        }
        sql.append(" ORDER by a.id desc ");
        TypedQuery<SearchBonusAdminVO> query = super.createQuery(sql.toString(),params, SearchBonusAdminVO.class);
        query.setFirstResult((searchForm.getPageIndex()-1)* searchForm.getPageSize());
        query.setMaxResults(searchForm.getPageSize());
        return query.getResultList();
    }

    @Override
    public int totalSearchPunish(SearchBonusAdminForm searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select  COUNT(*) from BonusPenalty b where b.deleted=false and b.bonus = false");

        if(searchForm.getTitle()!=null){
            sql.append(" and LOWER(a.title) like :title ");
            params.put("title","%"+searchForm.getTitle().toLowerCase()+"%");
        }
        if(searchForm.getStatus()!=null){
            sql.append(" and LOWER(b.status) =:status ");
            params.put("status",searchForm.getStatus());
        }
        if (searchForm.getDate() != null && !searchForm.getDate().isEmpty()) {
            sql.append(" and  b.effective_date BETWEEN :dateStart and :dateEnd ");
            params.put("dateStart", searchForm.getDate().get(0));
            params.put("dateEnd", searchForm.getDate().get(1));
        }
        sql.append(" ORDER by a.id desc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(),params, Long.class);
        return query.getSingleResult().intValue();
    }
}
