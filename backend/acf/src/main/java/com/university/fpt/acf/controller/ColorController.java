package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.service.ColorService;
import com.university.fpt.acf.vo.ColorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/color")
public class ColorController {
    @Autowired
    private ColorService colorService;
    @PostMapping("/search")
    public ResponseEntity<ResponseCommon> searchColor(@RequestBody SearchColorForm searchColorForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total=0;
        List<ColorVO> listColor = new ArrayList<>();
        try {
            listColor = colorService.searchColor(searchColorForm);
            total = colorService.getTotalSearchColor(searchColorForm);
            responseCommon.setData(listColor);
            message = "Thành công";
            if(total==0){
                message = "Không tìm thấy";
            }
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(listColor);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }
    @PostMapping("/add")
    public  ResponseEntity<ResponseCommon> addColor(@RequestBody AddColorForm addColorForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean checkAdd = false;
        try {
            if (addColorForm.getName() == null || addColorForm.getName() == "") {
                message = "Dữ liệu bị null! Không thêm được!";
            } else {
                checkAdd = colorService.insertColor(addColorForm);
                if (checkAdd == false) {
                    message = "Thêm màu sắc không thành công!";
                } else {
                    message = "Thêm thành công!";
                }
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkAdd);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon, HttpStatus.OK);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(checkAdd);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
    @PutMapping("/update")
    public  ResponseEntity<ResponseCommon> updateColor(@RequestBody UpdateColorForm updateColorForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkUpdate = false;
        try{
            if(updateColorForm.getName()==null || updateColorForm.getName()==""){
                message="Tên màu sắc chống";
            }else {
                checkUpdate =colorService.updateColor(updateColorForm);
                if(checkUpdate==false){
                    message="Chỉnh sửa không thành công";
                }else{
                    message="Chỉnh sửa thành công";
                }
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkUpdate);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
        }catch (Exception e){
            message = e.getMessage();
            responseCommon.setData(checkUpdate);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
    @DeleteMapping("/delete")
    public  ResponseEntity<ResponseCommon> deleteColor(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkDelete = false;
        try{
            checkDelete = colorService.deleteColor(id);
            if(checkDelete==false){
                message="Xóa không thành công";
            }else{
                message="Xóa thành công";
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkDelete);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
        }catch (Exception e){
            message = e.getMessage();
            responseCommon.setData(checkDelete);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
}
