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

public class AccountReportSareeReceivedView extends Activity {

	final Context context = this;
	
	private TableLayout tableLayout;
	private TableLayout accountItemsReportTableLayout;
	private DbAdapter dbHelper;
	private TextView accountItemReportHeaderName = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accountitemscashreport);
		setTitle("Sarees Recieved Report - Selected Date: "+AccountReportView.selectedDate);
		dbHelper = new DbAdapter(this);
		dbHelper.open();
		tableLayout = (TableLayout) findViewById(R.id.accountPerticularsLayout);
		accountItemReportHeaderName = (TextView)findViewById(R.id.accountItemReportHeaderName);
		accountItemReportHeaderName.setText("# Sarees");
		accountItemsReportTableLayout = (TableLayout) findViewById(R.id.accountItemsReportTableLayout);
		displayListView();
	}

	private void displayListView() {
		int grandTotalNumOfSarees=0;
		Map totalNumOfSareesPerPerticularMap = null;
		List sareesReceivedList = dbHelper
				.fetchAccountEntryParticularsForSareesReceived(AccountReportView.selectedType,AccountReportView.selectedDate);
		if (sareesReceivedList != null) {
			if (sareesReceivedList.size()>0) {
				totalNumOfSareesPerPerticularMap = new HashMap();
				tableLayout.removeAllViews();
				try {
					for(int i=0;i<sareesReceivedList.size();i++){
						SareesReceivedReportVO sareesReceivedReportVO = (SareesReceivedReportVO)sareesReceivedList.get(i);
						String createdDate = sareesReceivedReportVO.getDate();
						if(createdDate != null){
							createdDate = AppUtil.changeDateFormat(createdDate);
						}
						String particularName = sareesReceivedReportVO.getParticularName();
						String numOfSarees = sareesReceivedReportVO.getNumOfSarees();
						String numOfSareesPerPerticularName = (String)totalNumOfSareesPerPerticularMap.get(particularName);
						if(numOfSareesPerPerticularName != null && !numOfSareesPerPerticularName.equals("")){
							int numOfSareesVal = Integer.parseInt(numOfSareesPerPerticularName);
							numOfSareesVal = numOfSareesVal + Integer.parseInt(numOfSarees);
							totalNumOfSareesPerPerticularMap.put(particularName, numOfSareesVal+"");
						}else{
							totalNumOfSareesPerPerticularMap.put(particularName, numOfSarees);
						}
						grandTotalNumOfSarees = grandTotalNumOfSarees+Integer.parseInt(numOfSarees);
						String firstName = sareesReceivedReportVO.getName();
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
						textview.setText(numOfSarees);
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
		displayTotalRateAmountAdAmount(grandTotalNumOfSarees,totalNumOfSareesPerPerticularMap);
	}

	private void displayTotalRateAmountAdAmount(int grandTotalNumOfSarees,Map totalNumOfSareesPerPerticularMap) {
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
					params.span = 1;
					textview.setText("");
					textview.setLayoutParams(params);
					textview.setGravity(Gravity.LEFT);
					tr.addView(textview);

					textview = new TextView(this);
					params = new TableRow.LayoutParams(
							TableRow.LayoutParams.MATCH_PARENT,
							TableRow.LayoutParams.WRAP_CONTENT);
					params.span = 4;
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
					textview.setText(grandTotalNumOfSarees+"");
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

					
				if(totalNumOfSareesPerPerticularMap != null){
				Set keys = totalNumOfSareesPerPerticularMap.keySet();
				Iterator iterator = keys.iterator();
				while(iterator.hasNext()){
					String key = (String)iterator.next();
					String val = (String)totalNumOfSareesPerPerticularMap.get(key);
					tr = new TableRow(this);
					tr.setLayoutParams(new LayoutParams(
							LayoutParams.FILL_PARENT,
							LayoutParams.WRAP_CONTENT));
					tr.setGravity(Gravity.LEFT);
					textview = new TextView(this);
					params = new TableRow.LayoutParams(
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
					params.span = 1;
					textview.setText("");
					textview.setLayoutParams(params);
					textview.setGravity(Gravity.LEFT);
					tr.addView(textview);

					textview = new TextView(this);
					textview.setBackgroundColor(Color.WHITE);
					textview.setTextColor(Color.BLACK);
					params = new TableRow.LayoutParams(
							TableRow.LayoutParams.MATCH_PARENT,
							TableRow.LayoutParams.WRAP_CONTENT);
					params.span = 4;
					textview.setLayoutParams(params);
					textview.setText(key);
					textview.setGravity(Gravity.LEFT);
					tr.addView(textview);

					textview = new TextView(this);
					textview.setBackgroundColor(Color.WHITE);
					params = new TableRow.LayoutParams(
							TableRow.LayoutParams.MATCH_PARENT,
							TableRow.LayoutParams.WRAP_CONTENT);
					params.span = 2;
					textview.setLayoutParams(params);
					textview.setBackgroundColor(Color.WHITE);
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
					textview.setTextColor(Color.BLACK);
					params = new TableRow.LayoutParams(
							TableRow.LayoutParams.MATCH_PARENT,
							TableRow.LayoutParams.WRAP_CONTENT);
					params.span = 3;
					textview.setLayoutParams(params);
					textview.setText(val);
					textview.setGravity(Gravity.RIGHT);
					tr.addView(textview);
					accountItemsReportTableLayout.addView(tr, new TableLayout.LayoutParams(
							LayoutParams.FILL_PARENT,
							LayoutParams.WRAP_CONTENT));
					v = new View(this);
					v.setLayoutParams(new TableRow.LayoutParams(
							TableRow.LayoutParams.FILL_PARENT, 1));
					v.setBackgroundColor(Color.rgb(51, 51, 51));
					accountItemsReportTableLayout.addView(v);
				}
			}
	}
}