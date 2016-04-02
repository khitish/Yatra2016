package com.mobisys.android.FirstJsonExUrl;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Hashtable;
public class FirstJsonExUrlActivity extends Activity  {
    /** Called when the activity is first created. */
	public final static String BaseUrl="http://manojpusa.club/yatra_app";
    public static String json;
    public  static Hashtable ht;
    public ListView listv;
 	public class GetDeptAyncTask extends AsyncTask<Hashtable<String,String>,Void,String> {

        @Override
        protected String doInBackground(Hashtable<String, String>... params) {
           ht = params[0];

               json = HelperHttp.getJSONResponseFromURL(BaseUrl + "/login.php", ht);

            if (json != null) {
                Log.i(BaseUrl + "vikash request","");

                Log.i("vikash:00","json exist....");
             //   Toast.makeText(getApplication(),"login",Toast.LENGTH_LONG).show();
                Log.i("vikash:test", ""+json);


                if("authenticatedUser".equals(json)){
                    Log.d("login main","");
                    Bundle b = new Bundle();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtras(b);
                    startActivity(intent);

                }else{
                    Log.d("not login","");

                }
//                parseJsonString(deptList, json);
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
			   Toast.makeText(FirstJsonExUrlActivity.this, "Success", Toast.LENGTH_SHORT).show();
//			   DeptArrayAdapter adapter=new DeptArrayAdapter(FirstJsonExUrlActivity.this,R.id.text1,deptList);
//                  listv=(ListView)findViewById(R.id.lv);
//                  listv.setAdapter(adapter);
//                 // Log.i("vikash="+listv,"gupta1");Json Response

               }
			  else{
                  Toast.makeText(FirstJsonExUrlActivity.this, "Fail", Toast.LENGTH_SHORT).show();
//
              }
		  }
    }
    EditText Email,Password;
    String name="",password="";
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Email=(EditText)findViewById(R.id.Email_Id);
        Password=(EditText)findViewById(R.id.PassWord);




    }
    public void login(View v){

//        Bundle b = new Bundle();
//        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//        intent.putExtras(b);
//        startActivity(intent);


        name=Email.getText().toString();
        password=Password.getText().toString();
        Log.d("test vds"+name,password);
        executeAsyncTask();
    }
    public void Sign_up(View v){
        Bundle b = new Bundle();
        Intent intent = new Intent(getApplicationContext(),Sign_Up.class);
        intent.putExtras(b);
        startActivity(intent);

    }




    private void executeAsyncTask(){
		  Hashtable<String,String> ht=new Hashtable<String,String>();
		  GetDeptAyncTask async=new GetDeptAyncTask();
		 ht.put("username",name+"");
        ht.put("password",password+"");

        Hashtable[] ht_array={ht};

		  async.execute(ht_array);
		 }
    public void show(View v){



    }






}