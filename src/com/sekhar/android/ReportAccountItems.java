package com.sekhar.android;

import java.util.List;

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

public class ReportAccountItems extends Activity {

	final Context context = this;
	private TableLayout tableLayout;
	private DbAdapter dbHelper;
	private TextView totalRate = null;
	private TextView reportTotalAvailableAmnt = null;
	private TextView reportTotalAdAmountDiff = null;
	private TextView totalAmount = null;
	private TextView totalAdAmount = null;
	private TextView totalAmountForDiff = null;
	private TextView totalAmountExpenseDiff = null;
	
	private TextView reportFirstName = null;
	private TextView reportLastName = null;
	private TextView reportPhone = null;
	private TextView reportAddress = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reportaccountitems);
		if(AccountList.selectedAccountFirstName==null){
			AccountList.selectedAccountFirstName="";
		}
		setTitle(AccountList.selectedAccountFirstName.toUpperCase()+" Report");
		dbHelper = new DbAdapter(this);
		dbHelper.open();
		tableLayout = (TableLayout) findViewById(R.id.accountPerticularsLayout);

		reportFirstName = (TextView) findViewById(R.id.reportAccountFirstName);
		reportFirstName.setText(AccountList.selectedAccountFirstName);
		reportLastName = (TextView) findViewById(R.id.reportAccountLasttName);
		reportLastName.setText(AccountList.selectedAccountLastName);
		reportPhone = (TextView) findViewById(R.id.reportAccountPhone);
		reportPhone.setText(AccountList.selectedAccountPhone);
		reportAddress = (TextView) findViewById(R.id.reportAccountAddress);
		reportAddress.setText(AccountList.selectedAccountAddress);
		
		totalRate = (TextView) findViewById(R.id.totalRate);
		totalRate.setText("0.00");

		reportTotalAvailableAmnt = (TextView) findViewById(R.id.reportTotalAvailableAmnt);
		reportTotalAvailableAmnt.setText("0.00");
		
		reportTotalAdAmountDiff = (TextView) findViewById(R.id.reportTotalAdAmountDiff);
		reportTotalAdAmountDiff.setText("0.00");
		
		totalAmount = (TextView) findViewById(R.id.totalAmount);
		totalAmount.setText("0.00");

		totalAdAmount = (TextView) findViewById(R.id.totalAdAmount);
		totalAdAmount.setText("0.00");

		totalAmountForDiff = (TextView) findViewById(R.id.totalAmountForDiff);
		totalAmountForDiff.setText("0.00");

		totalAmountExpenseDiff = (TextView) findViewById(R.id.totalAmountExpenseDiff);
		totalAmountExpenseDiff.setText("0.00");
		displayListView();
	}

	private void displayListView() {

		Cursor cursorAccountEntryList = dbHelper
				.fetchAccountEntrysByAccountId(AccountList.selectedAccountId);
		if (cursorAccountEntryList != null) {
			try {
				tableLayout.removeAllViews();
				do {
					// column headers
					String accountEntryId = cursorAccountEntryList
							.getString(cursorAccountEntryList
									.getColumnIndex(DbAdapter.KEY_ACCOUNT_ENTRY_ROWID));
					
					TableRow tr = new TableRow(this);
					tr.setLayoutParams(new LayoutParams(
							LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					tr.setGravity(Gravity.CENTER_HORIZONTAL);
					
					TextView textview = new TextView(this);
					TableRow.LayoutParams params = new TableRow.LayoutParams(
							TableRow.LayoutParams.MATCH_PARENT,
							TableRow.LayoutParams.WRAP_CONTENT);
					params.span = 3;
					textview.setLayoutParams(params);
					textview.setText("Date");
					textview.setBackgroundColor(Color.WHITE);
					textview.setTextColor(Color.BLACK);
					tr.addView(textview);

					textview = new TextView(this);
					params = new TableRow.LayoutParams(
							TableRow.LayoutParams.MATCH_PARENT,
							TableRow.LayoutParams.WRAP_CONTENT);
					params.span = 3;
					textview.setLayoutParams(params);
					textview.setText("Particulars");
					textview.setBackgroundColor(Color.WHITE);
					textview.setTextColor(Color.BLACK);
					tr.addView(textview);

					textview = new TextView(this);
					textview.setText("# Items");
					textview.setBackgroundColor(Color.WHITE);
					textview.setTextColor(Color.BLACK);
					params = new TableRow.LayoutParams(
							TableRow.LayoutParams.MATCH_PARENT,
							TableRow.LayoutParams.WRAP_CONTENT);
					params.span = 2;
					textview.setLayoutParams(params);
					textview.setGravity(Gravity.CENTER_HORIZONTAL);
					tr.addView(textview);

					textview = new TextView(this);
					params = new TableRow.LayoutParams(
							TableRow.LayoutParams.MATCH_PARENT,
							TableRow.LayoutParams.WRAP_CONTENT);
					params.span = 3;
					textview.setLayoutParams(params);
					textview.setText("Rate");
					textview.setBackgroundColor(Color.WHITE);
					textview.setTextColor(Color.BLACK);
					textview.setGravity(Gravity.RIGHT);
					tr.addView(textview);

					textview = new TextView(this);
					params = new TableRow.LayoutParams(
							TableRow.LayoutParams.MATCH_PARENT,
							TableRow.LayoutParams.WRAP_CONTENT);
					params.span = 2;
					textview.setLayoutParams(params);
					textview.setText("Amount");
					textview.setBackgroundColor(Color.WHITE);
					textview.setTextColor(Color.BLACK);
					textview.setGravity(Gravity.RIGHT);
					tr.addView(textview);

					textview = new TextView(this);
					params = new TableRow.LayoutParams(
							TableRow.LayoutParams.MATCH_PARENT,
							TableRow.LayoutParams.WRAP_CONTENT);
					params.span = 2;
					textview.setLayoutParams(params);
					textview.setText("Exp. Amount");
					textview.setBackgroundColor(Color.WHITE);
					textview.setTextColor(Color.BLACK);
					textview.setGravity(Gravity.RIGHT);
					tr.addView(textview);

					textview = new TextView(this);
					params = new TableRow.LayoutParams(
							TableRow.LayoutParams.MATCH_PARENT,
							TableRow.LayoutParams.WRAP_CONTENT);
					params.span = 3;
					textview.setLayoutParams(params);
					textview.setText("Advance Amount");
					textview.setBackgroundColor(Color.WHITE);
					textview.setTextColor(Color.BLACK);
					textview.setGravity(Gravity.RIGHT);
					tr.addView(textview);
					tableLayout.addView(tr,
							new TableLayout.LayoutParams(
									LayoutParams.FILL_PARENT,
									LayoutParams.WRAP_CONTENT));

					double totalAvailableAdAmount = dbHelper
							.fetchAvailableAdAmountForNextEntryByAccountEntryId(accountEntryId,AccountList.selectedAccountId);
					if(totalAvailableAdAmount != -1){
						tr = new TableRow(this);
						tr.setLayoutParams(new LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.WRAP_CONTENT));
						tr.setGravity(Gravity.CENTER_HORIZONTAL);
						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						textview.setLayoutParams(params);
						tr.addView(textview);
	
						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						textview.setText("Opening Advance Amount");
						textview.setLayoutParams(params);
						tr.addView(textview);
	
						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 2;
						textview.setLayoutParams(params);
						textview.setGravity(Gravity.CENTER_HORIZONTAL);
						tr.addView(textview);
	
						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
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
						textview.setText(AppUtil.formatDouble(totalAvailableAdAmount));
						textview.setGravity(Gravity.RIGHT);
						tr.addView(textview);
						tableLayout.addView(tr,
								new TableLayout.LayoutParams(
										LayoutParams.FILL_PARENT,
										LayoutParams.WRAP_CONTENT));
						View v = new View(this);
						v.setLayoutParams(new TableRow.LayoutParams(
								TableRow.LayoutParams.FILL_PARENT, 1));
						v.setBackgroundColor(Color.rgb(51, 51, 51));
						tableLayout.addView(v);
					}
					List perticularDeailsList = dbHelper
							.fetchAccountEntryParticularsByAccountEntryId(accountEntryId);
					if (perticularDeailsList != null) {
						if (perticularDeailsList.size()>0) {
								for(int i=0;i<perticularDeailsList.size();i++){
									PerticularDetail perticularDetail = (PerticularDetail)perticularDeailsList.get(i);
									String entryParticularsId = perticularDetail.getEntryParticularsId();
									
									String createdDate = perticularDetail.getCreatedDate();
									String quantity = perticularDetail.getQuantity();
									String particularName =perticularDetail.getParticularName();
									String rate = perticularDetail.getRate();
									String calculatedAmount = perticularDetail.getCalculatedAmount();
									String amount = perticularDetail.getAmount();
									String advanceAmount = perticularDetail.getAdvanceAmount();
								tr = new TableRow(this);
								tr.setId(Integer.parseInt(entryParticularsId));
								tr.setLayoutParams(new LayoutParams(
										LayoutParams.FILL_PARENT,
										LayoutParams.WRAP_CONTENT));
								tr.setGravity(Gravity.CENTER_HORIZONTAL);
								textview = new TextView(this);
								params = new TableRow.LayoutParams(
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
								textview.setText(particularName);
								tr.addView(textview);

								textview = new TextView(this);
								textview.setText(quantity);
								params = new TableRow.LayoutParams(
										TableRow.LayoutParams.MATCH_PARENT,
										TableRow.LayoutParams.WRAP_CONTENT);
								params.span = 2;
								textview.setLayoutParams(params);
								textview.setGravity(Gravity.CENTER_HORIZONTAL);
								tr.addView(textview);

								textview = new TextView(this);
								params = new TableRow.LayoutParams(
										TableRow.LayoutParams.MATCH_PARENT,
										TableRow.LayoutParams.WRAP_CONTENT);
								params.span = 3;
								textview.setLayoutParams(params);
								textview.setText(rate);
								textview.setGravity(Gravity.RIGHT);
								tr.addView(textview);

								textview = new TextView(this);
								params = new TableRow.LayoutParams(
										TableRow.LayoutParams.MATCH_PARENT,
										TableRow.LayoutParams.WRAP_CONTENT);
								params.span = 2;
								textview.setLayoutParams(params);
								textview.setText(calculatedAmount);
								textview.setGravity(Gravity.RIGHT);
								tr.addView(textview);

								textview = new TextView(this);
								params = new TableRow.LayoutParams(
										TableRow.LayoutParams.MATCH_PARENT,
										TableRow.LayoutParams.WRAP_CONTENT);
								params.span = 2;
								textview.setLayoutParams(params);
								textview.setText(amount);
								textview.setGravity(Gravity.RIGHT);
								tr.addView(textview);

								textview = new TextView(this);
								params = new TableRow.LayoutParams(
										TableRow.LayoutParams.MATCH_PARENT,
										TableRow.LayoutParams.WRAP_CONTENT);
								params.span = 3;
								textview.setLayoutParams(params);
								textview.setText(advanceAmount);
								textview.setGravity(Gravity.RIGHT);
								tr.addView(textview);
								tableLayout.addView(tr,
										new TableLayout.LayoutParams(
												LayoutParams.FILL_PARENT,
												LayoutParams.WRAP_CONTENT));
								View v = new View(this);
								v.setLayoutParams(new TableRow.LayoutParams(
										TableRow.LayoutParams.FILL_PARENT, 1));
								v.setBackgroundColor(Color.rgb(51, 51, 51));
								tableLayout.addView(v);

							} 
						}

						// total table rows

						tr = new TableRow(this);
						tr.setLayoutParams(new LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.WRAP_CONTENT));
						tr.setGravity(Gravity.CENTER_HORIZONTAL);
						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						textview.setLayoutParams(params);
						textview.setText("");
						textview.setTextColor(Color.BLACK);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						textview.setLayoutParams(params);
						textview.setText("");
						textview.setTextColor(Color.BLACK);
						tr.addView(textview);

						textview = new TextView(this);
						textview.setText("");
						textview.setTextColor(Color.BLACK);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 2;
						textview.setLayoutParams(params);
						textview.setGravity(Gravity.CENTER_HORIZONTAL);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						textview.setLayoutParams(params);
						textview.setText("Total:");
						textview.setBackgroundColor(Color.GRAY);
						textview.setTextColor(Color.BLACK);
						textview.setGravity(Gravity.LEFT);
						tr.addView(textview);

						TextView totalRatePerEntry = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 2;
						totalRatePerEntry.setLayoutParams(params);
						totalRatePerEntry.setText("");
						totalRatePerEntry.setBackgroundColor(Color.GRAY);
						totalRatePerEntry.setTextColor(Color.BLACK);
						totalRatePerEntry.setGravity(Gravity.RIGHT);
						tr.addView(totalRatePerEntry);

						TextView totalAmountPerEntry = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 2;
						totalAmountPerEntry.setLayoutParams(params);
						totalAmountPerEntry.setText("");
						totalAmountPerEntry.setBackgroundColor(Color.GRAY);
						totalAmountPerEntry.setTextColor(Color.BLACK);
						totalAmountPerEntry.setGravity(Gravity.RIGHT);
						tr.addView(totalAmountPerEntry);

						TextView totalAdAmountPerEntry = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						totalAdAmountPerEntry.setLayoutParams(params);
						totalAdAmountPerEntry.setText("");
						totalAdAmountPerEntry.setBackgroundColor(Color.GRAY);
						totalAdAmountPerEntry.setTextColor(Color.BLACK);
						totalAdAmountPerEntry.setGravity(Gravity.RIGHT);
						tr.addView(totalAdAmountPerEntry);
						tableLayout.addView(tr, new TableLayout.LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.WRAP_CONTENT));

						tr = new TableRow(this);
						tr.setLayoutParams(new LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.WRAP_CONTENT));
						tr.setGravity(Gravity.CENTER_HORIZONTAL);
						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						textview.setLayoutParams(params);
						textview.setText("");
						textview.setTextColor(Color.BLACK);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						textview.setLayoutParams(params);
						textview.setText("");
						textview.setTextColor(Color.BLACK);
						tr.addView(textview);

						textview = new TextView(this);
						textview.setText("");
						textview.setTextColor(Color.BLACK);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 2;
						textview.setLayoutParams(params);
						textview.setGravity(Gravity.CENTER_HORIZONTAL);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						textview.setLayoutParams(params);
						textview.setText("");
						textview.setBackgroundColor(Color.GRAY);
						textview.setTextColor(Color.BLACK);
						textview.setGravity(Gravity.RIGHT);
						tr.addView(textview);


						TextView totalAmountForDiffPerEntry = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 2;
						totalAmountForDiffPerEntry.setLayoutParams(params);
						totalAmountForDiffPerEntry.setText("");
						totalAmountForDiffPerEntry.setBackgroundColor(Color.GRAY);
						totalAmountForDiffPerEntry.setTextColor(Color.BLACK);
						totalAmountForDiffPerEntry.setGravity(Gravity.RIGHT);
						tr.addView(totalAmountForDiffPerEntry);


						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 2;
						textview.setLayoutParams(params);
						textview.setText("");
						textview.setBackgroundColor(Color.GRAY);
						textview.setTextColor(Color.BLACK);
						textview.setGravity(Gravity.RIGHT);
						tr.addView(textview);
						
						TextView totalAdAmountForDiffPerEntry = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						totalAdAmountForDiffPerEntry.setLayoutParams(params);
						totalAdAmountForDiffPerEntry.setText("");
						totalAdAmountForDiffPerEntry.setBackgroundColor(Color.GRAY);
						totalAdAmountForDiffPerEntry.setTextColor(Color.BLACK);
						totalAdAmountForDiffPerEntry.setGravity(Gravity.RIGHT);
						tr.addView(totalAdAmountForDiffPerEntry);
						tableLayout.addView(tr, new TableLayout.LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.WRAP_CONTENT));

						tr = new TableRow(this);
						tr.setLayoutParams(new LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.WRAP_CONTENT));
						tr.setGravity(Gravity.CENTER_HORIZONTAL);
						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						textview.setLayoutParams(params);
						textview.setText("");
						textview.setTextColor(Color.BLACK);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						textview.setLayoutParams(params);
						textview.setText("");
						textview.setTextColor(Color.BLACK);
						tr.addView(textview);

						textview = new TextView(this);
						textview.setText("");
						textview.setTextColor(Color.BLACK);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 2;
						textview.setLayoutParams(params);
						textview.setGravity(Gravity.CENTER_HORIZONTAL);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						textview.setLayoutParams(params);
						textview.setText("Balance:");
						textview.setBackgroundColor(Color.GRAY);
						textview.setTextColor(Color.BLACK);
						textview.setGravity(Gravity.LEFT);
						tr.addView(textview);

						TextView totalAmountExpenseDiffPerEntry = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 2;
						totalAmountExpenseDiffPerEntry.setLayoutParams(params);
						totalAmountExpenseDiffPerEntry.setText("");
						totalAmountExpenseDiffPerEntry.setBackgroundColor(Color.GRAY);
						totalAmountExpenseDiffPerEntry.setTextColor(Color.BLACK);
						totalAmountExpenseDiffPerEntry.setGravity(Gravity.RIGHT);
						tr.addView(totalAmountExpenseDiffPerEntry);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 2;
						textview.setLayoutParams(params);
						textview.setText("");
						textview.setBackgroundColor(Color.GRAY);
						textview.setTextColor(Color.BLACK);
						textview.setGravity(Gravity.RIGHT);
						tr.addView(textview);
						
						TextView totalAdAmountDiffPerEntry = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						totalAdAmountDiffPerEntry.setLayoutParams(params);
						totalAdAmountDiffPerEntry.setText("");
						totalAdAmountDiffPerEntry.setBackgroundColor(Color.GRAY);
						totalAdAmountDiffPerEntry.setTextColor(Color.BLACK);
						totalAdAmountDiffPerEntry.setGravity(Gravity.RIGHT);
						tr.addView(totalAdAmountDiffPerEntry);
						tableLayout.addView(tr, new TableLayout.LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.WRAP_CONTENT));

						// calculate totals per entry
						displayTotalRateAmountAdAmount(totalRatePerEntry,
								totalAmountPerEntry, totalAdAmountPerEntry,
								totalAmountForDiffPerEntry,
								totalAmountExpenseDiffPerEntry, accountEntryId,totalAdAmountForDiffPerEntry,totalAdAmountDiffPerEntry);

						// empty rows
						tr = new TableRow(this);
						tr.setLayoutParams(new LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.WRAP_CONTENT));
						tr.setGravity(Gravity.CENTER_HORIZONTAL);
						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						textview.setLayoutParams(params);
						textview.setText("");
						textview.setTextColor(Color.BLACK);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span =3;
						textview.setLayoutParams(params);
						textview.setText("");
						textview.setTextColor(Color.BLACK);
						tr.addView(textview);

						textview = new TextView(this);
						textview.setText("");
						textview.setTextColor(Color.BLACK);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 2;
						textview.setLayoutParams(params);
						textview.setGravity(Gravity.CENTER_HORIZONTAL);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						textview.setLayoutParams(params);
						textview.setText("");
						textview.setTextColor(Color.BLACK);
						textview.setGravity(Gravity.RIGHT);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 2;
						textview.setLayoutParams(params);
						textview.setText("");
						textview.setTextColor(Color.BLACK);
						textview.setGravity(Gravity.RIGHT);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 2;
						textview.setLayoutParams(params);
						textview.setText("");
						textview.setTextColor(Color.BLACK);
						textview.setGravity(Gravity.RIGHT);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						textview.setLayoutParams(params);
						textview.setText("");
						textview.setTextColor(Color.BLACK);
						textview.setGravity(Gravity.RIGHT);
						tr.addView(textview);
						tableLayout.addView(tr, new TableLayout.LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.WRAP_CONTENT));

						tr = new TableRow(this);
						tr.setLayoutParams(new LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.WRAP_CONTENT));
						tr.setGravity(Gravity.CENTER_HORIZONTAL);
						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						textview.setLayoutParams(params);
						textview.setText("");
						textview.setTextColor(Color.BLACK);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						textview.setLayoutParams(params);
						textview.setText("");
						textview.setTextColor(Color.BLACK);
						tr.addView(textview);

						textview = new TextView(this);
						textview.setText("");
						textview.setTextColor(Color.BLACK);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 2;
						textview.setLayoutParams(params);
						textview.setGravity(Gravity.CENTER_HORIZONTAL);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						textview.setLayoutParams(params);
						textview.setText("");
						textview.setTextColor(Color.BLACK);
						textview.setGravity(Gravity.RIGHT);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 2;
						textview.setLayoutParams(params);
						textview.setText("");
						textview.setTextColor(Color.BLACK);
						textview.setGravity(Gravity.RIGHT);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 2;
						textview.setLayoutParams(params);
						textview.setText("");
						textview.setTextColor(Color.BLACK);
						textview.setGravity(Gravity.RIGHT);
						tr.addView(textview);

						textview = new TextView(this);
						params = new TableRow.LayoutParams(
								TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT);
						params.span = 3;
						textview.setLayoutParams(params);
						textview.setText("");
						textview.setTextColor(Color.BLACK);
						textview.setGravity(Gravity.RIGHT);
						tr.addView(textview);
						tableLayout.addView(tr, new TableLayout.LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.WRAP_CONTENT));

					}
				} while (cursorAccountEntryList.moveToNext());
				displayTotalRateAmountAdAmount();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	private void displayTotalRateAmountAdAmount(TextView totalRate,
			TextView totalAmount, TextView totalAdAmount,
			TextView totalAmountForDiff, TextView totalAmountExpenseDiff,
			String accountEntryId,TextView totalAdAmountForDiffPerEntry,TextView totalAdAmountDiffPerEntry) {
		Cursor cursor = dbHelper
				.fetchTotalRateAmountAdAmountByAccountEntryId(accountEntryId);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					double totalRateValue = cursor.getDouble(0);
					String totalRateStr = null;
					if(totalRateValue>0){
						totalRateStr = AppUtil.formatDouble(totalRateValue);	
					}else{
						totalRateStr = String.valueOf(totalRateValue); 
					}

					double totalAmountValue = cursor.getDouble(1);
					String totalAmountStr =null;
					if(totalAmountValue>0){
						totalAmountStr = AppUtil.formatDouble(totalAmountValue);
					}else{
						totalAmountStr = String.valueOf(totalAmountValue);
					}

					double totalAdAmountDouble = cursor.getDouble(2);
					double totalAvailableAdAmount = dbHelper
							.fetchAvailableAdAmountForNextEntryByAccountEntryId(accountEntryId,AccountList.selectedAccountId);
					if(totalAvailableAdAmount != -1){
						totalAdAmountDouble = totalAdAmountDouble+totalAvailableAdAmount;
					}
					
					String totalAdAmountStr =null;
					if(totalAdAmountDouble>0){
						totalAdAmountStr = AppUtil.formatDouble(totalAdAmountDouble);
					}else{
						totalAdAmountStr = String.valueOf(totalAdAmountDouble);
					}

					totalRate.setText(totalRateStr);
					totalRate.setTextColor(Color.BLUE);
					totalAmount.setText(totalAmountStr);
					totalAmount.setTextColor(Color.BLUE);
					totalAdAmount.setText(totalAdAmountStr);
					totalAdAmount.setTextColor(Color.BLUE);
					totalAmountForDiff.setText("(-) "+totalAmountStr);
					totalAmountForDiff.setTextColor(Color.BLUE);
					double diff = totalRateValue - totalAmountValue;
					totalAmountExpenseDiff.setText(Double.toString(diff));
					totalAdAmountForDiffPerEntry.setText(Double.toString(diff));
					
					if(diff==0.0){
						totalAmountExpenseDiff.setText(String.valueOf(diff));
						totalAdAmountForDiffPerEntry.setText("(-) "+String.valueOf(diff));
					}else{
						totalAmountExpenseDiff.setText(AppUtil.formatDouble(diff));
						totalAdAmountForDiffPerEntry.setText("(-) "+AppUtil.formatDouble(diff));
					}
					
					if (diff < 0) {
						totalAmountExpenseDiff.setTextColor(Color.RED);
						totalAdAmountForDiffPerEntry.setTextColor(Color.RED);
					} else {
						totalAmountExpenseDiff.setTextColor(Color.GREEN);
						totalAdAmountForDiffPerEntry.setTextColor(Color.GREEN);
					}
					double adDiff = totalAdAmountDouble-diff;
					if(adDiff==0.0){
						totalAdAmountDiffPerEntry.setText(String.valueOf(adDiff));
					}else{
						totalAdAmountDiffPerEntry.setText(AppUtil.formatDouble(adDiff));
					}
					
					if(adDiff<0){
						totalAdAmountDiffPerEntry.setTextColor(Color.RED);
					}else{
						totalAdAmountDiffPerEntry.setTextColor(Color.GREEN);
					}

				} while (cursor.moveToNext());
			}
		}
	}

	private void displayTotalRateAmountAdAmount() {
		Cursor cursorAccountEntryList = dbHelper
				.fetchAccountEntrysByAccountId(AccountList.selectedAccountId);
		if (cursorAccountEntryList != null) {
			double totalRateValue=0.00;
			double totalAmountValue = 0.00;
			double totalAdAmountValue=0.00;
			try {
				do {
					String accountEntryId = cursorAccountEntryList
							.getString(cursorAccountEntryList
									.getColumnIndex(DbAdapter.KEY_ACCOUNT_ENTRY_ROWID));
					Cursor cursor = dbHelper
							.fetchTotalRateAmountAdAmountByAccountEntryId(accountEntryId);
					if (cursor != null) {
						if (cursor.moveToFirst()) {
							do {
								totalRateValue = totalRateValue + cursor.getDouble(0);

								totalAmountValue = totalAmountValue + cursor.getDouble(1);
								
								totalAdAmountValue = totalAdAmountValue +  cursor.getDouble(2);
							} while (cursor.moveToNext());
						}
					}
				} while (cursorAccountEntryList.moveToNext());
				String totalRateStr = Double
						.toString(totalRateValue);				
				totalRate.setText(totalRateStr);
				
				String totalAmountStr = Double
						.toString(totalAmountValue);				
				totalRate.setTextColor(Color.BLUE);
				
				totalAmount.setText(totalAmountStr);
				totalAmount.setTextColor(Color.BLUE);

				String totalAdAmountStr = Double
						.toString(totalAdAmountValue);
				totalAdAmount.setText(totalAdAmountStr);
				totalAdAmount.setTextColor(Color.BLUE);
				
				totalAmountForDiff.setText("(-) "+totalAmountStr);
				totalAmountForDiff.setTextColor(Color.BLUE);
				double diff = totalRateValue-totalAmountValue;
				if(diff==0.0){
					totalAmountExpenseDiff.setText(String.valueOf(diff));
					reportTotalAvailableAmnt.setText("(-) "+String.valueOf(diff));
				}else{
					totalAmountExpenseDiff.setText(AppUtil.formatDouble(diff));
					reportTotalAvailableAmnt.setText("(-) "+AppUtil.formatDouble(diff));
				}
				
				if(diff<0){
					totalAmountExpenseDiff.setTextColor(Color.RED);
					reportTotalAvailableAmnt.setTextColor(Color.RED);
				}else{
					totalAmountExpenseDiff.setTextColor(Color.GREEN);
					reportTotalAvailableAmnt.setTextColor(Color.GREEN);
				}
				double adDiff = totalAdAmountValue-diff;
				if(adDiff==0.0){
					reportTotalAdAmountDiff.setText(String.valueOf(adDiff));
				}else{
					reportTotalAdAmountDiff.setText(AppUtil.formatDouble(adDiff));
				}
				
				if(adDiff<0){
					reportTotalAdAmountDiff.setTextColor(Color.RED);
				}else{
					reportTotalAdAmountDiff.setTextColor(Color.GREEN);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
