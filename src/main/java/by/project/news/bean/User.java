package by.project.news.bean;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String login;
	private String role;
	private String age;

	public User() {
	}

	public User(String login, String role) {
		this.login = login;
		this.role = role;
	}

	public User(String login, String role, String age) {
		this(login, role);
		this.role = age;
	}

	User(UserBuilder builder) {
		this.login = builder.login;
		this.role = builder.role;
		this.age = builder.age;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, login, role);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(age, other.age) && Objects.equals(login, other.login) && Objects.equals(role, other.role);
	}

	@Override
	public String toString() {
		return getClass().getName() + " [login=" + login + ", role=" + role + ", age=" + age + "]";
	}

	// Builder

	public static class UserBuilder {

		// required or optional

		private String login;
		private String role;
		private String age;

		public UserBuilder() {

		}

		public UserBuilder(String login, String role) {
			this.login = login;
			this.role = role;
		}
		
		public UserBuilder(String login, String role, String age) {
			this(login, role);
			this.age = age;
		}

		public UserBuilder setLogin(String login) {

			this.login = login;
			return this;
		}

		public UserBuilder setRole(String role) {

			this.role = role;
			return this;
		}
		
		public UserBuilder setAge(String age) {

			this.age = age;
			return this;
		}

		public User build() {
			return new User(this);
		}
	}

}
