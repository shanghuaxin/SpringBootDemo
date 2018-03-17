
package com.example.webservice.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Data;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "country", namespace = "http://www.seecom.com.cn/webservice", propOrder = {
    "name",
    "capital"
})
@Data
public class Country {

    @XmlElement(namespace = "http://www.seecom.com.cn/webservice")
    protected String name;
    @XmlElement(namespace = "http://www.seecom.com.cn/webservice")
    protected String capital;

}
