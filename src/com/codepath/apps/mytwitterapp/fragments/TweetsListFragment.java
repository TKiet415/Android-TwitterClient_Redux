package com.codepath.apps.mytwitterapp.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.mytwitterapp.EndlessScrollListener;
import com.codepath.apps.mytwitterapp.MyTwitterApp;
import com.codepath.apps.mytwitterapp.R;
import com.codepath.apps.mytwitterapp.TweetsAdapter;
import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TweetsListFragment extends Fragment {

	PullToRefreshListView lvTweets;
	TweetsAdapter adapter;
	ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	List<Tweet> myList;
	//FragmentActivity fa;

	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inf.inflate(R.layout.fragment_tweets_list, parent, false);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// look at getting fields possibly here tmr...

		// Log.d("DEBUG", tweets.toString());

		adapter = new TweetsAdapter(getActivity(), tweets);
		lvTweets = (PullToRefreshListView) getActivity().findViewById(
				R.id.lvTweets);
		lvTweets.setAdapter(adapter);
		
		//getView();

		/*
		 * ActiveAndroid.beginTransaction(); try { for (Tweet tweetInstance :
		 * tweets) { tweetInstance.getUser().save(); tweetInstance.save(); }
		 * ActiveAndroid.setTransactionSuccessful(); } finally {
		 * ActiveAndroid.endTransaction(); }
		 */
		// super.onSuccess(arg0);
		lvTweets.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() { // TODO Auto-generated method stub
				fetchTimelineAsync(0);
			}
		});

		//if (isInternetAvailable(this)) {
			/*lvTweets.setOnScrollListener((new EndlessScrollListener() {

				@Override
				public void onLoadMore(int page, int totalItemsCount) {
					// Triggered only when new data needs to be appended to //
					// the // list
					// Add whatever code is needed to append new items to //
					// your //AdapterView
					customLoadMoreDataFromApi(totalItemsCount); // or
																// customLoadMoreDataFromApi(totalItemsCount);
				}
			}));*/
		//}
		
		lvTweets.setOnScrollListener((new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Triggered only when new data needs to be appended to //
				// the // list
				// Add whatever code is needed to append new items to //
				// your //AdapterView
				customLoadMoreDataFromApi(totalItemsCount); // or
															// customLoadMoreDataFromApi(totalItemsCount);
			}
		}));

	}
	
	protected void customLoadMoreDataFromApi(int totalItemsCount) {
		// Dummy method. Нет надобность для информация
	}

	protected void fetchTimelineAsync(int i) {
		// Dummy method. Нет надобность для информация
	}
	
	public long getMinId() {
		if (adapter.getCount() == 0)
			return -1;
		return adapter.getItem(
				adapter.getCount()
				).getUid();
	}
	
	public void pushTweet(Tweet tweet) {
		adapter.insert(tweet, 0);
	}
	
	public TweetsAdapter getAdapter() {
		return adapter;
	}
}
