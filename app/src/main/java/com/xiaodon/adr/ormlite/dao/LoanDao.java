package com.xiaodon.adr.ormlite.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.xiaodon.adr.ormlite.bean.Loan;
import com.xiaodon.adr.ormlite.helper.LoanHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanDao {
    private Dao<Loan,Integer> loanDao;
    private LoanHelper loanHelper;

    public LoanDao(Context context){
        try{
            loanHelper = LoanHelper.getHelper(context);
            loanDao = loanHelper.getLoanDao();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //添加
    public void add(Loan loan){
        try{
            loanDao.create(loan);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //删除 按ID
    public void del(Integer id){
        try{
            loanDao.deleteById(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //删除 全部
    public void delAll(){
        try{
            loanDao.delete(getAllLoan());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //更新
    public void update(Loan loan){
        try{
            loanDao.update(loan);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //查
    public List<Loan> getAllLoan(){
        List<Loan> loans = new ArrayList<>();
        try{
            loans = loanDao.queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return loans;
    }
    public Loan getLoanbyId(Integer id){
        Loan loan = null;
        try{
            loan = loanDao.queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return loan;
    }
}
