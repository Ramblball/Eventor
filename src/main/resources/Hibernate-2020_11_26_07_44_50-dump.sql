--
-- PostgreSQL database dump
--

-- Dumped from database version 12.5 (Ubuntu 12.5-0ubuntu0.20.04.1)
-- Dumped by pg_dump version 12.5 (Ubuntu 12.5-0ubuntu0.20.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: eventor_schema; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA eventor_schema;


ALTER SCHEMA eventor_schema OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: event; Type: TABLE; Schema: eventor_schema; Owner: postgres
--

CREATE TABLE eventor_schema.event (
    id integer NOT NULL,
    name character varying(32) NOT NULL,
    place character varying(128),
    "time" timestamp without time zone NOT NULL,
    description character varying(512) DEFAULT ''::bpchar NOT NULL,
    user_id integer NOT NULL,
    category integer NOT NULL
);


ALTER TABLE eventor_schema.event OWNER TO postgres;

--
-- Name: event_id_seq; Type: SEQUENCE; Schema: eventor_schema; Owner: postgres
--

CREATE SEQUENCE eventor_schema.event_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE eventor_schema.event_id_seq OWNER TO postgres;

--
-- Name: event_id_seq; Type: SEQUENCE OWNED BY; Schema: eventor_schema; Owner: postgres
--

ALTER SEQUENCE eventor_schema.event_id_seq OWNED BY eventor_schema.event.id;


--
-- Name: event_vector; Type: TABLE; Schema: eventor_schema; Owner: postgres
--

CREATE TABLE eventor_schema.event_vector (
    event_id integer NOT NULL,
    event_description tsvector
);


ALTER TABLE eventor_schema.event_vector OWNER TO postgres;

--
-- Name: user; Type: TABLE; Schema: eventor_schema; Owner: postgres
--

CREATE TABLE eventor_schema."user" (
    id integer NOT NULL,
    name character varying(32) NOT NULL,
    salt bytea NOT NULL,
    hash bytea NOT NULL
);


ALTER TABLE eventor_schema."user" OWNER TO postgres;

--
-- Name: user_id_seq; Type: SEQUENCE; Schema: eventor_schema; Owner: postgres
--

CREATE SEQUENCE eventor_schema.user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE eventor_schema.user_id_seq OWNER TO postgres;

--
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: eventor_schema; Owner: postgres
--

ALTER SEQUENCE eventor_schema.user_id_seq OWNED BY eventor_schema."user".id;


--
-- Name: users_events; Type: TABLE; Schema: eventor_schema; Owner: postgres
--

CREATE TABLE eventor_schema.users_events (
    user_id integer NOT NULL,
    event_id integer NOT NULL
);


ALTER TABLE eventor_schema.users_events OWNER TO postgres;

--
-- Name: event id; Type: DEFAULT; Schema: eventor_schema; Owner: postgres
--

ALTER TABLE ONLY eventor_schema.event ALTER COLUMN id SET DEFAULT nextval('eventor_schema.event_id_seq'::regclass);


--
-- Name: user id; Type: DEFAULT; Schema: eventor_schema; Owner: postgres
--

ALTER TABLE ONLY eventor_schema."user" ALTER COLUMN id SET DEFAULT nextval('eventor_schema.user_id_seq'::regclass);


--
-- Name: event event_pk; Type: CONSTRAINT; Schema: eventor_schema; Owner: postgres
--

ALTER TABLE ONLY eventor_schema.event
    ADD CONSTRAINT event_pk PRIMARY KEY (id);


--
-- Name: event_vector event_vector_pk; Type: CONSTRAINT; Schema: eventor_schema; Owner: postgres
--

ALTER TABLE ONLY eventor_schema.event_vector
    ADD CONSTRAINT event_vector_pk PRIMARY KEY (event_id);


--
-- Name: user user_pk; Type: CONSTRAINT; Schema: eventor_schema; Owner: postgres
--

ALTER TABLE ONLY eventor_schema."user"
    ADD CONSTRAINT user_pk PRIMARY KEY (id);


--
-- Name: event_id_uindex; Type: INDEX; Schema: eventor_schema; Owner: postgres
--

CREATE UNIQUE INDEX event_id_uindex ON eventor_schema.event USING btree (id);


--
-- Name: event_vector_event_id_uindex; Type: INDEX; Schema: eventor_schema; Owner: postgres
--

CREATE UNIQUE INDEX event_vector_event_id_uindex ON eventor_schema.event_vector USING btree (event_id);


--
-- Name: idx_gin_description; Type: INDEX; Schema: eventor_schema; Owner: postgres
--

CREATE INDEX idx_gin_description ON eventor_schema.event_vector USING gin (event_description);


--
-- Name: user_id_uindex; Type: INDEX; Schema: eventor_schema; Owner: postgres
--

CREATE UNIQUE INDEX user_id_uindex ON eventor_schema."user" USING btree (id);


--
-- Name: user_name_uindex; Type: INDEX; Schema: eventor_schema; Owner: postgres
--

CREATE UNIQUE INDEX user_name_uindex ON eventor_schema."user" USING btree (name);


--
-- Name: event event_user_id_fk; Type: FK CONSTRAINT; Schema: eventor_schema; Owner: postgres
--

ALTER TABLE ONLY eventor_schema.event
    ADD CONSTRAINT event_user_id_fk FOREIGN KEY (user_id) REFERENCES eventor_schema."user"(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: users_events users_events_event_id_fk; Type: FK CONSTRAINT; Schema: eventor_schema; Owner: postgres
--

ALTER TABLE ONLY eventor_schema.users_events
    ADD CONSTRAINT users_events_event_id_fk FOREIGN KEY (event_id) REFERENCES eventor_schema.event(id) ON DELETE CASCADE;


--
-- Name: users_events users_events_user_id_fk; Type: FK CONSTRAINT; Schema: eventor_schema; Owner: postgres
--

ALTER TABLE ONLY eventor_schema.users_events
    ADD CONSTRAINT users_events_user_id_fk FOREIGN KEY (user_id) REFERENCES eventor_schema."user"(id) ON DELETE CASCADE;


--
-- Name: event_vector vector_event_id_fk; Type: FK CONSTRAINT; Schema: eventor_schema; Owner: postgres
--

ALTER TABLE ONLY eventor_schema.event_vector
    ADD CONSTRAINT vector_event_id_fk FOREIGN KEY (event_id) REFERENCES eventor_schema.event(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

