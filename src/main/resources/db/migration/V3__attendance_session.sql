CREATE TABLE IF NOT EXISTS attendance_sessions (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),

    course_id UUID NOT NULL,
    initiator_id UUID NOT NULL,

    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    geo_radius_meters DOUBLE PRECISION NOT NULL,

    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,

    CONSTRAINT fk_attendance_course
        FOREIGN KEY (course_id)
        REFERENCES courses (id)
        ON DELETE CASCADE,

    CONSTRAINT fk_attendance_initiator
        FOREIGN KEY (initiator_id)
        REFERENCES users (id)
        ON DELETE CASCADE
);
