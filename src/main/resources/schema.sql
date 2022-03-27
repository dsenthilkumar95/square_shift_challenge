CREATE TABLE cart (
    userId INTEGER NOT NULL,
    productId INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    PRIMARY KEY (userId,productId)
);
CREATE TABLE price (
    weight INTEGER NOT NULL,
    distance INTEGER NOT NULL,
    price INTEGER NOT NULL,
    PRIMARY KEY (weight, distance)
)