package com.chiibi.moneyflow;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.chiibi.moneyflow.TransactionInfo;
import java.util.List;

@Dao
public interface TransactionInfoDAO {

        @Insert
        void Insert(TransactionInfo transactionInfo);

        @Query("SELECT * FROM TRANS_INFO")
        List<TransactionInfo> allItem();

        @Query("DELETE FROM TRANS_INFO WHERE Item like :delete")
        int deletecolumn(String delete);

        @Query("UPDATE TRANS_INFO SET Type = :type,Item = :item ,Amount = :amount WHERE id =:ids")
        int UpdateColumn(String type, String item, int amount, int ids);
}

