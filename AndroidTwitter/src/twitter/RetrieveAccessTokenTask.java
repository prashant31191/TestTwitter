package twitter;



import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import rahul.twitter.demo.Global;
import rahul.twitter.demo.UpdateStatus;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.http.AccessToken;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void>
{
	
	final String TAG = getClass().getName();
	private ProgressDialog pd;
	private Context	context;
	private OAuthProvider provider;
	private OAuthConsumer consumer;
	private SharedPreferences prefs;
	String token; 
	String secret;
	String responseText;
	public RetrieveAccessTokenTask(Context context, OAuthConsumer consumer,OAuthProvider provider, SharedPreferences prefs) {
		this.context = context;
		this.consumer = consumer;
		this.provider = provider;
		this.prefs=prefs;
	}

	@Override
	protected void onPreExecute() 
	{
	
		pd = ProgressDialog.show(context, "","Loading...",true);
	    pd.setCancelable(true);
	    pd.setCanceledOnTouchOutside(false);
	}


	@Override
	protected Void doInBackground(Uri... params) 
	{
		final Uri uri = params[0];


		final String oauth_verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);
		Log.i(TAG, "Oauth Verifier:::"+oauth_verifier);
		
		
			
		try {
			provider.retrieveAccessToken(consumer, oauth_verifier);
			
			

			
						
			final Editor edit = prefs.edit();
			
			edit.putString(OAuth.OAUTH_TOKEN, consumer.getToken());
			edit.putString(OAuth.OAUTH_TOKEN_SECRET, consumer.getTokenSecret());
			
			edit.commit();

			token = prefs.getString(OAuth.OAUTH_TOKEN, "");
			secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");

			consumer.setTokenWithSecret(token, secret);
			String screen_name = executeAfterAccessTokenRetrieval();
			
	        
	        HttpGet get = new HttpGet(screen_name);
	        HttpClient client = new DefaultHttpClient();
	        HttpResponse response = client.execute(get);
	        HttpEntity entity = response.getEntity();

	        responseText = EntityUtils.toString(entity);
	        Log.i(TAG,"Response---"+responseText);
			Global.RESPONSETEXT = responseText;
	
			Log.i(TAG, "OAuth - Access Token Retrieved");
			

		} catch (Exception e) {
			Log.e(TAG, "OAuth - Access Token Retrieval Error", e);
		}
		return null;
	}
	
	
	@Override
	protected void onPostExecute(Void result) 
	{
		
		Intent intent = new Intent(context,UpdateStatus.class);
		intent.putExtra("user_token", token);
		intent.putExtra("user_secret", secret);
		intent.putExtra("response_text", responseText);
		context.startActivity(intent);
		pd.dismiss();
	}

	
	private String executeAfterAccessTokenRetrieval() 
	{
		User user = null;
		try 
		{
			String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
			String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
			
			AccessToken a = new AccessToken(token,secret);
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
			
			twitter.setOAuthAccessToken(a);
	        
	        Log.d("NAME : ",  ""+twitter.showUser(twitter.getScreenName()).getName());
	            
	   
	        user = twitter.showUser(twitter.getScreenName());
	        
	        Log.d("SCREEN NAME IS ",  user.getScreenName());
	        
	        return user.getScreenName();
		} 
		catch (Exception e) {
			Log.e(TAG, "OAuth - Error sending to Twitter", e);
		}
		return user.getScreenName();
	}
}



/*
 * String accessToken = twitterConnection.getOAuthAccessToken
		    (requestToken,editPinCode.getText().toString());

		oHelper.storeAccessToken(accessToken);

		Log.i("Access Token:", accessToken.getToken());

		Log.i("Access Secret:", accessToken.getTokenSecret());

		long userID = accessToken.getUserId();

		User user = twitterConnection.showUser(userID);

		user.getName();

 * */


