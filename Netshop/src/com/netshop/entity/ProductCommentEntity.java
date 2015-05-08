package com.netshop.entity;

import java.util.List;

public class ProductCommentEntity extends ProductEntity {
	public ProductCommentEntity(){
		
	}
	public List<Comment> evaluate;
	
	public List<Comment> getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(List<Comment> evaluate) {
		this.evaluate = evaluate;
	}

	public class Comment{
		public String uid;
		public String nickname;
		public String content;
		public String time;
		public String getUid() {
			return uid;
		}
		public void setUid(String uid) {
			this.uid = uid;
		}
		public String getNickname() {
			return nickname;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		
	}
}
