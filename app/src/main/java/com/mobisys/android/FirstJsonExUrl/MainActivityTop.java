package com.mobisys.android.FirstJsonExUrl;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityTop extends Activity {

	// Within which the entire activity is enclosed
	DrawerLayout mDrawerLayout;
	
	// ListView represents Navigation Drawer
	ListView mDrawerList;
	
	// ActionBarDrawerToggle indicates the presence of Navigation Drawer in the action bar
	ActionBarDrawerToggle mDrawerToggle;	
	
	// Title of the action bar
	String mTitle="";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_top);
		

		mTitle = (String) getTitle();


		// Getting reference to the DrawerLayout
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


		mDrawerList = (ListView) findViewById(R.id.drawer_list);

		// Getting reference to the ActionBarDrawerToggle
		mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,
													R.drawable.ic_drawer,
													R.string.drawer_open,
													R.string.drawer_close){

			/** Called when drawer is closed */
            public void onDrawerClosed(View view) {
            	getActionBar().setTitle(mTitle);
            	invalidateOptionsMenu();




            }



            /** Called when a drawer is opened */
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle("Select");
                invalidateOptionsMenu();
                Log.i("Title1:", mTitle);
            }
			
		};

		// Setting DrawerToggle on DrawerLayout
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		// Creating an ArrayAdapter to add items to the listview mDrawerList
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getBaseContext(), 
					R.layout.drawer_list_item  , 
					getResources().getStringArray(R.array.rivers) 
				);
		
		// Setting the adapter on mDrawerList
		mDrawerList.setAdapter(adapter);
		
		// Enabling Home button
		getActionBar().setHomeButtonEnabled(true);
		
		// Enabling Up navigation
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// Setting item click listener for the listview mDrawerList
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			
			@Override
			public void onItemClick(AdapterView<?> parent,
							View view,
							int position,
							long id) {

				// Getting an array of rivers
				String[] rivers = getResources().getStringArray(R.array.rivers);
				
				//Currently selected river
				mTitle = rivers[position];
                Log.i("Title2:",mTitle);
				
				// Creating a fragment object
				PlanTrip rFragment = new PlanTrip();
				
				// Creating a Bundle object
				Bundle data = new Bundle();
				
				// Setting the index of the currently selected item of mDrawerList
				data.putInt("position", position);
				
				// Setting the position to the fragment
				rFragment.setArguments(data);
				
				// Getting reference to the FragmentManager
				FragmentManager fragmentManager  = getFragmentManager();
				
				// Creating a fragment transaction
				FragmentTransaction ft = fragmentManager.beginTransaction();
				
				// Adding a fragment to the fragment transaction
				ft.replace(R.id.content_frame, rFragment);
				
				// Committing the transaction
				ft.commit();
				
				// Closing the drawer
				mDrawerLayout.closeDrawer(mDrawerList);
                if("Search".equals(rivers[position])){
                    search();
                }
				
			}
		});



    }
    ArrayAdapter<String> adapter;
     ListView lv;
    public void search(){

        ActionBar actionBar = getActionBar();


        actionBar.setCustomView(R.layout.actionbar_view);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
        @Override
        public void run() {
        final EditText search;
        ActionBar actionBar = getActionBar();


        actionBar.setCustomView(R.layout.actionbar_view);
        search = (EditText) actionBar.getCustomView().findViewById(R.id.searchfield);
        lv = (ListView) findViewById(R.id.list_view);
        Log.d(Constants.CITIES[10]+"","city");

        adapter = new ArrayAdapter<String>(getApplication(), R.layout.list_item, R.id.lblListItem, Constants.CITIES);
         lv.setAdapter(adapter);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                MainActivityTop.this.adapter.getFilter().filter(cs);
                Log.i("vikash test","");
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
                 lv.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                //  Toast.makeText(getApplication(),"afterTextChanged",Toast.LENGTH_LONG).show();
            }

            // show1();

        });





        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
                TextView texte=  (TextView)view.findViewById(R.id.lblListItem) ;
                String ss= texte.getText().toString();

                search.setText(ss+"", TextView.BufferType.EDITABLE);


                lv.setVisibility(View.GONE);

                Toast.makeText(getApplication(),""+ss,Toast.LENGTH_LONG).show();

            }
        });

        }
        }, 1000);

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);

    }




	
	
	 @Override
	 protected void onPostCreate(Bundle savedInstanceState) {
		 super.onPostCreate(savedInstanceState);	     
	     mDrawerToggle.syncState();	
	 }
	
	/** Handling the touch event of app icon */
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
          return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
	
	/** Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main_activity1, menu);
		return true;
	}

}
