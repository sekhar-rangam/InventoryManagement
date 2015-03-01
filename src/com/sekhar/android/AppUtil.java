package com.sekhar.android;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Environment;

public class AppUtil {

	public static final String ADVANCE_PAYMENT = "ADVANCE PAYMENT";
	public static final String CASH_PAYMENT = "CASH PAYMENT";
	public static final String SAREE = "SAREE";
	public static final String SAREE_BY_CASH = "SAREE BY CASH";
	public static final String RAW_MATERIAL = "RAW MATERIAL";
	public static final String COLOR_DYEING = "COLOR DYEING";
	public static final String HANDLOOM_SPARE_PARTS = "HANDLOOM SPARE PARTS";
	public static final String JARI_LOADING = "JARI LOADING";

	public static final DateFormat formatter = new SimpleDateFormat(
			"dd/MM/yyyy");
	public static final SimpleDateFormat newFormat = new SimpleDateFormat(
			"dd-MMM-yyyy");
	public static final SimpleDateFormat newFormat1 = new SimpleDateFormat(
			"dd-MMM-yyyy-HH-mm-ss");

	public static List getEntryTypeList() {
		final List<String> list = new ArrayList<String>();
		list.add(ADVANCE_PAYMENT);
		list.add(CASH_PAYMENT);
		list.add(SAREE);
		list.add(SAREE_BY_CASH);
		list.add(RAW_MATERIAL);
		list.add(COLOR_DYEING);
		list.add(HANDLOOM_SPARE_PARTS);
		list.add(JARI_LOADING);
		return list;
	}

	public static String formatDouble(double dblValue) {
		DecimalFormat df = new DecimalFormat("####.00");
		return df.format(dblValue);
	}

	public static Date convertToDate(String dateStr) {
		Date date = null;
		try {
			date = formatter.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	public static String changeDateFormat(String dateStr) {
		Date temp = convertToDate(dateStr);
		String dateInNewFormat = newFormat.format(temp);
		return dateInNewFormat;
	}

	public static void restoreDb(File currentDB,String restoreDBFileName) {
		try {
			backupDb(currentDB);
			File sd = Environment.getExternalStorageDirectory();
			File data = Environment.getDataDirectory();

			if (sd.canWrite()) {
				File f = new File(sd.getAbsolutePath()+"/AccountManagement");
				if(!f.exists()){
					f.mkdir();
				}
				String restoreDBPath = "AccountManagement/"+restoreDBFileName;
				File restoreDB = new File(sd, restoreDBPath);

				if (currentDB.exists()) {
					FileChannel src = new FileInputStream(restoreDB)
							.getChannel();
					FileChannel dst = new FileOutputStream(currentDB)
							.getChannel();
					dst.transferFrom(src, 0, src.size());
					src.close();
					dst.close();
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void backupDb(File currentDB) {
		try {
			
			File sd = Environment.getExternalStorageDirectory();
			File data = Environment.getDataDirectory();

			if (sd.canWrite()) {
				File f = new File(sd.getAbsolutePath()+"/AccountManagement");
				if(!f.exists()){
					f.mkdir();
				}
				Date currentDate = new Date();
				String currentDateStr = newFormat1.format(currentDate);
				
				currentDateStr= currentDateStr.trim();
				System.out.println("---------------------------------------------------------------"+currentDateStr);
				String backupDBPath = "AccountManagement/backup_"+currentDateStr+".db";
				File backupDB = new File(sd, backupDBPath);

				if (currentDB.exists()) {
					FileChannel src = new FileInputStream(currentDB)
							.getChannel();
					FileChannel dst = new FileOutputStream(backupDB)
							.getChannel();
					dst.transferFrom(src, 0, src.size());
					src.close();
					dst.close();
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}