package org.theboar.android;

import java.util.Date;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;

public class CNS
{

	public static final String FADE = "fd", ROTATE = "rt", SCALE = "sc";
	public static final String LOGPRINT = "Boar";

	public static SharedPreferences getSharedPreferences(Context cntxt)
	{
		return PreferenceManager.getDefaultSharedPreferences(cntxt);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static int getActionBarHeight(Activity act)
	{
		TypedValue tv = new TypedValue();
		act.getTheme().resolveAttribute(android.R.attr.actionBarSize,tv,true);
		return act.getResources().getDimensionPixelSize(tv.resourceId);
	}

	public static int getPXfromDP(int dp, Context cntx)
	{
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dp,cntx.getResources().getDisplayMetrics());
	}

	public static int getDPfromPX(int dp, Context cntx)
	{
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dp,cntx.getResources().getDisplayMetrics());
	}

	public static boolean isTablet(Context context)
	{
		return (context.getResources().getConfiguration().screenLayout
		& Configuration.SCREENLAYOUT_SIZE_MASK)
		>= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	public static boolean isPortrait(Context context)
	{
		if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) return true;
		else return false;
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("InlinedApi")
	public static int getFillParent()
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) { return FrameLayout.LayoutParams.MATCH_PARENT; }
		return FrameLayout.LayoutParams.FILL_PARENT;
	}

	/**@param type
	 * 	one of FADE, ROTATE, SCALE
	 * @param from
	 * 	current - alpha, angle, scale 0:invisible
	 * @param to
	 * 	resulting - alpha, angle, scale
	 * @param duration
	 * 	Duration Ideally 400 for Fade and 1000 for rotate**/
	public static Animation Animate(View view, String type, float from, float to,
			int duration)
	{
		Animation anim = null;
		if (type == FADE)
		{
			anim = new AlphaAnimation(from,to);
			if (to == 1) view.setVisibility(View.VISIBLE);
			else if (to == 0) view.setVisibility(View.GONE);
			else anim.setFillAfter(true);
		}
		else if (type == ROTATE)
		{

			anim = new RotateAnimation(from,to,Animation.RELATIVE_TO_SELF,
					0.5f,Animation.RELATIVE_TO_SELF,0.5f);

		}
		else if (type == SCALE)
		{
			anim = new ScaleAnimation(from,to,from,to,Animation.RELATIVE_TO_SELF,
					0.5f,Animation.RELATIVE_TO_SELF,0.5f);
			anim.setInterpolator(new DecelerateInterpolator());
		}
		else {
			return null;
		}
		anim.setDuration(duration);
		return anim;
	}

	public static void onTouchHighlight(final Context ctx, View v, final View animView)
	{
		v.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{

				switch (event.getAction())
				{
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_OUTSIDE:
				case MotionEvent.ACTION_UP:
				{
					if (animView instanceof FrameLayout) {
						ColorDrawable trans = new ColorDrawable(ctx.getResources().getColor(R.color.Transparent));
						((FrameLayout) animView).setForeground(trans);
					}
					break;
				}
				case MotionEvent.ACTION_DOWN:
				{
					if (animView instanceof FrameLayout) {
//						ColorDrawable whiteTrans = new ColorDrawable(ctx.getResources().getColor(R.color.white_20));
						((FrameLayout) animView).setForeground(ctx.getResources().getDrawable(R.drawable.article_border));
					}
					break;
				}
				}

				// if you reach here, you have consumed the event
				return false;
			}
		});
	}

	public static String changeHTMLattr(String html, String attr, String value)
	{
//		html = html.replaceAll("\\s*(?:width)\\s*=\\s*\"[^\"]*\"\\s*"," max-width=\"100%\" ");
		html = html.replaceAll("\\s*(?:" + attr + ")\\s*=\\s*\'[^\']*\'\\s*"," " + attr + "=\"" + value + "\" ");
		html = html.replaceAll("\\s*(?:" + attr + ")\\s*=\\s*\"[^\"]*\"\\s*"," " + attr + "=\"" + value + "\" ");
		return html;
	}

	public static boolean isNetworkConnected(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			// There are no active networks.
			return false;
		} else return true;
	}

	public static String timeElapsed(Date date)
	{
		long epoch = date.getTime();
		String timePassedString = (String) DateUtils.getRelativeTimeSpanString
				(epoch,System.currentTimeMillis(),DateUtils.SECOND_IN_MILLIS);
		return timePassedString;
	}

	public static String correctYouTube(String str)
	{
		String spanEmbed = "<span class='embed-youtube' ";
		int ioYoutubeSpan = str.indexOf(spanEmbed);
		while (ioYoutubeSpan >= 0) {

			int ioCloseTag1 = str.indexOf(">",ioYoutubeSpan) + 1;
			int ioSRC = str.indexOf("src='http://www.youtube",ioCloseTag1);
			int ioCloseTagSpan = str.indexOf("</span>",ioCloseTag1);

			if (ioSRC > ioCloseTag1 && ioSRC < ioCloseTagSpan) {
				int ioYouTubeLink = str.indexOf("?",ioSRC);
				if (ioYouTubeLink < ioCloseTagSpan) {
					String url = str.substring(ioSRC + 5,ioYouTubeLink);
					String playButtonUrl = "https://www.youtube.com/yt/advertise/medias/images/yt-advertise-whyitworks-playbutton.png";
					String videoID = url.substring(url.indexOf("/embed/") + 7,url.length());
					String imgUrl = "http://img.youtube.com/vi/" + videoID + "/0.jpg";
					String div = "<div style='position:relative'>" +
							"<img style='position: absolute;left: 0;right: 0;margin: auto;bottom: 0;width: 80px;top: 0;' src='" +
							playButtonUrl + "'/>" +
							"<a href='" + url + "'>" +
							"<img src='" + imgUrl + "' style='width:100%'/>" +
							"</a></div>";

					StringBuilder strB = new StringBuilder(str);
					strB.delete(ioCloseTag1,ioCloseTagSpan);
					strB.insert(ioCloseTag1,div);

					str = strB.toString();
				}
			}
			ioYoutubeSpan = str.indexOf(spanEmbed,ioYoutubeSpan + 1);
//			Log.d("PRINT","" + str.substring(ioYoutubeSpan));
		}
		return str;
	}

}
