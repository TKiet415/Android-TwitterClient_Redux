package com.codepath.apps.mytwitterapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mytwitterapp.fragments.UserTimelineFragment;
import com.codepath.apps.mytwitterapp.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {
	
	TextView tvName, tvTagline, tvFollowers, tvFollowing;
	ImageView ivProfileImage;
	String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		
		Intent i = getIntent();
		User usr = (User) i.getSerializableExtra("user");
		populateProfileHeader(usr);
		
		//name = (String) getIntent().getStringExtra("userinfo");
		/*MyTwitterApp.getRestClient().getMyInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				// TODO Auto-generated method stub
				User u = User.fromJson(json);
				getActionBar().setTitle("@" + u.getScreenName());
				populateProfileHeader(usr);
			}
		});*/
		
		UserTimelineFragment utf = UserTimelineFragment.newInstance(5, name);
		Bundle b = new Bundle();
		b.putSerializable("user", usr);
		utf.setArguments(b);
		
		ft.replace(R.id.frame_profile_container, utf);
		ft.commit();
	}
	
	private void populateProfileHeader(User user) {
		// TODO Auto-generated method stub
		try {
			tvName = (TextView) findViewById(R.id.tvName);
			tvTagline = (TextView) findViewById(R.id.tvTagline);
			tvFollowers = (TextView) findViewById(R.id.tvFollowers);
			tvFollowing = (TextView) findViewById(R.id.tvFollowing);
			ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
			tvName.setText(user.getName());
			tvTagline.setText(user.getTagline());
			tvFollowers.setText(user.getFollowersCount() + " followers");
			tvFollowing.setText(user.getFriendsCount() + " following");
			ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
/*
	@Override
	public void onRssItemSelected(String link) {
		// TODO Auto-generated method stub
		
	}*/
}
