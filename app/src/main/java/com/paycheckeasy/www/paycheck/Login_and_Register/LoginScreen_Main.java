package com.paycheckeasy.www.paycheck.Login_and_Register;

import android.content.*;
import android.os.*;
import android.support.annotation.NonNull;
import android.support.design.widget.*;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.support.v7.app.*;
import android.util.*;
import com.google.firebase.auth.*;
import com.paycheckeasy.www.paycheck.*;
import com.paycheckeasy.www.paycheck.R;

import java.util.*;
import android.database.*; 


public class LoginScreen_Main extends AppCompatActivity{

    private TabLayout mTabLayout;
	
    private ViewPager mViewPager;

    private SignIn_Fragment mSignIn = new SignIn_Fragment();
	
    private Register_Fragment mSignUp = new Register_Fragment();

    private String SignIn_Title;
    private String Register_Title ;

    private String tab_title[];
	
//	public FirebaseAuth mFirebaseAuth;
//	private FirebaseAuth.AuthStateListener mAuthStateListener;
//	public FirebaseUser mFirebaseUser;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);

        setContentView(R.layout.b1_login_screen_main);

        Find_View();

	}

    private void Find_View(){

        mTabLayout = (TabLayout) findViewById(R.id.login_screen_tablayout);
		
        mTabLayout.addTab(mTabLayout.newTab().setText(getResources().getString(R.string.signin_title)));
		
        mTabLayout.addTab(mTabLayout.newTab().setText(getResources().getString(R.string.register_title)));
		
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
		
        mViewPager = (ViewPager) findViewById(R.id.login_screen_viewpager);
		
		mViewPager.setOffscreenPageLimit(2);
		
        setViewPager();
		
        mTabLayout.setupWithViewPager(mViewPager);
    }


    private void setViewPager(){

        List<Fragment> fragmentList = new ArrayList<Fragment>();
		
        fragmentList.add(mSignIn);
		
        fragmentList.add(mSignUp);
		
        Login_ViewPager_Adapter myFragmentAdapter = new Login_ViewPager_Adapter(this.getSupportFragmentManager(), fragmentList);
		
        mViewPager.setAdapter(myFragmentAdapter);
    }



    public class Login_ViewPager_Adapter extends FragmentPagerAdapter{

        private List<Fragment> fragmentList;
       // private List<String> titleList;

        public Login_ViewPager_Adapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
           // this.titleList = titleList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            SignIn_Title = getResources().getString(R.string.signin_title);
            Register_Title = getResources().getString(R.string.register_title);
            tab_title = new String[]{SignIn_Title, Register_Title};
            return tab_title[position];
        }
    }

	
	

}
