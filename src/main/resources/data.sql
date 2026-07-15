-- =========================================================
-- PawStock Warehouse - Sample Data
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

-- Products (20 sample records)
INSERT INTO products (
    product_name, description, price, pet_type, size,
    brand_id, category_id, supplier_id, created_at
)
VALUES (
    'Adult Dog Dry Food',
    'Balanced dry food formulated for adult dogs.',
    69.99, 'Dog', '12 kg', 1, 1, 1, CURRENT_TIMESTAMP
);

INSERT INTO products (
    product_name, description, price, pet_type, size,
    brand_id, category_id, supplier_id, created_at
)
VALUES (
    'Indoor Cat Dry Food',
    'Complete nutrition for indoor adult cats.',
    54.99, 'Cat', '7 kg', 1, 1, 2, CURRENT_TIMESTAMP
);

INSERT INTO products (
    product_name, description, price, pet_type, size,
    brand_id, category_id, supplier_id, created_at
)
VALUES (
    'Puppy Wet Food Pack',
    'Soft wet food portions for growing puppies.',
    29.49, 'Dog', '12 x 385 g', 1, 1, 1, CURRENT_TIMESTAMP
);

INSERT INTO products (
    product_name, description, price, pet_type, size,
    brand_id, category_id, supplier_id, created_at
)
VALUES (
    'KONG Classic Rubber Toy',
    'Durable rubber toy that can be filled with treats.',
    18.99, 'Dog', 'Medium', 2, 2, 2, CURRENT_TIMESTAMP
);

INSERT INTO products (
    product_name, description, price, pet_type, size,
    brand_id, category_id, supplier_id, created_at
)
VALUES (
    'KONG Puppy Teething Toy',
    'Soft rubber toy designed for puppy teeth and gums.',
    15.49, 'Dog', 'Small', 2, 2, 3, CURRENT_TIMESTAMP
);

INSERT INTO products (
    product_name, description, price, pet_type, size,
    brand_id, category_id, supplier_id, created_at
)
VALUES (
    'KONG Cat Wubba Toy',
    'Interactive cat toy with fabric tails and a rattle.',
    10.99, 'Cat', 'One Size', 2, 2, 2, CURRENT_TIMESTAMP
);

INSERT INTO products (
    product_name, description, price, pet_type, size,
    brand_id, category_id, supplier_id, created_at
)
VALUES (
    'TetraMin Tropical Flakes',
    'Daily flake food for tropical freshwater fish.',
    12.99, 'Fish', '200 g', 3, 1, 1, CURRENT_TIMESTAMP
);

INSERT INTO products (
    product_name, description, price, pet_type, size,
    brand_id, category_id, supplier_id, created_at
)
VALUES (
    'Tetra Betta Pellets',
    'Floating pellets made for betta fish.',
    7.49, 'Fish', '35 g', 3, 1, 3, CURRENT_TIMESTAMP
);

INSERT INTO products (
    product_name, description, price, pet_type, size,
    brand_id, category_id, supplier_id, created_at
)
VALUES (
    'Aquarium Water Conditioner',
    'Conditions tap water for freshwater aquariums.',
    14.99, 'Fish', '250 mL', 3, 5, 1, CURRENT_TIMESTAMP
);

INSERT INTO products (
    product_name, description, price, pet_type, size,
    brand_id, category_id, supplier_id, created_at
)
VALUES (
    'Adult Rabbit Food',
    'Timothy-based pellets for adult rabbits.',
    24.99, 'Small Animal', '2.25 kg', 4, 1, 2, CURRENT_TIMESTAMP
);

INSERT INTO products (
    product_name, description, price, pet_type, size,
    brand_id, category_id, supplier_id, created_at
)
VALUES (
    'Western Timothy Hay',
    'High-fibre hay for rabbits, guinea pigs, and chinchillas.',
    19.99, 'Small Animal', '1.1 kg', 4, 1, 3, CURRENT_TIMESTAMP
);

INSERT INTO products (
    product_name, description, price, pet_type, size,
    brand_id, category_id, supplier_id, created_at
)
VALUES (
    'Guinea Pig Essentials Food',
    'Vitamin C fortified pellets for adult guinea pigs.',
    22.49, 'Small Animal', '2.25 kg', 4, 1, 2, CURRENT_TIMESTAMP
);

INSERT INTO products (
    product_name, description, price, pet_type, size,
    brand_id, category_id, supplier_id, created_at
)
VALUES (
    'Premium Bird Seed Mix',
    'Seed blend for budgies and other small birds.',
    13.99, 'Bird', '1.8 kg', 5, 1, 1, CURRENT_TIMESTAMP
);

INSERT INTO products (
    product_name, description, price, pet_type, size,
    brand_id, category_id, supplier_id, created_at
)
VALUES (
    'Hamster Starter Habitat',
    'Ventilated starter cage with wheel and food dish.',
    59.99, 'Small Animal', 'Medium', 5, 4, 2, CURRENT_TIMESTAMP
);

INSERT INTO products (
    product_name, description, price, pet_type, size,
    brand_id, category_id, supplier_id, created_at
)
VALUES (
    'Glass Reptile Terrarium',
    'Secure glass habitat with front-opening doors.',
    129.99, 'Reptile', '20 gallon', 5, 4, 3, CURRENT_TIMESTAMP
);

INSERT INTO products (
    product_name, description, price, pet_type, size,
    brand_id, category_id, supplier_id, created_at
)
VALUES (
    'Double-Sided Grooming Brush',
    'Pin and bristle brush for routine coat care.',
    16.99, 'Dog', 'Medium', 5, 3, 1, CURRENT_TIMESTAMP
);

INSERT INTO products (
    product_name, description, price, pet_type, size,
    brand_id, category_id, supplier_id, created_at
)
VALUES (
    'Cat Nail Clippers',
    'Compact stainless-steel clippers for cat claws.',
    11.49, 'Cat', 'Small', 5, 3, 2, CURRENT_TIMESTAMP
);

INSERT INTO products (
    product_name, description, price, pet_type, size,
    brand_id, category_id, supplier_id, created_at
)
VALUES (
    'Stainless Steel Pet Bowl',
    'Non-slip food or water bowl for daily use.',
    14.99, 'Dog', '1.5 L', 5, 5, 3, CURRENT_TIMESTAMP
);

INSERT INTO products (
    product_name, description, price, pet_type, size,
    brand_id, category_id, supplier_id, created_at
)
VALUES (
    'Adjustable Dog Leash',
    'Comfortable nylon leash with a padded handle.',
    21.99, 'Dog', '1.8 m', 5, 5, 1, CURRENT_TIMESTAMP
);

INSERT INTO products (
    product_name, description, price, pet_type, size,
    brand_id, category_id, supplier_id, created_at
)
VALUES (
    'Reptile Heat Lamp',
    'Basking lamp fixture for reptile habitats.',
    34.99, 'Reptile', '150 W', 5, 5, 3, CURRENT_TIMESTAMP
);
