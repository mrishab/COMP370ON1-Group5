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
VALUES ('Unauthenticated', 'User', 'test@test.com', 'NONE');

CREATE SEQUENCE grad_plan_sequence START 1;
CREATE TABLE grad_plan (
    id BIGINT PRIMARY KEY DEFAULT nextval('grad_plan_sequence'),
    file_name VARCHAR(255) NOT NULL,
    pdf_content_base64 TEXT
    program_name VARCHAR(255) NOT NULL,
    major_name VARCHAR(255) NOT NULL,
    credits_completed BIGINT NOT NULL,
    credits_required BIGINT NOT NULL,
    cgpa DOUBLE PRECISION,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    audited_at TIMESTAMP,
    calendar_term_semester VARCHAR(255) NOT NULL,
    calendar_term_year INTEGER NOT NULL,
    program_level VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    archived BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES _user(id)
);

CREATE SEQUENCE class_plan_sequence START 1;
CREATE TABLE class_plan (
    id BIGINT PRIMARY KEY DEFAULT nextval('class_plan_sequence'),
    description VARCHAR(255),
    grad_plan_id BIGINT NOT NULL,
    classes JSONB,
    availability JSONB,
    class_distribution JSONB,
    burden_capacity JSONB,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    archived BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (grad_plan_id) REFERENCES grad_plan(id)
);

CREATE INDEX idx_grad_plan_user_id ON grad_plan(user_id);
CREATE INDEX idx_class_plan_grad_plan_id ON class_plan(grad_plan_id);

-- Course class and schedules
CREATE SEQUENCE class_sequence START 1;
CREATE TABLE course_class (
    id BIGINT PRIMARY KEY DEFAULT nextval('class_sequence'),
    section VARCHAR(255) NOT NULL,
    instructor VARCHAR(255) NOT NULL,
    crn VARCHAR(255) NOT NULL,
    room VARCHAR(255) NOT NULL,
    method VARCHAR(255) NOT NULL,
    course_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    archived BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (course_id) REFERENCES course(id)
);

CREATE TABLE course_class_class_schedules (
    course_class_id BIGINT NOT NULL,
    day_of_week VARCHAR(255),
    start_time VARCHAR(255),
    end_time VARCHAR(255),
    FOREIGN KEY (course_class_id) REFERENCES course_class(id)
);

CREATE INDEX idx_course_class_course_id ON course_class(course_id);

-- Course table
CREATE SEQUENCE course_sequence START 1;
CREATE TABLE course (
    id BIGINT PRIMARY KEY DEFAULT nextval('course_sequence'),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    course_code VARCHAR(255) NOT NULL,
    credits VARCHAR(255) NOT NULL,
    course_number VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    archived BOOLEAN DEFAULT FALSE
);

CREATE SEQUENCE class_schedule_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE class_schedule (
    id BIGINT PRIMARY KEY,
    day VARCHAR(255) NOT NULL,
    start_time VARCHAR(255) NOT NULL,
    end_time VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    archived BOOLEAN
);

CREATE TABLE class_distribution (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    key VARCHAR(255) NOT NULL UNIQUE,
    input VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    archived BOOLEAN
);

-- Insert initial enum values
INSERT INTO class_distribution (key, input) VALUES
('CONCENTRATED', 'concentrated'),
('SPARSE', 'sparse');

CREATE TABLE burden_capacity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    key VARCHAR(255) NOT NULL,
    input VARCHAR(255) NOT NULL,
    archived BOOLEAN,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert enum values
INSERT INTO burden_capacity (key, input) VALUES
    ('HARD', 'hard'),
    ('MEDIUM', 'medium'),
    ('LOW', 'low');

CREATE SEQUENCE availability_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE availability_day_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE availability_hour_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE availability (
    id BIGINT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    archived BOOLEAN
);

CREATE TABLE availability_day (
    id BIGINT PRIMARY KEY,
    availability_id BIGINT NOT NULL,
    day VARCHAR(255) NOT NULL,
    FOREIGN KEY (availability_id) REFERENCES availability(id)
);

CREATE TABLE availability_hour (
    id BIGINT PRIMARY KEY,
    availability_day_id BIGINT NOT NULL,
    hour_of_the_day INTEGER NOT NULL,
    is_available BOOLEAN NOT NULL,
    FOREIGN KEY (availability_day_id) REFERENCES availability_day(id)
);
