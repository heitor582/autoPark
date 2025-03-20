package model;

import java.time.Instant;
import java.util.UUID;

public class User {
		private final UUID id;
		private final String username;
		private final String password;
		private final boolean isAdmin;
		private final Instant createdAt;
		private final Instant updatedAt;


	public User(final UUID id, final String username, final String password, final boolean isAdmin, final Instant createdAt, final Instant updatedAt) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static User create(final String username, final String password, final boolean isAdmin){
		Instant now = Instant.now();
		return new User(UUID.randomUUID(), username, password, isAdmin, now,now);
	}

	public User update(final String username, final String password, final boolean isAdmin) {
		return new User(this.id, username, password, isAdmin, this.createdAt, Instant.now());
	}

	public UUID getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}
}

