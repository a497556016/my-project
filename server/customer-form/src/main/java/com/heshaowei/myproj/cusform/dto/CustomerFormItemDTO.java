package com.heshaowei.myproj.cusform.dto;

import com.google.common.base.Converter;
import com.heshaowei.myproj.cusform.entity.mongo.CustomerFormItem;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;

import java.util.Map;

@Data
@Builder
public class CustomerFormItemDTO extends Converter<CustomerFormItem, CustomerFormItemDTO> {
    private ObjectId id;
    private String name;
    private String question;
    private String component;
    private Boolean disabled;
    private Map<String, Object> meta;

    @Override
    protected CustomerFormItemDTO doForward(CustomerFormItem customerFormItem) {
        CustomerFormItemDTO dto = CustomerFormItemDTO.builder().build();
        BeanUtils.copyProperties(customerFormItem, dto);
        return dto;
    }

    @Override
    protected CustomerFormItem doBackward(CustomerFormItemDTO customerFormItemDTO) {
        CustomerFormItem item = new CustomerFormItem();
        BeanUtils.copyProperties(customerFormItemDTO, item);
        return item;
    }
}
