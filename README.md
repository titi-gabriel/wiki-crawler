# wiki-crawler
Prerequisites: Java 17, PostgresSQL, Postman(optional)

To run the app you'll need to have the following tables created in the database:

CREATE TABLE public.countries (
	country_id serial NOT NULL,
	country_name text NULL,
	population int4 NULL,
	area numeric NULL,
	CONSTRAINT countries_pkey PRIMARY KEY (country_id)
);

CREATE TABLE public.cities (
	city_id serial NOT NULL,
	country_id int4 NULL,
	"name" text NULL,
	population int4 NULL,
	density numeric NULL,
	CONSTRAINT cities_pkey PRIMARY KEY (city_id)
);
ALTER TABLE public.cities ADD CONSTRAINT fk_country FOREIGN KEY (country_id) REFERENCES public.countries(country_id);

In "application.properties" file can be found and changed the url and credentials for DB connectivity. Please change them accordingly to your DB environment.

Endpoints:
 - POST at http://localhost:8080/countries -> will read the countries data from wikipedia and save them in the DB
 - GET at http://localhost:8080/countries -> will return a list of all countries saved in the DB
 - POST at http://localhost:8080/cities ->  will read the cities data from wikipedia and save them in the DB
 - GET at http://localhost:8080/cities -> will return a list of all cities saved in the DB
 
Estimated development time ~2-3h
