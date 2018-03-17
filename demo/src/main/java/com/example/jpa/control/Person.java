package com.example.jpa.control;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;

@Table(name="t_person")//表添加前缀
@Data
@Entity//把当前类映射到数据库中
@ToString(exclude="ex")
@JsonIgnoreProperties("ex")
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id// 解决org.hibernate.AnnotationException: No identifier specified for entity异常
	@GeneratedValue
	private Long id;
	
	@Basic//数据库中添加字段
	@Column(name="p_name", nullable = false, length = 10, unique = true)
	private String name;
	
	@Column(name="p_weight", nullable = false, length = 10)
	private double weight;
	
	@Basic//数据库中添加字段
	@Transient //表示不生成数据库字段
	private String ex;

	public Person(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Person() {
		super();
	}
	
}
