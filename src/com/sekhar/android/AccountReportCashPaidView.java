package com.sekhar.android;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.mkyong.android.R;

public class AccountReportCashPaidView extends Activity {

	final Context context = this;
	private TableLayout tableLayout;
	private DbAdapter dbHelper;
	private TableLayout accountItemsReportTableLayout;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accountitemscashreport);
		setTitle("Cash Paid Report - Selected Date: "+AccountReportView.selectedDate);
		dbHelper = new DbAdapter(this);
		dbHelper.open();
		tableLayout = (TableLayout) findViewById(R.id.accountPerticularsLayout);
		accountItemsReportTableLayout = (TableLayout) findViewById(R.id.accountItemsReportTableLayout);
		displayListView();
	}

	private void displayListView() {
		Cursor cursor = dbHelper
				.fetchAccountEntryParticularsForCashPaid(AccountReportView.selectedType,AccountReportView.selectedDate);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				tableLayout.removeAllViews();
				try {
					do {

						String createdDate = cursor
								.getString(cursor
										.getColumnIndex(DbAdapter.KEY_ACCOUNT_ENTRY_PARTICULARS_DATE));
						if(createdDate != null){
							createdDate = AppUtil.changeDateFormat(createdDate);
						}
						String particularName = cursor
								.getString(cursor
										.getColumnIndex(DbAdapter.KEY_ACCOUNT_ENTRY_PARTICULARS_NAME));
						String amount = cursor
								.getString(cursor
										.getColumnIndex(DbAdapter.KEY_ACCOUNT_ENTRY_PARTICULARS_EXPENSE_AMOUNT));
						String accountEntryId = cursor
								.getString(cursor
										.getColumnIndex(DbAdapter.KEY_ACCOUNT_ENTRY_PARTICULARS_ACCOUNT_ENTRY_ID));
						String firstName = dbHelper.fetchAccountNameByAccountEntryId(accountEntryId);
						final TableRow tr = new TableRow(this);
						tr.setLayoutParams(new LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.WRAP_CONTENT));
						tr.setGravity(Gravity.LEFT);
						TextView textview = new TextView(this);
						TableRow.LayoutParams params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						textview.setLayoutParams(params);
						textview.setText(createdDate);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						textview.setLayoutParams(params);
						textview.setText(firstName);
						textview.setWidth(115);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						textview.setText(particularName);
						textview.setLayoutParams(params);
						textview.setGravity(Gravity.LEFT);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 2;
						textview.setLayoutParams(params);
						textview.setGravity(Gravity.RIGHT);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 2;
						textview.setLayoutParams(params);
						textview.setGravity(Gravity.RIGHT);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 2;
						textview.setLayoutParams(params);
						textview.setGravity(Gravity.RIGHT);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						textview.setLayoutParams(params);
						textview.setText(amount);
						textview.setGravity(Gravity.RIGHT);
						tr.addView(textview);
						tableLayout.addView(tr, new TableLayout.LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.WRAP_CONTENT));
						View v = new View(this);
						v.setLayoutParams(new TableRow.LayoutParams(
								TableRow.LayoutParams.FILL_PARENT, 1));
						v.setBackgroundColor(Color.rgb(51, 51, 51));
						tableLayout.addView(v);

					} while (cursor.moveToNext());

				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		displayTotalRateAmountAdAmount();
	}

	private void displayTotalRateAmountAdAmount() {
		Cursor cursor = dbHelper
				.fetchTotalRateAmountAdAmountByAccountEntryDate(AccountReportView.selectedType,AccountReportView.selectedDate);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {

					double totalAmountValue = cursor.getDouble(1);
					String totalAmountStr = Double.toString(totalAmountValue);

					TableRow tr = new TableRow(this);
					tr.setLayoutParams(new LayoutParams(
							LayoutParams.FILL_PARENT,
							LayoutParams.WRAP_CONTENT));
					tr.setGravity(Gravity.LEFT);
					TextView textview = new TextView(this);
					TableRow.LayoutParams params = new TableRow.LayoutParams(
							TableRow.LayoutParams.MATCH_PARENT,
							TableRow.LayoutParams.WRAP_CONTENT);
					params.span = 3;
					textview.setLayoutParams(params);
					textview.setText("");
					tr.addView(textview);

					textview = new TextView(this);
					params = new TableRow.LayoutParams(
							TableRow.LayoutParams.MATCH_PARENT,
							TableRow.LayoutParams.WRAP_CONTENT);
					params.span = 3;
					textview.setLayoutParams(params);
					textview.setText("");
					textview.setWidth(115);
					tr.addView(textview);

					textview = new TextView(this);
					params = new TableRow.LayoutParams(
							TableRow.LayoutParams.MATCH_PARENT,
							TableRow.LayoutParams.WRAP_CONTENT);
					params.span = 3;
					textview.setText("");
					textview.setLayoutParams(params);
					textview.setGravity(Gravity.LEFT);
					tr.addView(textview);

					textview = new TextView(this);
					params = new TableRow.LayoutParams(
							TableRow.LayoutParams.MATCH_PARENT,
							TableRow.LayoutParams.WRAP_CONTENT);
					params.span = 2;
					textview.setBackgroundColor(Color.WHITE);
					textview.setTextColor(Color.BLACK);
					textview.setLayoutParams(params);
					textview.setText("Total:");
					textview.setGravity(Gravity.LEFT);
					tr.addView(textview);

					textview = new TextView(this);
					textview.setBackgroundColor(Color.WHITE);
					params = new TableRow.LayoutParams(
							TableRow.LayoutParams.MATCH_PARENT,
							TableRow.LayoutParams.WRAP_CONTENT);
					params.span = 2;
					textview.setLayoutParams(params);
					textview.setGravity(Gravity.RIGHT);
					tr.addView(textview);

					textview = new TextView(this);
					textview.setBackgroundColor(Color.WHITE);
					params = new TableRow.LayoutParams(
							TableRow.LayoutParams.MATCH_PARENT,
							TableRow.LayoutParams.WRAP_CONTENT);
					params.span = 2;
					textview.setLayoutParams(params);
					textview.setGravity(Gravity.RIGHT);
					tr.addView(textview);

					textview = new TextView(this);
					textview.setBackgroundColor(Color.WHITE);
					params = new TableRow.LayoutParams(
							TableRow.LayoutParams.MATCH_PARENT,
							TableRow.LayoutParams.WRAP_CONTENT);
					params.span = 3;
					textview.setLayoutParams(params);
					textview.setText(totalAmountStr+"");
					textview.setTextColor(Color.BLACK);
					textview.setGravity(Gravity.RIGHT);
					tr.addView(textview);
					accountItemsReportTableLayout.addView(tr, new TableLayout.LayoutParams(
							LayoutParams.FILL_PARENT,
							LayoutParams.WRAP_CONTENT));
					View v = new View(this);
					v.setLayoutParams(new TableRow.LayoutParams(
							TableRow.LayoutParams.FILL_PARENT, 1));
					v.setBackgroundColor(Color.rgb(51, 51, 51));
					accountItemsReportTableLayout.addView(v);

				} while (cursor.moveToNext());
			}
		}
	}
}