CREATE DATABASE lights;
\c lights;

CREATE TABLE users(id serial PRIMARY KEY, fname varchar, lname varchar,email varchar, location varchar ,password varchar);
CREATE TABLE blackouts(id serial PRIMARY  KEY, lights boolean);
CREATE DATABASE lights_test WITH TEMPLATE lights;