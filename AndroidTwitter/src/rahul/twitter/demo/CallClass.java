package rahul.twitter.demo;

import twitter.PrepareRequestTokenActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CallClass extends Activity{
    

    private Button btn_test;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        btn_test = (Button)findViewById(R.id.btntest);
        
        btn_test.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(CallClass.this, PrepareRequestTokenActivity.class);
                startActivity(intent);
            }
        });
        
    }
    
}
