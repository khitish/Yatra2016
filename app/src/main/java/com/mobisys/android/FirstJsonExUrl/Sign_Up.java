package com.mobisys.android.FirstJsonExUrl;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Hashtable;


public class Sign_Up extends Activity {
    public final static String BaseUrl="http://manojpusa.club/yatra_app";
    public static String json;
    public  static Hashtable ht;
    public class GetDeptAyncTask extends AsyncTask<Hashtable<String,String>,Void,String> {

        @Override
        protected String doInBackground(Hashtable<String, String>... params) {
            ht = params[0];

            json = HelperHttp.getJSONResponseFromURL(BaseUrl + "/signup.php?", ht);

            if (json != null) {
//                Log.i(BaseUrl + "vikash request", "");
//
//                Log.i("vikash:00","json exist....");
//                //   Toast.makeText(getApplication(),"login",Toast.LENGTH_LONG).show();
//                Log.i("vikash:test", ""+json);
                if("authenticatedUser".equals(json)){
                    Log.d("login main","");
                    Bundle b = new Bundle();
                    Intent intent = new Intent(getApplicationContext(),FragmentActivity.class);
                    intent.putExtras(b);
                    startActivity(intent);

                }else{
                    Log.d("not login","");

                }
            }
            else {
                // Toast.makeText(getApplication(),"no login",Toast.LENGTH_LONG).show();
                Log.i("vikash:00", "json null allo...."+ json);
                return "Invalid Company Id";
            }
            return "SUCCESS";
        }

        //
        @Override
        public void onPostExecute(String result){

            if(result=="SUCCESS")
            {
                Toast.makeText(Sign_Up.this, "LogIn", Toast.LENGTH_SHORT).show();
//			   DeptArrayAdapter adapter=new DeptArrayAdapter(FirstJsonExUrlActivity.this,R.id.text1,deptList);
//                  listv=(ListView)findViewById(R.id.lv);
//                  listv.setAdapter(adapter);
//                 // Log.i("vikash="+listv,"gupta1");Json Response
                Bundle b = new Bundle();
                Intent intent = new Intent(Sign_Up.this,FirstJsonExUrlActivity.class);
                intent.putExtras(b);
                startActivity(intent);

            }
            else{
                Toast.makeText(Sign_Up.this, "LogIn Fail...", Toast.LENGTH_SHORT).show();
//
            }
        }
    }

    EditText email,password,repassword,name;
    String Email,Password,Repassword,Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        email=(EditText)findViewById(R.id.Email);
        password=(EditText)findViewById(R.id.password);
        repassword=(EditText)findViewById(R.id.Repassword);
        name=(EditText)findViewById(R.id.name);


    }


    public void signup(View v){
        Email=email.getText().toString();
        Password=password.getText().toString();
        Name=name.getText().toString();
        Repassword=repassword.getText().toString();

        if(Password.equals(Repassword)){
            executeAsyncTask();
        }else{
            Toast.makeText(getApplicationContext(),"PassWord No Match...",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void executeAsyncTask(){
        Hashtable<String,String> ht=new Hashtable<String,String>();
        GetDeptAyncTask async=new GetDeptAyncTask();
        ht.put("username",Email+"");
        ht.put("password",Password+"");
        ht.put("name",Name);

        Hashtable[] ht_array={ht};

        async.execute(ht_array);
    }
}
