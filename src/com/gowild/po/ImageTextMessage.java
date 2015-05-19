package com.gowild.po;

import java.util.List;

public class ImageTextMessage extends BaseMessage {
	private Integer ArticleCount;
	private List<ArticlesMessage> Articles;
	public Integer getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(Integer articleCount) {
		ArticleCount = articleCount;
	}
	public List<ArticlesMessage> getArticles() {
		return Articles;
	}
	public void setArticles(List<ArticlesMessage> articles) {
		Articles = articles;
	}
	
}
