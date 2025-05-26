-- V1__init.sql

CREATE TABLE franchises (
                            id   uuid PRIMARY KEY,
                            name varchar(255) NOT NULL
);

CREATE TABLE branches (
                          id            uuid PRIMARY KEY,
                          name          varchar(255) NOT NULL,
                          franchise_id  uuid
);

CREATE TABLE products (
                          id         uuid PRIMARY KEY,
                          name       varchar(255) NOT NULL,
                          quantity   int NOT NULL,
                          branch_id  uuid
);

ALTER TABLE branches
    ADD CONSTRAINT fk_branches_franchise
        FOREIGN KEY (franchise_id)
            REFERENCES franchises (id);

ALTER TABLE products
    ADD CONSTRAINT fk_products_branch
        FOREIGN KEY (branch_id)
            REFERENCES branches (id);
