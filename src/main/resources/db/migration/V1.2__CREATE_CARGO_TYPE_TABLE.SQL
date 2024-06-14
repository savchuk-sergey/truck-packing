CREATE TABLE IF NOT EXISTS cargo_packing.cargo_type (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
    representation VARCHAR
)