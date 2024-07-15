-- products table
CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    brand VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    prod_type VARCHAR(255),
    prod_info TEXT
);

-- Index on name for faster product searches
CREATE INDEX IF NOT EXISTS idx_products_name ON products(name);

