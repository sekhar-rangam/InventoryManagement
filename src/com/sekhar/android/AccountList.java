package com.sekhar.android;
  
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.mkyong.android.R;
  
@SuppressLint("NewApi")
public class AccountList extends Activity {
	final Context context = this;
	 private DbAdapter dbHelper;
	 private SimpleCursorAdapter dataAdapter; 
	 private Button addAccountBtn = null;
	 private Button backupDataBtn = null;
	 private ImageButton accountReportView =null;
 public static String selectedAccountId=null;
 public static String selectedAccountFirstName=null;
 public static String selectedAccountLastName=null;
 public static String selectedAccountPhone=null;
 public static String selectedAccountAddress=null;
 @Override
 public void onCreate(Bundle savedInstanceState) { 
  super.onCreate(savedInstanceState); 
  setContentView(R.layout.accountlist); 
  addAccountBtn = (Button) findViewById(R.id.addAccount);
  accountReportView =(ImageButton)findViewById(R.id.accountReportView);
  dbHelper = new DbAdapter(this); 
  dbHelper.open(); 
  
  accountReportView.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			 Intent intent = new Intent(context, AccountReportView.class);
			 startActivity(intent);
		}
	});
  
  displayListView(); 
  
  addAccountBtn.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			LayoutInflater li = LayoutInflater.from(context);
			View accountpopup = li.inflate(R.layout.accountpopup, null);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					context);
			alertDialogBuilder.setView(accountpopup);

			final EditText firstName = (EditText)accountpopup.findViewById(R.id.firstName);
			final EditText lastName = (EditText)accountpopup.findViewById(R.id.lastName);
			final EditText phone = (EditText)accountpopup.findViewById(R.id.phone);
			final EditText address = (EditText)accountpopup.findViewById(R.id.address);
			
			
			alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dbHelper.insertAccount(firstName.getText().toString(),lastName.getText().toString(),phone.getText().toString(),address.getText().toString());
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
}

 private void displayListView() { 
  
  
  Cursor cursor = dbHelper.fetchAllAccounts(); 
  
  // The desired columns to be bound 
  String[] columns = new String[] { 
    DbAdapter.KEY_ACCOUNT_FIRST_NAME, 
    DbAdapter.KEY_ACCOUNT_LAST_NAME, 
    DbAdapter.KEY_ACCOUNT_PHONE, 
    DbAdapter.KEY_ACCOUNT_ADDRESS,
    DbAdapter.KEY_ACCOUNT_ROWID,
  }; 
  
  // the XML defined views which the data will be bound to 
  int[] to = new int[] {  
    R.id.firstname, 
    R.id.lastname, 
    R.id.phone, 
    R.id.address, 
    R.id._id
  }; 
  
  // create the adapter using the cursor pointing to the desired data  
  //as well as the layout information 
  dataAdapter = new SimpleCursorAdapter( 
    this, R.layout.layoutaccountinfo,  
    cursor,  
    columns,  
    to, 
    0); 
  
  final ListView listView = (ListView) findViewById(R.id.listView1); 
  // Assign adapter to ListView 
  listView.setAdapter(dataAdapter); 
  
  listView.setOnItemLongClickListener(new OnItemLongClickListener() {

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		final Cursor cursor = (Cursor) listView.getItemAtPosition(arg2);
		listView.setSelection(arg2);
		final String accountId =  
		    cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_ACCOUNT_ROWID));
		 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					context);
		 alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton("Delete",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int id) {
							 dbHelper.deleteAccount(accountId);
							 displayListView();
						}
					})
			.setNeutralButton("Edit",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int id) {
							LayoutInflater li = LayoutInflater.from(context);
							View accountpopup = li.inflate(R.layout.accountpopup, null);
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
									context);
							alertDialogBuilder.setView(accountpopup);
							
							String firstNameStr = cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_ACCOUNT_FIRST_NAME));
							String lastnNameStr = cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_ACCOUNT_LAST_NAME));
							String phoneStr = cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_ACCOUNT_PHONE));
							String addressStr = cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_ACCOUNT_ADDRESS));
							

							final EditText firstName = (EditText)accountpopup.findViewById(R.id.firstName);
							firstName.setText(firstNameStr);
							final EditText lastName = (EditText)accountpopup.findViewById(R.id.lastName);
							lastName.setText(lastnNameStr);
							final EditText phone = (EditText)accountpopup.findViewById(R.id.phone);
							phone.setText(phoneStr);
							final EditText address = (EditText)accountpopup.findViewById(R.id.address);
							address.setText(addressStr);
							
							
							alertDialogBuilder
									.setCancelable(false)
									.setPositiveButton("OK",
											new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog,
														int id) {
													dbHelper.updateAccount(accountId,firstName.getText().toString(),lastName.getText().toString(),phone.getText().toString(),address.getText().toString());
													displayListView();
												}
											})
									.setNegativeButton("Cancel",
											new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog,
														int id) {
													listView.setSelected(false);
													dialog.cancel();
												}
											});
							AlertDialog alertDialog = alertDialogBuilder.create();
							alertDialog.show();
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
   String accountId =  
    cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_ACCOUNT_ROWID));
   selectedAccountFirstName = cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_ACCOUNT_FIRST_NAME));
   selectedAccountLastName = cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_ACCOUNT_LAST_NAME));
   selectedAccountPhone = cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_ACCOUNT_PHONE));
   selectedAccountAddress = cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_ACCOUNT_ADDRESS));
   selectedAccountId = accountId;
   
   Intent intent = new Intent(context, AccountItemListActivity.class);
    
   startActivity(intent);
  
   } 
  }); 
  
  EditText myFilter = (EditText) findViewById(R.id.myFilter); 
  myFilter.addTextChangedListener(new TextWatcher() { 
  
   public void afterTextChanged(Editable s) { 
   } 
  
   public void beforeTextChanged(CharSequence s, int start,  
     int count, int after) { 
   } 
  
   public void onTextChanged(CharSequence s, int start,  
     int before, int count) { 
    dataAdapter.getFilter().filter(s.toString()); 
   } 
  }); 
    
  dataAdapter.setFilterQueryProvider(new FilterQueryProvider() { 
         public Cursor runQuery(CharSequence constraint) { 
             return dbHelper.fetchAccountsByFirstName(constraint.toString()); 
         } 
     }); 
  
 } 
}