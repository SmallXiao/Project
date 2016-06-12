/*
 * 微信公众平台(JAVA) SDK
 *
 * Copyright (c) 2014, Ansitech Network Technology Co.,Ltd All rights reserved.
 * http://www.ansitech.com/weixin/sdk/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.project.utils;

import java.util.List;

/**
 * 这个类实现了<tt>OutputMessage</tt>，用来回复文本消息
 *
 * <p>提供了获取文本内容<code>getContent()</code>等主要方法.</p>
 *
 * @author qsyang<yangqisheng274@163.com>
 */
public class NewsOutputMessage extends OutputMessage {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3316056043353686238L;
	/**
     * 消息类型:文本消息
     */
    private String MsgType = "news";

    public String getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(String articleCount) {
        ArticleCount = articleCount;
    }

    /**
     * 文本消息
     */
    private String ArticleCount;


    public NewsOutputMessage() {
    }


    public List<ArticlesBo> getArticles() {
        return Articles;
    }

    public void setArticles(List<ArticlesBo> articles) {
        Articles = articles;
    }

    private List<ArticlesBo> Articles;
    /**
     * 获取 消息类型
     *
     * @return 消息类型
     */
    @Override
    public String getMsgType() {
        return MsgType;
    }


}