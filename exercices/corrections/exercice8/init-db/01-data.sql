-- Création de la table
CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock INTEGER DEFAULT 0,
    category VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Données de démonstration
INSERT INTO products (name, description, price, stock, category) VALUES
    ('Laptop Pro', 'Ordinateur portable haute performance', 1299.99, 15, 'Electronics'),
    ('Wireless Mouse', 'Souris sans fil ergonomique', 29.99, 50, 'Electronics'),
    ('USB-C Hub', 'Hub USB-C 7 ports', 49.99, 30, 'Electronics'),
    ('Mechanical Keyboard', 'Clavier mécanique RGB', 89.99, 25, 'Electronics'),
    ('Monitor 27"', 'Écran 4K 27 pouces', 399.99, 10, 'Electronics'),
    ('Desk Chair', 'Chaise de bureau ergonomique', 249.99, 8, 'Furniture'),
    ('Standing Desk', 'Bureau assis-debout électrique', 599.99, 5, 'Furniture'),
    ('Webcam HD', 'Webcam 1080p avec micro', 79.99, 20, 'Electronics'),
    ('Headphones', 'Casque audio sans fil', 149.99, 35, 'Electronics'),
    ('Book Stand', 'Support de livre ajustable', 24.99, 40, 'Accessories')
ON CONFLICT DO NOTHING;
