package com.chiibi.moneyflow;

import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.chiibi.moneyflow.Constant.AMOUNT_COLUMN;
import static com.chiibi.moneyflow.Constant.ITEM_COLUMN;
import static com.chiibi.moneyflow.Constant.TYPE_COLUMN;

public class MainActivity extends AppCompatActivity {

    private ArrayList<HashMap> list;
    private int balance = 0;
    private TextView txtBalance;
    private AlertDialog.Builder builderSingle;
    public static MoneyflowDB moneyflowDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtBalance = findViewById(R.id.txtBalance);

        builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("Select One Name:-");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Edit");
        arrayAdapter.add("Delete");
        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        moneyflowDB = Room.databaseBuilder(this, MoneyflowDB.class, "TRANS_INFO").allowMainThreadQueries().build();
        ListView lview =  findViewById(R.id.record);
        populateList();

        ListViewAdapter adapter = new ListViewAdapter(this, list);
        lview.setAdapter(adapter);
        Constant.activity = this;
        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which==0){
                            Log.d("Check", "onClick: IN edit");

                            Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                            intent.putExtra("position", position-1);
                            Constant.Checkedit = 1;
                            startActivity(intent);

                        }else{

                            new AsyncTask<Void, Void, List<TransactionInfo>>() {
                                @Override
                                protected List<TransactionInfo> doInBackground(Void... voids) {
                                    List<TransactionInfo> result = moneyflowDB.transactionInfoDAO().allItem();
                                    return result;
                                }

                                @Override
                                protected void onPostExecute(List<TransactionInfo> usesInfos) {
                                    moneyflowDB.transactionInfoDAO().deletecolumn(usesInfos.get(position).getItem());
                                    reload();
                                }

                                @Override
                                protected void onProgressUpdate(Void... values) {
                                }
                            }.execute();
                            reload();
                        }
                    }
                });
                builderSingle.show();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab_addBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.fab_addBtn){
                    Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                    startActivity(intent);

                }
            }
        });
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    private void populateList() {
        list = new ArrayList<>();
        new AsyncTask<Void, Void, List<TransactionInfo>>() {
            @Override
            protected List<TransactionInfo> doInBackground(Void... voids) {
                List<TransactionInfo> result = moneyflowDB.transactionInfoDAO().allItem();
                return result;
            }

            @Override
            protected void onPostExecute(List<TransactionInfo> transactionInfo) {
                for (TransactionInfo items: transactionInfo){
                    HashMap temp = new HashMap();
                    if (items.getType().equals("income")) {
                        temp.put(TYPE_COLUMN, "+");

                        balance += Integer.valueOf(items.getAmount());

                    }else{
                        temp.put(TYPE_COLUMN, "-");
                        balance -= Integer.valueOf(items.getAmount());
                    }
                    temp.put(ITEM_COLUMN, items.getItem());
                    temp.put(AMOUNT_COLUMN, items.getAmount());
                    list.add(temp);
                }
                ChangeColor(balance, balance);

                txtBalance.setText(String.valueOf(balance));
            }

            @Override
            protected void onProgressUpdate(Void... values) {
            }
        }.execute();
    }

    private void ChangeColor(int allmoneyincome, int moneyleft) {
        if (allmoneyincome == 0 && moneyleft == 0){
            txtBalance.setTextColor(Color.parseColor("#db1a1a"));
        }else{
            int percent;
            percent = (moneyleft*100/allmoneyincome);
            if (percent > 50){
                txtBalance.setTextColor(Color.parseColor("#00B800"));
            }else if (percent >= 25 && percent <= 50){
                txtBalance.setTextColor(Color.parseColor("#ffdb00"));
            }else if(percent < 25){
                txtBalance.setTextColor(Color.parseColor("#db1a1a"));
            }
        }

    }
}

