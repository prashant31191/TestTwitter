package rahul.twitter.demo;

import android.content.SharedPreferences;
import android.net.Uri;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;

public class Global {

	
	public static OAuthConsumer consumerNew = null;
	public  static OAuthProvider providerNew = null;
	public static SharedPreferences prefsNew;
	public static Uri uriNew;
	public static String RESPONSETEXT;
}
