package com.gaobo.e5community.model;

import java.io.Serializable;

public class BaseGoods implements Serializable {
	private int id;// 自身id
	private int categoryId;// 类别id
	private String name;// 名称
	private float price;// 价格
	private String image;// 图片
	private String describe;// 描述
}
