package by.project.news.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class NewsData implements Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	private int page;
	private int recordsPerPage;
	private int endPages;
	private List<News> newses;

	NewsData() {
	}

	NewsData(NewsDataBuilder newsDataBuilder) {
		this.page = newsDataBuilder.page;
		this.recordsPerPage = newsDataBuilder.recordsPerPage;
		this.endPages = newsDataBuilder.endPages;
		this.newses = newsDataBuilder.newses;

	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRecordsPerPage() {
		return recordsPerPage;
	}

	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	public int getEndPages() {
		return endPages;
	}

	public void setEndPages(int endPages) {
		this.endPages = endPages;
	}

	public List<News> getNewses() {
		return newses;
	}

	public void setNewses(List<News> newses) {
		this.newses = newses;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(endPages, newses, page, recordsPerPage);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewsData other = (NewsData) obj;
		return endPages == other.endPages && Objects.equals(newses, other.newses) && page == other.page
				&& recordsPerPage == other.recordsPerPage;
	}

	
	@Override
	public String toString() {
		return getClass().getName() + " [page=" + page + ", recordsPerPage=" + recordsPerPage + ", endPages=" + endPages + ", newses="
				+ newses + "]";
	}
	
	// Builder

	public static class NewsDataBuilder {

		// optional
		private int page;
		private int recordsPerPage;
		private int endPages;
		private List<News> newses;

		public NewsDataBuilder() {
		}

		public NewsDataBuilder setPage(int page) {

			this.page = page;
			return this;
		}

		public NewsDataBuilder setRecordsPerPage(int recordsPerPage) {

			this.recordsPerPage = recordsPerPage;
			return this;
		}

		public NewsDataBuilder setEndPages(int endPages) {

			this.endPages = endPages;
			return this;
		}

		public NewsDataBuilder setNewses(List<News> newses) {

			this.newses = newses;
			return this;
		}

		public NewsData build() {

			return new NewsData(this);
		}

	}

}
