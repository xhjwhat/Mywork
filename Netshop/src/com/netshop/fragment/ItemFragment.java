package com.netshop.fragment;

import com.netshop.app.R;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ItemFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View contextView = inflater.inflate(R.layout.fragment_item, container, false);
		TextView mTextView = (TextView) contextView.findViewById(R.id.textview);
		
		Bundle mBundle = getArguments();
		String title = mBundle.getString("arg");
		Spanned text = Html.fromHtml(title,imgGetter,null);     
		mTextView.setText(text);
		
		return contextView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	ImageGetter imgGetter = new Html.ImageGetter() {     
        @Override   
         public Drawable getDrawable(String source) {     
               Drawable drawable = null;     
               drawable = Drawable.createFromPath(source);  // Or fetch it from the URL     
               // Important     
               drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable     
                            .getIntrinsicHeight());     
               return drawable;     
         }     
}; 

}