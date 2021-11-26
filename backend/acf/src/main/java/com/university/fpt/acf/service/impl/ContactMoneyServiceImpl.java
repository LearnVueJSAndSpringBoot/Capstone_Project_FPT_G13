package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.Contact;
import com.university.fpt.acf.entity.ContactMoney;
import com.university.fpt.acf.form.AddContactMoneyForm;
import com.university.fpt.acf.form.SearchContactMoneyForm;
import com.university.fpt.acf.repository.ContactMoneyCustomRepository;
import com.university.fpt.acf.repository.ContactMoneyRepository;
import com.university.fpt.acf.repository.ContactRepository;
import com.university.fpt.acf.service.ContactMoneyService;
import com.university.fpt.acf.vo.CompanyVO;
import com.university.fpt.acf.vo.ContactMoneyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ContactMoneyServiceImpl implements ContactMoneyService {
    @Autowired
    private ContactMoneyCustomRepository contactMoneyCustomRepository;

    @Autowired
    private ContactMoneyRepository contactMoneyRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public List<HashMap<String, Object>> searchContactMoney(SearchContactMoneyForm searchContactMoneyForm) {
        List<ContactMoney> contactMonies = new ArrayList<>();
        List<HashMap<String,Object>> mapList = new ArrayList<>();
        HashMap<String,Object> stringObjectHashMap = new HashMap<>();
        try {
            contactMonies = contactMoneyCustomRepository.searchContactMoney(searchContactMoneyForm);
            Long idContact = 0l;
            String nameContact = "";
            LocalDate dateStart = LocalDate.now();
            LocalDate dateEnd = LocalDate.now();
            Integer moneyAdvance = 0;
            String moneyContact = "0";
            String numberFinish = "";
            Integer statusDone = -2;
            List<ContactMoneyVo> contactMoneyVos = new ArrayList<>();
            for(ContactMoney contactMoney : contactMonies){
                if(!contactMoney.getContact().getName().equals(nameContact)){
                    stringObjectHashMap = new HashMap<>();
                    contactMoneyVos = new ArrayList<>();
                    mapList.add(stringObjectHashMap);
                    idContact = contactMoney.getContact().getId();
                    nameContact = contactMoney.getContact().getName();
                    dateStart = contactMoney.getContact().getCreated_date();
                    dateEnd = contactMoney.getContact().getDateFinish();
                    moneyAdvance = Integer.parseInt(contactMoney.getMoney());
                    moneyContact = contactMoney.getContact().getTotalMoney();
                    numberFinish = contactMoney.getContact().getNumberFinish();
                    statusDone = contactMoney.getContact().getStatusDone();

                    ContactMoneyVo contactMoneyVo = new ContactMoneyVo();
                    contactMoneyVo.setId(contactMoney.getId());
                    contactMoneyVo.setDate(contactMoney.getDate());
                    contactMoneyVo.setMoney(Integer.parseInt(contactMoney.getMoney()));
                    contactMoneyVos.add(contactMoneyVo);

                    stringObjectHashMap.put("idContact",idContact);
                    stringObjectHashMap.put("nameContact",nameContact);
                    stringObjectHashMap.put("dateStart",dateStart);
                    stringObjectHashMap.put("dateEnd",dateEnd);
                    stringObjectHashMap.put("moneyAdvance",moneyAdvance);
                    stringObjectHashMap.put("moneyContact",moneyContact);
                    stringObjectHashMap.put("numberFinish",numberFinish);
                    stringObjectHashMap.put("statusDone",statusDone);
                    stringObjectHashMap.put("contactMoneyDetail",contactMoneyVos);
                }else{
                    stringObjectHashMap.put("moneyAdvance",Integer.parseInt(stringObjectHashMap.get("moneyAdvance").toString())+Integer.parseInt(contactMoney.getMoney()));
                    ContactMoneyVo contactMoneyVo = new ContactMoneyVo();
                    contactMoneyVo.setId(contactMoney.getId());
                    contactMoneyVo.setDate(contactMoney.getDate());
                    contactMoneyVo.setMoney(Integer.parseInt(contactMoney.getMoney()));
                    contactMoneyVos.add(contactMoneyVo);
                }

            }
        } catch (Exception e) {
            throw new RuntimeException("Error position repository " + e.getMessage());
        }
        return mapList;
    }

    @Override
    public int getTotalSearchContactMoney(SearchContactMoneyForm searchContactMoneyForm) {
        if (searchContactMoneyForm.getTotal() != null && searchContactMoneyForm.getTotal() != 0) {
            return searchContactMoneyForm.getTotal().intValue();
        }
        int total = 0;
        try {
            total = contactMoneyCustomRepository.getTotalsearchContactMoney(searchContactMoneyForm);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return total;
    }

    @Override
    public Boolean addContactMoney(AddContactMoneyForm addContactMoneyForm) {
        Boolean check = false;
        try{
            ContactMoney contactMoney = new ContactMoney();

            AccountSercurity accountSercurity = new AccountSercurity();
            contactMoney.setCreated_by(accountSercurity.getUserName());
            contactMoney.setModified_by(accountSercurity.getUserName());

            contactMoney.setMoney(addContactMoneyForm.getMoney()+"");
            Contact contact = new Contact();
            contact.setId(addContactMoneyForm.getContact());
            contactMoney.setContact(contact);
            contactMoneyRepository.save(contactMoney);
        }catch (Exception ex){
            throw new RuntimeException("không thể thêm mới tạm ứng hợp đồng");
        }
        return check;
    }

    @Override
    public Boolean editContactMoney(AddContactMoneyForm addContactMoneyForm) {
        Boolean check = false;
        try{
            ContactMoney contactMoney = contactMoneyRepository.getContactMoneyByID(addContactMoneyForm.getContact());

            AccountSercurity accountSercurity = new AccountSercurity();
            contactMoney.setModified_by(accountSercurity.getUserName());
            contactMoney.setModified_date(LocalDate.now());
            contactMoney.setDate(LocalDate.now());
            contactMoney.setMoney(addContactMoneyForm.getMoney()+"");

            contactMoneyRepository.save(contactMoney);
        }catch (Exception ex){
            throw new RuntimeException("không thể sửa tạm ứng hợp đồng");
        }
        return check;
    }

    @Override
    public Boolean deleteContactMoney(Long id) {
        Boolean check = false;
        try{
            ContactMoney contactMoney = contactMoneyRepository.getContactMoneyByID(id);

            AccountSercurity accountSercurity = new AccountSercurity();
            contactMoney.setModified_by(accountSercurity.getUserName());
            contactMoney.setModified_date(LocalDate.now());
            contactMoney.setDeleted(true);

            contactMoneyRepository.save(contactMoney);
        }catch (Exception ex){
            throw new RuntimeException("không thể sửa tạm ứng hợp đồng");
        }
        return check;
    }

    @Override
    public Boolean confirmContactMoney(AddContactMoneyForm addContactMoneyForm) {
        Boolean check = false;
        try{
            AccountSercurity accountSercurity = new AccountSercurity();
            Contact contactNew = contactRepository.getContactByID(addContactMoneyForm.getContact());
            contactNew.setModified_by(accountSercurity.getUserName());
            contactNew.setModified_date(LocalDate.now());
            contactNew.setStatusDone(1);
            contactRepository.save(contactNew);
        }catch (Exception ex){
            throw new RuntimeException("không thể thêm mới tạm ứng hợp đồng");
        }
        return check;
    }
}