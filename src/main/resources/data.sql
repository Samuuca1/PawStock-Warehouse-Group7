-- =========================================================
-- PawStock Warehouse - Deliverable 1 Sample Data
-- =========================================================

-- Brands
INSERT INTO brands (brand_name, country)
VALUES ('Royal Canin', 'France');

INSERT INTO brands (brand_name, country)
VALUES ('KONG', 'United States');

INSERT INTO brands (brand_name, country)
VALUES ('Tetra', 'Germany');

INSERT INTO brands (brand_name, country)
VALUES ('Oxbow Animal Health', 'United States');

INSERT INTO brands (brand_name, country)
VALUES ('Hagen', 'Canada');

-- Categories
INSERT INTO categories (category_name, description)
VALUES ('Food', 'Dry food, wet food, treats, and nutrition products.');

INSERT INTO categories (category_name, description)
VALUES ('Toys', 'Interactive and enrichment toys for pets.');

INSERT INTO categories (category_name, description)
VALUES ('Grooming', 'Pet grooming and hygiene supplies.');

INSERT INTO categories (category_name, description)
VALUES ('Habitats', 'Aquariums, cages, beds, and pet habitats.');

INSERT INTO categories (category_name, description)
VALUES ('Accessories', 'Leashes, bowls, collars, and daily accessories.');

-- Suppliers
INSERT INTO suppliers (supplier_name, email, phone)
VALUES ('Ontario Pet Distribution', 'orders@ontariopet.ca', '416-555-1101');

INSERT INTO suppliers (supplier_name, email, phone)
VALUES ('Canadian Animal Supply', 'sales@canadiananimalsupply.ca', '905-555-2202');

INSERT INTO suppliers (supplier_name, email, phone)
VALUES ('NorthStar Pet Wholesale', 'support@northstarpet.ca', '647-555-3303');

-- Products
INSERT INTO products (
    product_name,
    description,
    price,
    pet_type,
    size,
    brand_id,
    category_id,
    supplier_id,
    created_at
)
VALUES (
    'Adult Dog Dry Food',
    'Balanced dry food formulated for adult dogs.',
    69.99,
    'Dog',
    '12 kg',
    1,
    1,
    1,
    CURRENT_TIMESTAMP
);

