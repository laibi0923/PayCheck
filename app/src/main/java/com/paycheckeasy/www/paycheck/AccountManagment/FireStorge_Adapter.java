package com.paycheckeasy.www.paycheck.AccountManagment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.paycheckeasy.www.paycheck.R;

/*
 *  Create Date on 4 Nov 2018
 *  https://www.youtube.com/watch?v=lAGI6jGS4vs
 */

public class FireStorge_Adapter extends FirestoreRecyclerAdapter<Account_Model, Account_ViewHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FireStorge_Adapter(@NonNull FirestoreRecyclerOptions<Account_Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Account_ViewHolder holder, int position, @NonNull Account_Model model) {

        if (getItemViewType(position) == R.layout.c2_ac_managment_recycleitem_card){

            // Card
            holder.Last_Modift_TextView.setText((int) model.getLast_TimeStampLong());
            holder.Cash_Name_EditText.setText(model.getCard_Name());
            holder.Bank_No_First_EditText.setText(model.getFirst_Num());
            holder.Bank_No_Last_EditText.setText(model.getLast_Num());
            holder.Holder_Name_EditText.setText(model.getHolder_Name());
            holder.Expiry_Date_EditText.setText(model.getExpiry_Date());

            String Card_Type = model.getType();
            String Amount = model.getAmount();
            int Color_Code = model.getColor_Code();
            int Create_Date = (int) model.getCreated_TimeStampLong();
            String Remark = model.getRemark();

        }else {

            // Cash
            holder.Last_Modift_TextView.setText((int) model.getLast_TimeStampLong());
            holder.Cash_Name_EditText.setText(model.getCard_Name());

        }

    }

    @Override
    public int getItemViewType(int position) {

        if(getItem(position).getType() != null && !getItem(position).getType().equals("Cash")){

            return R.layout.c2_ac_managment_recycleitem_card;

        }else if (getItem(position).getType().equals("Cash")) {

            return R.layout.c3_ac_managment_recycleitem_cash;
        }
        return position;
    }


    @NonNull
    @Override
    public Account_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View ItemView = null;

        if (viewType == R.layout.c2_ac_managment_recycleitem_card){

            ItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.c2_ac_managment_recycleitem_card, parent, false);
            return new Account_ViewHolder(ItemView, "Card");

        }else if (viewType == R.layout.c3_ac_managment_recycleitem_cash){

            ItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.c3_ac_managment_recycleitem_cash, parent, false);
            return new Account_ViewHolder(ItemView, "Cash");

        }
        return null;
    }


}
