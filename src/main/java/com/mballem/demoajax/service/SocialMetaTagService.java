package com.mballem.demoajax.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.mballem.demoajax.domain.SocialMetaTag;

@Service
public class SocialMetaTagService {

	private static Logger log = org.slf4j.LoggerFactory.getLogger(SocialMetaTagService.class);

	
	public SocialMetaTag getSocialMetaTagByUrl(String url) {
		
		SocialMetaTag twitter = getTwitterCardByUrl(url);
		if(!isEmpty(twitter)) {
			return twitter;
		}
		
		SocialMetaTag openGraph = getOpenGraphByUrl(url);
		if(!isEmpty(openGraph)) {
			return openGraph;
		}
		return null;
	}
	
	private SocialMetaTag getOpenGraphByUrl(String url) {

		SocialMetaTag metaTag = new SocialMetaTag();

		Document doc;
		try {
			doc = Jsoup.connect(url).get();

			metaTag.setTitle(doc.head().select("meta[property=og:title]").attr("content"));
			metaTag.setSite(doc.head().select("meta[property=og:site_name]").attr("content"));
			metaTag.setImage(doc.head().select("meta[property=og:image]").attr("content"));
			metaTag.setUrl(doc.head().select("meta[property=og:url]").attr("content"));

		} catch (IOException e) {
			log.error(e.getLocalizedMessage(), e.getCause());
		}

		return metaTag;
	}

	private SocialMetaTag getTwitterCardByUrl(String url) {

		SocialMetaTag metaTag = new SocialMetaTag();

		Document doc;
		try {
			doc = Jsoup.connect(url).get();

			metaTag.setTitle(doc.head().select("meta[property=twitter:title]").attr("content"));
			metaTag.setSite(doc.head().select("meta[property=twitter:site]").attr("content"));
			metaTag.setImage(doc.head().select("meta[property=twitter:image]").attr("content"));
			metaTag.setUrl(doc.head().select("meta[property=twitter:url]").attr("content"));

		} catch (IOException e) {
			log.error(e.getLocalizedMessage(), e.getCause());
		}

		return metaTag;
	}

	private boolean isEmpty(SocialMetaTag metaTag) {

		if (metaTag.getImage().isEmpty())
			return true;
		if (metaTag.getSite().isEmpty())
			return true;
		if (metaTag.getTitle().isEmpty())
			return true;
		if (metaTag.getUrl().isEmpty())
			return true;
		return false;
	}
}
