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
	private int maxNewses;
	private List<News> newses;

	NewsData() {
	}

	NewsData(NewsDataBuilder newsDataBuilder) {
		this.page = newsDataBuilder.page;
		this.recordsPerPage = newsDataBuilder.recordsPerPage;
		this.maxNewses = newsDataBuilder.maxNewses;
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

	public void setRecordsPerPage(int newsPerPage) {
		this.recordsPerPage = newsPerPage;
	}

	public int getMaxNewses() {
		return maxNewses;
	}

	public void setMaxNewses(int endPages) {
		this.maxNewses = endPages;
	}

	public List<News> getNewses() {
		return newses;
	}

	public void setNewses(List<News> newses) {
		this.newses = newses;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maxNewses, recordsPerPage, newses, page);
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
		return maxNewses == other.maxNewses && recordsPerPage == other.recordsPerPage
				&& Objects.equals(newses, other.newses) && page == other.page;
	}

	@Override
	public String toString() {
		return getClass().getName() + " [page=" + page + ", recordsPerPage=" + recordsPerPage + ", maxNewses="
				+ maxNewses + ", newses=" + newses + "]";
	}

	// Builder

	public static class NewsDataBuilder {

		// optional
		private int page;
		private int recordsPerPage;
		private int maxNewses;
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

		public NewsDataBuilder setMaxNewses(int maxNewses) {

			this.maxNewses = maxNewses;
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
