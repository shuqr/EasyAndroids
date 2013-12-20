package com.frame.easyandroid.db.dao.aonntation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
	/**
	 * 指定实体与数据库列的对应关系
	 * @return
	 */
	String value();
}
