package com.xiaodon.adr.ormlite.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.xiaodon.adr.ormlite.bean.Loan;

import java.sql.SQLException;

public class LoanHelper extends OrmLiteSqliteOpenHelper {
    private static final String databaseName = "table_loans.db";
    private Dao<Loan,Integer> loanDao = null;

    public LoanHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
    try {
        TableUtils.createTable(connectionSource, Loan.class);
        Log.e("onCreate: ","数据库创建成功！" );
    }catch (SQLException e){
        e.printStackTrace();
        Log.e("onCreate: ", "数据库创建失败");
    }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
    try{
        TableUtils.dropTable(connectionSource,Loan.class,true);
        onCreate(sqLiteDatabase,connectionSource);
    }catch (SQLException e){
        e.printStackTrace();
    }
    }

    /**
     * 单例模式获取当前helper的一个实例
     */
    private static LoanHelper Instance;
    public static synchronized LoanHelper getHelper(Context context){
        if(Instance == null){
            synchronized (LoanHelper.class){
                if(Instance == null){
                    Instance = new LoanHelper(context);
                }
            }
        }
        return Instance;
    }

    public Dao<Loan,Integer> getLoanDao()throws  SQLException{
        if(loanDao == null){
            loanDao = getDao(Loan.class);
        }
        return loanDao;
    }

    public synchronized void clearData(Class<Loan> clazz){
        try{
            TableUtils.clearTable(connectionSource,clazz);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 关闭资源
     */
    public void close(){
        super.close();
        loanDao = null;
    }
}
