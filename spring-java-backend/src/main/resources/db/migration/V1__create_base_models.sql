-- Creating user table

CREATE SEQUENCE USER_SEQUENCE START 1;
CREATE TABLE _USER (
    ID INTEGER PRIMARY KEY DEFAULT NEXTVAL('USER_SEQUENCE'),
    FIRST_NAME VARCHAR(255) NOT NULL,
    LAST_NAME VARCHAR(255) NOT NULL,
    EMAIL VARCHAR(255) UNIQUE NOT NULL,
    PASSWORD VARCHAR(255) NOT NULL,
    archived BOOLEAN DEFAULT FALSE
);

-- Create default unauthenticated user
INSERT INTO _USER (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD)
VALUES ('Unauthenticated', 'User', 'test@test.com', 'NONE');

CREATE SEQUENCE grad_plan_sequence START 1;
CREATE TABLE grad_plan (
    id BIGINT PRIMARY KEY DEFAULT nextval('grad_plan_sequence'),
    user_id VARCHAR(255) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    archived BOOLEAN DEFAULT FALSE
);

CREATE SEQUENCE class_plan_sequence START 1;
CREATE TABLE class_plan (
    id BIGINT PRIMARY KEY DEFAULT nextval('class_plan_sequence'),
    user_id VARCHAR(255) NOT NULL,
    grad_plan_id BIGINT,
    workload VARCHAR(255),
    class_distribution VARCHAR(255),
    classes JSONB,
    archived BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (grad_plan_id) REFERENCES grad_plan(id)
);

CREATE INDEX idx_grad_plan_user_id ON grad_plan(user_id);
CREATE INDEX idx_class_plan_user_id ON class_plan(user_id);
