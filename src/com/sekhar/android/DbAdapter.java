package com.sekhar.android;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbAdapter {
	private DbAdapter dbHelper;

	// Database name AccountManagement
	private static final String DATABASE_NAME = "AccountManagement";

	// Account Table
	private static final String SQLITE_MASTER_ITEM_TYPE_TABLE = "MasterItemType";
	public static final String KEY_ITEM_TYPE_ROWID = "_id";
	public static final String KEY_ITEM_TYPE_NAME = "name";
	public static final String KEY_ITEM_TYPE = "type";
	public static final String KEY_ITEM_TYPE_MARKET_PRICE = "ItemTypeMarketPrice";
	public static final String KEY_ITEM_TYPE_MEASURE = "UnitMeasure";

	// Account Table
	private static final String SQLITE_ACCOUNT_TABLE = "Account";
	public static final String KEY_ACCOUNT_ROWID = "_id";
	public static final String KEY_ACCOUNT_FIRST_NAME = "firstname";
	public static final String KEY_ACCOUNT_LAST_NAME = "lastname";
	public static final String KEY_ACCOUNT_PHONE = "phone";
	public static final String KEY_ACCOUNT_ADDRESS = "address";

	// Account Entry Table
	private static final String SQLITE_ACCOUNT_ENTRY_TABLE = "AccountEntry";
	public static final String KEY_ACCOUNT_ENTRY_ROWID = "_id";
	public static final String KEY_ACCOUNT_ENTRY_ACCOUNT_ID = "account_id";
	public static final String KEY_ACCOUNT_ENTRY_NAME = "accountEntryName";
	public static final String KEY_ACCOUNT_ENTRY_DATE = "createdDate";
	public static final String KEY_ACCOUNT_ENTRY_SAREES_LIMIT = "sareesLimit";

	// Account Entry PARTICULARS Table
	private static final String SQLITE_ACCOUNT_ENTRY_PARTICULARS_TABLE = "AccountEntryParticulars";
	public static final String KEY_ACCOUNT_ENTRY_PARTICULARS_ROWID = "_id";
	public static final String KEY_ACCOUNT_ENTRY_PARTICULARS_ACCOUNT_ENTRY_ID = "accountEntryId";
	public static final String KEY_ACCOUNT_ENTRY_PARTICULARS_DATE = "createdDate";
	public static final String KEY_ACCOUNT_ENTRY_PARTICULARS_QUANTITY = "numItems";
	public static final String KEY_ACCOUNT_ENTRY_PARTICULARS_UNIT_MEASURE = "unitMeasure";
	public static final String KEY_ACCOUNT_ENTRY_PARTICULARS_NAME = "accountEntryParticularName";
	public static final String KEY_ACCOUNT_ENTRY_PARTICULARS_RATE = "accountEntryParticularRate";
	public static final String KEY_ACCOUNT_ENTRY_PARTICULARS_CALCULATED_AMOUNT = "accountEntryCalculatedAmount";
	public static final String KEY_ACCOUNT_ENTRY_PARTICULARS_EXPENSE_AMOUNT = "accountEntryParticularAmount";
	public static final String KEY_ACCOUNT_ENTRY_PARTICULARS_ADVANCE_AMOUNT = "accountEntryParticularAdvanceAmount";
	public static final String KEY_ACCOUNT_ENTRY_PARTICULARS_TYPE = "accountEntryParticularAdvanceType";

	// Create Master Item Type
	private static final String DATABASE_MASTER_ITEM_TYPE_CREATE = "CREATE TABLE if not exists "
			+ SQLITE_MASTER_ITEM_TYPE_TABLE
			+ " ("
			+ KEY_ITEM_TYPE_ROWID
			+ " integer PRIMARY KEY autoincrement,"
			+ KEY_ITEM_TYPE_NAME
			+ ","
			+ KEY_ITEM_TYPE
			+ ","
			+ KEY_ITEM_TYPE_MARKET_PRICE
			+ ","
			+ KEY_ITEM_TYPE_MEASURE + ");";

	// Create table query for account
	private static final String DATABASE_ACCOUNT_CREATE = "CREATE TABLE if not exists "
			+ SQLITE_ACCOUNT_TABLE
			+ " ("
			+ KEY_ACCOUNT_ROWID
			+ " integer PRIMARY KEY autoincrement,"
			+ KEY_ACCOUNT_FIRST_NAME
			+ ","
			+ KEY_ACCOUNT_LAST_NAME
			+ ","
			+ KEY_ACCOUNT_PHONE
			+ ","
			+ KEY_ACCOUNT_ADDRESS + ");";

	// Create table query for account
	private static final String DATABASE_ACCOUNT_ENTRY_CREATE = "CREATE TABLE if not exists "
			+ SQLITE_ACCOUNT_ENTRY_TABLE
			+ " ("
			+ KEY_ACCOUNT_ENTRY_ROWID
			+ " integer PRIMARY KEY autoincrement,"
			+ KEY_ACCOUNT_ENTRY_ACCOUNT_ID
			+ ","
			+ KEY_ACCOUNT_ENTRY_NAME
			+ ","
			+ KEY_ACCOUNT_ENTRY_DATE
			+ ","
			+KEY_ACCOUNT_ENTRY_SAREES_LIMIT
			+ ","
			+ " FOREIGN KEY("
			+ KEY_ACCOUNT_ENTRY_ACCOUNT_ID
			+ ") REFERENCES "
			+ SQLITE_ACCOUNT_TABLE
			+ "("
			+ KEY_ACCOUNT_ROWID
			+ ") ON DELETE CASCADE);";

	// Create table query for account
	private static final String DATABASE_ACCOUNT_ENTRY_PARTICULAR_CREATE = "CREATE TABLE if not exists "
			+ SQLITE_ACCOUNT_ENTRY_PARTICULARS_TABLE
			+ " ("
			+ KEY_ACCOUNT_ENTRY_PARTICULARS_ROWID
			+ " integer PRIMARY KEY autoincrement,"
			+ KEY_ACCOUNT_ENTRY_PARTICULARS_ACCOUNT_ENTRY_ID
			+ ","
			+ KEY_ACCOUNT_ENTRY_PARTICULARS_DATE 
			+ " DATE ,"
			+ KEY_ACCOUNT_ENTRY_PARTICULARS_QUANTITY
			+ ","
			+ KEY_ACCOUNT_ENTRY_PARTICULARS_UNIT_MEASURE
			+ ","
			+ KEY_ACCOUNT_ENTRY_PARTICULARS_NAME
			+ ","
			+ KEY_ACCOUNT_ENTRY_PARTICULARS_RATE
			+ ","
			+ KEY_ACCOUNT_ENTRY_PARTICULARS_CALCULATED_AMOUNT
			+ ","
			+ KEY_ACCOUNT_ENTRY_PARTICULARS_EXPENSE_AMOUNT
			+ ","
			+KEY_ACCOUNT_ENTRY_PARTICULARS_TYPE
			+ ","
			+ KEY_ACCOUNT_ENTRY_PARTICULARS_ADVANCE_AMOUNT
			+ ","
			+ " FOREIGN KEY("
			+ KEY_ACCOUNT_ENTRY_PARTICULARS_ACCOUNT_ENTRY_ID
			+ ") REFERENCES "
			+ SQLITE_ACCOUNT_ENTRY_TABLE
			+ "("
			+ KEY_ACCOUNT_ENTRY_ROWID + ") ON DELETE CASCADE);";

	private static final String TAG = "DbAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	private static final int DATABASE_VERSION = 1;
	private final Context mCtx;

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			Log.w(TAG, DATABASE_MASTER_ITEM_TYPE_CREATE);
			db.execSQL(DATABASE_MASTER_ITEM_TYPE_CREATE);

			Log.w(TAG, DATABASE_ACCOUNT_CREATE);
			db.execSQL(DATABASE_ACCOUNT_CREATE);

			Log.w(TAG, DATABASE_ACCOUNT_ENTRY_CREATE);
			db.execSQL(DATABASE_ACCOUNT_ENTRY_CREATE);

			Log.w(TAG, DATABASE_ACCOUNT_ENTRY_PARTICULAR_CREATE);
			db.execSQL(DATABASE_ACCOUNT_ENTRY_PARTICULAR_CREATE);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			// db.execSQL("DROP TABLE IF EXISTS " + SQLITE_ACCOUNT_TABLE);
			onCreate(db);
		}
	}

	public DbAdapter(Context ctx) {
		this.mCtx = ctx;
	}

	public DbAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		
		return this;
	}

	public void close() {
		if (mDbHelper != null) {
			mDbHelper.close();
		}
	}

	public long createItemType(String name, String type, String marketPrice,
			String unitMeasure) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ITEM_TYPE_NAME, name);
		initialValues.put(KEY_ITEM_TYPE, type);
		initialValues.put(KEY_ITEM_TYPE_MARKET_PRICE, marketPrice);
		initialValues.put(KEY_ITEM_TYPE_MEASURE, unitMeasure);
		return mDb.insert(SQLITE_MASTER_ITEM_TYPE_TABLE, null, initialValues);
	}

	public long createAccount(String firstName, String lastName, String phone,
			String address) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ACCOUNT_FIRST_NAME, firstName);
		initialValues.put(KEY_ACCOUNT_LAST_NAME, lastName);
		initialValues.put(KEY_ACCOUNT_PHONE, phone);
		initialValues.put(KEY_ACCOUNT_ADDRESS, address);
		return mDb.insert(SQLITE_ACCOUNT_TABLE, null, initialValues);
	}

	public long createAccountEntry(String accountId, String cratedDate,
			String accountEntryName,int sareesLimit) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ACCOUNT_ENTRY_ACCOUNT_ID, accountId);
		initialValues.put(KEY_ACCOUNT_ENTRY_NAME, accountEntryName);
		initialValues.put(KEY_ACCOUNT_ENTRY_DATE, cratedDate);
		initialValues.put(KEY_ACCOUNT_ENTRY_SAREES_LIMIT, sareesLimit+"");
		return mDb.insert(SQLITE_ACCOUNT_ENTRY_TABLE, null, initialValues);
	}

	public long createAccountEntryParticular(String accountEntryId,
			String cratedDate, String accountEntryParticularName,
			String numItems,String unitMeasure, String accountEntryParticularRate,
			String accountEntryParticularCalculatedAmount,
			String accountEntryExpenseAmount,
			String accountEntryParticularAdvanceAmount,String type) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ACCOUNT_ENTRY_PARTICULARS_ACCOUNT_ENTRY_ID,
				accountEntryId);
		initialValues.put(KEY_ACCOUNT_ENTRY_PARTICULARS_DATE, cratedDate);
		initialValues.put(KEY_ACCOUNT_ENTRY_PARTICULARS_NAME,
				accountEntryParticularName);
		initialValues.put(KEY_ACCOUNT_ENTRY_PARTICULARS_QUANTITY, numItems);
		initialValues.put(KEY_ACCOUNT_ENTRY_PARTICULARS_UNIT_MEASURE, unitMeasure);
		initialValues.put(KEY_ACCOUNT_ENTRY_PARTICULARS_RATE,
				accountEntryParticularRate);
		initialValues.put(KEY_ACCOUNT_ENTRY_PARTICULARS_CALCULATED_AMOUNT,
				accountEntryParticularCalculatedAmount);
		initialValues.put(KEY_ACCOUNT_ENTRY_PARTICULARS_EXPENSE_AMOUNT,
				accountEntryExpenseAmount);
		initialValues.put(KEY_ACCOUNT_ENTRY_PARTICULARS_ADVANCE_AMOUNT,
				accountEntryParticularAdvanceAmount);
		initialValues.put(KEY_ACCOUNT_ENTRY_PARTICULARS_TYPE,
				type);
		return mDb.insert(SQLITE_ACCOUNT_ENTRY_PARTICULARS_TABLE, null,
				initialValues);
	}

	public Cursor fetchAllItemTypes() {
		Cursor mCursor = mDb.query(SQLITE_MASTER_ITEM_TYPE_TABLE, new String[] {
				KEY_ITEM_TYPE_ROWID, KEY_ITEM_TYPE_NAME, KEY_ITEM_TYPE,
				KEY_ITEM_TYPE_MARKET_PRICE, KEY_ITEM_TYPE_MEASURE }, null,
				null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor fetchItemTypesById(String id) {
		Cursor mCursor = mDb.query(SQLITE_MASTER_ITEM_TYPE_TABLE, new String[] {
				KEY_ITEM_TYPE_ROWID, KEY_ITEM_TYPE_NAME, KEY_ITEM_TYPE,
				KEY_ITEM_TYPE_MARKET_PRICE, KEY_ITEM_TYPE_MEASURE },
				KEY_ITEM_TYPE_ROWID + " in (" + id + ")", null, null, null,
				null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor fetchItemTypeByItemType(String type) throws SQLException {
		Log.w(TAG, type);
		Cursor mCursor = null;
		mCursor = mDb.query(true, SQLITE_MASTER_ITEM_TYPE_TABLE, new String[] {
				KEY_ITEM_TYPE_ROWID, KEY_ITEM_TYPE_NAME, KEY_ITEM_TYPE,
				KEY_ITEM_TYPE_MARKET_PRICE, KEY_ITEM_TYPE_MEASURE },
				KEY_ITEM_TYPE + " in (" + type + ")", null, null, null, null,
				null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor fetchItemTypeByName(String name) throws SQLException {
		Log.w(TAG, name);
		Cursor mCursor = null;
		mCursor = mDb.query(true, SQLITE_MASTER_ITEM_TYPE_TABLE, new String[] {
				KEY_ITEM_TYPE_ROWID, KEY_ITEM_TYPE_NAME, KEY_ITEM_TYPE,
				KEY_ITEM_TYPE_MARKET_PRICE, KEY_ITEM_TYPE_MEASURE },
				KEY_ITEM_TYPE_NAME + " like '%" + name + "%'", null, null,
				null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor fetchAccountsByFirstName(String inputText)
			throws SQLException {
		Log.w(TAG, inputText);
		Cursor mCursor = null;
		if (inputText == null || inputText.length() == 0) {
			mCursor = mDb.query(SQLITE_ACCOUNT_TABLE, new String[] {
					KEY_ACCOUNT_ROWID, KEY_ACCOUNT_FIRST_NAME,
					KEY_ACCOUNT_LAST_NAME, KEY_ACCOUNT_PHONE,
					KEY_ACCOUNT_ADDRESS }, null, null, null, null, null);
		} else {
			try {
				Integer.parseInt(inputText);
				mCursor = mDb.query(true, SQLITE_ACCOUNT_TABLE, new String[] {
						KEY_ACCOUNT_ROWID, KEY_ACCOUNT_FIRST_NAME,
						KEY_ACCOUNT_LAST_NAME, KEY_ACCOUNT_PHONE,
						KEY_ACCOUNT_ADDRESS }, KEY_ACCOUNT_ROWID + " like '%"
						+ inputText + "%'", null, null, null, null, null);
			} catch (Exception e) {
				// TODO: handle exception
				mCursor = mDb.query(true, SQLITE_ACCOUNT_TABLE, new String[] {
						KEY_ACCOUNT_ROWID, KEY_ACCOUNT_FIRST_NAME,
						KEY_ACCOUNT_LAST_NAME, KEY_ACCOUNT_PHONE,
						KEY_ACCOUNT_ADDRESS }, KEY_ACCOUNT_FIRST_NAME + " like '%"
						+ inputText + "%'", null, null, null, null, null);
			}
		}
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public Cursor fetchAccountEntrysByAccountId(String accountId)
			throws SQLException {
		Cursor mCursor = null;
		if(accountId !=null){
		Log.w(TAG, accountId);
		try {
			System.out.println("accountId------------>"+accountId);
			mCursor = mDb.query(SQLITE_ACCOUNT_ENTRY_TABLE, new String[] {
					KEY_ACCOUNT_ENTRY_ROWID, KEY_ACCOUNT_ENTRY_NAME,
					KEY_ACCOUNT_ENTRY_DATE,KEY_ACCOUNT_ENTRY_SAREES_LIMIT }, KEY_ACCOUNT_ENTRY_ACCOUNT_ID
					+ " in ('" + accountId + "')", null, null, null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		}
		return mCursor;
	}

	public String fetchAccountNameByAccountEntryId(String accountEntryId)
			throws SQLException {
		Log.w(TAG, accountEntryId);
		Cursor mCursor = null;
		String firstName = null;
		try {

			mCursor = mDb.query(SQLITE_ACCOUNT_ENTRY_TABLE,
					new String[] { KEY_ACCOUNT_ENTRY_ACCOUNT_ID },
					KEY_ACCOUNT_ENTRY_ROWID + " in ('" + accountEntryId
							+ "')", null, null, null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
			}
			String accountId = null;
			if (mCursor != null) {
				try {
					do {
						accountId = mCursor
								.getString(mCursor
										.getColumnIndex(DbAdapter.KEY_ACCOUNT_ENTRY_ACCOUNT_ID));
					} while (mCursor.moveToNext());
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			// account name
			mCursor = mDb.query(SQLITE_ACCOUNT_TABLE,
					new String[] { KEY_ACCOUNT_FIRST_NAME }, KEY_ACCOUNT_ROWID
							+ " in ('" + accountId + "')", null, null, null,
					null);
			if (mCursor != null) {
				mCursor.moveToFirst();
			}
			if (mCursor != null) {
				try {
					do {
						firstName = mCursor
								.getString(mCursor
										.getColumnIndex(DbAdapter.KEY_ACCOUNT_FIRST_NAME));
					} while (mCursor.moveToNext());
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return firstName;
	}

	public List fetchAccountEntryParticularsByAccountEntryId(
			String accountEntryId) throws SQLException {
		Log.w(TAG, accountEntryId);
		Cursor cursor = null;
		List perticularDeailsList =null;
		try {

			cursor = mDb.query(SQLITE_ACCOUNT_ENTRY_PARTICULARS_TABLE,
					new String[] { KEY_ACCOUNT_ENTRY_PARTICULARS_ROWID,
							KEY_ACCOUNT_ENTRY_PARTICULARS_DATE,
							KEY_ACCOUNT_ENTRY_PARTICULARS_QUANTITY,
							KEY_ACCOUNT_ENTRY_PARTICULARS_UNIT_MEASURE,
							KEY_ACCOUNT_ENTRY_PARTICULARS_NAME,
							KEY_ACCOUNT_ENTRY_PARTICULARS_RATE,
							KEY_ACCOUNT_ENTRY_PARTICULARS_CALCULATED_AMOUNT,
							KEY_ACCOUNT_ENTRY_PARTICULARS_EXPENSE_AMOUNT,
							KEY_ACCOUNT_ENTRY_PARTICULARS_TYPE,
							KEY_ACCOUNT_ENTRY_PARTICULARS_ADVANCE_AMOUNT },
					KEY_ACCOUNT_ENTRY_PARTICULARS_ACCOUNT_ENTRY_ID + " in ( '"
							+ accountEntryId + "')", null, null, null,
							null);
			
			if (cursor != null) {
				perticularDeailsList = new ArrayList();
				if (cursor.moveToFirst()) {
						do {
							String entryParticularsId = cursor
									.getString(cursor
											.getColumnIndex(DbAdapter.KEY_ACCOUNT_ENTRY_PARTICULARS_ROWID));
							
							String createdDate = cursor
									.getString(cursor
											.getColumnIndex(DbAdapter.KEY_ACCOUNT_ENTRY_PARTICULARS_DATE));
							String quantity = cursor
									.getString(cursor
											.getColumnIndex(DbAdapter.KEY_ACCOUNT_ENTRY_PARTICULARS_QUANTITY));
							String unitMeasure = cursor
									.getString(cursor
											.getColumnIndex(DbAdapter.KEY_ACCOUNT_ENTRY_PARTICULARS_UNIT_MEASURE));
							if(unitMeasure != null){
								quantity = quantity+" "+unitMeasure;
							}
							String particularName = cursor
									.getString(cursor
											.getColumnIndex(DbAdapter.KEY_ACCOUNT_ENTRY_PARTICULARS_NAME));
							String rate = cursor
									.getString(cursor
											.getColumnIndex(DbAdapter.KEY_ACCOUNT_ENTRY_PARTICULARS_RATE));
							if(rate !=null && !rate.equals("")){
								rate = AppUtil.formatDouble(Double.valueOf(rate));
							}
							String calculatedAmount = cursor
									.getString(cursor
											.getColumnIndex(DbAdapter.KEY_ACCOUNT_ENTRY_PARTICULARS_CALCULATED_AMOUNT));
							if(calculatedAmount !=null && !calculatedAmount.equals("")){
								calculatedAmount = AppUtil.formatDouble(Double.valueOf(calculatedAmount));
							}
							String amount = cursor
									.getString(cursor
											.getColumnIndex(DbAdapter.KEY_ACCOUNT_ENTRY_PARTICULARS_EXPENSE_AMOUNT));
							if(amount !=null && !amount.equals("")){
								amount = AppUtil.formatDouble(Double.valueOf(amount));
							}
							String advanceAmount = cursor
									.getString(cursor
											.getColumnIndex(DbAdapter.KEY_ACCOUNT_ENTRY_PARTICULARS_ADVANCE_AMOUNT));
							if(advanceAmount !=null && !advanceAmount.equals("")){
								advanceAmount = AppUtil.formatDouble(Double.valueOf(advanceAmount));
							}
							PerticularDetail perticularDetail = new PerticularDetail();
							perticularDetail.setEntryParticularsId(entryParticularsId);
							perticularDetail.setCreatedDate(createdDate);
							perticularDetail.setParticularName(particularName);
							perticularDetail.setQuantity(quantity);
							perticularDetail.setRate(rate);
							perticularDetail.setCalculatedAmount(calculatedAmount);
							perticularDetail.setAmount(amount);
							perticularDetail.setAdvanceAmount(advanceAmount);
							perticularDeailsList.add(perticularDetail);
						}while (cursor.moveToNext());
					}
				}
			if(perticularDeailsList != null && perticularDeailsList.size()>0){
				Collections.sort(perticularDeailsList, new Comparator() {

					@Override
					public int compare(Object lhs, Object rhs) {
						// TODO Auto-generated method stub
						PerticularDetail perticularDetailLhs = (PerticularDetail)lhs;
						String dateStr1= perticularDetailLhs.getCreatedDate();
						PerticularDetail perticularDetailRhs = (PerticularDetail)rhs;
						String dateStr2= perticularDetailRhs.getCreatedDate();
							Date date1 = AppUtil.convertToDate(dateStr1);
							Date date2 = AppUtil.convertToDate(dateStr2);
						return date1.compareTo(date2);
					}
				});
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return perticularDeailsList;
	}

	public Cursor fetchAccountEntryParticularsForCashPaid(String type,
			String date) throws SQLException {
		Cursor mCursor = null;
		try {
			mCursor = mDb.query(SQLITE_ACCOUNT_ENTRY_PARTICULARS_TABLE,
					new String[] { KEY_ACCOUNT_ENTRY_PARTICULARS_ROWID,
							KEY_ACCOUNT_ENTRY_PARTICULARS_ACCOUNT_ENTRY_ID,
							KEY_ACCOUNT_ENTRY_PARTICULARS_DATE,
							KEY_ACCOUNT_ENTRY_PARTICULARS_QUANTITY,
							KEY_ACCOUNT_ENTRY_PARTICULARS_NAME,
							KEY_ACCOUNT_ENTRY_PARTICULARS_RATE,
							KEY_ACCOUNT_ENTRY_PARTICULARS_CALCULATED_AMOUNT,
							KEY_ACCOUNT_ENTRY_PARTICULARS_EXPENSE_AMOUNT,
							KEY_ACCOUNT_ENTRY_PARTICULARS_ADVANCE_AMOUNT,
							KEY_ACCOUNT_ENTRY_PARTICULARS_TYPE},
					KEY_ACCOUNT_ENTRY_PARTICULARS_TYPE + " in ('" + type
							+ "') and " + KEY_ACCOUNT_ENTRY_PARTICULARS_DATE
							+ " like '%" + date + "%'", null, null, null,
					null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return mCursor;
	}

	public List fetchAccountEntryParticularsForSareesReceived(String type,
			String date) throws SQLException {
		Cursor mCursor = null;
		List sareesReceivedList = new ArrayList();
		try {
			mCursor  = fetchAllAccounts();
			if (mCursor != null) {
				try {
					SareesReceivedReportVO receivedReportVO =null;
					do {
						String id = mCursor.getString(mCursor
								.getColumnIndex(DbAdapter.KEY_ACCOUNT_ROWID));
						String firstName = mCursor.getString(mCursor
								.getColumnIndex(DbAdapter.KEY_ACCOUNT_FIRST_NAME));
						 Cursor cursor = fetchAccountEntrysByAccountId(id); 
						 String entryIdStr="";
						 if (cursor != null) {
								try {
									do {
										String entryId = cursor.getString(cursor
												.getColumnIndex(DbAdapter.KEY_ACCOUNT_ENTRY_ROWID));
										System.out.println("entryId---------->"+entryId);
										entryIdStr = entryIdStr + "\"" + entryId + "\"";
										entryIdStr = entryIdStr + ",";
									} while (cursor.moveToNext());
								} catch (Exception e) {
									// TODO: handle exception
								}
						}
						 if (entryIdStr.length() > 0) {
							 entryIdStr = entryIdStr.substring(0, entryIdStr.length() - 1);
							 
								Cursor partiCularsCursor = mDb.query(SQLITE_ACCOUNT_ENTRY_PARTICULARS_TABLE,
										new String[] {
												 "SUM("+KEY_ACCOUNT_ENTRY_PARTICULARS_QUANTITY+")",
												KEY_ACCOUNT_ENTRY_PARTICULARS_DATE,
												KEY_ACCOUNT_ENTRY_PARTICULARS_NAME},
										KEY_ACCOUNT_ENTRY_PARTICULARS_TYPE + " in ('" + type
												+ "') and " + KEY_ACCOUNT_ENTRY_PARTICULARS_DATE
												+ " like '%" + date + "%' and "+KEY_ACCOUNT_ENTRY_PARTICULARS_ACCOUNT_ENTRY_ID+" in ("+entryIdStr+")", null, KEY_ACCOUNT_ENTRY_PARTICULARS_DATE+","+KEY_ACCOUNT_ENTRY_PARTICULARS_NAME, null,
										null);
							 if(partiCularsCursor != null){
								 try {
									 partiCularsCursor.moveToFirst();
									 do {
										 int  totalSarees = partiCularsCursor.getInt(0);
											String dateStr = partiCularsCursor.getString(partiCularsCursor
													.getColumnIndex(DbAdapter.KEY_ACCOUNT_ENTRY_PARTICULARS_DATE));
											String particularName = partiCularsCursor.getString(partiCularsCursor
													.getColumnIndex(DbAdapter.KEY_ACCOUNT_ENTRY_PARTICULARS_NAME));
											receivedReportVO = new SareesReceivedReportVO();
											receivedReportVO.setName(firstName);
											receivedReportVO.setDate(dateStr);
											receivedReportVO.setParticularName(particularName);
											receivedReportVO.setNumOfSarees(totalSarees+"");
											sareesReceivedList.add(receivedReportVO);
										} while (partiCularsCursor.moveToNext());									
								} catch (Exception e) {
									// TODO: handle exception
								}
							 }
						}
					} while (mCursor.moveToNext());
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return sareesReceivedList;
	}

	
	public List fetchFianlAccountEntryParticulars(String startDate,
			String endDate) throws SQLException {
		Cursor mCursor = null;
		List finalReportVOList = new ArrayList();
		try {
			mCursor  = fetchAllAccounts();
			if (mCursor != null) {
				try {
					FinalReportVO finalReportVO =null;
					do {
						String id = mCursor.getString(mCursor
								.getColumnIndex(DbAdapter.KEY_ACCOUNT_ROWID));
						String firstName = mCursor.getString(mCursor
								.getColumnIndex(DbAdapter.KEY_ACCOUNT_FIRST_NAME));
						 Cursor cursor = fetchAccountEntrysByAccountId(id); 
						 String entryIdStr="";
						 if (cursor != null) {
								try {
									do {
										String entryId = cursor.getString(cursor
												.getColumnIndex(DbAdapter.KEY_ACCOUNT_ENTRY_ROWID));
										System.out.println("entryId---------->"+entryId);
										entryIdStr = entryIdStr + "\"" + entryId + "\"";
										entryIdStr = entryIdStr + ",";
									} while (cursor.moveToNext());
								} catch (Exception e) {
									// TODO: handle exception
								}
						}
						 if (entryIdStr.length() > 0) {
							 entryIdStr = entryIdStr.substring(0, entryIdStr.length() - 1);
							 
							 Cursor partiCularsCursor = mDb
										.query(SQLITE_ACCOUNT_ENTRY_PARTICULARS_TABLE,
												new String[] {
														"SUM("
																+ KEY_ACCOUNT_ENTRY_PARTICULARS_CALCULATED_AMOUNT
																+ ")",
														"SUM("
																+ KEY_ACCOUNT_ENTRY_PARTICULARS_EXPENSE_AMOUNT
																+ ")",
														"SUM("
																+ KEY_ACCOUNT_ENTRY_PARTICULARS_ADVANCE_AMOUNT
																+ ")"}, KEY_ACCOUNT_ENTRY_PARTICULARS_DATE
														+ " BETWEEN '" + startDate + "' and '" + endDate + "' and "+KEY_ACCOUNT_ENTRY_PARTICULARS_ACCOUNT_ENTRY_ID+" in ("+entryIdStr+")", null, null,
												null, null);
							 
							 if(partiCularsCursor != null){
								 try {
									 partiCularsCursor.moveToFirst();
									 do {
										 int  calculatedAmnt = partiCularsCursor.getInt(0);
										 int  expenseAmnt = partiCularsCursor.getInt(1);
										 int calculatedAmntExpenseBal = calculatedAmnt-expenseAmnt;
										 int  adAmount = partiCularsCursor.getInt(2);
											String dateStr = startDate+" - "+endDate;
											finalReportVO = new FinalReportVO();
											finalReportVO.setName(firstName);
											finalReportVO.setDate(dateStr);
											if(calculatedAmntExpenseBal<0){
												finalReportVO.setCredit(calculatedAmntExpenseBal+"");
											}else{
												finalReportVO.setDebit(calculatedAmntExpenseBal+"");	
											}
											finalReportVO.setAdAmount(adAmount+"");
											finalReportVO.setBalance((adAmount-calculatedAmntExpenseBal)+"");
											finalReportVOList.add(finalReportVO);
											System.out.println(firstName+"---"+calculatedAmnt+"---"+expenseAmnt+"---"+calculatedAmntExpenseBal+"---"+adAmount);
										} while (partiCularsCursor.moveToNext());									
								} catch (Exception e) {
									// TODO: handle exception
								}
							 }
						}
					} while (mCursor.moveToNext());
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return finalReportVOList;
	}

	
	public Cursor fetchTotalRateAmountAdAmountByAccountEntryId(
			String accountEntryId) throws SQLException {
		Log.w(TAG, accountEntryId);
		Cursor mCursor = null;
		try {

			mCursor = mDb
					.query(SQLITE_ACCOUNT_ENTRY_PARTICULARS_TABLE,
							new String[] {
									"SUM("
											+ KEY_ACCOUNT_ENTRY_PARTICULARS_CALCULATED_AMOUNT
											+ ")",
									"SUM("
											+ KEY_ACCOUNT_ENTRY_PARTICULARS_EXPENSE_AMOUNT
											+ ")",
									"SUM("
											+ KEY_ACCOUNT_ENTRY_PARTICULARS_ADVANCE_AMOUNT
											+ ")" },
							KEY_ACCOUNT_ENTRY_PARTICULARS_ACCOUNT_ENTRY_ID
									+ " in ('" + accountEntryId + "')", null,
							null, null, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return mCursor;
	}

	public double fetchAvailableAdAmountForNextEntryByAccountEntryId(
			String accountEntryId,String accountId) throws SQLException {
		Log.w(TAG, accountEntryId);
		Cursor mCursor = null;
		String idStr = "";
		double totalAdAmount=0;
		double totalDiffAmount=0;
		double totalAvailableAdAmount=-1;
		try {

			mCursor = mDb.query(SQLITE_ACCOUNT_ENTRY_TABLE,
					new String[] { KEY_ACCOUNT_ENTRY_ROWID },
					KEY_ACCOUNT_ENTRY_ROWID + " < " + accountEntryId+" and "+KEY_ACCOUNT_ENTRY_ACCOUNT_ID+" in ('"+accountId+"')", null,
					null, null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
				do {
					String id = mCursor.getString(mCursor
							.getColumnIndex(DbAdapter.KEY_ACCOUNT_ENTRY_ROWID));
					idStr = idStr + "\"" + id + "\"";
					idStr = idStr + ",";
				} while (mCursor.moveToNext());
			}
			if(idStr.length()>0){
				if (idStr.length() > 0) {
					idStr = idStr.substring(0, idStr.length() - 1);
				}
				mCursor = mDb
					.query(SQLITE_ACCOUNT_ENTRY_PARTICULARS_TABLE,
							new String[] {
							"SUM("+ KEY_ACCOUNT_ENTRY_PARTICULARS_ADVANCE_AMOUNT+ ")" },
							KEY_ACCOUNT_ENTRY_PARTICULARS_ACCOUNT_ENTRY_ID
									+ " in (" + idStr + ")", null,
							null, null, null);
				if (mCursor != null) {
					if (mCursor.moveToFirst()) {
						do {
							totalAdAmount = mCursor.getDouble(0);
						} while (mCursor.moveToNext());
					}
				}
				
				mCursor = mDb
						.query(SQLITE_ACCOUNT_ENTRY_PARTICULARS_TABLE,
								new String[] {
								"(SUM("+ KEY_ACCOUNT_ENTRY_PARTICULARS_CALCULATED_AMOUNT+ ") - SUM("+KEY_ACCOUNT_ENTRY_PARTICULARS_EXPENSE_AMOUNT+ "))" },
								KEY_ACCOUNT_ENTRY_PARTICULARS_ACCOUNT_ENTRY_ID
										+ " in (" + idStr + ")", null,
								null, null, null);
					if (mCursor != null) {
						if (mCursor.moveToFirst()) {
							do {
								totalDiffAmount = mCursor.getDouble(0);
							} while (mCursor.moveToNext());
						}
					}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		totalAvailableAdAmount = totalAdAmount-totalDiffAmount;
		return totalAvailableAdAmount;
	}

	public Cursor fetchTotalRateAmountAdAmountByAccountEntryDate(String type,
			String date) throws SQLException {
		Log.w(TAG, date);
		Cursor mCursor = null;
		try {

			mCursor = mDb
					.query(SQLITE_ACCOUNT_ENTRY_PARTICULARS_TABLE,
							new String[] {
									"SUM("
											+ KEY_ACCOUNT_ENTRY_PARTICULARS_CALCULATED_AMOUNT
											+ ")",
									"SUM("
											+ KEY_ACCOUNT_ENTRY_PARTICULARS_EXPENSE_AMOUNT
											+ ")",
									"SUM("
											+ KEY_ACCOUNT_ENTRY_PARTICULARS_ADVANCE_AMOUNT
											+ ")" },
							KEY_ACCOUNT_ENTRY_PARTICULARS_TYPE + " in ('"
									+ type + "') and "
									+ KEY_ACCOUNT_ENTRY_PARTICULARS_DATE
									+ " like '%" + date + "%'", null, null,
							null, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return mCursor;
	}

	public int fetchTotalSareesCountByAccountEntryId(
			String accountEntryId) throws SQLException {
		Log.w(TAG, accountEntryId);
		Cursor mCursor = null;
		try {
			mCursor = mDb
					.query(SQLITE_ACCOUNT_ENTRY_PARTICULARS_TABLE,
							new String[] {
									"SUM("
											+ KEY_ACCOUNT_ENTRY_PARTICULARS_QUANTITY
											+ ")"},
							KEY_ACCOUNT_ENTRY_PARTICULARS_ACCOUNT_ENTRY_ID
									+ " in ('" + accountEntryId + "') and "+KEY_ACCOUNT_ENTRY_PARTICULARS_TYPE + " in ('"
									+ AppUtil.SAREE + "')", null,
							null, null, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		int totalSarees=0;
		try {
			mCursor.moveToFirst();
			do {
				totalSarees = mCursor.getInt(0);
			} while (mCursor.moveToNext());
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}
		return totalSarees;
	}
	
	public Cursor fetchAllAccounts() {
		Cursor mCursor = mDb.query(SQLITE_ACCOUNT_TABLE,
				new String[] { KEY_ACCOUNT_ROWID, KEY_ACCOUNT_FIRST_NAME,
						KEY_ACCOUNT_LAST_NAME, KEY_ACCOUNT_PHONE,
						KEY_ACCOUNT_ADDRESS }, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public long updateItemType(String itemTypeId, String name, String type,
			String marketPrice, String unitMeasure) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ITEM_TYPE_NAME, name);
		initialValues.put(KEY_ITEM_TYPE, type);
		initialValues.put(KEY_ITEM_TYPE_MARKET_PRICE, marketPrice);
		initialValues.put(KEY_ITEM_TYPE_MEASURE, unitMeasure);
		return mDb.update(SQLITE_MASTER_ITEM_TYPE_TABLE, initialValues,
				KEY_ITEM_TYPE_ROWID + "='" + itemTypeId + "'", null);
	}

	public long updateAccount(String accountId, String firstName,
			String lastName, String phone, String address) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ACCOUNT_FIRST_NAME, firstName);
		initialValues.put(KEY_ACCOUNT_LAST_NAME, lastName);
		initialValues.put(KEY_ACCOUNT_PHONE, phone);
		initialValues.put(KEY_ACCOUNT_ADDRESS, address);
		return mDb.update(SQLITE_ACCOUNT_TABLE, initialValues,
				KEY_ACCOUNT_ROWID + "='" + accountId + "'", null);
	}

	public long updateAccountEntry(String accountEntryId, String accountId,
			String cratedDate, String accountEntryName,int sareesLimit) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ACCOUNT_ENTRY_ACCOUNT_ID, accountId);
		initialValues.put(KEY_ACCOUNT_ENTRY_NAME, accountEntryName);
		initialValues.put(KEY_ACCOUNT_ENTRY_DATE, cratedDate);
		initialValues.put(KEY_ACCOUNT_ENTRY_SAREES_LIMIT, sareesLimit+"");
		return mDb.update(SQLITE_ACCOUNT_ENTRY_TABLE, initialValues,
				KEY_ACCOUNT_ENTRY_ROWID + "='" + accountEntryId + "'", null);
	}

	public void deleteItemType(String itemTypeId) {
		mDb.delete(SQLITE_MASTER_ITEM_TYPE_TABLE, KEY_ITEM_TYPE_ROWID + "="
				+ itemTypeId, null);
	}

	public void deleteAccount(String accountId) {
		Cursor cursor =  fetchAccountEntrysByAccountId(accountId);
		if(cursor != null){
			do {
				String entryId = cursor.getString(cursor
						.getColumnIndex(DbAdapter.KEY_ACCOUNT_ENTRY_ROWID));
				deleteAccountEntry(entryId);
			} while (cursor.moveToNext());
		}
		mDb.delete(SQLITE_ACCOUNT_TABLE, KEY_ACCOUNT_ROWID + "=" + accountId,
				null);
	}


	public void deleteAccountEntry(String accountEntryId) {
		List list =  fetchAccountEntryParticularsByAccountEntryId(accountEntryId);
		if(list != null){
			for(int i=0;i<list.size();i++){
				PerticularDetail perticularDetail = (PerticularDetail)list.get(i);
				String id = perticularDetail.getEntryParticularsId();
				deleteAccountParticularEntry(id);
			}
		}
		mDb.delete(SQLITE_ACCOUNT_ENTRY_TABLE, KEY_ACCOUNT_ENTRY_ROWID + "="
				+ accountEntryId, null);
	}

	public void deleteAccountParticularEntry(String accountParticularEntryId) {
		mDb.delete(SQLITE_ACCOUNT_ENTRY_PARTICULARS_TABLE,
				KEY_ACCOUNT_ENTRY_PARTICULARS_ROWID + "="
						+ accountParticularEntryId, null);
	}
	
	public void insertAccount(String fisrtName, String lastName, String phone,
			String address) {
		createAccount(fisrtName, lastName, phone, address);
	}

	public void insertAccountEntry(String selectedAccountId, String cratedDate,
			String accountEntryName,int sareesLimit) {
		createAccountEntry(selectedAccountId, cratedDate, accountEntryName,sareesLimit);
	}

	public void insertItemType(String name, String type, String marketPrice,
			String unitMeasure) {
		createItemType(name, type, marketPrice, unitMeasure);
	}

}