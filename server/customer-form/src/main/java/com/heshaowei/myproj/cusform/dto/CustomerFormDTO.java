package com.heshaowei.myproj.cusform.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Converter;
import com.google.common.collect.Lists;
import com.heshaowei.myproj.cusform.common.json.ObjectIdSerializer;
import com.heshaowei.myproj.cusform.entity.mongo.CustomerForm;
import com.heshaowei.myproj.cusform.entity.mongo.CustomerFormItem;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Builder
@Accessors(chain = true)
public class CustomerFormDTO extends Converter<CustomerForm, CustomerFormDTO> {
    private ObjectId id;
    private String title;
    private String desc;
    private Date createTime;
    private String createBy;
    private Map<String, Object> meta;

    private List<CustomerFormItemDTO> formItems;


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
        Iterable<CustomerFormItem> items = CustomerFormItemDTO.builder().build().reverse().convertAll(customerFormDTO.getFormItems());
        form.setFormItems(Lists.newArrayList(items));
        return form;
    }
}
