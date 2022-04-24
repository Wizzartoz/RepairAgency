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
    price               float4         NOT NULL DEFAULT 0

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
    master_login  VARCHAR(50)   NOT NULL,
    FOREIGN KEY (id_request) REFERENCES request (id_request)
);



INSERT INTO "user"
VALUES (DEFAULT, 'user1', 'magik455', 'misha@mail.ru', '+380958316823', 'MASTER', 250, 'anton', 'smoylev');
INSERT INTO "user"
VALUES (DEFAULT, 'user2', 'maznich40', 'misha1@mail.ru', '+380958316825', 'CUSTOMER', 250, 'misha', 'smoylev');
INSERT INTO "user"
VALUES (DEFAULT, 'user3', 'Wizzartoz', 'misha2@mail.ru', '+380958316826', 'MANAGER', 0, 'misha', 'maznichko');

INSERT INTO request
VALUES (DEFAULT, 'ВОТ ТАКАЯ ВОТ ЖИЗНЬ', DEFAULT, 'under consideration', 'unpaid', 100);
INSERT INTO request
VALUES (DEFAULT, 'ВОТ ТАКАЯ ВОТ ЖИЗНЬ', DEFAULT, 'under consideration', 'waiting for payment', 1000);
INSERT INTO request
VALUES (DEFAULT, 'ВОТ ТАКАЯ ВОТ ЖИЗНЬ', DEFAULT, 'done', 'paid', 250);

INSERT INTO feedback VALUES (DEFAULT,'меня всё устраивает',5,DEFAULT,1,'user1');

INSERT INTO user_request
VALUES ('user1', 1);
INSERT INTO user_request
VALUES ('user1', 2);
INSERT INTO user_request
VALUES ('user2', 3);




