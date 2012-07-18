package twitter;



import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import rahul.twitter.demo.Global;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

public class PrepareRequestTokenActivity extends Activity 
{
	private OAuthConsumer consumer = null;
	private OAuthProvider provider = null;
	//private String callback = "http://www.android.com";
	//private static final Uri CALLBACK_URI = Uri.parse("http://www.android.com");
	private String TAG = "=======PrepareRequestTokenActivity==========";
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		try {
	   		consumer = new CommonsHttpOAuthConsumer(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
	   		Log.e(TAG, "Consumer created");
	   	    provider = new CommonsHttpOAuthProvider(Constants.REQUEST_URL,Constants.ACCESS_URL,Constants.AUTHORIZE_URL);
	   	    
	   	    provider.setOAuth10a(true);
	   	    
	   	    Global.consumerNew = consumer;
	   	    Global.providerNew = provider;
	   	    
	   	 Log.e(TAG, "Provider created");
	   	} catch (Exception e) {
	   		Log.e(TAG, "Error creating consumer / provider",e);
		}
	 
	       Log.i(TAG, "Starting task to retrieve request token.");
	       new OAuthRequestTokenTask(this,consumer,provider).execute();
	       
	       
	       
	       
	       
	       
		//new OAuthRequestTokenTask(this,consumer,provider).execute();
	   	/*try {
			Log.i(TAG, "Retrieving request token from Google servers");
			
			final String url = provider.retrieveRequestToken(consumer, callback);
			
			
			
			Log.i(TAG, "Popping a browser with the authorize URL : " + url);
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url)).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | 
						Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_FROM_BACKGROUND);
			startActivity(intent);

			
		} catch (Exception e) {
			Log.e(TAG, "Error during OAUth retrieve request token", e);
		}*/
	
	}
	
	@Override
	protected void onNewIntent(Intent intent) 
	{
		super.onNewIntent(intent);
		Log.i(TAG, "here onNewIntent");
		Global.prefsNew = PreferenceManager.getDefaultSharedPreferences(this);
		
		final Uri uri = intent.getData();
		Global.uriNew = uri;
		if (uri != null && uri.getScheme().equals(Constants.OAUTH_CALLBACK_SCHEME)) {
			Log.i(TAG, "Callback received : " + uri);
			Log.i(TAG, "Retrieving Access Token");
			
			//startActivity(new Intent(PrepareRequestTokenActivity.this, Twitter_Followers.class));
			
			new RetrieveAccessTokenTask(this,consumer,provider,Global.prefsNew).execute(uri);
		
			
		}
	}

}
