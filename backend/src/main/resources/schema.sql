-- Create 'accounts' table
CREATE TABLE IF NOT EXISTS accounts (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    otp_key VARCHAR(16),
    otp_enabled BOOLEAN NOT NULL,
    creation_date DATE
);

-- Create 'roles' table
CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    ordinal INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL,
    is_visible BOOLEAN NOT NULL
);

-- Create 'account_roles' table for the many-to-many relationship
CREATE TABLE IF NOT EXISTS account_roles (
    account_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (account_id, role_id),
    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Create 'notes' table
CREATE TABLE notes (
    id SERIAL PRIMARY KEY,
    author_id BIGINT NOT NULL,
    uuid VARCHAR(255) NOT NULL,
    is_private BOOLEAN NOT NULL,
    expiration_date DATE,
    creation_date DATE NOT NULL,
    view_count INT NOT NULL,
    content VARCHAR(4096) NOT NULL
);

CREATE TABLE notes (
    id SERIAL PRIMARY KEY,
    author_id BIGINT NOT NULL,
    uuid VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(8192) NOT NULL,
    is_private BOOLEAN NOT NULL,
    creation_date DATE NOT NULL,
    view_count INT NOT NULL
);

CREATE TABLE attachments (
    id SERIAL PRIMARY KEY,
    author_id BIGINT NOT NULL,
    uuid VARCHAR(255) NOT NULL,
    content BYTEA NOT NULL,
    is_private BOOLEAN NOT NULL,
    creation_date DATE NOT NULL
);

CREATE TABLE proxy_network (
    id SERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    current_ip VARCHAR(128),
    bandwidth_used BIGINT NOT NULL DEFAULT 0,
);



