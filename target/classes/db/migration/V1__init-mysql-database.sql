-- Drop existing tables if they exist
DROP TABLE IF EXISTS notification;
DROP TABLE IF EXISTS document;
DROP TABLE IF EXISTS user;

-- Create user table
CREATE TABLE user (
    id CHAR(36) NOT NULL,
    version INT DEFAULT NULL,
    first_name VARCHAR(255) DEFAULT NULL,
    last_name VARCHAR(255) DEFAULT NULL,
    username VARCHAR(128) NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Create document table
CREATE TABLE document (
    id CHAR(36) NOT NULL,
    file_name VARCHAR(128) DEFAULT NULL,
    content_type VARCHAR(36) DEFAULT NULL,
    user_id CHAR(36) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    notify_at TIMESTAMP DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Create notification table
CREATE TABLE notification (
    id CHAR(36) NOT NULL,
    document_id CHAR(36) NOT NULL UNIQUE,
    user_id CHAR(36) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (document_id) REFERENCES document(id) ON DELETE CASCADE
) ENGINE=InnoDB;
