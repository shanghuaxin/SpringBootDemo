package com.example.webservice.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "name"
})
@XmlRootElement(name = "getCountryRequest", namespace = "http://www.seecom.com.cn/webservice")
@Data
public class GetCountryRequest {

    @XmlElement(namespace = "http://www.seecom.com.cn/webservice", required = true)
    protected String name;
}
