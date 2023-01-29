-- liquibase formatted sql

-- changeset nS:1
CREATE TABLE notification_task
(
    id SERIAL,
    notificationText TEXT,
    chatId BIGINT,
    dateTime TIMESTAMP
)