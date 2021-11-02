package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.AcceptPersonalLeaveApplicationAdminForm;
import com.university.fpt.acf.form.AddPositionForm;
import com.university.fpt.acf.form.SearchPersonalLeaveAdminApplicationForm;
import com.university.fpt.acf.service.PersonalLeaveApplicationAdminService;
import com.university.fpt.acf.vo.SearchPersonalLeaveApplicationAdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin")
public class PersonalLeaveApplicationAdminController {
    @Autowired
    PersonalLeaveApplicationAdminService personalLeaveApplicationService;
    @PostMapping("/acceptPersonalLeaveApplication")
    public ResponseEntity<ResponseCommon> searchPersonalLeaveApplication(@RequestBody SearchPersonalLeaveAdminApplicationForm personalLeaveApplicationForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total=0;
        List<SearchPersonalLeaveApplicationAdminVO> getAllPositionVOList = new ArrayList<>();
        try {
            getAllPositionVOList = personalLeaveApplicationService.searchPersonalApplication(personalLeaveApplicationForm);
            total = personalLeaveApplicationService.totalPersonalApplication(personalLeaveApplicationForm);
            responseCommon.setData(getAllPositionVOList);
            message = "Get personal Leave Application successfully";
            if(total==0){
                message = "Get personal Leave Application not found";
            }
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(getAllPositionVOList);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }
    @PostMapping("/accept")
    public  ResponseEntity<ResponseCommon> acceptPersonalApplication(@RequestBody AcceptPersonalLeaveApplicationAdminForm acceptForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAccept = false;
        try {
            if(acceptForm.getIdApplication()==null ){
                message="Data is null! Dont Accept!";
            }else {
                checkAccept =personalLeaveApplicationService.acceptPersonalLeaveApplication(acceptForm);
                if(checkAccept==false){
                    message="Accept personalLeaveApplication fail!";
                }else{
                    message="Accept personalLeaveApplication sucessfuly!";
                }
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkAccept);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
        }catch (Exception e){
            message = e.getMessage();
            responseCommon.setData(checkAccept);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }

}
