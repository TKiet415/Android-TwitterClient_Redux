package com.codepath.apps.mytwitterapp.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.mytwitterapp.EndlessScrollListener;
import com.codepath.apps.mytwitterapp.R;
import com.codepath.apps.mytwitterapp.TweetsAdapter;
import com.codepath.apps.mytwitterapp.models.Tweet;

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
		adapter = new TweetsAdapter(getActivity(), tweets);
		lvTweets = (PullToRefreshListView) v.findViewById(
				R.id.lvTweets);
		lvTweets.setAdapter(adapter);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

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
	
	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		// TODO Auto-generated method stub
		super.startActivityForResult(intent, requestCode);
	}
	
	protected void customLoadMoreDataFromApi(int totalItemsCount) {
		// Dummy method. Нет надобности в информации
	}

	public void fetchTimelineAsync(int i) {
		// Dummy method. Нет надобности в информации
	}
	
	public long getMinId() {
		if (adapter.getCount() == 0)
			return -1;
		return adapter.getItem(
				adapter.getCount() - 1
				).getUid();
	}
	
	public void pushTweet(Tweet tweet) {
		getAdapter().insert(tweet, 0);
	}
	
	public TweetsAdapter getAdapter() {
		return adapter;
	}
}
