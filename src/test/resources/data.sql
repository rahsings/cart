-- Insert into users table
INSERT INTO users (mobile, email, name) VALUES
('07234567890', 'rahul.singh@gmail.com', 'Rahul'),
('09876543213', 'sonal.singh@yahoo.com', 'Sonal');

-- Insert into addresses table
INSERT INTO addresses (pin_code, city, state, country, street, locality, landmark, user_id, house_no) VALUES
('110001', 'New Delhi', 'Delhi', 'India', 'Connaught Place St', 'Connaught Place', 'Near Central Park', 1, '#14D'),
('400001', 'Mumbai', 'MH', 'India', 'Marine Drive St', 'Nariman Point', 'Near Marine Drive', 2, '#15E');

-- Insert into products table
INSERT INTO products (name, price, prod_type, prod_info, brand) VALUES
('Iphone 13', 699.99, 'Electronics', 'Latest smartphone with cutting-edge features', 'Apple'),
('Macbook Pro', 1299.99, 'Electronics', 'High-performance laptop for professionals', 'Apple'),
('Apple Watch Series 9', 199.99, 'Electronics', 'Advanced smartwatch with health tracking', 'Apple'),
('Samsung 4K Ultra HD TV', 899.99, 'Electronics', 'Ultra HD 4K television with HDR support', 'Samsung');
