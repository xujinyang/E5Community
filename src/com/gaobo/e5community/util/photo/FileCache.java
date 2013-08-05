package com.gaobo.e5community.util.photo;

import java.io.File;
import java.text.DecimalFormat;

import android.content.Context;

public class FileCache {

	private File cacheDir;

	public FileCache(Context context) {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					"E5Community");
		else
			cacheDir = context.getCacheDir();
		if (!cacheDir.exists())
			cacheDir.mkdirs();
	}
	public File getFile(String url) {
		String filename = String.valueOf(url.hashCode());
		File f = new File(cacheDir, filename);
		return f;
	}

	/**
	 * 清理缓存
	 */
	public void clear() {
		File[] files = cacheDir.listFiles();
		if (files == null)
			return;
		for (File f : files) {
			f.delete();
		}
	}
	/**
	 * 获得HuaiHaichao文件夹下面的缓存大小
	 * 
	 * @return
	 */
	public String getCacheSize() {
		try {
			String Size = FormetFileSize(getFolderSize(cacheDir));
			System.out.println("缓存大小为" + Size);
			return Size;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 获得缓存工具类
	 */
	public long getFolderSize(java.io.File file) throws Exception {
		long size = 0;
		java.io.File[] fileList = file.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isDirectory()) {
				size = size + getFolderSize(fileList[i]);
			} else {
				size = size + fileList[i].length();
			}
		}
		return size;
	}

	// 转换文件大小
	public String FormetFileSize(long fileS) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

}