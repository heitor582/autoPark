package model;

import java.math.BigInteger;
import java.time.Instant;
import java.util.UUID;

public class Price {
	private final UUID id;
	private final int aboveTime;
	private final UUID garageId;
	private final BigInteger price;
	private final Instant createdAt;

	public Price(final UUID id, final int aboveTime, final UUID garageId, final BigInteger price, final Instant createdAt) {
		this.id = id;
		this.aboveTime = aboveTime;
		this.garageId = garageId;
		this.price = price;
		this.createdAt = createdAt;
	}

	public static Price create(final int aboveTime, final UUID garageId, final BigInteger price){
		return new Price(UUID.randomUUID(), aboveTime, garageId, price, Instant.now());
	}

	public UUID getId() {
		return id;
	}

	public int getAboveTime() {
		return aboveTime;
	}

	public UUID getGarageId() {
		return garageId;
	}

	public BigInteger getPrice() {
		return price;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}
}
