package com.sekhar.android;
  
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleCursorAdapter;

import com.mkyong.android.R;
  
@SuppressLint("NewApi")
public class AccountItemListActivity extends Activity {
	final Context context = this;
	 private DbAdapter dbHelper; 
	 private SimpleCursorAdapter dataAdapter; 
	 private Button addAccountEntryBtn = null;
	 private ImageButton reportView =null;
	 public static String selectedAccountEntryId=null;
	 public static String selectedAccountEntryName=null;
	 public static int numOfSareesLimit=0;
 @Override
 public void onCreate(Bundle savedInstanceState) { 
  super.onCreate(savedInstanceState); 
  setContentView(R.layout.accountitemslist);
  if(AccountList.selectedAccountFirstName==null){
	  AccountList.selectedAccountFirstName="";
  }
  setTitle("Name: "+AccountList.selectedAccountFirstName.toUpperCase());
  addAccountEntryBtn = (Button) findViewById(R.id.addAccountEntry);
  reportView =(ImageButton)findViewById(R.id.reportView);
  dbHelper = new DbAdapter(this); 
  dbHelper.open(); 
  
  displayListView();
  
  addAccountEntryBtn.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			LayoutInflater li = LayoutInflater.from(context);
			View accountentrypopup = li.inflate(R.layout.accountentrypopup, null);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					context);
			alertDialogBuilder.setView(accountentrypopup);

			final EditText accountEntryName = (EditText)accountentrypopup.findViewById(R.id.accountEntryName);
			final NumberPicker numberPicker = (NumberPicker)accountentrypopup.findViewById(R.id.numberPicker1);
			numberPicker.setMaxValue(25);
			numberPicker.setMinValue(0);
			numberPicker.setValue(16);
			final DatePicker accountDatePicker = (DatePicker)accountentrypopup.findViewById(R.id.accountEntryDatePicker);
			
			
			alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dbHelper.insertAccountEntry(AccountList.selectedAccountId,accountDatePicker.getDayOfMonth()+"/"+(accountDatePicker.getMonth()+1)+"/"+accountDatePicker.getYear(),accountEntryName.getText().toString(),numberPicker.getValue());
									displayListView();
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
	});
  
  reportView.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			 Intent intent = new Intent(context, ReportAccountItems.class);
			 startActivity(intent);
		}
	});
  }
 
 private void displayListView() { 
	  
	  
	  Cursor cursor = dbHelper.fetchAccountEntrysByAccountId(AccountList.selectedAccountId); 
	  if(cursor != null){
	  // The desired columns to be bound 
	  String[] columns = new String[] {
	    DbAdapter.KEY_ACCOUNT_ENTRY_NAME, 
	    DbAdapter.KEY_ACCOUNT_ENTRY_DATE,
	    DbAdapter.KEY_ACCOUNT_ENTRY_ROWID
	  }; 
	  
	  // the XML defined views which the data will be bound to 
	  int[] to = new int[] {  
	    R.id.accountEntryName, 
	    R.id.createdDate ,
	    R.id.entryNumber
	  }; 
	  
	  // create the adapter using the cursor pointing to the desired data  
	  //as well as the layout information 
	  dataAdapter = new SimpleCursorAdapter( 
	    this, R.layout.layoutaccountentryinfo,  
	    cursor,  
	    columns,  
	    to, 
	    0); 
	  
	  final ListView listView = (ListView) findViewById(R.id.accountEntryList); 
	  // Assign adapter to ListView 
	  listView.setAdapter(dataAdapter); 
	  
	  listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				final Cursor cursor = (Cursor) listView.getItemAtPosition(arg2);
				listView.setSelection(arg2);
				final String accountEntryId =  
				    cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_ACCOUNT_ENTRY_ROWID));
				   
				 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							context);
				 alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("Delete",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									 dbHelper.deleteAccountEntry(accountEntryId);
									 displayListView();
								}
							})
					.setNeutralButton("Edit",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									LayoutInflater li = LayoutInflater.from(context);
									View accountentrypopup = li.inflate(R.layout.accountentrypopup, null);
									AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
											context);
									alertDialogBuilder.setView(accountentrypopup);

									final String entryName =  
										    cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_ACCOUNT_ENTRY_NAME));
									final String date =  
										    cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_ACCOUNT_ENTRY_DATE));
									final String sareesLimit =  
										    cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_ACCOUNT_ENTRY_SAREES_LIMIT));
									
									final EditText accountEntryName = (EditText)accountentrypopup.findViewById(R.id.accountEntryName);
									accountEntryName.setText(entryName);
									final NumberPicker numberPicker = (NumberPicker)accountentrypopup.findViewById(R.id.numberPicker1);
									numberPicker.setMaxValue(25);
									numberPicker.setMinValue(0);
									numberPicker.setValue(Integer.parseInt(sareesLimit));
									final DatePicker accountDatePicker = (DatePicker)accountentrypopup.findViewById(R.id.accountEntryDatePicker);
									if(date != null){
										String str[] = date.split("/");
										String day = str[0];
										String month = str[1];
										String year = str[2];
										accountDatePicker.updateDate(Integer.parseInt(year), (Integer.parseInt(month)-1),Integer.parseInt(day));
									}									
									alertDialogBuilder
											.setCancelable(false)
											.setPositiveButton("OK",
													new DialogInterface.OnClickListener() {
														public void onClick(DialogInterface dialog,
																int id) {
															dbHelper.updateAccountEntry(accountEntryId,AccountList.selectedAccountId,accountDatePicker.getDayOfMonth()+"/"+(accountDatePicker.getMonth()+1)+"/"+accountDatePicker.getYear(),accountEntryName.getText().toString(),numberPicker.getValue());
															displayListView();
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
									alertDialog.show();								}
							}).setNegativeButton("Cancel",
									new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									listView.setSelected(false);
									dialog.cancel();
								}
							});
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
				 
				return false;
			}
		});

	  
	  listView.setOnItemClickListener(new OnItemClickListener() { 
	   @Override
	   public void onItemClick(AdapterView<?> listView, View view,  
	     int position, long id) { 
	   // Get the cursor, positioned to the corresponding row in the result set 
	   Cursor cursor = (Cursor) listView.getItemAtPosition(position); 
	  
	   // Get the state's capital from this row in the database. 
	   String entryId =  
	    cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_ACCOUNT_ENTRY_ROWID)); 
	   selectedAccountEntryId=entryId;
	   selectedAccountEntryName =cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_ACCOUNT_ENTRY_NAME));
	   numOfSareesLimit = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_ACCOUNT_ENTRY_SAREES_LIMIT)));
	   Intent intent = new Intent(context, AccountItems.class);
	   startActivity(intent);
	   } 
	  }); 
	  }
	 }
}