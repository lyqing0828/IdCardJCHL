package com.idcard.hnd.idcardjchl.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * @ClassName: Location
 * @Title: Location.java
 * @Description: 站点表（三级站点）
 * @author：张亚运
 * @date 2017-11-8 下午05:18:41
 * 
 */
@Table(name = "bas_location")
public class Location implements Serializable {

	private static final long serialVersionUID = 7324866950180433112L;

	/** id */
	@Column
	@Id
	private String id;
	/** 名称 */
	@Column
	private String name;
	/** 上级id */
	@Column
	private String sid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	@Override
	public String toString() {
		return "Location{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", sid='" + sid + '\'' +
				'}';
	}
}
