package com.chiibi.moneyflow;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.chiibi.moneyflow.TransactionInfo;
import com.chiibi.moneyflow.TransactionInfoDAO;

@Database(entities = {TransactionInfo.class}, version = 1)
abstract class MoneyflowDB extends RoomDatabase {
    public abstract TransactionInfoDAO transactionInfoDAO();
}