package rahul.twitter.demo;

import twitter.Constants;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class UpdateStatus extends Activity{
	
	String user_token, user_secret,response_text;
	private AccessToken accessToken = new AccessToken(user_token,user_secret);
	private Twitter twitter;
	String tag = "Update Status";
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Bundle bun = getIntent().getExtras();
		
		Log.i(tag, "here");
		
		if (bun == null) return;
        
		user_token = bun.getString("user_token");
		user_secret = bun.getString("user_secret");
		response_text = bun.getString("response_text");
		Log.i(tag, "response_text::"+response_text);
		Log.i(tag, "user_secret::"+user_secret);
		Log.i(tag, "response_text::"+response_text);
		
		postOn();
	}

	
	public void postOn(){
		try {
			accessToken = new AccessToken(user_token,user_secret);
	    	twitter = new TwitterFactory().getOAuthAuthorizedInstance(Constants.CONSUMER_KEY,Constants.CONSUMER_SECRET,accessToken);
	    	 twitter.getAuthorization();
	    	 twitter.setOAuthAccessToken(accessToken);
	    	 Log.i(tag, "authenticated::"+twitter.isOAuthEnabled());
	    	 	Status status = twitter.updateStatus("Sharing message on Twitter");
	    	 //Status status = twitter.updateStatus(" My second Status Update");
	 			Log.i(tag, "Status id::"+status.getId());
	 			
	 			Toast.makeText(UpdateStatus.this, "Status id::"+status.getId(), Toast.LENGTH_SHORT).show();
	 				 
	    	
	    	
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

