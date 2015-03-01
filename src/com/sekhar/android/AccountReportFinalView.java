package com.sekhar.android;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.mkyong.android.R;

public class AccountReportFinalView extends Activity {

	final Context context = this;
	
	private TableLayout tableLayout;
	private TableLayout accountItemsReportTableLayout;
	private DbAdapter dbHelper;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accountfinalreport);
		setTitle("Final Report - Selected Start Date: "+AccountReportView.selectedStartDate + " End Date: "+AccountReportView.selectedEndDate);
		dbHelper = new DbAdapter(this);
		dbHelper.open();
		tableLayout = (TableLayout) findViewById(R.id.accountFinalPerticularsLayout);
		accountItemsReportTableLayout = (TableLayout) findViewById(R.id.accountFinalReportTableLayout);
		displayListView();
	}

	private void displayListView() {
		int grandTotalNumOfSarees=0;
		List finalReportVOList = dbHelper
				.fetchFianlAccountEntryParticulars(AccountReportView.selectedStartDate,AccountReportView.selectedEndDate);
		if (finalReportVOList != null) {
			if (finalReportVOList.size()>0) {
				tableLayout.removeAllViews();
				try {
					for(int i=0;i<finalReportVOList.size();i++){
						FinalReportVO finalReportVO = (FinalReportVO)finalReportVOList.get(i);

						String createdDate = finalReportVO.getDate();
						String firstName = finalReportVO.getName();
						String creditAmnt = finalReportVO.getCredit();
						String debitAmnt = finalReportVO.getDebit();
						String adAmnount = finalReportVO.getAdAmount();
						String balanceAmnt = finalReportVO.getBalance();
						
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
						textview.setText(creditAmnt);
						textview.setLayoutParams(params);
						textview.setGravity(Gravity.LEFT);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 2;
						textview.setText(debitAmnt);
						textview.setLayoutParams(params);
						textview.setGravity(Gravity.RIGHT);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 2;
						textview.setText(adAmnount);
						textview.setLayoutParams(params);
						textview.setGravity(Gravity.RIGHT);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 2;
						textview.setText(balanceAmnt);
						textview.setLayoutParams(params);
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
					}

				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}
}