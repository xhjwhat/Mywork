package com.netshop.activity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import net.sf.json.xml.XMLSerializer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.l99gson.Gson;
import com.netshop.adapter.CommentAdapter;
import com.netshop.app.R;
import com.netshop.entity.Comment;
import com.netshop.entity.CommentEntity;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;

public class ProductCommentActivity extends Activity {
	private TextView title;
	private ImageView backImg;
	private ListView list;
	private String productid;
	private List<Comment> commentList;
	private CommentAdapter adapter;
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.shop_comment);
		Intent intent = getIntent();
		if(intent!=null){
			productid=intent.getStringExtra("key");
		}
		backImg = (ImageView) findViewById(R.id.title_back_img);
		backImg.setVisibility(View.VISIBLE);
		backImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		title = (TextView)findViewById(R.id.title_text);
		title.setText("商品评论");
		list = (ListView)findViewById(R.id.shopcomm_list);
		HttpRequest request = new HttpRequest("3", "0005");
		request.setPc(productid);
		request.setPg("1");
		request.setPs("20");
		request.request(new HttpCallBack() {
			@Override
			public void success(String json) {
				XMLSerializer xmls = new XMLSerializer();
				String result = "<response description=\"获取成功\" error=\"0\"><list currentpage=\"1\" totalpage=\"1\" totalnum=\"1\"><evaluate uid=\"3\" nickname=\"醉酒街头\" content=\"这个产品挺不错的\" time=\"2015-05-02 16:24:29\"/><evaluate uid=\"2\" nickname=\"无所畏惧\" content=\"产品很给力\" time=\"2015-05-01 19:28:29\"/></list></response>";
				json = xmls.read(result).toString().replace("@", "");
				Gson gson = new Gson();
				CommentEntity entity = gson.fromJson(json, CommentEntity.class);
				commentList = new ArrayList<Comment>();
				Object temp = entity.getList().getEvaluate();
				if(temp == null){
					Toast.makeText(ProductCommentActivity.this, "没有评论数据", Toast.LENGTH_SHORT).show();
					return;
					//finish();
				}
				if(temp instanceof LinkedHashMap<?, ?>){
					LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) temp;
					Comment comment = new Comment();
					comment.setContent(String.valueOf(map.get("content")));
					comment.setNickname(String.valueOf(map.get("nickname")));
					comment.setTime(String.valueOf(map.get("time")));
					comment.setUid(String.valueOf(map.get("uid")));
					commentList.add(comment);
				}else{
					//LinkedHashMap<String, Object> object = (LinkedHashMap<String, Object>) temp;
					ArrayList<LinkedHashMap<String, Object>> objects = (ArrayList<LinkedHashMap<String, Object>>) temp;
					for(LinkedHashMap<String, Object> map:objects){
						Comment comment = new Comment();
						comment.setContent(String.valueOf(map.get("content")));
						comment.setNickname(String.valueOf(map.get("nickname")));
						comment.setTime(String.valueOf(map.get("time")));
						comment.setUid(String.valueOf(map.get("uid")));
						commentList.add(comment);
					}
				}
				adapter = new CommentAdapter(ProductCommentActivity.this, commentList);
				list.setAdapter(adapter);
			}
			
			@Override
			public void fail(String failReason) {
				Toast.makeText(ProductCommentActivity.this, failReason, Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	}
}
