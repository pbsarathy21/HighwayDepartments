package com.spiderindia.departmentsofhighway.SqLiteDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by pyr on 12-Jun-18.
 */

public class MyDataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dataBaseManager";
    public static final String TABLE_NAME_BRIDGE = "highWayBridge";
    public static final String TABLE_NAME_INVENTARY = "highWayInventry";
    public static final String TABLE_NAME_CONDITION = "highWayCondition";
    public static final String TABLE_NAME_MAINTAINANCE = "highWayMaintainance";
    public static final String TABLE_NAME_CIRCLE = "highWayCircle";
    public static final String TABLE_NAME_DIVISION = "highWayDivision";
    public static final String TABLE_NAME_SUBDIVISION = "highWaySubDivision";
    public static final String TABLE_NAME_ROAD = "highWayRoad";
    public static final String TABLE_NAME_LINKID = "highWayLinkId";
    public static final String TABLE_NAME_mBRIDGE = "highWaymBridgeId";
    public static final String TABLE_NAME_SPINNER = "highWaySpinner";

    public static final String TABLE_NAME_CULVERT = "highWayCulvert";


    public static  String CULVERT_ID = "culvertId";

    //Bridge strat
    public static  String BRIDGE_ID = "bridgeId";
    public static  String CIRCLE = "circle_id";
    public static  String DIVISION = "division_id";
    public static  String SUBDIVISION = "sub_division_id";
    public static  String ROAD = "road_id";
    public static  String LINKID = "link_id";
    public static  String DESCRIPTION = "description";
    public static  String LOCATION = "location";
    public static  String STARTCHAINAGE = "start_chainage";
    public static  String BRIDGE = "bridge";
    public static  String BRIDGENO = "bridge_no";
    public static  String YR_OF_CONSTRU = "yr_of_constr";
    public static  String CONSTRU_COST = "constru_cost";
    public static  String CONSTRU_DATE = "constru_date";
    public static  String IMAGE_PATH = "image_path";
    public static  String DOCUMENT_PATH = "document_path";
    public static  String DOCUMENT_NAME = "document_name";
    public static  String SECOND_IMAGE_PATH = "second_image_path";
    public static  String ENCODED_IMAGE_PATH = "encoded_image_path";
    public static  String ENCODED_SECOND_IMAGE_PATH = "encoded_second_image_path";
    //Bridge End

    //Culvert start



    // Inventary strat
    public static  String INVENTARY_ID_I = "inventaryId";
    public static  String BRIDGE_TYPE_I = "bridge_type";
    public static  String LENGTH_I = "length";
    public static  String WIDTH_I = "width";
    public static  String PARTIAL_WED_I = "partial_wed";
    public static  String RIVER_NAME_I = "river_name";
    public static  String CONTROL_AUTHORITY_RIVER_I = "river_authority_cntrl";
    public static  String RIVER_FLOW_I = "river_flow";
    public static  String CHECK_DAM_I = "check_dam";
    public static  String LINEAR_WATRE_WAY_I = "linear_water_way";

    public static  String DESIGN_DISCHARGE_I = "design_dischrg";
    public static  String LOADING_I = "loading";
    public static  String SLAB_DESIGN_I = "slab_design";
    public static  String NO_LANES_I = "no_lanes";
    public static  String SPANS_I = "spans";
    public static  String MAX_SPANS_I = "max_spans";
    public static  String VERTICAL_CLEARENCE_I = "vertical_clearence";

    public static  String LEFT_FOOT_PATH_I = "l_foot_path";
    public static  String RIGHT_FOOT_PATH_I = "r_foot_path";
    public static  String SUPER_STRUCTURE_TYP_I = "super_stru_typ";
    public static  String SLAB_THIKNS_I = "slab_thikns";
    public static  String BEARING_TYPE_I = "bearing_typ";
    public static  String PARAPET_I = "parapet";
    public static  String WEARING_COAT_TYP_I = "wearing_coat_typ";
    public static  String PIER_FOUNDATN_I = "pier_foundatn";

    public static  String ABUTMNT_FOUNDATN_I = "abutmnt_foundtn";
    public static  String MLF_I = "mlf";
    public static  String BANK_PROTECTION_TYP_I = "bank_protection_typ";
    public static  String APPROACH_TYP_I = "approach_typ";
    public static  String FLOOR_PROTECTION_I = "floor_protection";
    public static  String FLOOR_PROTECTION_TYP_I = "floor_protection_typ";
    public static  String TYPE_OF_SUBSTRUCTURE_I = "sub_structure_typ";

    //Inventary End

    /*Contion start*/
    public static  String CONDITION_ID = "conditionId";
    public static  String SHAPE_OF_PIER = "pier_shape";
    public static  String BRIDGE_ANGLE = "bridge_angle";
    public static  String BED_LEVEL = "bed_level";
    public static  String BED_SLOPE = "bed_slope";


    //FOUNDATION
    public static  String CRACKS_LEFT_UPS_F = "cracks_left_ups_f";
    public static  String CRACKS_RIGHT_UPS_F = "cracks_right_ups_f";
    public static  String SETTLEMENT_LEFT_UPS_F = "settlement_left_ups_f";
    public static  String SETTLEMENT_RIGHT_UPS_F = "settlement_right_ups_f";
    public static  String SPALIT_LEFT_UPS_F = "spalit_left_ups_f";
    public static  String SPALIT_RIGHT_UPS_F = "spalit_right_ups_f";

    public static  String CRACKS_LEFT_DOWNS_F = "cracks_left_downs_f";
    public static  String CRACKS_RIGHT_DOWNS_F = "cracks_right_downs_f";
    public static  String SETTLEMENT_LEFT_DOWNS_F = "settlement_left_downs_f";
    public static  String SETTLEMENT_RIGHT_DOWNS_F = "settlement_right_downs_f";
    public static  String SPALIT_LEFT_DOWNS_F = "spalit_left_downs_f";
    public static  String SPALIT_RIGHT_DOWNS_F = "spalit_right_downs_f";

    public static  String SCOUR_F = "scour_f";
    public static  String SPALLED_F = "spalled_f";
    public static  String CRACKED_F = "cracked_f";

    //SUB STRUCTURE
    public static  String CRACKS_LEFT_UPS_SUBS = "caraks_l_ups_subS";
    public static  String CRACKS_RIGHT_UPS_SUBS = "caraks_r_ups_subS";
    public static  String VEGETATION_LEFT_UPS_SUBS = "vegetation_l_ups_subS";
    public static  String VEGETATION_RIGHT_UPS_SUBS = "vegetation_r_ups_subS";
    public static  String  TILTING_LEFT_UPS_SUBS = "tilting_l_ups_subS";
    public static  String TILTING_RIGHT_UPS_SUBS = "tilting_r_ups_subS";
    public static  String SPALIT_LEFT_UPS_SUBS = "spality_l_ups_subS";
    public static  String SPALIT_RIGHT_UPS_SUBS = "spality_r_ups_subS";

    public static  String CRACKS_LEFT_DOWNS_SUBS = "caraks_l_downs_subS";
    public static  String CRACKS_RIGHT_DOWNS_SUBS = "caraks_r_downs_subS";
    public static  String VEGETATION_LEFT_DOWNS_SUBS = "vegetation_l_downs_subS";
    public static  String VEGETATION_RIGHT_DOWNS_SUBS = "vegetation_r_downs_subS";
    public static  String  TILTING_LEFT_DOWNS_SUBS = "tilting_l_downs_subS";
    public static  String TILTING_RIGHT_DOWNS_SUBS = "tilting_r_downs_subS";
    public static  String SPALIT_LEFT_DOWNS_SUBS = "spality_l_downs_subS";
    public static  String SPALIT_RIGHT_DOWNS_SUBS = "spality_r_downs_subS";

    public static  String CRACKS_PIERS = "cracks_piers";
    public static  String TILTING_PIERS = "tilting_piers";
    public static  String VEGETATION_PIERS = "vegetation_piers";
    public static  String SPALIT_PIERS = "spality_piers";

    //SUPER STRUCTURE

    public static  String CRACKED_SUPERS = "cracked_superS";
    public static  String CORROSION_SUPERS = "corrosion_superS";
    public static  String SPALLED_SUPERS = "spalled_superS";
    public static  String LEACHED_SUPERS = "leached_superS";
    public static  String BUCKLED_SUPERS = "bucked_superS";
    public static  String VEGETATION_SUPERS = "vegetation_superS";
    public static  String SALIYO_SUPERS = "saliyo_superS";
    public static  String SCALED_SUPERS = "scaled_superS";
    public static  String SPALITY_SUPERS = "spality_superS";


    public static  String BROKEN_HR = "broken_hr";
    public static  String CORRODED_HR = "corroded_hr";
    public static  String SPALLING_HR = "spalling_hr";

    public static  String BROKEN_FP  = "broken_fp";
    public static  String DISINTEGRATION_NO_Fp  = "disintegration_no_fp";

    //WEARING COAT TYPE
    public static  String CRACKS_WC  = "cracks_wc";
    public static  String POTHOLES_WC  = "potholes_wc";
    public static  String RAVELLED_WC  = "ravelled_wc";
    public static  String FAILED_JOINTS_WC  = "failed_joint_wc";
    public static  String POOR_DRAINAGE_WC  = "poor_drainage_wc";

    //BEARING
    public static  String RUSTED_B  = "rusted_b";
    public static  String TILTED_B = "tilted_b";
    public static  String FLATTERING_B  = "flattering_b";
    public static  String SPLITINAL_CRACK_B  = "splitinal_crack_b";

    //EXPANSION JOINTS
    public static  String WORNOUT_EJ  = "wornout_ej";
    public static  String BLEED_EJ  = "bleed_ej";
    public static  String CRACKED_EJ  = "cracked_ej";

    //VENTWAY / WATERWAY
    public static  String SILTED_VW  = "silted_vw";
    public static  String SCOUR_VW  = "scour_vw";

    /*Contion end*/

    //MAINTAINANCE HISTORY
    public static  String MAINTAINANCE_ID  = "maintainance_id";
    public static  String YEAR_MH  = "year_mh";
    public static  String TYPE_MH  = "type_mh";
    public static  String COMPONENT_MH  = "component_mh";
    public static  String AMOUNT_MH  = "amount_mh";

    /*Maintainance end*/


    //Spinner
    public static  String CIRCLE_ID  = "circle_idd";
    public static  String CIRCLE_NAME  = "circle_name";

    public static  String DIVISION_ID  = "division_idd";
    public static  String DIVISION_NAME  = "division_name";

    public static  String SubDIVISION_ID  = "subdivision_idd";
    public static  String SubDIVISION_NAME  = "subdivision_name";

    public static  String LINK_ID  = "link_idd";
    public static  String mBRIDGE_Id  = "mBRIDGE_Id";
    public static  String mBRIDGE_NAME  = "mBRIDGE_name";
    public static  String LINK_NAME  = "road_name";


    public static  String ROAD_ID  = "road_idd";
    public static  String ROAD_NAME  = "road_name";

    public static  String SPINNER_ID  = "spinner_id";
    public static  String SPINNER_ITEM_ID  = "spinner_item_id";
    public static  String SPINNER_ITEM_NAME  = "spinner_item_name";
    public static  String SPINNER_ITEM_TYPE  = "spinner_item_type";


    String CREATE_BRIDGE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_BRIDGE + " (" +BRIDGE_ID + " INTEGER PRIMARY KEY," +
            CIRCLE + " TEXT,"+
            DIVISION + " TEXT,"+
            SUBDIVISION + " TEXT,"+
            ROAD + " TEXT,"+
            LINKID + " TEXT,"+
            DESCRIPTION + " TEXT,"+
            LOCATION + " TEXT,"+
            STARTCHAINAGE + " TEXT,"+
            BRIDGE + " TEXT,"+
            BRIDGENO + " TEXT,"+
            YR_OF_CONSTRU + " TEXT,"+
            CONSTRU_COST + " TEXT,"+
            CONSTRU_DATE + " TEXT,"+
            DOCUMENT_PATH + " TEXT,"+
            DOCUMENT_NAME + " TEXT,"+
            IMAGE_PATH  + " TEXT,"+
            SECOND_IMAGE_PATH + " TEXT,"+
            ENCODED_IMAGE_PATH  + " TEXT,"+
            ENCODED_SECOND_IMAGE_PATH + " TEXT" +" )";

    String CREATE_CULVERT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CULVERT + " (" +CULVERT_ID + " INTEGER PRIMARY KEY," +
            CIRCLE + " TEXT,"+
            DIVISION + " TEXT,"+
            SUBDIVISION + " TEXT,"+
            ROAD + " TEXT,"+
            LINKID + " TEXT" +" )";

          /*  LINKID + " TEXT,"+
            DESCRIPTION + " TEXT,"+
            LOCATION + " TEXT,"+
            STARTCHAINAGE + " TEXT,"+
            BRIDGE + " TEXT,"+
            BRIDGENO + " TEXT,"+
            YR_OF_CONSTRU + " TEXT,"+
            CONSTRU_COST + " TEXT,"+
            CONSTRU_DATE + " TEXT,"+
            DOCUMENT_PATH + " TEXT,"+
            DOCUMENT_NAME + " TEXT,"+
            IMAGE_PATH  + " TEXT,"+
            SECOND_IMAGE_PATH + " TEXT,"+
            ENCODED_IMAGE_PATH  + " TEXT,"+
            ENCODED_SECOND_IMAGE_PATH + " TEXT" +" )";*/


    String CREATE_INVENTARY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_INVENTARY + " (" +INVENTARY_ID_I + " INTEGER PRIMARY KEY," +
            BRIDGE_TYPE_I + " TEXT,"+
            LENGTH_I + " TEXT,"+
            WIDTH_I + " TEXT,"+
            PARTIAL_WED_I + " TEXT,"+
            RIVER_NAME_I + " TEXT,"+
            CONTROL_AUTHORITY_RIVER_I + " TEXT,"+
            RIVER_FLOW_I + " TEXT,"+
            CHECK_DAM_I + " TEXT,"+
            LINEAR_WATRE_WAY_I + " TEXT,"+

            DESIGN_DISCHARGE_I + " TEXT,"+
            LOADING_I + " TEXT,"+
            SLAB_DESIGN_I + " TEXT,"+
            NO_LANES_I + " TEXT,"+
            SPANS_I + " TEXT,"+
            MAX_SPANS_I + " TEXT,"+
            VERTICAL_CLEARENCE_I + " TEXT,"+

            LEFT_FOOT_PATH_I + " TEXT,"+
            RIGHT_FOOT_PATH_I + " TEXT,"+
            SUPER_STRUCTURE_TYP_I + " TEXT,"+
            SLAB_THIKNS_I + " TEXT,"+
            BEARING_TYPE_I + " TEXT,"+
            PARAPET_I + " TEXT,"+
            WEARING_COAT_TYP_I + " TEXT,"+
            PIER_FOUNDATN_I + " TEXT,"+

            ABUTMNT_FOUNDATN_I + " TEXT,"+
            MLF_I + " TEXT,"+
            BANK_PROTECTION_TYP_I + " TEXT,"+
            APPROACH_TYP_I + " TEXT,"+
            FLOOR_PROTECTION_I + " TEXT,"+
            FLOOR_PROTECTION_TYP_I + " TEXT,"+
            TYPE_OF_SUBSTRUCTURE_I + " TEXT" +" )";

    String CREATE_CONDITION_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CONDITION + " (" +CONDITION_ID + " INTEGER PRIMARY KEY," +
            SHAPE_OF_PIER + " TEXT,"+
            BRIDGE_ANGLE + " TEXT,"+
            BED_LEVEL + " TEXT,"+
            BED_SLOPE + " TEXT,"+

            CRACKS_LEFT_UPS_F + " TEXT,"+
            CRACKS_RIGHT_UPS_F + " TEXT,"+
            SETTLEMENT_LEFT_UPS_F + " TEXT,"+
            SETTLEMENT_RIGHT_UPS_F + " TEXT,"+
            SPALIT_LEFT_UPS_F + " TEXT,"+
            SPALIT_RIGHT_UPS_F + " TEXT,"+
            CRACKS_LEFT_DOWNS_F + " TEXT,"+
            CRACKS_RIGHT_DOWNS_F + " TEXT,"+
            SETTLEMENT_LEFT_DOWNS_F + " TEXT,"+
            SETTLEMENT_RIGHT_DOWNS_F + " TEXT,"+
            SPALIT_LEFT_DOWNS_F + " TEXT,"+
            SPALIT_RIGHT_DOWNS_F + " TEXT,"+
            SCOUR_F + " TEXT,"+
            SPALLED_F + " TEXT,"+
            CRACKED_F + " TEXT,"+

            CRACKS_LEFT_UPS_SUBS + " TEXT," +
            CRACKS_RIGHT_UPS_SUBS + " TEXT," +
            VEGETATION_LEFT_UPS_SUBS + " TEXT," +
            VEGETATION_RIGHT_UPS_SUBS + " TEXT," +
            TILTING_LEFT_UPS_SUBS + " TEXT," +
            TILTING_RIGHT_UPS_SUBS + " TEXT," +
            SPALIT_LEFT_UPS_SUBS + " TEXT," +
            SPALIT_RIGHT_UPS_SUBS + " TEXT," +
            CRACKS_LEFT_DOWNS_SUBS + " TEXT," +
            CRACKS_RIGHT_DOWNS_SUBS + " TEXT," +
            VEGETATION_LEFT_DOWNS_SUBS + " TEXT," +
            VEGETATION_RIGHT_DOWNS_SUBS + " TEXT," +
            TILTING_LEFT_DOWNS_SUBS + " TEXT," +
            TILTING_RIGHT_DOWNS_SUBS + " TEXT," +
            SPALIT_LEFT_DOWNS_SUBS + " TEXT," +
            SPALIT_RIGHT_DOWNS_SUBS + " TEXT," +
            CRACKS_PIERS + " TEXT," +
            TILTING_PIERS + " TEXT," +
            VEGETATION_PIERS + " TEXT," +
            SPALIT_PIERS + " TEXT," +

            CRACKED_SUPERS + " TEXT," +
            CORROSION_SUPERS + " TEXT," +
            SPALLED_SUPERS + " TEXT," +
            LEACHED_SUPERS + " TEXT," +
            BUCKLED_SUPERS + " TEXT," +
            VEGETATION_SUPERS + " TEXT," +
            SALIYO_SUPERS + " TEXT," +
            SCALED_SUPERS + " TEXT," +
            SPALITY_SUPERS + " TEXT," +
            BROKEN_HR + " TEXT," +
            CORRODED_HR + " TEXT," +
            SPALLING_HR + " TEXT," +
            BROKEN_FP + " TEXT," +
            DISINTEGRATION_NO_Fp + " TEXT," +

            CRACKS_WC + " TEXT," +
            POTHOLES_WC + " TEXT," +
            RAVELLED_WC + " TEXT," +
            FAILED_JOINTS_WC + " TEXT," +
            POOR_DRAINAGE_WC + " TEXT," +

            RUSTED_B + " TEXT," +
            TILTED_B + " TEXT," +
            FLATTERING_B + " TEXT," +
            SPLITINAL_CRACK_B + " TEXT," +

            WORNOUT_EJ + " TEXT," +
            BLEED_EJ + " TEXT," +
            CRACKED_EJ + " TEXT," +
            SILTED_VW + " TEXT," +
            SCOUR_VW + " TEXT" +" )";


    String CREATE_MAINTAINANCE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_MAINTAINANCE + " (" +MAINTAINANCE_ID + " INTEGER PRIMARY KEY," +
            YEAR_MH + " TEXT,"+
            TYPE_MH + " TEXT,"+
            COMPONENT_MH + " TEXT,"+
            AMOUNT_MH + " TEXT" +" )";
    //SPINNER

    String CREATE_SPINNER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_SPINNER + " (" +BRIDGE_ID + " INTEGER PRIMARY KEY," +
            CIRCLE + " TEXT,"+
            DIVISION + " TEXT,"+
            SUBDIVISION + " TEXT,"+
            ROAD + " TEXT,"+
            LINKID + " TEXT,"+
            IMAGE_PATH + " TEXT" +" )";

    String CREATE_CIRCLE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CIRCLE + " (" +CIRCLE_ID + " INTEGER PRIMARY KEY," +
            CIRCLE_NAME + " TEXT" +" )";

    String CREATE_DIVISION_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_DIVISION+ " (" +DIVISION_ID + " INTEGER PRIMARY KEY," +
            DIVISION_NAME + " TEXT" +" )";

    String CREATE_SUBDIVISION_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_SUBDIVISION+ " (" +SubDIVISION_ID + " INTEGER PRIMARY KEY," +
            SubDIVISION_NAME + " TEXT" +" )";

    String CREATE_ROAD_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_ROAD+ " (" +ROAD_ID + " INTEGER PRIMARY KEY," +
            ROAD_NAME + " TEXT" +" )";

    String CREATE_LINKID_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_LINKID+ " (" +LINK_ID + " INTEGER PRIMARY KEY," +
            LINK_NAME + " TEXT" +" )";

    String CREATE_mBRIDGE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_mBRIDGE+ " (" +mBRIDGE_Id + " INTEGER PRIMARY KEY," +
            mBRIDGE_NAME + " TEXT" +" )";


    //ArrayList<ImageListDetails> imageList;
    public MyDataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_BRIDGE_TABLE);
        db.execSQL(CREATE_INVENTARY_TABLE);
        db.execSQL(CREATE_CONDITION_TABLE);
        db.execSQL(CREATE_MAINTAINANCE_TABLE);
        db.execSQL(CREATE_CIRCLE_TABLE);
        db.execSQL(CREATE_DIVISION_TABLE);
        db.execSQL(CREATE_SUBDIVISION_TABLE);
        db.execSQL(CREATE_ROAD_TABLE);
        db.execSQL(CREATE_LINKID_TABLE);
        db.execSQL(CREATE_mBRIDGE_TABLE);

        db.execSQL(CREATE_CULVERT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_BRIDGE);

        // Create tables again
        onCreate(db);
    }

    public  void createTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_BRIDGE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_BRIDGE + "("+ BRIDGE_ID + " INTEGER PRIMARY KEY," + LOCATION + " TEXT,"+ BRIDGE + " TEXT,"+ BRIDGENO + " TEXT" + ")";
        db.execSQL( "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_BRIDGE + "("+ BRIDGE_ID + " INTEGER PRIMARY KEY," + CIRCLE + " TEXT,"+ DIVISION + " TEXT,"+ SUBDIVISION + " TEXT,"+ ROAD + " TEXT,"+ LINKID + " TEXT,"+ DESCRIPTION + " TEXT,"+ LOCATION + " TEXT,"+ STARTCHAINAGE + " TEXT,"+ BRIDGE + " TEXT" + BRIDGENO + " TEXT"+ YR_OF_CONSTRU + " TEXT"+ CONSTRU_COST + " TEXT"+CONSTRU_DATE + " TEXT"+ IMAGE_PATH + " TEXT" + ")");
        db.execSQL(CREATE_INVENTARY_TABLE);
    }

    public long InsertDetails(ContentValues con,String tableName) {

        SQLiteDatabase db = this.getWritableDatabase();
        // TODO Auto-generated method stub

        return db.insert(tableName, null, con);

    }

    public boolean UpdateDetails(ContentValues content,String tableName,String coloumnName)
    {
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();

            printContentValues(content);

            int result=db.update(tableName,content,coloumnName + " =?", new String[]{String.valueOf(1)});

            System.out.println("result of update"+result);
            if(result>0)
            {
                return true;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }
    public Cursor  getAllDetails(String tableName,String coloumnName)
    {
        String selectQuery = "SELECT * FROM "+ tableName+" WHERE "+ coloumnName +" = " + "1" ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

            } while (cursor.moveToNext());
        }

        return cursor ;
    }

    public Cursor  getAllCircleDetails(String tableName)
    {
        String selectQuery = "SELECT * FROM "+ tableName ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            } while (cursor.moveToNext());
        }

        return cursor ;
    }


    public void dropTable(String tableName)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS '" + tableName + "'");
    }

    public  long  getCounts()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME_BRIDGE);
        db.close();
        return count;
    }
    public void printContentValues(ContentValues vals)
    {
        Set<Map.Entry<String, Object>> s=vals.valueSet();
        Iterator itr = s.iterator();

        while(itr.hasNext())
        {
            Map.Entry me = (Map.Entry)itr.next();
            String key = me.getKey().toString();
            Object value =  me.getValue();

            Log.d("DatabaseSync new deeps", "Key:"+key+", values:"+(String)(value == null?null:value.toString()));
        }
    }

    public void deleteDataBase(Context con)
    {
        con.deleteDatabase(DATABASE_NAME);
    }

    public void emptyTable(String tableName)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from "+ tableName);
        System.out.println("Dedleted "+tableName);
    }

    public boolean isEmpty(String TableName){

        SQLiteDatabase database = this.getReadableDatabase();
        int NoOfRows = (int) DatabaseUtils.queryNumEntries(database,TableName);

        if (NoOfRows == 0){
            return true;
        }else {
            return false;
        }
    }

    public void insertAllTables()
    {
        //culvert
        ContentValues culvertCon=new ContentValues();
        culvertCon.put(CIRCLE,"0");
        culvertCon.put(DIVISION,"0");
        culvertCon.put(SUBDIVISION,"0");
        culvertCon.put(ROAD,"0");
        culvertCon.put(LINKID,"0");

        //Bridge
        ContentValues con=new ContentValues();
        con.put(CIRCLE,"0");
        con.put(DIVISION,"0");
        con.put(SUBDIVISION,"0");
        con.put(ROAD,"0");
        con.put(LINKID,"0");
        con.put(DESCRIPTION,"");
        con.put(LOCATION,"");
        con.put(STARTCHAINAGE,"");
        con.put(BRIDGE,"");
        con.put(BRIDGENO,"");
        con.put(YR_OF_CONSTRU,"");
        con.put(CONSTRU_COST,"");
        con.put(CONSTRU_DATE,"");
        con.put(DOCUMENT_PATH,"");
        con.put(DOCUMENT_NAME,"No File Choosen");
        con.put(IMAGE_PATH,"");
        con.put(SECOND_IMAGE_PATH,"");
        con.put(ENCODED_IMAGE_PATH,"");
        con.put(ENCODED_SECOND_IMAGE_PATH,"");

        ContentValues innentaryCon=new ContentValues();
        innentaryCon.put(BRIDGE_TYPE_I,"");
        innentaryCon.put(LENGTH_I,"");
        innentaryCon.put(WIDTH_I,"");
        innentaryCon.put(PARTIAL_WED_I,"0");
        innentaryCon.put(RIVER_NAME_I,"");
        innentaryCon.put(CONTROL_AUTHORITY_RIVER_I,"");
        innentaryCon.put(RIVER_FLOW_I,"");
        innentaryCon.put(CHECK_DAM_I,"0");
        innentaryCon.put(LINEAR_WATRE_WAY_I,"");

        //form 2
        innentaryCon.put(DESIGN_DISCHARGE_I,"");
        innentaryCon.put(LOADING_I,"");
        innentaryCon.put(SLAB_DESIGN_I,"");
        innentaryCon.put(NO_LANES_I,"");
        innentaryCon.put(SPANS_I,"");
        innentaryCon.put(MAX_SPANS_I,"");
        innentaryCon.put(VERTICAL_CLEARENCE_I,"");

        //form 3

        innentaryCon.put(LEFT_FOOT_PATH_I,"");
        innentaryCon.put(RIGHT_FOOT_PATH_I,"");
        innentaryCon.put(SUPER_STRUCTURE_TYP_I,"");
        innentaryCon.put(SLAB_THIKNS_I,"");
        innentaryCon.put(BEARING_TYPE_I,"");
        innentaryCon.put(PARAPET_I,"");
        innentaryCon.put(WEARING_COAT_TYP_I,"");
        innentaryCon.put(PIER_FOUNDATN_I,"");

        //form 4
        innentaryCon.put(ABUTMNT_FOUNDATN_I,"");
        innentaryCon.put(MLF_I,"");
        innentaryCon.put(BANK_PROTECTION_TYP_I,"");
        innentaryCon.put(APPROACH_TYP_I,"");
        innentaryCon.put(FLOOR_PROTECTION_I,"");
        innentaryCon.put(FLOOR_PROTECTION_TYP_I,"");
        innentaryCon.put(TYPE_OF_SUBSTRUCTURE_I,"");



        //CONDITION
        ContentValues conditionCon=new ContentValues();

        conditionCon.put(SHAPE_OF_PIER,"");
        conditionCon.put(BRIDGE_ANGLE,"");
        conditionCon.put(BED_LEVEL,"");
        conditionCon.put(BED_SLOPE,"");

        //form 2
        conditionCon.put(CRACKS_LEFT_UPS_F,"0");

        conditionCon.put(CRACKS_RIGHT_UPS_F,"0");
        conditionCon.put(SETTLEMENT_LEFT_UPS_F,"0");
        conditionCon.put(SETTLEMENT_RIGHT_UPS_F,"0");
        conditionCon.put(SPALIT_LEFT_UPS_F,"0");
        conditionCon.put(SPALIT_RIGHT_UPS_F,"0");

        conditionCon.put(CRACKS_LEFT_DOWNS_F,"0");
        conditionCon.put(CRACKS_RIGHT_DOWNS_F,"0");
        conditionCon.put(SETTLEMENT_LEFT_DOWNS_F,"0");
        conditionCon.put(SETTLEMENT_RIGHT_DOWNS_F,"0");
        conditionCon.put(SPALIT_LEFT_DOWNS_F,"0");
        conditionCon.put(SPALIT_RIGHT_DOWNS_F,"0");

        conditionCon.put(SCOUR_F,"0");
        conditionCon.put(SPALLED_F,"0");
        conditionCon.put(CRACKED_F,"0");

        //form 3

        conditionCon.put(CRACKS_LEFT_UPS_SUBS,"0");
        conditionCon.put(CRACKS_RIGHT_UPS_SUBS,"0");
        conditionCon.put(VEGETATION_LEFT_UPS_SUBS,"0");

        conditionCon.put(VEGETATION_RIGHT_UPS_SUBS,"0");
        conditionCon.put(TILTING_LEFT_UPS_SUBS,"0");
        conditionCon.put(TILTING_RIGHT_UPS_SUBS,"0");
        conditionCon.put(SPALIT_LEFT_UPS_SUBS,"0");
        conditionCon.put(SPALIT_RIGHT_UPS_SUBS,"0");
        conditionCon.put(CRACKS_LEFT_DOWNS_SUBS,"0");
        conditionCon.put(CRACKS_RIGHT_DOWNS_SUBS,"0");
        conditionCon.put(VEGETATION_LEFT_DOWNS_SUBS,"0");
        conditionCon.put(VEGETATION_RIGHT_DOWNS_SUBS,"0");

        conditionCon.put(TILTING_LEFT_DOWNS_SUBS,"0");
        conditionCon.put(TILTING_RIGHT_DOWNS_SUBS,"0");
        conditionCon.put(SPALIT_LEFT_DOWNS_SUBS,"0");
        conditionCon.put(SPALIT_RIGHT_DOWNS_SUBS,"0");

        conditionCon.put(CRACKS_PIERS,"0");
        conditionCon.put(TILTING_PIERS,"0");
        conditionCon.put(VEGETATION_PIERS,"0");
        conditionCon.put(SPALIT_PIERS,"0");

        //form4

        conditionCon.put(CRACKED_SUPERS,"0");
        conditionCon.put(CORROSION_SUPERS,"0");
        conditionCon.put(SPALLED_SUPERS,"0");
        conditionCon.put(LEACHED_SUPERS,"0");

        conditionCon.put(BUCKLED_SUPERS,"0");
        conditionCon.put(VEGETATION_SUPERS,"0");
        conditionCon.put(SALIYO_SUPERS,"0");
        conditionCon.put(SCALED_SUPERS,"0");
        conditionCon.put(SPALITY_SUPERS,"0");

        conditionCon.put(BROKEN_HR,"0");
        conditionCon.put(CORRODED_HR,"0");
        conditionCon.put(SPALLING_HR,"0");

        conditionCon.put(BROKEN_FP,"0");
        conditionCon.put(DISINTEGRATION_NO_Fp,"0");

        conditionCon.put(CRACKS_WC,"0");
        conditionCon.put(POTHOLES_WC,"0");
        conditionCon.put(RAVELLED_WC,"0");
        conditionCon.put(FAILED_JOINTS_WC,"0");
        conditionCon.put(POOR_DRAINAGE_WC,"0");

        conditionCon.put(RUSTED_B,"0");
        conditionCon.put(TILTED_B,"0");
        conditionCon.put(FLATTERING_B,"0");
        conditionCon.put(SPLITINAL_CRACK_B,"0");

        conditionCon.put(WORNOUT_EJ,"0");
        conditionCon.put(BLEED_EJ,"0");
        conditionCon.put(CRACKED_EJ,"0");
        conditionCon.put(SILTED_VW,"0");
        conditionCon.put(SCOUR_VW,"0");



        ContentValues maintainanceCon=new ContentValues();
        maintainanceCon.put(YEAR_MH,"");
        maintainanceCon.put(TYPE_MH,"0");
        maintainanceCon.put(COMPONENT_MH,"0");
        maintainanceCon.put(AMOUNT_MH,"");


        if(isEmpty(TABLE_NAME_BRIDGE))
            InsertDetails(con,TABLE_NAME_BRIDGE);

        if(isEmpty(TABLE_NAME_CULVERT))
            InsertDetails(culvertCon,TABLE_NAME_CULVERT);

        if(isEmpty(TABLE_NAME_INVENTARY))
            InsertDetails(innentaryCon,TABLE_NAME_INVENTARY);

        if(isEmpty(TABLE_NAME_CONDITION))
            InsertDetails(conditionCon,TABLE_NAME_CONDITION);

        if(isEmpty(TABLE_NAME_MAINTAINANCE))
            InsertDetails(maintainanceCon,TABLE_NAME_MAINTAINANCE);
    }
}
