package com.heshaowei.myproj.cusform.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.heshaowei.myproj.cusform.common.json.ObjectIdDeserializer;
import com.heshaowei.myproj.cusform.common.json.ObjectIdSerializer;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {


    @Bean
    public SimpleModule simpleModule() {
        SimpleModule module = new SimpleModule();

        //ObjectId序列化
        module.addSerializer(ObjectId.class, new ObjectIdSerializer());

        //ObjectId反序列化
        module.addDeserializer(ObjectId.class, new ObjectIdDeserializer());

        return module;
    }

}