--CREATE SEQUENCE "PUBLIC"."EDGE_SEQ" START WITH 1 INCREMENT BY 5;
--CREATE SEQUENCE "PUBLIC"."VERTEX_SEQ" START WITH 1 INCREMENT BY 5;

CREATE MEMORY TABLE "PUBLIC"."EDGE"(
                                       "DIRECTION" INTEGER NOT NULL,
                                       "DISTANCE" INTEGER NOT NULL,
                                       "TYPE" TINYINT,
                                       "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
                                       "VERTEX1_ID" BIGINT,
                                       "VERTEX2_ID" BIGINT
);
ALTER TABLE "PUBLIC"."EDGE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_20" PRIMARY KEY("ID");

CREATE MEMORY TABLE "PUBLIC"."SCHEME"(
                                         "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
                                         "LEVEL" BIGINT,
                                         "DESCRIPTION" CHARACTER VARYING(255)
);
ALTER TABLE "PUBLIC"."SCHEME" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_9" PRIMARY KEY("ID");

CREATE MEMORY TABLE "PUBLIC"."SCHEME_EDGES"(
                                               "EDGE_ID" BIGINT NOT NULL,
                                               "SCHEME_ID" BIGINT NOT NULL
);

CREATE MEMORY TABLE "PUBLIC"."SCHEME_SUB_SCHEME_IDS"(
                                                        "SCHEME_ID" BIGINT NOT NULL,
                                                        "SUB_SCHEME_ID" BIGINT
);

CREATE MEMORY TABLE "PUBLIC"."SCHEME_VERTEXES"(
                                                  "SCHEME_ID" BIGINT NOT NULL,
                                                  "VERTEX_ID" BIGINT NOT NULL
);

CREATE MEMORY TABLE "PUBLIC"."VERTEX"(
                                         "AVAILABILITY" BOOLEAN,
                                         "TYPE" TINYINT,
                                         "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
                                         "DESCRIPTION" CHARACTER VARYING(255),
                                         "NAME" CHARACTER VARYING(255)
);
ALTER TABLE "PUBLIC"."VERTEX" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_96B" PRIMARY KEY("ID");

CREATE MEMORY TABLE "PUBLIC"."VERTEX_ANGLES"(
                                                "ANGLES" INTEGER,
                                                "ANGLES_KEY" BIGINT NOT NULL,
                                                "VERTEX_ID" BIGINT NOT NULL
);
ALTER TABLE "PUBLIC"."VERTEX_ANGLES" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_C" PRIMARY KEY("ANGLES_KEY", "VERTEX_ID");

ALTER TABLE "PUBLIC"."EDGE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_2" CHECK("TYPE" BETWEEN 0 AND 1) NOCHECK;
ALTER TABLE "PUBLIC"."VERTEX" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_96" CHECK("TYPE" BETWEEN 0 AND 2) NOCHECK;
ALTER TABLE "PUBLIC"."EDGE" ADD CONSTRAINT "PUBLIC"."FK7GRTS29D2VTBTR6ET2I2MXUST" FOREIGN KEY("VERTEX1_ID") REFERENCES "PUBLIC"."VERTEX"("ID") NOCHECK;
ALTER TABLE "PUBLIC"."SCHEME_VERTEXES" ADD CONSTRAINT "PUBLIC"."FK7GN9LSGUO1NC2J17PTGHSCF3P" FOREIGN KEY("SCHEME_ID") REFERENCES "PUBLIC"."SCHEME"("ID") NOCHECK;
ALTER TABLE "PUBLIC"."SCHEME_SUB_SCHEME_IDS" ADD CONSTRAINT "PUBLIC"."FKEDBL5RPPPS8IV12P5YFJD2QG" FOREIGN KEY("SCHEME_ID") REFERENCES "PUBLIC"."SCHEME"("ID") NOCHECK;
ALTER TABLE "PUBLIC"."SCHEME_EDGES" ADD CONSTRAINT "PUBLIC"."FK877XU20SD7AJIMM45HXBD4DHF" FOREIGN KEY("SCHEME_ID") REFERENCES "PUBLIC"."SCHEME"("ID") NOCHECK;
ALTER TABLE "PUBLIC"."SCHEME_EDGES" ADD CONSTRAINT "PUBLIC"."FKHS2L856X7X014R208ABMGLDWI" FOREIGN KEY("EDGE_ID") REFERENCES "PUBLIC"."EDGE"("ID") NOCHECK;
ALTER TABLE "PUBLIC"."VERTEX_ANGLES" ADD CONSTRAINT "PUBLIC"."FKALSKPWBN2U7JUW726IMQJTQ5V" FOREIGN KEY("VERTEX_ID") REFERENCES "PUBLIC"."VERTEX"("ID") NOCHECK;
ALTER TABLE "PUBLIC"."EDGE" ADD CONSTRAINT "PUBLIC"."FKD8BQWU8UIMWECKI59VELFDQBV" FOREIGN KEY("VERTEX2_ID") REFERENCES "PUBLIC"."VERTEX"("ID") NOCHECK;