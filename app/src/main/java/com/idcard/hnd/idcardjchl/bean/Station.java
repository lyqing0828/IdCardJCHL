package com.idcard.hnd.idcardjchl.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * @ClassName: Station
 * @Title: Station.java
 * @Description: 站点表（二级站点）
 * @author：张亚运
 * @date 2017-11-8 下午05:18:41
 * 
 */
@Table(name = "bas_station")
public class Station implements Serializable {

	private static final long serialVersionUID = 7324866950180433112L;

	/** id */
	@Column
	private String id;
	/** 名称 */
	@Column
	private String name;
	/** 上级id */
	@Column
	private String pid;

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

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Override
	public String toString() {
		return "Station{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", pid='" + pid + '\'' +
				'}';
	}
}
