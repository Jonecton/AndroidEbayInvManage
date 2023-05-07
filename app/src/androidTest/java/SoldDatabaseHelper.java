import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SoldDatabaseHelper extends SQLiteOpenHelper {
    // Define database name and version
    private static final String DATABASE_NAME = "sold.db";
    private static final int DATABASE_VERSION = 1;

    // Define table and column names
    private static final String TABLE_NAME = "items";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_BRAND = "brand";
    private static final String COLUMN_SIZE = "size";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_COST = "cost";
    private static final String COLUMN_LIST_PRICE = "list_price";

    // Define SQL statement to create table
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_BRAND + " TEXT NOT NULL, "
            + COLUMN_SIZE + " TEXT NOT NULL, "
            + COLUMN_TYPE + " TEXT NOT NULL, "
            + COLUMN_COST + " REAL NOT NULL, "
            + COLUMN_LIST_PRICE + " REAL NOT NULL);";

    public SoldDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
