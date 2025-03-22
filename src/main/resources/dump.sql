CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    is_admin BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS garages (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    owner_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS garage_histories (
    id UUID PRIMARY KEY,
    garage_id UUID NOT NULL,
    price BIGINT,
    car_plate TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    deleted_at TIMESTAMP DEFAULT NULL,
    FOREIGN KEY (garage_id) REFERENCES garages(id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX IF NOT EXISTS unique_car_plate_active
ON garage_histories (car_plate)
WHERE deleted_at IS NULL;

CREATE TABLE IF NOT EXISTS garage_prices (
    id UUID PRIMARY KEY,
    above_time INT NOT NULL,
    garage_id UUID NOT NULL,
    price BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    FOREIGN KEY (garage_id) REFERENCES garages(id) ON DELETE CASCADE,
    UNIQUE (above_time, id)
);