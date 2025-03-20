package model;

import java.math.BigInteger;
import java.time.Instant;
import java.util.UUID;

public class Price {
	private UUID id;
	private int aboveTime;
	private UUID garageId;
	private BigInteger price;
	private Instant createdAt;
}
