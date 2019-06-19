package com.idcard.hnd.idcardjchl.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * @ClassName: Region
 * @Title: Region.java
 * @Description: z站点区域表（一级检查站）
 * @author：张亚运
 * @date 2017-11-8 下午05:18:41
 * 
 */

@Table(name = "bas_region")
public class Region implements Serializable {

	private static final long serialVersionUID = 7324866950180433112L;

	/** id */
	@Column
	private String id;
	/** 名称 */
	@Column
	private String name;

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

	@Override
	public String toString() {
		return "Region{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
