-- Creating user table

CREATE SEQUENCE USER_SEQUENCE START 1;
CREATE TABLE _USER (
    ID INTEGER PRIMARY KEY DEFAULT NEXTVAL('USER_SEQUENCE'),
    FIRST_NAME VARCHAR(255) NOT NULL,
    LAST_NAME VARCHAR(255) NOT NULL,
    EMAIL VARCHAR(255) UNIQUE NOT NULL,
    PASSWORD VARCHAR(255) NOT NULL,
    CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ARCHIVED BOOLEAN DEFAULT FALSE
);

-- Create default unauthenticated user
INSERT INTO _USER (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD)
VALUES ('UFV', 'Student', 'test@student.ufv.ca', 'test');

CREATE SEQUENCE grad_plan_sequence START 1;
CREATE TABLE grad_plan (
    id BIGINT PRIMARY KEY DEFAULT nextval('grad_plan_sequence'),
    file_name VARCHAR(255) NOT NULL,
    pdf_content_base64 TEXT,
    details TEXT,
    program_name VARCHAR(255),
    major_name VARCHAR(255),
    credits_completed BIGINT,
    credits_required BIGINT,
    cgpa DOUBLE PRECISION,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    program_level VARCHAR(255),
    user_id BIGINT NOT NULL,
    archived BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES _user(id)
);

CREATE SEQUENCE availability_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE availability_day_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE availability_hour_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE availability (
    id BIGINT PRIMARY KEY DEFAULT nextval('availability_sequence'),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE availability_day (
    id BIGINT PRIMARY KEY DEFAULT nextval('availability_day_sequence'),
    availability_id BIGINT NOT NULL,
    day VARCHAR(255) NOT NULL,
    FOREIGN KEY (availability_id) REFERENCES availability(id)
);

CREATE TABLE availability_hour (
    id BIGINT PRIMARY KEY DEFAULT nextval('availability_hour_sequence'),
    availability_day_id BIGINT NOT NULL,
    hour_of_the_day INTEGER NOT NULL,
    is_available BOOLEAN NOT NULL,
    FOREIGN KEY (availability_day_id) REFERENCES availability_day(id)
);

CREATE SEQUENCE class_plan_sequence START 1;
CREATE TABLE class_plan (
    id BIGINT PRIMARY KEY DEFAULT nextval('class_plan_sequence'),
    description VARCHAR(255),
    grad_plan_id BIGINT NOT NULL,
    availability_id BIGINT,
    class_distribution VARCHAR(255) NOT NULL,
    burden_capacity VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    archived BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (grad_plan_id) REFERENCES grad_plan(id),
    FOREIGN KEY (availability_id) REFERENCES availability(id)
);

CREATE INDEX idx_grad_plan_user_id ON grad_plan(user_id);
CREATE INDEX idx_class_plan_grad_plan_id ON class_plan(grad_plan_id);

CREATE SEQUENCE class_detail_sequence START 1;
CREATE TABLE class_detail (
    id BIGINT PRIMARY KEY DEFAULT nextval('class_detail_sequence'),
    section VARCHAR(255) NOT NULL,
    instructor VARCHAR(255) NOT NULL,
    room VARCHAR(255) NOT NULL,
    method VARCHAR(255) NOT NULL
);

CREATE SEQUENCE course_sequence START 1;
CREATE TABLE course (
    id BIGINT PRIMARY KEY DEFAULT nextval('course_sequence'),
    _subject VARCHAR(255) NOT NULL,
    number INTEGER NOT NULL,
    title VARCHAR(255),
    class_detail_id BIGINT NOT NULL,
    class_plan_id BIGINT NOT NULL,
    FOREIGN KEY (class_detail_id) REFERENCES class_detail(id),
    FOREIGN KEY (class_plan_id) REFERENCES class_plan(id)
);

CREATE SEQUENCE class_schedule_sequence START WITH 1 INCREMENT BY 1;
CREATE TABLE class_schedule (
    id BIGINT PRIMARY KEY DEFAULT nextval('class_schedule_sequence'),
    day VARCHAR(255) NOT NULL,
    start_time VARCHAR(255) NOT NULL,
    end_time VARCHAR(255) NOT NULL,
    class_detail_id BIGINT,
    FOREIGN KEY (class_detail_id) REFERENCES class_detail(id)
);

CREATE SEQUENCE class_distribution_sequence START 1;
CREATE TABLE class_distribution (
    id BIGINT PRIMARY KEY DEFAULT nextval('class_distribution_sequence'),
    key VARCHAR(255) NOT NULL UNIQUE,
    input VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Insert initial enum values
INSERT INTO class_distribution (key, input) VALUES
('CONCENTRATED', 'concentrated'),
('SPARSE', 'sparse'),
('BALANCED', 'balanced');

CREATE SEQUENCE burden_capacity_sequence START 1;
CREATE TABLE burden_capacity (
    id BIGINT PRIMARY KEY DEFAULT nextval('burden_capacity_sequence'),
    key VARCHAR(255) NOT NULL,
    input VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert enum values
INSERT INTO burden_capacity (key, input) VALUES
    ('HIGH', 'high'),
    ('MEDIUM', 'medium'),
    ('LOW', 'low');
