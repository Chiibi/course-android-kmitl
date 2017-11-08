package com.chiibi.moneyflow;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.List;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {


    private Button Save_bt;
    private EditText item;
    private EditText amount;
    private RadioGroup radioBtnGroup;
    private int position;
    private int SQL_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        radioBtnGroup = findViewById(R.id.radioGroup);
        Save_bt = findViewById(R.id.saveBtn);
        item = findViewById(R.id.desc_et);
        amount = findViewById(R.id.amount_et);
        Save_bt.setOnClickListener(this);

        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);
        Log.d("addcheckposition", String.valueOf(position));

        if (Constant.Checkedit == 1){
            new AsyncTask<Void, Void, List<TransactionInfo>>() {
                @Override
                protected List<TransactionInfo> doInBackground(Void... voids) {
                    List<TransactionInfo> result = MainActivity.moneyflowDB.transactionInfoDAO().allItem();
                    return result;
                }

                @Override
                protected void onPostExecute(List<TransactionInfo> transactionInfos) {
                    item.setText(transactionInfos.get(position).getItem());
                    if(transactionInfos.get(position).getType().equals("income")) {
                        radioBtnGroup.check(R.id.incomeRBtn);
                    }else{
                        radioBtnGroup.check(R.id.outcomeRBtn);
                    }
                    amount.setText(String.valueOf(transactionInfos.get(position).getAmount()));
                    SQL_ID = transactionInfos.get(position).getId();
                    Log.d("check ID", "onPostExecute: " + transactionInfos.get(position).getId());
                }
            }.execute();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.saveBtn) {

            if (Constant.Checkedit == 1){
                new AsyncTask<Void, Void, List<TransactionInfo>>() {
                    @Override
                    protected List<TransactionInfo> doInBackground(Void... voids) {
                        List<TransactionInfo> result = MainActivity.moneyflowDB.transactionInfoDAO().allItem();
                        return result;
                    }

                    @Override
                    protected void onPostExecute(List<TransactionInfo> transactionInfos) {
                        MainActivity.moneyflowDB.transactionInfoDAO().UpdateColumn(getType(),
                                item.getText().toString(),
                                Integer.valueOf(amount.getText().toString()), SQL_ID);
                    }
                }.execute();

            }else{
                final TransactionInfo transactionInfo = new TransactionInfo();
                transactionInfo.setItem(item.getText().toString());
                transactionInfo.setAmount(Integer.valueOf(amount.getText().toString()));
                transactionInfo.setType(getType());
                new AsyncTask<Void, Void, List<TransactionInfo>>() {
                    @Override
                    protected List<TransactionInfo> doInBackground(Void... voids) {
                        List<TransactionInfo> result = MainActivity.moneyflowDB.transactionInfoDAO().allItem();
                        return result;
                    }

                    @Override
                    protected void onPostExecute(List<TransactionInfo> transactionInfos) {
                        MainActivity.moneyflowDB.transactionInfoDAO().Insert(transactionInfo);
                    }
                }.execute();
            }
        }
        Constant.Checkedit = 0;
        Intent intent = new Intent(this, MainActivity.class);
        Constant.activity.finish();
        startActivity(intent);
        finish();
    }

    private String getType() {
        RadioGroup radioBtnGroup = findViewById(R.id.radioGroup);
        int selectedId = radioBtnGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.incomeRBtn) return "income";
        else if (selectedId == R.id.outcomeRBtn) return "expense";
        else return null;
    }
}
