package cn.com.siemens.novel.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.springframework.util.StopWatch.TaskInfo;
import org.wltea.analyzer.lucene.IKAnalyzer;

import cn.com.siemens.novel.bean.Novel;
import cn.com.siemens.novel.util.ChineseToEnglish;

public class IndexService {
	private File INDEX_DIR = new File("d:/lucene");
	private IKAnalyzer ikAnalyzer = new IKAnalyzer(true);

	public IndexWriter openIndexWriter() throws CorruptIndexException, LockObtainFailedException, IOException {
		IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_35, ikAnalyzer);
		conf.setOpenMode(OpenMode.CREATE_OR_APPEND);
		return new IndexWriter(FSDirectory.open(INDEX_DIR), conf);
	}

	public Document buildDocument(Novel n) {
		Document document = new Document();
		Field id = new Field("id", n.getId(), Field.Store.YES, Field.Index.NOT_ANALYZED);
		Field name = new Field("name", n.getId()+ChineseToEnglish.getPingYin(n.getAuthor()), Field.Store.YES, Field.Index.ANALYZED);
		Field nameZh = new Field("nameZh", n.getName()+n.getAuthor(), Field.Store.YES, Field.Index.ANALYZED);
		document.add(id);
		document.add(name);
		document.add(nameZh);
		return document;
	}

	public Query buildQuery(String queryName, String criteria) throws ParseException {
		QueryParser qp = new QueryParser(Version.LUCENE_35, queryName, ikAnalyzer);
		return qp.parse(criteria);
	}

	public Query buildMultiQuery(String criteria, String... queryNames) throws ParseException {
		BooleanQuery query = new BooleanQuery();
		for (String name : queryNames) {
			if("name".equals(name)){
				query.add(new WildcardQuery(new Term(name, "*" + criteria + "*")), Occur.SHOULD);

			}

			query.add(new QueryParser(Version.LUCENE_35, name, ikAnalyzer).parse(criteria), Occur.SHOULD);
		}
		return query;
	}

	public List<Novel> query(String criteria, String... queryName) {
		List<Novel> list = null;
		IndexReader reader = null;
		try {
			reader = IndexReader.open(FSDirectory.open(INDEX_DIR));
			IndexSearcher searcher = new IndexSearcher(reader);
			Query query = buildMultiQuery(criteria, queryName);
			TopDocs dos = searcher.search(query, 10);
			int totaldocs = dos.totalHits;
			System.out.println("total:" + totaldocs);
			ScoreDoc[] docs = dos.scoreDocs;
			if (totaldocs > 0) {
				list = new ArrayList<Novel>();
				for (ScoreDoc doc : docs) {
					Document docuemnt = searcher.doc(doc.doc);
					Novel contact = new Novel();
					contact.setId(docuemnt.get("id"));
					list.add(contact);
				}
			}
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return list;

	}

	public void updateIndex(TaskInfo task) {
		/*List<TaskInfo> list = query(task.getSearchId(), "searchId");
		if (list != null && list.size() > 0) {
			list.get(0).setNote(task.getNote());
			list.get(0).setCommond(task.getCommond());
			list.get(0).setTime(task.getTime());
			IndexWriter writer = null;
			try {
				writer = openIndexWriter();

				writer.updateDocument(new Term("searchId", task.getSearchId()), buildDocument(list.get(0)));
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (CorruptIndexException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}*/
	}

	public void index(List<Novel> list) {
		IndexWriter writer = null;
		try {
			writer = openIndexWriter();
			if (list != null && list.size() > 0)
				for (Novel contact : list) {
					writer.addDocument(buildDocument(contact));
				}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (CorruptIndexException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	public void index(Novel task) {
		IndexWriter writer = null;
		try {

			writer = openIndexWriter();
			writer.addDocument(buildDocument(task));

		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (CorruptIndexException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	public void delete(String searchId) {
		IndexWriter writer = null;
		try {
			writer = openIndexWriter();
			Query query = buildQuery("searchId", searchId);
			writer.deleteDocuments(query);
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (CorruptIndexException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}
	
	public static void main(String[] args) {
		IndexService indexService = new IndexService();
		/*MongoTemplate mongoTemplate = (MongoTemplate) SpringUtils.springFactory.getBean("mongoTemplate");
		org.springframework.data.mongodb.core.query.Query qu = new org.springframework.data.mongodb.core.query.Query();
		int limit = 10;
		qu.limit(limit);
		List<Novel> list = mongoTemplate.find(qu, Novel.class);
		for(Novel n:list){
			indexService.index(n);
			System.out.println(n);
		}*/
		System.out.println(indexService.query("悲剧", "name","nameZh"));;
	}

}
