package com.gaobo.e5community.model;

public class Service {
	private int id;// 自身id
	private int categoryId;// 类别id
	private String name;// 名称
	private float price;// 价格
	private String image;// 图片
	private int cid;// 所属社区ID
	private String describe;// 描述
	private String create_at;// 发布时间
	private int is_enable;// 是否下架

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getCreate_at() {
		return create_at;
	}

	public void setCreate_at(String create_at) {
		this.create_at = create_at;
	}

	public int getIs_enable() {
		return is_enable;
	}

	public void setIs_enable(int is_enable) {
		this.is_enable = is_enable;
	}
}
