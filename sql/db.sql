DROP
    database IF EXISTS testdb;

CREATE
    DATABASE "agent";

CREATE TABLE IF NOT EXISTS public."user"
(
    id_user  SERIAL PRIMARY KEY,
    login    VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    email    VARCHAR(50),
    phone    VARCHAR(50),
    role     VARCHAR(25) NOT NULL,
    bank     INTEGER NOT NULL DEFAULT 0,
    name     VARCHAR(50) NOT NULL,
    surname  VARCHAR(50) NOT NULL,
    UNIQUE (login)
);

CREATE TABLE IF NOT EXISTS public."request"
(
    id_request          SERIAL PRIMARY KEY,
    description         VARCHAR(1000) NOT NULL,
    date                timestamp DEFAULT current_timestamp,
    complication_status VARCHAR(50)    NOT NULL DEFAULT 'under consideration',
    payment_status      VARCHAR(50)    NOT NULL DEFAULT 'unpaid',
    price               float4         NOT NULL DEFAULT 0,
    master_login VARCHAR(50)


);

CREATE TABLE IF NOT EXISTS public."user_request"
(
    login      VARCHAR REFERENCES "user" (login) on delete cascade,
    id_request INT REFERENCES request (id_request) on delete cascade,
    UNIQUE (login, id_request)
);

CREATE TABLE IF NOT EXISTS public."feedback"
(
    feedback_id   SERIAL PRIMARY KEY,
    feedback_text VARCHAR(1000) NOT NULL,
    rating        INTEGER       NOT NULL,
    date          timestamp DEFAULT current_timestamp,
    id_request    INTEGER   NOT NULL,
    master_login  VARCHAR(50),
    FOREIGN KEY (id_request) REFERENCES request (id_request)
);

INSERT INTO "user"
VALUES (DEFAULT, 'user3', 'Wizzartoz', 'misha2@mail.ru', '+380958316826', 'MANAGER', 0, 'misha', 'maznichko');




