INSERT INTO "PUBLIC"."VERTEX" VALUES
                                  (TRUE, 1, 0, 1, NULL, '113 cabinet', '113'),
                                  (TRUE, 1, 0, 2, NULL, '112 cabinet', '112'),
                                  (TRUE, 1, 0, 3, NULL, '111 cabinet', '111'),
                                  (TRUE, 1, 1, 4, NULL, 'big crossroad', 'crossroad'),
                                  (TRUE, 1, 1, 5, NULL, 'very big crossroad', 'very crossroad');

INSERT INTO "PUBLIC"."EDGE" VALUES
                                (200, 1, NULL, 2, 4),
                                (200, 2, NULL, 1, 4),
                                (200, 3, NULL, 5, 4),
                                (200, 4, NULL, 5, 3);
INSERT INTO "PUBLIC"."VERTEX_NEIGHBORS" VALUES
                                            (0, 4, 2),
                                            (180, 2, 4),
                                            (180, 4, 1),
                                            (0, 1, 4),
                                            (90, 4, 5),
                                            (270, 5, 4),
                                            (0, 3, 5),
                                            (180, 5, 3);