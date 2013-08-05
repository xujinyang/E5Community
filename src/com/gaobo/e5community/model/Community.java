package com.gaobo.e5community.model;
/**
 * 社区实体类
 * @author moblieXu
 *
 */
public class Community {
private int id;
private String name;
private long longitude;//经度
private long latitude;//纬度
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public long getLongitude() {
	return longitude;
}
public void setLongitude(long longitude) {
	this.longitude = longitude;
}
public long getLatitude() {
	return latitude;
}
public void setLatitude(long latitude) {
	this.latitude = latitude;
}


}
