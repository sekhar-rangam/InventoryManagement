package com.sekhar.android;
  
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.mkyong.android.R;
  
@SuppressLint("NewApi")
public class ItemTypeList extends Activity {
	final Context context = this;
	 private DbAdapter dbHelper;
	 private SimpleCursorAdapter dataAdapter; 
	 private Button addItemTypeBtn = null;
	 private ImageButton restoreDBButton = null;
	 private ImageButton backUpDBButton = null;
	 public static String selectedItemType=null;
	 public static EditText marketPrice=null;
	 public static  EditText measure=null;
 public static String selectedItemTypeName=null;
 @Override
 public void onCreate(Bundle savedInstanceState) { 
  super.onCreate(savedInstanceState); 
  setContentView(R.layout.itemtype); 
  addItemTypeBtn = (Button) findViewById(R.id.addItemType);
  restoreDBButton = (ImageButton)findViewById(R.id.restoreDBButton);
  backUpDBButton = (ImageButton)findViewById(R.id.backUpDBButton);
  dbHelper = new DbAdapter(this); 
  dbHelper.open(); 
  
  displayListView(); 
  
  addItemTypeBtn.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			LayoutInflater li = LayoutInflater.from(context);
			View itemTypePopUp = li.inflate(R.layout.itemtypepopup, null);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					context);
			alertDialogBuilder.setView(itemTypePopUp);
			
			final EditText name = (EditText)itemTypePopUp.findViewById(R.id.itemTypeNameEdtTxt);
			name.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
					// TODO Auto-generated method stub
				}
				
				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
						int arg3) {
					// TODO Auto-generated method stub
				}
				
				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					if(itemTypeNameList != null && itemTypeNameList.contains(name.getText().toString())){
						name.setTextColor(Color.RED);
						Toast.makeText(getApplicationContext(), 
								name.getText().toString()+" already exists. Enter other name.", Toast.LENGTH_LONG).show();
					}else{
						name.setTextColor(Color.BLACK);
					}
				}
			});
			marketPrice = (EditText)itemTypePopUp.findViewById(R.id.itemTypeMarketPriceEdtTxt);
			measure = (EditText)itemTypePopUp.findViewById(R.id.itemTypeUnitMeasureEdtTxt);
			
			final Spinner itemTypeSpinnerObj = (Spinner) itemTypePopUp
					.findViewById(R.id.itemTypeSpinner);
			List itemTypeList = AppUtil.getEntryTypeList();
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
					context, android.R.layout.simple_spinner_item,
					itemTypeList);
			dataAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			itemTypeSpinnerObj.setAdapter(dataAdapter);
			itemTypeSpinnerObj.setOnItemSelectedListener(new ItemTypeOnItemSelectedListener());
			
			
			alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									name.setTextColor(Color.BLACK);
									String selectedType = (String)itemTypeSpinnerObj.getSelectedItem();
									if(itemTypeNameList != null && itemTypeNameList.contains(name.getText().toString())){
										name.setTextColor(Color.RED);
										Toast.makeText(getApplicationContext(), 
												name.getText().toString()+" already exists, enter other name.", Toast.LENGTH_LONG).show();
									}else if((marketPrice.getText().toString().equalsIgnoreCase("")
											|| marketPrice.getText().toString().equalsIgnoreCase("0")) && !selectedType.equalsIgnoreCase(AppUtil.ADVANCE_PAYMENT) 
													&& !selectedType.equalsIgnoreCase(AppUtil.CASH_PAYMENT)
													&& !selectedType.equalsIgnoreCase(AppUtil.SAREE_BY_CASH)
													&& !selectedType.equalsIgnoreCase(AppUtil.COLOR_DYEING)
													&& !selectedType.equalsIgnoreCase(AppUtil.HANDLOOM_SPARE_PARTS)
													&& !selectedType.equalsIgnoreCase(AppUtil.JARI_LOADING)){
										Toast.makeText(getApplicationContext(), 
												"Enter market price.", Toast.LENGTH_LONG).show();
									}else{
										dbHelper.insertItemType(name.getText().toString(),selectedItemType ,marketPrice.getText().toString(), measure.getText().toString());
										displayListView();
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
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
		}
	});
  
  restoreDBButton.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			try {
				LayoutInflater li = LayoutInflater.from(context);
				View dbbackuplistpopup = li.inflate(R.layout.dbbackuplistpopup, null);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);
				alertDialogBuilder.setView(dbbackuplistpopup);
				final ListView dbbackuplist = (ListView) dbbackuplistpopup.findViewById(R.id.dbbackuplist);
				File sd = Environment.getExternalStorageDirectory();
				String rootFolder = sd.getAbsolutePath()+"/AccountManagement";
				final List filesList = FileUtils.getFileList(rootFolder);
		        ListAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, filesList);
				dbbackuplist.setAdapter(adapter);
				dbbackuplist.setOnItemClickListener(new OnItemClickListener() {
					  @Override
					  public void onItemClick(AdapterView<?> parent, View view,
					    int position, long id) {
						  AppUtil.restoreDb(context.getDatabasePath("AccountManagement").getAbsoluteFile(),(String)filesList.get(position));
						  Toast.makeText(getApplicationContext(),
					      "Successfully restored selected data", Toast.LENGTH_LONG)
					      .show();
						  displayListView();
					  }
					}); 
				alertDialogBuilder.show();
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(context, "Error while restoring data", Toast.LENGTH_LONG).show();
			}
		}
	});

  backUpDBButton.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			try {
				AppUtil.backupDb(context.getDatabasePath("AccountManagement").getAbsoluteFile());
				Toast.makeText(context, "Success in back data", Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(context, "Error while back data", Toast.LENGTH_LONG).show();
			}
		}
	});
 } 
 private ArrayList itemTypeNameList =null;
 private void displayListView() { 
	 itemTypeNameList = new ArrayList();
  
  Cursor cursor = dbHelper.fetchAllItemTypes();
  if(cursor !=null){
	  try {
		  do {
				String itemTypeName = cursor
						.getString(cursor
								.getColumnIndex(DbAdapter.KEY_ITEM_TYPE_NAME));
				itemTypeNameList.add(itemTypeName);

			} while (cursor.moveToNext());
	} catch (Exception e) {
		// TODO: handle exception
	}
 }
    // The desired columns to be bound 
  String[] columns = new String[] { 
    DbAdapter.KEY_ITEM_TYPE_NAME, 
    DbAdapter.KEY_ITEM_TYPE_MARKET_PRICE, 
    DbAdapter.KEY_ITEM_TYPE_MEASURE,
  }; 
  
  // the XML defined views which the data will be bound to 
  int[] to = new int[] {  
    R.id.itemTypeNameLayout, 
    R.id.itemTypeMarketPriceLayout, 
    R.id.itemTypeMeasureLayout 
  }; 
  
  // create the adapter using the cursor pointing to the desired data  
  //as well as the layout information 
  dataAdapter = new SimpleCursorAdapter( 
    this, R.layout.layoutitemtypeinfo,  
    cursor,  
    columns,  
    to, 
    0); 
  
 final ListView listView = (ListView) findViewById(R.id.itemTypeListView); 
  // Assign adapter to ListView 
  listView.setAdapter(dataAdapter); 
  listView.setOnItemLongClickListener(new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			final Cursor cursor = (Cursor) listView.getItemAtPosition(arg2);
			listView.setSelection(arg2);
			final String itemTypeId =  
			    cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_ITEM_TYPE_ROWID));
			 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);
			 alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("Delete",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int id) {
								 dbHelper.deleteItemType(itemTypeId);
								 displayListView();
							}
						})
				.setNeutralButton("Edit",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int id) {
								LayoutInflater li = LayoutInflater.from(context);
								View itemTypePopUp = li.inflate(R.layout.itemtypepopup, null);
								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
										context);
								alertDialogBuilder.setView(itemTypePopUp);
								
								String typeNameStr =  
									    cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_ITEM_TYPE_NAME));
								String marketPriceStr =  
									    cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_ITEM_TYPE_MARKET_PRICE));
								String measureStr =  
									    cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_ITEM_TYPE_MEASURE));
								String itemType =  
									    cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_ITEM_TYPE));
								
								final EditText name = (EditText)itemTypePopUp.findViewById(R.id.itemTypeNameEdtTxt);
								name.setText(typeNameStr);
								marketPrice = (EditText)itemTypePopUp.findViewById(R.id.itemTypeMarketPriceEdtTxt);
								marketPrice.setText(marketPriceStr);
								measure = (EditText)itemTypePopUp.findViewById(R.id.itemTypeUnitMeasureEdtTxt);
								measure.setText(measureStr);
								
								final Spinner itemTypeSpinnerObj = (Spinner) itemTypePopUp
										.findViewById(R.id.itemTypeSpinner);
								int selectIndex=0;
								List itemTypeList = AppUtil.getEntryTypeList();
								if(itemType !=null){
									selectIndex=itemTypeList.indexOf(itemType);
								}
								ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
										context, android.R.layout.simple_spinner_item,
										itemTypeList);
								dataAdapter
										.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
								itemTypeSpinnerObj.setAdapter(dataAdapter);
								itemTypeSpinnerObj.setSelection(selectIndex);
								itemTypeSpinnerObj.setOnItemSelectedListener(new ItemTypeOnItemSelectedListener());
								
								
								alertDialogBuilder
										.setCancelable(false)
										.setPositiveButton("OK",
												new DialogInterface.OnClickListener() {
													public void onClick(DialogInterface dialog,
															int id) {
														dbHelper.updateItemType(itemTypeId,name.getText().toString(),selectedItemType ,marketPrice.getText().toString(), measure.getText().toString());
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
   String itemTypeName =  
    cursor.getString(cursor.getColumnIndexOrThrow(DbAdapter.KEY_ITEM_TYPE_NAME)); 
   selectedItemTypeName = itemTypeName;
   
  
   } 
  }); 
  
  EditText itemTypeFilter = (EditText) findViewById(R.id.itemTypeFilter); 
  itemTypeFilter.addTextChangedListener(new TextWatcher() { 
  
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
             return dbHelper.fetchItemTypeByName(constraint.toString()); 
         } 
     }); 
  
 } 
}


class ItemTypeOnItemSelectedListener implements OnItemSelectedListener {
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		ItemTypeList.selectedItemType = parent.getItemAtPosition(pos).toString();
		if(ItemTypeList.selectedItemType.equalsIgnoreCase(AppUtil.ADVANCE_PAYMENT)
				|| ItemTypeList.selectedItemType.equalsIgnoreCase(AppUtil.CASH_PAYMENT)
				|| ItemTypeList.selectedItemType.equalsIgnoreCase(AppUtil.SAREE_BY_CASH)
				|| ItemTypeList.selectedItemType.equalsIgnoreCase(AppUtil.COLOR_DYEING)
				|| ItemTypeList.selectedItemType.equalsIgnoreCase(AppUtil.HANDLOOM_SPARE_PARTS)
				|| ItemTypeList.selectedItemType.equalsIgnoreCase(AppUtil.JARI_LOADING)){
			ItemTypeList.marketPrice.setText("");
			ItemTypeList.marketPrice.setEnabled(false);
			ItemTypeList.measure.setText("");
			ItemTypeList.measure.setEnabled(false);
		}else{
			ItemTypeList.marketPrice.setEnabled(true);
			ItemTypeList.measure.setEnabled(true);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		System.out.println("nothing seleted...........................");
	}

}