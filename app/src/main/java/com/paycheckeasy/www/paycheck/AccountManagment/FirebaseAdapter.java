package com.paycheckeasy.www.paycheck.AccountManagment;
import android.content.*;
import android.support.v7.widget.RecyclerView;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.firebase.ui.database.*;
import com.google.firebase.database.*;
import com.paycheckeasy.www.paycheck.*;

import com.paycheckeasy.www.paycheck.R;

public class FirebaseAdapter extends FirebaseRecyclerAdapter<Account_Model, Account_ViewHolder>
{
	private Context context;
	private Query ref;

	public FirebaseAdapter(Class<Account_Model> model, int modelLayout, Class<Account_ViewHolder> viewHolder, Query ref, Context context){
		super(model, modelLayout, viewHolder, ref);
		this.context = context;
		this.ref = ref;
	}


	@Override
	protected void populateViewHolder(final Account_ViewHolder viewHolder, final Account_Model model, final int position) {

		// TODO: Implement this method
		final Account_Model mAccount_Model = new Account_Model();

		if(getItemViewType(position) == R.layout.c2_ac_managment_recycleitem_card){

			mAccount_Model.setPosition(position);
			mAccount_Model.setCreated_TimeStamp(model.getCreated_TimeStampLong());
			mAccount_Model.setLast_TimeStamp(model.getLast_TimeStampLong());
			mAccount_Model.setColor_Code(model.getColor_Code());
			mAccount_Model.setCard_Name(model.getCard_Name());
			mAccount_Model.setFirst_Num(model.getFirst_Num());
			mAccount_Model.setLast_Num(model.getLast_Num());
			mAccount_Model.setHolder_Name(model.getHolder_Name());
			mAccount_Model.setExpiry_Date(model.getExpiry_Date());
			mAccount_Model.setType(model.getType());
			mAccount_Model.setAmount(model.getAmount());
			mAccount_Model.setRemark(model.getRemark());

			viewHolder.setCardValue(mAccount_Model);

			viewHolder.Edit_Button.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view) {

					Intent mIntent = new Intent(context, New_Card_Activity.class);

					mIntent.putExtra("DatabaseRef_Key", getRef(viewHolder.getPosition()).getKey());
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

			viewHolder.ViewAll_Button.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View view)
				{
					// TODO: Implement this method
					Toast.makeText(context, "ViewAll onclick", Toast.LENGTH_SHORT).show();
				}
			});

			viewHolder.NewRecord_Button.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View view)
				{
					// TODO: Implement this method
					Toast.makeText(context, "NewRecord onclick", Toast.LENGTH_SHORT).show();
				}
			});

			viewHolder.ViewLastest_Button.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View view)
				{
					// TODO: Implement this method
					Toast.makeText(context, "ViewLastest onclick", Toast.LENGTH_SHORT).show();
				}
			});


		}else{

			mAccount_Model.setPosition(position);
			mAccount_Model.setCreated_TimeStamp(model.getCreated_TimeStampLong());
			mAccount_Model.setLast_TimeStamp(model.getLast_TimeStampLong());
			mAccount_Model.setColor_Code(model.getColor_Code());
			mAccount_Model.setCard_Name(model.getCard_Name());
			mAccount_Model.setAmount(model.getAmount());
			mAccount_Model.setRemark(model.getRemark());

			viewHolder.setCashValue(mAccount_Model);

			viewHolder.ViewAll_Button.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View view)
				{
					// TODO: Implement this method
					Toast.makeText(context, "ViewAll onclick", Toast.LENGTH_SHORT).show();
				}
			});

			viewHolder.NewRecord_Button.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View view)
				{
					// TODO: Implement this method
					Toast.makeText(context, "NewRecord onclick", Toast.LENGTH_SHORT).show();
				}
			});

			viewHolder.ViewLastest_Button.setOnClickListener(new View.OnClickListener(){

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

		if(getItem(position).getType() != null && !getItem(position).getType().equals("Cash")){

			return R.layout.c2_ac_managment_recycleitem_card;

		}else if (getItem(position).getType().equals("Cash")) {

			return R.layout.c3_ac_managment_recycleitem_cash;
		}
		return position;
	}


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
