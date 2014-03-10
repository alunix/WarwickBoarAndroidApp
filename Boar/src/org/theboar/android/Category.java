package org.theboar.android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;

public class Category {
	
	public static final int NEWS = 0;
	public static final int COMMENT = 1;
	public static final int FEATURES = 2;
	public static final int LIFESTYLE = 3;
	public static final int MONEY = 4;
	public static final int ARTS = 5;
	public static final int BOOKS = 6;
	public static final int FILM = 7;
	public static final int GAMES = 8;
	public static final int MUSIC = 9;
	public static final int SCI_TECH = 10;
	public static final int TRAVEL = 11;
	public static final int TV = 12;
	public static final int SPORT = 13;
	public static final int PHOTOGRAPHY = 14;
	
	private static final String[] CATEGORY_STRINGS = { "News", "Comment", "Features", "Lifestyle", "Money",
		"Arts", "Books", "Film", "Games", "Music", "Science-Tech", "Travel", "TV", "Sport", "Photography"};
	
	
	public static final int SOCIAL = 40;
	
	public static final int OTHER = 80;
	
	public static String getCategoryNameShort(int categoryId){
		if(categoryId == SCI_TECH){
			return "Sci+Tech";
		}
		if (categoryId >= 0 && categoryId <= 14){
			return CATEGORY_STRINGS[categoryId];
		}
		else return "Other";
	}
	
	
	public static int getCategoryColour(int categoryId, Resources res){
		switch (categoryId){
			case NEWS: return res.getColor(R.color.news);
			case COMMENT: return res.getColor(R.color.comment);
			case FEATURES: return res.getColor(R.color.features);
			case LIFESTYLE: return res.getColor(R.color.lifestyle);
			case MONEY: return res.getColor(R.color.money);
			case ARTS: return res.getColor(R.color.arts);
			case BOOKS: return res.getColor(R.color.books);
			case FILM: return res.getColor(R.color.film);
			case GAMES: return res.getColor(R.color.games);
			case MUSIC: return res.getColor(R.color.music);
			case SCI_TECH: return res.getColor(R.color.science_tech);
			case TRAVEL: return res.getColor(R.color.travel);
			case TV: return res.getColor(R.color.tv);
			case SPORT: return res.getColor(R.color.sport);
			case PHOTOGRAPHY: return res.getColor(R.color.photography);
		}
		return 0xff000000;
	}
	
	public static int getCategoryColour(int catagoryId){
		switch (catagoryId){
			case NEWS: return 0xffc4161c;
			case COMMENT: return 0xffffc20e;
			case FEATURES: return 0xff8dc63f;
			case LIFESTYLE: return 0xffff00ff;
			case MONEY: return 0xfff26522;
			case ARTS: return 0xffF26522;
			case BOOKS: return 0xff008fd5;
			case FILM: return 0xff790020;
			case GAMES: return 0xff0096a6;
			case MUSIC: return 0xffed1c24;
			case SCI_TECH: return 0xff00abbd;
			case TRAVEL: return 0xff1e196a;
			case TV: return 0xff92278f;
			case SPORT: return 0xff00a651;
			case PHOTOGRAPHY: return 0xff777777;
		}
		return 0xff000000;
	}
	
	/* Takes a JSON Object for an article and returns the category ID */
	public static int parseCategoryID(JSONObject story){
		try {
			JSONArray cats = story.getJSONArray("categories");
			for(int i = 0; i < cats.length(); i++){
				String catSlug = cats.getJSONObject(i).getString("slug");
				Log.d("Category", "Category: "+ catSlug);
				for(int j = 0; j < CATEGORY_STRINGS.length; j++){
					if(catSlug.equalsIgnoreCase(CATEGORY_STRINGS[j])){
						Log.d("Category", "Category ID: " + j);
						return j;
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return OTHER;
	}
	
}