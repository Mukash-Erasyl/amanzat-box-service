DROP TABLE IF EXISTS box CASCADE;

DROP TABLE IF EXISTS box_dimensions CASCADE;

CREATE TABLE box_dimensions (
                                id UUID PRIMARY KEY,
                                height DOUBLE PRECISION,
                                width DOUBLE PRECISION,
                                length DOUBLE PRECISION,
                                created_at TIMESTAMP,
                                updated_at TIMESTAMP
);

CREATE TABLE box (
                     id UUID PRIMARY KEY,
                     address VARCHAR(255),
                     city VARCHAR(255),
                     box_dimension_id UUID,
                     price DECIMAL(19, 2),
                     status VARCHAR(50),
                     type VARCHAR(50),
                     volume DOUBLE PRECISION,
                     created_at TIMESTAMP,
                     updated_at TIMESTAMP,
                     CONSTRAINT fk_box_dimension FOREIGN KEY (box_dimension_id) REFERENCES box_dimensions(id) ON DELETE SET NULL
);

INSERT INTO box_dimensions(id, height, width, length, created_at, updated_at)
VALUES
    ('550e8400-e29b-41d4-a716-446655440000', 1, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('550e8400-e29b-41d4-a716-446655440001', 2.4, 1, 2.4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('550e8400-e29b-41d4-a716-446655440002', 2.4, 1.2, 2.4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('550e8400-e29b-41d4-a716-446655440003', 2.4, 2.4, 1.6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('550e8400-e29b-41d4-a716-446655440004', 2.4, 2.4, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('550e8400-e29b-41d4-a716-446655440005', 2.4, 6, 2.4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
