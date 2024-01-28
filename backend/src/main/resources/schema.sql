-- Create 'users' table
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    creation_date DATE,
    last_login DATE,
    api_token VARCHAR(255),
    session_token VARCHAR(255)
);

-- Create 'roles' table
CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255)
);

-- Create 'user_roles' table for the many-to-many relationship
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Create 'notes' table
CREATE TABLE notes (
    id BIGSERIAL PRIMARY KEY,
    author_id BIGINT NOT NULL,
    uuid VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    is_private BOOLEAN NOT NULL,
    burn_after_read BOOLEAN NOT NULL,
    expiration_date DATE,
    creation_date DATE NOT NULL,
    view_count INT NOT NULL
);