package model;

import java.time.Instant;
import java.util.UUID;

public class Garage {
	private final UUID id;
	private final String name;
	private final UUID ownerId;
	private final Instant createdAt;
	private final Instant updatedAt;

	public Garage(final UUID id, final String name, final UUID ownerId, final Instant createdAt, final Instant updatedAt) {
		this.id = id;
		this.name = name;
		this.ownerId = ownerId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static Garage create(final String name, final UUID ownerId){
		Instant now = Instant.now();
		return new Garage(UUID.randomUUID(), name, ownerId, now, now);
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public UUID getOwnerId() {
		return ownerId;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getUpdateAt() {
		return updatedAt;
	}
}
