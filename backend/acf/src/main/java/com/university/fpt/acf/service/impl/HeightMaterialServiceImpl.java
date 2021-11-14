package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.HeightMaterial;
import com.university.fpt.acf.repository.HeightMaterialRepository;
import com.university.fpt.acf.service.HeightMaterialService;
import com.university.fpt.acf.vo.HeightMaterialVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HeightMaterialServiceImpl implements HeightMaterialService {
    @Autowired
    private HeightMaterialRepository heightMaterialRepository;
    @Override
    public Boolean addHeightMaterial(String frameHeight) {
        Boolean insert = false;
        try {
            if(heightMaterialRepository.getIdByHeight(frameHeight)!=null){
                throw new Exception("Chiều cao đã tồn tại");
            }
            HeightMaterial h = new HeightMaterial();
            h.setFrameHeight(frameHeight);
            AccountSercurity accountSercurity = new AccountSercurity();
            h.setCreated_by(accountSercurity.getUserName());
            h.setCreated_date(LocalDate.now());
            h.setModified_by(accountSercurity.getUserName());
            heightMaterialRepository.save(h);
            insert = true;
        }catch (Exception e){
            e.getMessage();
        }
        return insert;
    }

    @Override
    @Transactional
    public Boolean deleteHeightMaterial(Long id) {
        Boolean delete = false;
        try {
            HeightMaterial h = heightMaterialRepository.getHeightMaterialById(id);
            if(h==null ){
                throw new Exception("Chiều cao không tồn tại");
            }
            heightMaterialRepository.delete(h);
            delete = true;
        }catch (Exception e){
            e.getMessage();
        }
        return delete;
    }

    @Override
    public List<HeightMaterialVO> getAllHeights() {
        List<HeightMaterialVO> list = new ArrayList<>();
        try {
            list = heightMaterialRepository.getAllHeight();
            if(list == null ){
                throw new Exception("Không tìm thấy ");
            }
        }catch (Exception e){
            e.getMessage();
        }
        return list;
    }

    @Override
    public List<HeightMaterialVO> getHeightsToInsertMaterial() {
        List<HeightMaterialVO> list = new ArrayList<>();
        try {
            list = heightMaterialRepository.getHeightMaterialToInsert();
            if(list == null ){
                throw new Exception("Không tìm thấy ");
            }
        }catch (Exception e){
            e.getMessage();
        }
        return list;
    }

    @Override
    public List<HeightMaterialVO> getHeightsToInsertCoverInsert() {
        List<HeightMaterialVO> list = new ArrayList<>();
        try {
            list = heightMaterialRepository.getHeightCoverSheetToInsert();
            if(list == null ){
                throw new Exception("Không tìm thấy ");
            }
        }catch (Exception e){
            e.getMessage();
        }
        return list;
    }
}