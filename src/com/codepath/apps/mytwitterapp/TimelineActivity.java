package com.codepath.apps.mytwitterapp;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.activeandroid.util.Log;
import com.codepath.apps.mytwitterapp.fragments.HomeTimelineFragment;
import com.codepath.apps.mytwitterapp.fragments.MentionsFragment;
import com.codepath.apps.mytwitterapp.models.Tweet;
import com.codepath.apps.mytwitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends FragmentActivity implements TabListener {

	//String name;
	User myUser;
	HomeTimelineFragment htf;
	MentionsFragment mtf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		//lvTweets = (PullToRefreshListView) findViewById(R.id.lvTweets);
		//fragmentTweets = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentTweets);

		setupNavigationTabs();
		loadMyProfile();
	}
	
	public void loadMyProfile() {
		MyTwitterApp.getRestClient().getMyInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject arg1) {
				// TODO Auto-generated method stub
				myUser = User.fromJson(arg1);
			}
		});
	}

	private void setupNavigationTabs() {
		// TODO Auto-generated method stub
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Tab tabHome = actionBar.newTab().setText("Home").setTag("HomeTimelineFragment").setIcon(R.drawable.ic_home).setTabListener(this);
		
		Tab tabMentions = actionBar.newTab().setText("Mentions").setTag("MentionsTimelineFragment").setIcon(R.drawable.ic_mentions).setTabListener(this);
		
		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		actionBar.selectTab(tabHome);
		
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	public static boolean isInternetAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
	}

	public void onCompose(MenuItem mi) {
		Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
		startActivityForResult(i, 2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		//super.onActivityResult(arg0, arg1, arg2);
		if (arg1 == RESULT_OK) {
			JSONObject jsonTweet;
			try {
				jsonTweet = new JSONObject(arg2.getExtras().getString("jsonTweet"));
			} catch (JSONException e) {
				e.printStackTrace();
				return;
			}
			htf.pushTweet(Tweet.fromJson(jsonTweet));
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		if (tab.getTag() == "HomeTimelineFragment") {
			// set the fragment in the framglayout to the home timeline
			if (htf == null)
				htf = new HomeTimelineFragment();
			fts.replace(R.id.frame_container, htf);
		} else {
			// set the fragment in the framelayout to the mentions timeline
			if (mtf == null)
				mtf = new MentionsFragment();
			fts.replace(R.id.frame_container, mtf);
		}
		fts.commit();
	}
	
	public void onProfileView(MenuItem mi) {
		Intent i = new Intent(this, ProfileActivity.class);
		//Log.d("DBG", name);
		i.putExtra("user", myUser);
		startActivity(i);
	}

	/*public void getUserInfo(View v) {
		//ProfileActivity pa = new ProfileActivity();
		//User user = new User();
		//user.getScreenName();
		//Log.d("DEBUG", pa.getUserName().toString());Intent i = new Intent(this, ProfileActivity.class);
		Intent i = new Intent(this, ProfileActivity.class);
		//Log.d("DEBUG", arg1)
		//Log.d("DBG", name);
		//i.putExtra("userinfo", name);
		startActivity(i);
	}*/

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
