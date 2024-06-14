CREATE TABLE IF NOT EXISTS cargo_packing.truck_type (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
    width INTEGER,
    height INTEGER
)