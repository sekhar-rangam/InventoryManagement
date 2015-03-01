package com.sekhar.android;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.mkyong.android.R;

public class AccountItems extends Activity {

	final Context context = this;
	private Button addExpenseBtn = null;
	private Button addSareeBtn = null;
	private TableLayout tableLayout;
	public static String selectedExpenseTypeName;
	public static String selectedExpenseTypeMarketPrice;
	public static String itemTypeName;
	public static String itemTypeMarketPriceStr;
	private DbAdapter dbHelper;
	private TextView totalRate = null;
	private TextView totalAmount = null;
	private TextView totalAdAmount = null;
	private TextView totalAmountForDiff = null;
	private TextView totalAmountExpenseDiff = null;
	private TextView totalAdAmountDiff = null;
	private TextView totalAvailableAmnt = null;
	private List expenseItemTypeList = null;
	private List sareeItemTypeList = null;
	public static Map itemTypeMarketPrice = null;
	public static Map itemTypeObj = null;
	public static Map itemTypeUnitMeasure = null;
	public static Map itemTypeRowId = null;
	private SimpleCursorAdapter dataAdapter;
	public static EditText expenseQuantity = null;
	public static EditText expenseAmount = null;

	public static EditText particularQuantity = null;
	public static EditText particularAmount = null;
	public static EditText marketPrice_changeclick=null;
	public static EditText measure_changeclick=null;
	public static TextView sareeMarketPrice = null;
	public static TextView expenseMarketPrice = null;
	public static TextView expenseUnitMeasureTxt = null;
	public static TextView sareeUnitMeasureTxt = null;
	public TextView totalSareesReceivedTxt = null;
	public TextView totalsareesRemainingTxt = null;
	public static String selectedItemType;
	public int sareesCount=0;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accountitems);
		if(AccountItemListActivity.selectedAccountEntryName==null){
			AccountItemListActivity.selectedAccountEntryName="";
		}
		setTitle("Name: "+AccountList.selectedAccountFirstName.toUpperCase()+" ("+AccountItemListActivity.selectedAccountEntryName.toUpperCase()+")");
		dbHelper = new DbAdapter(this);
		dbHelper.open();
		addExpenseBtn = (Button) findViewById(R.id.addExpenseBtn);
		addSareeBtn = (Button) findViewById(R.id.addSareeBtn);
		tableLayout = (TableLayout) findViewById(R.id.accountPerticularsLayout);

		totalRate = (TextView) findViewById(R.id.totalRate);
		totalRate.setText("0.00");

		totalAmount = (TextView) findViewById(R.id.totalAmount);
		totalAmount.setText("0.00");

		totalAdAmount = (TextView) findViewById(R.id.totalAdAmount);
		totalAdAmount.setText("0.00");

		totalAmountForDiff = (TextView) findViewById(R.id.totalAmountForDiff);
		totalAmountForDiff.setText("0.00");

		totalAmountExpenseDiff = (TextView) findViewById(R.id.totalAmountExpenseDiff);
		totalAmountExpenseDiff.setText("0.00");

		totalAdAmountDiff = (TextView) findViewById(R.id.totalAdAmountDiff);
		
		totalAvailableAmnt = (TextView) findViewById(R.id.totalAvailableAmnt);
		totalSareesReceivedTxt = (TextView) findViewById(R.id.totalsareesRecievedTxt);
		totalsareesRemainingTxt= (TextView) findViewById(R.id.totalsareesRemainingTxt);
		Cursor expenseCursor = dbHelper.fetchItemTypeByItemType("\""+AppUtil.ADVANCE_PAYMENT+"\""+","+"\""+AppUtil.RAW_MATERIAL+"\""+","+"\""+AppUtil.CASH_PAYMENT+"\""+","+"\""+AppUtil.COLOR_DYEING+"\""+","+"\""+AppUtil.HANDLOOM_SPARE_PARTS+"\""+","+"\""+AppUtil.JARI_LOADING+"\"");
		if (expenseCursor != null) {
			expenseItemTypeList = new ArrayList();
			try {
				do {
					String name = expenseCursor.getString(expenseCursor
							.getColumnIndex(DbAdapter.KEY_ITEM_TYPE_NAME));
					expenseItemTypeList.add(name);
				} while (expenseCursor.moveToNext());	
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		Cursor sareeCursor = dbHelper.fetchItemTypeByItemType("\""+AppUtil.SAREE+"\""+","+"\""+AppUtil.SAREE_BY_CASH+"\"");
		if (sareeCursor != null) {
			sareeItemTypeList = new ArrayList();
			try {
				do {
					String name = sareeCursor.getString(sareeCursor
							.getColumnIndex(DbAdapter.KEY_ITEM_TYPE_NAME));
					sareeItemTypeList.add(name);
				} while (sareeCursor.moveToNext());				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		
		Cursor cursor = dbHelper.fetchAllItemTypes();
		if (cursor != null) {
			itemTypeMarketPrice = new HashMap();
			itemTypeObj = new HashMap();
			itemTypeUnitMeasure = new HashMap();
			itemTypeRowId = new HashMap();
			try {
				do {
					String id = cursor.getString(cursor
							.getColumnIndex(DbAdapter.KEY_ITEM_TYPE_ROWID));
					
					String name = cursor.getString(cursor
							.getColumnIndex(DbAdapter.KEY_ITEM_TYPE_NAME));
					String itemTypeStr = cursor.getString(cursor
							.getColumnIndex(DbAdapter.KEY_ITEM_TYPE));
					itemTypeObj.put(name, itemTypeStr);
					String marketPrice = cursor.getString(cursor
							.getColumnIndex(DbAdapter.KEY_ITEM_TYPE_MARKET_PRICE));
					itemTypeMarketPrice.put(name, marketPrice);
					String measure = cursor.getString(cursor
							.getColumnIndex(DbAdapter.KEY_ITEM_TYPE_MEASURE));
					itemTypeUnitMeasure.put(name, measure);
					itemTypeRowId.put(name, id);
				} while (cursor.moveToNext());
	
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		displayListView();
		addExpenseBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				LayoutInflater li = LayoutInflater.from(context);
				View expensepopup = li.inflate(R.layout.expensepopup, null);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);
				alertDialogBuilder.setView(expensepopup);
				final DatePicker datePicker = (DatePicker) expensepopup
						.findViewById(R.id.expenseDatePicker);
				final Spinner expenseTypeSpinnerObj = (Spinner) expensepopup
						.findViewById(R.id.expenseTypeSpinner);
				if (expenseItemTypeList == null) {
					expenseItemTypeList = new ArrayList();
				}
				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
						context, android.R.layout.simple_spinner_item,
						expenseItemTypeList);
				dataAdapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				expenseTypeSpinnerObj.setAdapter(dataAdapter);
				expenseTypeSpinnerObj
						.setOnItemSelectedListener(new ExpenTypeOnItemSelectedListener());
				expenseQuantity = (EditText) expensepopup
						.findViewById(R.id.expenseQuantity);
				expenseAmount = (EditText) expensepopup
						.findViewById(R.id.expenseAmountTxt);
				expenseMarketPrice = (TextView)expensepopup
						.findViewById(R.id.expenseMarketPrice);
				expenseMarketPrice.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						int rowId = expenseMarketPrice.getId();
						if(rowId != -1 && rowId >0){
							Cursor cursor = dbHelper.fetchItemTypesById(String.valueOf(rowId));
							try {
								if (cursor != null) {
									final String rowIdStr = cursor.getString(cursor
											.getColumnIndex(DbAdapter.KEY_ITEM_TYPE_ROWID));
									String name = cursor.getString(cursor
											.getColumnIndex(DbAdapter.KEY_ITEM_TYPE_NAME));
									String itemTypeStr = cursor.getString(cursor
											.getColumnIndex(DbAdapter.KEY_ITEM_TYPE));
									String marketPrice = cursor.getString(cursor
											.getColumnIndex(DbAdapter.KEY_ITEM_TYPE_MARKET_PRICE));
									String measure = cursor.getString(cursor
											.getColumnIndex(DbAdapter.KEY_ITEM_TYPE_MEASURE));
									
									LayoutInflater li = LayoutInflater.from(context);
									View itemTypePopUp = li.inflate(R.layout.itemtypepopup, null);
									AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
											context);
									alertDialogBuilder.setView(itemTypePopUp);
									
									final EditText name_changeclick = (EditText)itemTypePopUp.findViewById(R.id.itemTypeNameEdtTxt);
									name_changeclick.setText(name);
									name_changeclick.setEnabled(false);
									marketPrice_changeclick = (EditText)itemTypePopUp.findViewById(R.id.itemTypeMarketPriceEdtTxt);
									marketPrice_changeclick.setText(marketPrice);
									measure_changeclick = (EditText)itemTypePopUp.findViewById(R.id.itemTypeUnitMeasureEdtTxt);
									measure_changeclick.setText(measure);
									measure_changeclick.setEnabled(false);
									
									int selectIndex=0;
									List itemTypeList1 = AppUtil.getEntryTypeList();
									if(itemTypeStr !=null){
										selectIndex=itemTypeList1.indexOf(itemTypeStr);
									}
									
									final Spinner itemTypeSpinnerObj = (Spinner) itemTypePopUp
											.findViewById(R.id.itemTypeSpinner);
									List itemTypeList = AppUtil.getEntryTypeList();
									ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
											context, android.R.layout.simple_spinner_item,
											itemTypeList);
									dataAdapter
											.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
									itemTypeSpinnerObj.setAdapter(dataAdapter);
									itemTypeSpinnerObj.setOnItemSelectedListener(new ItemTypeChangeClickOnItemSelectedListener());
									itemTypeSpinnerObj.setSelection(selectIndex);
									itemTypeSpinnerObj.setEnabled(false);
									alertDialogBuilder
											.setCancelable(false)
											.setPositiveButton("OK",
													new DialogInterface.OnClickListener() {
														public void onClick(DialogInterface dialog,
																int id) {
															dbHelper.updateItemType(rowIdStr,name_changeclick.getText().toString(),selectedItemType ,marketPrice_changeclick.getText().toString(), measure_changeclick.getText().toString());
															itemTypeMarketPrice.put(name_changeclick.getText().toString(), marketPrice_changeclick.getText().toString());
															double marketPrice = Double.valueOf((String)AccountItems.itemTypeMarketPrice.get(AccountItems.selectedExpenseTypeName));
															AccountItems.expenseMarketPrice.setText(String.valueOf(marketPrice));
															selectedExpenseTypeMarketPrice = String.valueOf(marketPrice);
															expenseQuantity.setText("");
														}
													})
											.setNegativeButton("Cancel",
													new DialogInterface.OnClickListener() {
														public void onClick(DialogInterface dialog,
																int id) {
															dialog.cancel();
														}
													});
									AlertDialog alertDialog = alertDialogBuilder.create();
									alertDialog.show();
								}
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					}
				});
				expenseUnitMeasureTxt = (TextView)expensepopup
						.findViewById(R.id.expenseUnitMeasure);
				expenseQuantity.addTextChangedListener(new TextWatcher(){
			        public void afterTextChanged(Editable s) {
			        	if(selectedExpenseTypeName!= null && !selectedExpenseTypeName.equalsIgnoreCase("")){
			        		String marketPriceStr = (String)AccountItems.itemTypeMarketPrice.get(AccountItems.selectedExpenseTypeName);
			        		if(marketPriceStr != null && !marketPriceStr.equals("")){
			        		double marketPrice = Double.valueOf(marketPriceStr);
				        		if(expenseQuantity.getText().toString()!= null 
										&& !expenseQuantity.getText().toString().trim().equalsIgnoreCase("")){
									double quantity = Double.valueOf(expenseQuantity.getText().toString());
									double totalAmount = marketPrice*quantity;
									expenseAmount.setText(AppUtil.formatDouble(totalAmount));
								}else{
									expenseAmount.setText("");
								}
			        		}
			        	}
			        }
			        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
			        public void onTextChanged(CharSequence s, int start, int before, int count){}
			    }); 
				

				alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										createExpenseRecord(
												datePicker.getDayOfMonth()
														+ "/"
														+ (datePicker
																.getMonth() + 1)
														+ "/"
														+ datePicker.getYear(),
												expenseQuantity.getText()
														.toString(),expenseUnitMeasureTxt.getText().toString(),selectedExpenseTypeMarketPrice,
														selectedExpenseTypeName, expenseAmount
														.getText().toString());
									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();

			}

		});

		addSareeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(sareesCount==AccountItemListActivity.numOfSareesLimit){
					Toast.makeText(context, "Sarees Limit "+AccountItemListActivity.numOfSareesLimit+" Reached", Toast.LENGTH_LONG).show();
				}else{
				LayoutInflater li = LayoutInflater.from(context);
				View sareeitempopup = li.inflate(R.layout.sareeitempopup, null);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);
				alertDialogBuilder.setView(sareeitempopup);
				final DatePicker datePicker = (DatePicker) sareeitempopup
						.findViewById(R.id.sareeDatePicker);

				final Spinner sareeTypeSpinnerObj = (Spinner) sareeitempopup
						.findViewById(R.id.sareeTypeSpinner);
				if (sareeItemTypeList == null) {
					sareeItemTypeList = new ArrayList();
				}
				final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
						context, android.R.layout.simple_spinner_item,
						sareeItemTypeList);
				dataAdapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				sareeTypeSpinnerObj.setAdapter(dataAdapter);
				sareeTypeSpinnerObj
						.setOnItemSelectedListener(new SareeTypeOnItemSelectedListener());
				particularQuantity = (EditText) sareeitempopup
						.findViewById(R.id.itemQuantity);
				particularAmount = (EditText) sareeitempopup
						.findViewById(R.id.sareeAmountTxt);
				sareeMarketPrice = (TextView)sareeitempopup
						.findViewById(R.id.marketPrice);
				sareeMarketPrice.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						int rowId = sareeMarketPrice.getId();
						if(rowId != -1){
							Cursor cursor = dbHelper.fetchItemTypesById(String.valueOf(rowId));
							if (cursor != null) {
								final String rowIdStr = cursor.getString(cursor
										.getColumnIndex(DbAdapter.KEY_ITEM_TYPE_ROWID));
								String name = cursor.getString(cursor
										.getColumnIndex(DbAdapter.KEY_ITEM_TYPE_NAME));
								String itemTypeStr = cursor.getString(cursor
										.getColumnIndex(DbAdapter.KEY_ITEM_TYPE));
								String marketPrice = cursor.getString(cursor
										.getColumnIndex(DbAdapter.KEY_ITEM_TYPE_MARKET_PRICE));
								String measure = cursor.getString(cursor
										.getColumnIndex(DbAdapter.KEY_ITEM_TYPE_MEASURE));
								
								LayoutInflater li = LayoutInflater.from(context);
								View itemTypePopUp = li.inflate(R.layout.itemtypepopup, null);
								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
										context);
								alertDialogBuilder.setView(itemTypePopUp);
								
								final EditText name_changeclick = (EditText)itemTypePopUp.findViewById(R.id.itemTypeNameEdtTxt);
								name_changeclick.setText(name);
								name_changeclick.setEnabled(false);
								marketPrice_changeclick = (EditText)itemTypePopUp.findViewById(R.id.itemTypeMarketPriceEdtTxt);
								marketPrice_changeclick.setText(marketPrice);
								measure_changeclick = (EditText)itemTypePopUp.findViewById(R.id.itemTypeUnitMeasureEdtTxt);
								measure_changeclick.setText(measure);
								measure_changeclick.setEnabled(false);
								
								int selectIndex=0;
								List itemTypeList1 = AppUtil.getEntryTypeList();
								if(itemTypeStr !=null){
									selectIndex=itemTypeList1.indexOf(itemTypeStr);
								}
								
								final Spinner itemTypeSpinnerObj = (Spinner) itemTypePopUp
										.findViewById(R.id.itemTypeSpinner);
								List itemTypeList = AppUtil.getEntryTypeList();
								ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
										context, android.R.layout.simple_spinner_item,
										itemTypeList);
								dataAdapter
										.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
								itemTypeSpinnerObj.setAdapter(dataAdapter);
								itemTypeSpinnerObj.setOnItemSelectedListener(new ItemTypeChangeClickOnItemSelectedListener());
								itemTypeSpinnerObj.setSelection(selectIndex);
								itemTypeSpinnerObj.setEnabled(false);
								alertDialogBuilder
										.setCancelable(false)
										.setPositiveButton("OK",
												new DialogInterface.OnClickListener() {
													public void onClick(DialogInterface dialog,
															int id) {
														dbHelper.updateItemType(rowIdStr,name_changeclick.getText().toString(),selectedItemType ,marketPrice_changeclick.getText().toString(), measure_changeclick.getText().toString());
														itemTypeMarketPrice.put(name_changeclick.getText().toString(), marketPrice_changeclick.getText().toString());
														double marketPrice = Double.valueOf((String)AccountItems.itemTypeMarketPrice.get(AccountItems.itemTypeName));
														AccountItems.sareeMarketPrice.setText(String.valueOf(marketPrice));
														itemTypeMarketPriceStr = String.valueOf(marketPrice);
														particularQuantity.setText("");
													}
												})
										.setNegativeButton("Cancel",
												new DialogInterface.OnClickListener() {
													public void onClick(DialogInterface dialog,
															int id) {
														dialog.cancel();
													}
												});
								AlertDialog alertDialog = alertDialogBuilder.create();
								alertDialog.show();
						
							}
						}
					}
				});
				sareeUnitMeasureTxt = (TextView)sareeitempopup
						.findViewById(R.id.sareeUnitMeasure);
				particularQuantity.addTextChangedListener(new TextWatcher(){
			        public void afterTextChanged(Editable s) {
			        	if(itemTypeName!= null && !itemTypeName.equalsIgnoreCase("")){
			        		double marketPrice = Double.valueOf((String)AccountItems.itemTypeMarketPrice.get(AccountItems.itemTypeName));
			        		if(particularQuantity.getText().toString()!= null 
									&& !particularQuantity.getText().toString().trim().equalsIgnoreCase("")){
								double quantity = Double.valueOf(particularQuantity.getText().toString());
								double totalAmount = marketPrice*quantity;
								particularAmount.setText(AppUtil.formatDouble(totalAmount));
							}else{
								particularAmount.setText("");
							}
			        	}
			        }
			        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
			        public void onTextChanged(CharSequence s, int start, int before, int count){}
			    });
				

				alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										String itemType = (String)AccountItems.itemTypeObj.get(AccountItems.itemTypeName);
										System.out.println("-----------------------------------------------*********************"+itemType);
										if(itemType != null && !itemType.equals(AppUtil.SAREE_BY_CASH) && (particularQuantity.getText().toString() == null || particularQuantity.getText().toString().equals(""))){
											Toast.makeText(context, "Enter Quantity", Toast.LENGTH_LONG).show();
										}else{
											int tempSareesCount=0;
											if(particularQuantity.getText().toString() != null && !particularQuantity.getText().toString().equals("")){
												int quantity = Integer.parseInt(particularQuantity.getText().toString());
												tempSareesCount = sareesCount+quantity;
											}
											String perticularQuantity = particularQuantity.getText().toString();
											if(perticularQuantity==null || perticularQuantity==""){
												perticularQuantity="0";
											}
											if(tempSareesCount>AccountItemListActivity.numOfSareesLimit){
													Toast.makeText(context, "You can add only "+(AccountItemListActivity.numOfSareesLimit-sareesCount)+" Saree(s)", Toast.LENGTH_LONG).show();	
											}else{
												CreateParticularItem(
														datePicker.getDayOfMonth()
																+ "/"
																+ (datePicker
																		.getMonth() + 1)
																+ "/"
																+ datePicker.getYear(),
																perticularQuantity,sareeUnitMeasureTxt.getText().toString(),
														itemTypeName, itemTypeMarketPriceStr,particularAmount.getText()
																.toString());
												}
										}
									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();

			}
		}
		});
	}

	private void displayListView() {
		sareesCount = dbHelper.fetchTotalSareesCountByAccountEntryId(AccountItemListActivity.selectedAccountEntryId);
		totalSareesReceivedTxt.setText(sareesCount+"");
		totalsareesRemainingTxt.setText(String.valueOf(AccountItemListActivity.numOfSareesLimit-sareesCount));
		tableLayout.removeAllViews();
		double totalAvailableAdAmount = dbHelper
				.fetchAvailableAdAmountForNextEntryByAccountEntryId(AccountItemListActivity.selectedAccountEntryId,AccountList.selectedAccountId);
		if(totalAvailableAdAmount != -1){
			final TableRow tr = new TableRow(this);
			tr.setLayoutParams(new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			tr.setGravity(Gravity.CENTER_HORIZONTAL);
			TextView textview = new TextView(this);
			TableRow.LayoutParams params = new TableRow.LayoutParams(
					TableRow.LayoutParams.MATCH_PARENT,
					TableRow.LayoutParams.WRAP_CONTENT);
			params.span = 3;
			textview.setLayoutParams(params);
			Date date = new Date();
			tr.addView(textview);

			textview = new TextView(this);
			params = new TableRow.LayoutParams(
					TableRow.LayoutParams.MATCH_PARENT,
					TableRow.LayoutParams.WRAP_CONTENT);
			params.span = 3;
			textview.setLayoutParams(params);
			textview.setText("Opening Advance Amount");
			textview.setWidth(115);
			tr.addView(textview);

			textview = new TextView(this);
			textview.setText("");
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
			params.span = 2;
			textview.setLayoutParams(params);
			textview.setText("");
			textview.setGravity(Gravity.RIGHT);
			tr.addView(textview);

			textview = new TextView(this);
			params = new TableRow.LayoutParams(
					TableRow.LayoutParams.MATCH_PARENT,
					TableRow.LayoutParams.WRAP_CONTENT);
			params.span = 2;
			textview.setLayoutParams(params);
			if(totalAvailableAdAmount<0){
				textview.setText(AppUtil.formatDouble(totalAvailableAdAmount));
			}else{
				textview.setText("");
			}
			textview.setGravity(Gravity.RIGHT);
			tr.addView(textview);
			
			textview = new TextView(this);
			params = new TableRow.LayoutParams(
					TableRow.LayoutParams.MATCH_PARENT,
					TableRow.LayoutParams.WRAP_CONTENT);
			params.span = 2;
			textview.setLayoutParams(params);
			textview.setText("");
			textview.setGravity(Gravity.RIGHT);
			tr.addView(textview);

			textview = new TextView(this);
			params = new TableRow.LayoutParams(
					TableRow.LayoutParams.MATCH_PARENT,
					TableRow.LayoutParams.WRAP_CONTENT);
			params.span = 3;
			textview.setLayoutParams(params);
			if(totalAvailableAdAmount>0){
				textview.setText(AppUtil.formatDouble(totalAvailableAdAmount));
			}else{
				textview.setText("");
			}
			textview.setGravity(Gravity.RIGHT);
			tr.addView(textview);
			tr.setOnLongClickListener(tablerowOnLongClickListener);
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
				.fetchAccountEntryParticularsByAccountEntryId(AccountItemListActivity.selectedAccountEntryId);
		if (perticularDeailsList != null) {
			if (perticularDeailsList.size()>0) {
				try {
					for(int i=0;i<perticularDeailsList.size();i++){
						PerticularDetail perticularDetail = (PerticularDetail)perticularDeailsList.get(i);
						String entryParticularsId = perticularDetail.getEntryParticularsId();
						
						String createdDate = perticularDetail.getCreatedDate();
						if(createdDate != null){
							createdDate = AppUtil.changeDateFormat(createdDate);
						}
						String quantity = perticularDetail.getQuantity();
						String particularName =perticularDetail.getParticularName();
						String rate = perticularDetail.getRate();
						String calculatedAmount = perticularDetail.getCalculatedAmount();
						String amount = perticularDetail.getAmount();
						String advanceAmount = perticularDetail.getAdvanceAmount();
						
						final TableRow tr = new TableRow(this);
						tr.setId(Integer.parseInt(entryParticularsId));
						tr.setLayoutParams(new LayoutParams(
								LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
						tr.setGravity(Gravity.CENTER_HORIZONTAL);
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
						textview.setText(particularName);
						textview.setWidth(115);
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
						params.span = 2;
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
						tr.setOnLongClickListener(tablerowOnLongClickListener);
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

				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		displayTotalRateAmountAdAmount();
	}

	private OnLongClickListener tablerowOnLongClickListener = new OnLongClickListener() {
		
		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			 //GET TEXT HERE
        	final TableRow tableRow = ((TableRow)v);
        	tableRow.setBackgroundColor(Color.RED);
        	if(tableRow.getId() > 0){
    		final int accountParticularEntryId =tableRow.getId(); 
        	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					context);
        	alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton("Delete",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int id) {
							 dbHelper.deleteAccountParticularEntry(String.valueOf(accountParticularEntryId));
							tableLayout.removeView(tableRow);
							displayListView();
						}
					}).setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int id) {
							tableRow.setBackgroundColor(Color.BLACK);
							dialog.cancel();
						}
					});
		 			AlertDialog alertDialog = alertDialogBuilder.create();
		 			alertDialog.show();
        	}
			return false;
		}
    };  
    
	private void displayTotalRateAmountAdAmount() {
		totalAvailableAmnt.setText("");
		totalAdAmountDiff.setText("");
		Cursor cursor = dbHelper
				.fetchTotalRateAmountAdAmountByAccountEntryId(AccountItemListActivity.selectedAccountEntryId);
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
					double totalAvailableAdAmount = dbHelper
							.fetchAvailableAdAmountForNextEntryByAccountEntryId(AccountItemListActivity.selectedAccountEntryId,AccountList.selectedAccountId);
					double totalADAmount = cursor.getDouble(2);
					if(totalAvailableAdAmount != -1){
						totalADAmount = totalAvailableAdAmount+totalADAmount;
					}
					String totalAdAmountStr =null;
					if(totalADAmount!=0.0){
						totalAdAmountStr = AppUtil.formatDouble(totalADAmount);
					}else{
						totalAdAmountStr = String.valueOf(totalADAmount);
					}
					

					System.out.println(totalRate + "--------" + totalAmount
							+ "------------" + totalAdAmount);
					totalRate.setText(totalRateStr);
					totalRate.setTextColor(Color.BLUE);
					totalAmount.setText(totalAmountStr);
					totalAmount.setTextColor(Color.BLUE);
					totalAdAmount.setText(totalAdAmountStr);
					totalAdAmount.setTextColor(Color.BLUE);
					totalAmountForDiff.setText("(-) "+totalAmountStr);
					totalAmountForDiff.setTextColor(Color.BLUE);
					double diff = totalRateValue - totalAmountValue;
					if(diff==0.0){
						totalAmountExpenseDiff.setText(String.valueOf(diff));
						if(AccountItemListActivity.numOfSareesLimit==sareesCount){
							totalAvailableAmnt.setText("(-) "+String.valueOf(diff));
						}
					}else{
						totalAmountExpenseDiff.setText(AppUtil.formatDouble(diff));
						if(AccountItemListActivity.numOfSareesLimit==sareesCount){
							totalAvailableAmnt.setText("(-) "+AppUtil.formatDouble(diff));
						}
					}
					
					if(diff<0){
						totalAmountExpenseDiff.setTextColor(Color.RED);
						if(AccountItemListActivity.numOfSareesLimit==sareesCount){
							totalAvailableAmnt.setTextColor(Color.RED);
						}
					}else{
						totalAmountExpenseDiff.setTextColor(Color.GREEN);
						if(AccountItemListActivity.numOfSareesLimit==sareesCount){
							totalAvailableAmnt.setTextColor(Color.GREEN);
						}
					}
					if(AccountItemListActivity.numOfSareesLimit==sareesCount){
						double adDiff = totalADAmount-diff;
						if(adDiff==0.0){
							totalAdAmountDiff.setText(String.valueOf(adDiff));
						}else{
							totalAdAmountDiff.setText(AppUtil.formatDouble(adDiff));
						}
						
						if(adDiff<0){
							totalAdAmountDiff.setTextColor(Color.RED);
						}else{
							totalAdAmountDiff.setTextColor(Color.GREEN);
						}	
					}
				} while (cursor.moveToNext());
			}
		}
	}

	private void createExpenseRecord(String date, String quantity,String unitMeasure,String selectedExpenseTypeMarketPrice,String itemTypeName, String amount) {
		String expenseAmount = null;
		String advanceAmount = null;
		String itemType = (String)itemTypeObj.get(itemTypeName);
		if (!itemType.equalsIgnoreCase(AppUtil.ADVANCE_PAYMENT)) {
			expenseAmount = amount;
		} 
		if (itemType.equalsIgnoreCase(AppUtil.ADVANCE_PAYMENT)) {
			advanceAmount = amount;
		} 
		dbHelper.createAccountEntryParticular(
				AccountItemListActivity.selectedAccountEntryId, date,
				selectedExpenseTypeName, quantity,unitMeasure,selectedExpenseTypeMarketPrice, null, expenseAmount, advanceAmount,itemType);
		displayListView();
	}

	private void CreateParticularItem(String date, String quantity,String unitMeasure,
			String itemName, String itemTypeMarketPriceStr,String amount) {
		String itemType = (String)itemTypeObj.get(itemName);
		dbHelper.createAccountEntryParticular(
				AccountItemListActivity.selectedAccountEntryId, date, itemTypeName,
				quantity,unitMeasure, itemTypeMarketPriceStr,amount,null, null,itemType);
		displayListView();
	}
}

class ExpenTypeOnItemSelectedListener implements OnItemSelectedListener {
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		AccountItems.selectedExpenseTypeName = parent.getItemAtPosition(pos).toString();
		if(AccountItems.itemTypeMarketPrice != null){
			String itemType = (String)AccountItems.itemTypeObj.get(AccountItems.selectedExpenseTypeName);
			double marketPrice=0.00;
			if(itemType!= null && itemType.equalsIgnoreCase(AppUtil.ADVANCE_PAYMENT)
					|| itemType.equalsIgnoreCase(AppUtil.CASH_PAYMENT)
					|| itemType.equalsIgnoreCase(AppUtil.COLOR_DYEING)
					|| itemType.equalsIgnoreCase(AppUtil.HANDLOOM_SPARE_PARTS)
					|| itemType.equalsIgnoreCase(AppUtil.JARI_LOADING)){
				AccountItems.expenseQuantity.setEnabled(false);
				AccountItems.expenseAmount.setEnabled(true);
				AccountItems.expenseQuantity.setText("");
				AccountItems.expenseUnitMeasureTxt.setText("");
				AccountItems.expenseMarketPrice.setText("");
				AccountItems.expenseMarketPrice.setEnabled(false);
				AccountItems.selectedExpenseTypeMarketPrice = "";
				AccountItems.expenseAmount.setText("");
			}else{
				AccountItems.expenseQuantity.setEnabled(true);
				if(AccountItems.itemTypeMarketPrice.get(AccountItems.selectedExpenseTypeName) != null 
						&&  !AccountItems.itemTypeMarketPrice.get(AccountItems.selectedExpenseTypeName).equals("")){
					marketPrice = Double.valueOf((String)AccountItems.itemTypeMarketPrice.get(AccountItems.selectedExpenseTypeName));
					AccountItems.expenseAmount.setEnabled(false);
				}else{
					AccountItems.expenseAmount.setEnabled(true);
				}
				
				String rowId = (String)AccountItems.itemTypeRowId.get(AccountItems.selectedExpenseTypeName);
				AccountItems.selectedExpenseTypeMarketPrice = String.valueOf(marketPrice);
				String unitMeasure = (String)AccountItems.itemTypeUnitMeasure.get(AccountItems.selectedExpenseTypeName);
				AccountItems.expenseUnitMeasureTxt.setText(unitMeasure);
				 SpannableString spanString = new SpannableString(String.valueOf(marketPrice));
				 spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
				AccountItems.expenseMarketPrice.setText(spanString);
				AccountItems.expenseMarketPrice.setEnabled(true);
				if(rowId != null && rowId != null){
					AccountItems.expenseMarketPrice.setId(Integer.valueOf(rowId));
				}else{
					AccountItems.expenseMarketPrice.setId(-1);
				}
				if(AccountItems.expenseQuantity.getText().toString()!= null 
						&& !AccountItems.expenseQuantity.getText().toString().trim().equalsIgnoreCase("")){
					double quantity = Double.valueOf(AccountItems.expenseQuantity.getText().toString());
					double totalAmount = marketPrice*quantity;
					AccountItems.expenseAmount.setText(AppUtil.formatDouble(totalAmount));
				}
			}
			
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		System.out.println("nothing seleted...........................");
	}

}

class SareeTypeOnItemSelectedListener implements OnItemSelectedListener {

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		AccountItems.itemTypeName = parent.getItemAtPosition(pos).toString();
		
		System.out.println("----------------------------------------------------------------------------"+AccountItems.itemTypeObj.get(AccountItems.itemTypeName));
		String itemType = (String)AccountItems.itemTypeObj.get(AccountItems.itemTypeName);
		if(itemType != null && itemType.equals(AppUtil.SAREE_BY_CASH)){
			AccountItems.particularQuantity.setEnabled(false);
			AccountItems.particularAmount.setEnabled(true);
			SpannableString spanString = new SpannableString("");
			 spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
			 AccountItems.sareeMarketPrice.setText(spanString);
			 AccountItems.sareeMarketPrice.setId(-1);
			 AccountItems.sareeUnitMeasureTxt.setText("");
			 AccountItems.itemTypeMarketPriceStr="";
		}else{
			if(AccountItems.itemTypeMarketPrice != null){
				String rowId = (String)AccountItems.itemTypeRowId.get(AccountItems.itemTypeName);
				if(rowId != null && rowId != ""){
					AccountItems.sareeMarketPrice.setId(Integer.valueOf(rowId));
				}else{
					AccountItems.sareeMarketPrice.setId(-1);
				}
				double marketPrice=0.00;
				if(AccountItems.itemTypeMarketPrice.get(AccountItems.itemTypeName) != null && !AccountItems.itemTypeMarketPrice.get(AccountItems.itemTypeName).equals("")){
					marketPrice = Double.valueOf((String)AccountItems.itemTypeMarketPrice.get(AccountItems.itemTypeName));
					AccountItems.particularAmount.setEnabled(false);
				}else{
					AccountItems.particularAmount.setEnabled(true);
				}
				AccountItems.itemTypeMarketPriceStr = String.valueOf(marketPrice);
				 SpannableString spanString = new SpannableString(String.valueOf(marketPrice));
				 spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
				 AccountItems.sareeMarketPrice.setText(spanString);
				String unitMeasure = (String)AccountItems.itemTypeUnitMeasure.get(AccountItems.itemTypeName);
				AccountItems.sareeUnitMeasureTxt.setText(unitMeasure);
				if(AccountItems.particularQuantity.getText().toString()!= null 
						&& !AccountItems.particularQuantity.getText().toString().trim().equalsIgnoreCase("")){
					double quantity = Double.valueOf(AccountItems.particularQuantity.getText().toString());
					double totalAmount = marketPrice*quantity;
					AccountItems.particularAmount.setText(AppUtil.formatDouble(totalAmount));
				}
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}
}

class ItemTypeChangeClickOnItemSelectedListener implements OnItemSelectedListener {
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		AccountItems.selectedItemType = parent.getItemAtPosition(pos).toString();
		if(AccountItems.selectedItemType.equalsIgnoreCase(AppUtil.ADVANCE_PAYMENT)
				|| AccountItems.selectedItemType.equalsIgnoreCase(AppUtil.CASH_PAYMENT) 
				|| AccountItems.selectedItemType.equalsIgnoreCase(AppUtil.COLOR_DYEING)
				|| AccountItems.selectedItemType.equalsIgnoreCase(AppUtil.HANDLOOM_SPARE_PARTS)
				|| AccountItems.selectedItemType.equalsIgnoreCase(AppUtil.JARI_LOADING)){
			AccountItems.marketPrice_changeclick.setText("");
			AccountItems.marketPrice_changeclick.setEnabled(false);
			AccountItems.measure_changeclick.setText("");
			AccountItems.measure_changeclick.setEnabled(false);
		}else{
			AccountItems.measure_changeclick.setEnabled(true);
			AccountItems.measure_changeclick.setEnabled(true);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		System.out.println("nothing seleted...........................");
	}

}