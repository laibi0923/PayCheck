package com.paycheckeasy.www.paycheck.AccountManagment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.paycheckeasy.www.paycheck.R;

/*
 *  Create Date on 4 Nov 2018
 *  https://www.youtube.com/watch?v=lAGI6jGS4vs
 */

public class FireStorge_Adapter extends FirestoreRecyclerAdapter<Account_Model, Account_ViewHolder> {

    private Context context;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */


    public FireStorge_Adapter(@NonNull FirestoreRecyclerOptions<Account_Model> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final Account_ViewHolder holder, final int position, @NonNull Account_Model model) {

        final  Account_Model mAccount_Model = new Account_Model();

        if (getItemViewType(position) == R.layout.c2_ac_managment_recycleitem_card){

            /***        For Card View      ***/
            //mAccount_Model.setCreate_Date(model.getCreate_Date());
            mAccount_Model.setLast_Modify_Date(model.getLast_Modify_Date());
            mAccount_Model.setCard_Name(model.getCard_Name());
            mAccount_Model.setFirst_Num(model.getFirst_Num());
            mAccount_Model.setLast_Num(model.getLast_Num());
            mAccount_Model.setHolder_Name(model.getHolder_Name());
            mAccount_Model.setExpiry_Date(model.getExpiry_Date());
            mAccount_Model.setType(model.getType());
            mAccount_Model.setAmount(model.getAmount());
            mAccount_Model.setColor_Code(model.getColor_Code());
            mAccount_Model.setRemark(model.getRemark());

            holder.setCardValue(mAccount_Model);


            holder.Edit_Button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    Intent mIntent = new Intent(context, New_Card_Activity.class);

                    mIntent.putExtra("DatabaseRef_Key", getSnapshots().getSnapshot(position).getId());
                    mIntent.putExtra("Color_Code", mAccount_Model.getColor_Code());
                    mIntent.putExtra("Bank_Name", mAccount_Model.getCard_Name());
                    mIntent.putExtra("Bank_No_First", mAccount_Model.getFirst_Num());
                    mIntent.putExtra("Bank_No_Last", mAccount_Model.getLast_Num());
                    mIntent.putExtra("Holder_Name", mAccount_Model.getHolder_Name());
                    mIntent.putExtra("Expiry_Date", mAccount_Model.getExpiry_Date());
                    mIntent.putExtra("BankCard_Associations", mAccount_Model.getType());
                    mIntent.putExtra("Credit_Limit", mAccount_Model.getAmount());
                    mIntent.putExtra("Remark", mAccount_Model.getRemark());

                    context.startActivity(mIntent);
                }
            });

            holder.ViewAll_Button.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view)
                {
                    // TODO: Implement this method
                    Toast.makeText(context, "ViewAll onclick", Toast.LENGTH_SHORT).show();
                }
            });

            holder.NewRecord_Button.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view)
                {
                    // TODO: Implement this method
                    Toast.makeText(context, "NewRecord onclick", Toast.LENGTH_SHORT).show();
                }
            });

            holder.ViewLastest_Button.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view)
                {
                    // TODO: Implement this method
                    Toast.makeText(context, "ViewLastest onclick", Toast.LENGTH_SHORT).show();
                }
            });


        }else {

            /***        For Cash View      ***/
//            mAccount_Model.setCreated_TimeStamp(model.getCreated_TimeStampLong());
//            mAccount_Model.setLast_TimeStamp(model.getLast_TimeStampLong());
            mAccount_Model.setColor_Code(model.getColor_Code());
            mAccount_Model.setCard_Name(model.getCard_Name());
            mAccount_Model.setAmount(model.getAmount());
            mAccount_Model.setRemark(model.getRemark());

            holder.setCashValue(mAccount_Model);

            holder.ViewAll_Button.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view)
                {
                    // TODO: Implement this method
                    Toast.makeText(context, "ViewAll onclick", Toast.LENGTH_SHORT).show();
                }
            });

            holder.NewRecord_Button.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view)
                {
                    // TODO: Implement this method
                    Toast.makeText(context, "NewRecord onclick", Toast.LENGTH_SHORT).show();
                }
            });

            holder.ViewLastest_Button.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view)
                {
                    // TODO: Implement this method
                    Toast.makeText(context, "ViewLastest onclick", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }


    @Override
    public int getItemViewType(int position) {

        if(getItem(position).getType().equals("Cash")){
            Log.e("getitemview", "c3");
            return R.layout.c3_ac_managment_recycleitem_cash;

        }else{
            Log.e("getitemview", "c2");
            return R.layout.c2_ac_managment_recycleitem_card;
        }
    }

    @NonNull
    @Override
    public Account_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View ItemView = null;

        switch (viewType){

            case R.layout.c2_ac_managment_recycleitem_card:
                ItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.c2_ac_managment_recycleitem_card, parent, false);
                return new Account_ViewHolder(ItemView, "Card");

            case R.layout.c3_ac_managment_recycleitem_cash:
                ItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.c3_ac_managment_recycleitem_cash, parent, false);
                return new Account_ViewHolder(ItemView, "Cash");
        }

        return null;
    }


}
