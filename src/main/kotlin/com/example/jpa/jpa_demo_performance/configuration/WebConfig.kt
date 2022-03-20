package com.example.jpa.jpa_demo_performance.configuration

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration
class WebConfig : WebMvcConfigurationSupport() {

    override fun extendMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {

        converters.forEach {
            if (it is MappingJackson2HttpMessageConverter){
                val mapper = it.objectMapper
                mapper.setFilterProvider(SimpleFilterProvider().addFilter("person", SimpleBeanPropertyFilter.serializeAll()))
                mapper.setFilterProvider(SimpleFilterProvider().addFilter("book", SimpleBeanPropertyFilter.serializeAll()))
            }
        }
    }

}
