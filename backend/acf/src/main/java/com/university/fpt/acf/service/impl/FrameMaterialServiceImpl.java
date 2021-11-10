package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.FrameMaterial;
import com.university.fpt.acf.entity.UnitMeasure;
import com.university.fpt.acf.form.AddFrameMaterialForm;
import com.university.fpt.acf.form.SearchFrameMaterialForm;
import com.university.fpt.acf.form.SearchHeightMaterialForm;
import com.university.fpt.acf.repository.FrameMaterialCustomRepository;
import com.university.fpt.acf.repository.FrameMaterialRepository;
import com.university.fpt.acf.repository.HeightMaterialRepository;
import com.university.fpt.acf.repository.UnitMeasureRepository;
import com.university.fpt.acf.service.FrameMaterialService;
import com.university.fpt.acf.vo.HeightMaterialVO;
import com.university.fpt.acf.vo.SearchFrameMaterialVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FrameMaterialServiceImpl implements FrameMaterialService {
    @Autowired
    private FrameMaterialCustomRepository customRepository;
    @Autowired
    private FrameMaterialRepository repository;
    @Autowired
    private HeightMaterialRepository heightMaterialRepository;
    @Autowired
    private UnitMeasureRepository unitMeasureRepository;
    @Override
    public List<HeightMaterialVO> getAllHeightNotFrameTable(SearchHeightMaterialForm searchForm) {
        List<HeightMaterialVO> list = new ArrayList<>();
        try {
            list = customRepository.getAllHeightNotFrameTable(searchForm);
            if(list == null ){
                throw new Exception("Không tìm thấy ");
            }
        }catch (Exception e){
            e.getMessage();
        }
        return list;
    }

    @Override
    @Transactional
    public Boolean addFrame(AddFrameMaterialForm addForm) {
        boolean check = false;
        try {
            FrameMaterial frame = new FrameMaterial();
            frame.setFrameLength(addForm.getLength());
            frame.setFrameWidth(addForm.getWidth());
            UnitMeasure unit = unitMeasureRepository.getUnitByID(addForm.getIdUnit());
            if(unit==null){
                throw new Exception("Đơn vị không tồn tại  ");
            }
            AccountSercurity accountSercurity = new AccountSercurity();
            frame.setCreated_by(accountSercurity.getUserName());
            frame.setCreated_date(LocalDate.now());
            frame.setModified_by(accountSercurity.getUserName());
            repository.saveAndFlush(frame);
            check = true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

    @Override
    public List<SearchFrameMaterialVO> searchFrame(SearchFrameMaterialForm searchForm) {
        List<SearchFrameMaterialVO> list = new ArrayList<>();
        try {
            list = customRepository.searchFrame(searchForm);
            if(list == null ){
                throw new Exception("Không tìm thấy ");
            }
        }catch (Exception e){
            e.getMessage();
        }
        return list;
    }

    @Override
    public int totalSearch(SearchFrameMaterialForm searchForm) {
        int size;
        try {
            size = customRepository.totalSearchFrame(searchForm);
        } catch (Exception e) {
            throw new RuntimeException("Error bonus  repository " + e.getMessage());
        }
        return size;
    }

    @Override
    @Transactional
    public Boolean deleteFrame(Long id) {
        Boolean check = false;
        try {
            FrameMaterial frame = repository.getFrameById(id);
            if(frame==null ){
                throw new Exception("Khung không tồn tại");
            }
            repository.delete(frame);
            check = true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }
}
