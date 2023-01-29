-- liquibase formatted sql

-- changeset nS:1
CREATE TABLE notification_task
(
    id                     SERIAL,
    notification_text      TEXT,
    chat_id                BIGINT,
    notification_date_time TIMESTAMP
)