CREATE DATABASE lights;
\c lights;

CREATE TABLE users(id serial PRIMARY KEY, blackout_id int, fname varchar, lname varchar,email varchar, location varchar ,password varchar);
CREATE TABLE blackouts(id serial PRIMARY  KEY, lights boolean);
CREATE TABLE blackouts_users(id serial PRIMARY KEY, user_id integer, blackout_id integer);
CREATE DATABASE lights_test WITH TEMPLATE lights;