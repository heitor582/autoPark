package model;

import java.math.BigInteger;
import java.time.Instant;
import java.util.UUID;

public class History {
	private UUID id;
	private String carPlate;
	private UUID garageId;
	private BigInteger price;
	private Instant createdAt;
	private Instant deletedAt;
}
