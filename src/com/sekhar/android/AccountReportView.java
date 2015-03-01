package com.sekhar.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.TextView;

import com.mkyong.android.R;

public class AccountReportView extends Activity {

	final Context context = this;
	private DbAdapter dbHelper;
	private TextView cashPaidItem = null;
	private TextView sareeReceivedItem = null;
	private TextView colorDyeingItem = null;
	private TextView finalReportItem = null;
	private TextView handloomSparePartsItem = null;
	private TextView jariLoadingPartsItem = null;

	public static String selectedType = null;
	public static String selectedDate = null;
	public static String selectedStartDate = null;
	public static String selectedEndDate = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accountreportview);
		dbHelper = new DbAdapter(this);
		dbHelper.open();
		cashPaidItem = (TextView) findViewById(R.id.cashPaidItem);
		sareeReceivedItem = (TextView) findViewById(R.id.sareeRecievedItem);
		colorDyeingItem = (TextView) findViewById(R.id.colorDyeingItem);
		finalReportItem = (TextView) findViewById(R.id.finalReportItem);
		handloomSparePartsItem = (TextView) findViewById(R.id.handloomSparePartsItem);
		jariLoadingPartsItem = (TextView) findViewById(R.id.jariLoadingPartsItem);
		final DatePicker datePicker = (DatePicker) findViewById(R.id.accountReportDatePicker);
		final DatePicker startDatePicker = (DatePicker) findViewById(R.id.accountReportStartDatePicker);
		final DatePicker endDatePicker = (DatePicker) findViewById(R.id.accountReportEndDatePicker);
		cashPaidItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				selectedType = AppUtil.CASH_PAYMENT;
				selectedDate = datePicker.getDayOfMonth() + "/"
						+ (datePicker.getMonth() + 1) + "/"
						+ datePicker.getYear();
				Intent intent = new Intent(context,
						AccountReportCashPaidView.class);
				startActivity(intent);
			}
		});

		sareeReceivedItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				selectedType = AppUtil.SAREE;
				selectedDate = datePicker.getDayOfMonth() + "/"
						+ (datePicker.getMonth() + 1) + "/"
						+ datePicker.getYear();
				Intent intent = new Intent(context,
						AccountReportSareeReceivedView.class);
				startActivity(intent);
			}
		});

		colorDyeingItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				selectedType = AppUtil.COLOR_DYEING;
				selectedDate = datePicker.getDayOfMonth() + "/"
						+ (datePicker.getMonth() + 1) + "/"
						+ datePicker.getYear();
				Intent intent = new Intent(context,
						AccountReportCashPaidView.class);
				startActivity(intent);
			}
		});

		handloomSparePartsItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				selectedType = AppUtil.HANDLOOM_SPARE_PARTS;
				selectedDate = datePicker.getDayOfMonth() + "/"
						+ (datePicker.getMonth() + 1) + "/"
						+ datePicker.getYear();
				Intent intent = new Intent(context,
						AccountReportCashPaidView.class);
				startActivity(intent);
			}
		});

		jariLoadingPartsItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				selectedType = AppUtil.JARI_LOADING;
				selectedDate = datePicker.getDayOfMonth() + "/"
						+ (datePicker.getMonth() + 1) + "/"
						+ datePicker.getYear();
				Intent intent = new Intent(context,
						AccountReportCashPaidView.class);
				startActivity(intent);
			}
		});
		
		finalReportItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				selectedStartDate = startDatePicker.getDayOfMonth() + "/"
						+ (startDatePicker.getMonth() + 1) + "/"
						+ startDatePicker.getYear();
				selectedEndDate = endDatePicker.getDayOfMonth() + "/"
						+ (endDatePicker.getMonth() + 1) + "/"
						+ endDatePicker.getYear();
				Intent intent = new Intent(context,
						AccountReportFinalView.class);
				startActivity(intent);
			}
		});

	}

}