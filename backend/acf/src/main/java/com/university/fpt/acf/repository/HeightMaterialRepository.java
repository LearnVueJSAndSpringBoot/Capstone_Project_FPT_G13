package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.HeightMaterial;
import com.university.fpt.acf.vo.HeightMaterialVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeightMaterialRepository extends JpaRepository<HeightMaterial,Long> {
    @Query("select h.height from HeightMaterial h where h.id=:id")
    String getHeightById(@Param("id") Long id);
    @Query("select h.id from HeightMaterial h where h.height=:height")
    String getIdByHeight(@Param("height") String height);
    @Query("select new  com.university.fpt.acf.vo.HeightMaterialVO(h.id,h.height) from HeightMaterial h")
    List<HeightMaterialVO> getAllHeight();
    @Query("select h from HeightMaterial h where h.id=:id")
    HeightMaterial getHeightMaterialById(@Param("id") Long id);
}