package com.heshaowei.myproj.cusform.dto;

import com.google.common.base.Converter;
import com.heshaowei.myproj.cusform.entity.mongo.CustomerForm;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
public class CustomerFormDTO extends Converter<CustomerForm, CustomerFormDTO> {
    private ObjectId id;
    private String title;
    private String desc;
    private Date createTime;
    private String createBy;


    @Override
    protected CustomerFormDTO doForward(CustomerForm form) {
        CustomerFormDTO dto = CustomerFormDTO.builder().build();
        BeanUtils.copyProperties(form, dto);
        return dto;
    }

    @Override
    protected CustomerForm doBackward(CustomerFormDTO customerFormDTO) {
        CustomerForm form = new CustomerForm();
        BeanUtils.copyProperties(customerFormDTO, form);
        return form;
    }
}
