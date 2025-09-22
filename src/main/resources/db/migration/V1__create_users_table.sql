CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       login VARCHAR(255) NOT NULL,
                       password_hash VARCHAR(255) NOT NULL,
                       type VARCHAR(50) NOT NULL,
                       street VARCHAR(255),
                       number VARCHAR(50),
                       city VARCHAR(100),
                       zip_code VARCHAR(20),
                       last_modified_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
                       CONSTRAINT uk_user_email UNIQUE (email)
);
