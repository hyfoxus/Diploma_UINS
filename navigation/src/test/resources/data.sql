INSERT INTO "PUBLIC"."VERTEX" (AVAILABILITY, TYPE, DESCRIPTION, NAME) VALUES
                                  (TRUE, 2, 'Main Entrance', 'Enter'),
                                  (TRUE, 1, 'Crossroad near entrance, toilet and living Room', 'Crossroad'),
                                  (TRUE, 0, 'Toilet first floor', 'Toilet'),
                                  (TRUE, 0, 'Living Room first floor', 'Living Room'),
                                  (TRUE, 1, 'Crossroad near ladder', 'Crossroad near ladder'),
                                  (TRUE, 2, 'Ladder to second floor', 'Ladder'),
                                  (TRUE, 1, 'Crossroad to kitchen and library', 'Crossroad'),
                                  (TRUE, 0, 'Library first floor', 'Library'),
                                  (TRUE, 0, 'Kitchen first floor', 'Kitchen'),
                                  (TRUE, 2, 'Ladder from First floor', 'Ladder from first floor'),
                                  (TRUE, 1, 'Crossroad second floor near ladder', 'Crossroad second floor'),
                                  (TRUE, 2, 'Ladder to third floor', 'Ladder to third flood'),
                                  (TRUE, 1, 'Crossroad second floor', 'Crossroad between toilet and bath'),
                                  (TRUE, 0, 'Toilet second floor', 'Toilet'),
                                  (TRUE, 0, 'Bath Second floor', 'Bath'),
                                  (TRUE, 1, 'Crossroad to Circle', 'Crossroad Enter to circle'),
                                  (TRUE, 1, 'Circle', 'Circle 17'),
                                  (TRUE, 0, 'Circle room', 'Circle Room 18'),
                                  (TRUE, 1, 'Circle', 'Circle 19'),
                                  (TRUE, 0, 'Circle room', 'Circle Room 20'),
                                  (TRUE, 1, 'Circle', 'Circle 21'),
                                  (TRUE, 0, 'Circle room', 'Circle Room 22'),
                                  (TRUE, 1, 'Circle', 'Circle 23'),
                                  (TRUE, 0, 'Circle room', 'Circle Room 24'),
                                  (TRUE, 1, 'Circle', 'Circle 25'),
                                  (TRUE, 0, 'Circle room', 'Circle Room 26'),
                                  (TRUE, 1, 'Circle', 'Circle 27'),
                                  (TRUE, 0, 'Circle room', 'Circle Room 28'),
                                  (TRUE, 2, 'Ladder from Second Floor', 'Ladder from Second Floor'),
                                  (TRUE, 0, 'Office Third Floor', 'Office 30'),
                                  (TRUE, 1, 'Crossroad 31 Third Floor', 'Crossroad 31 Third Floor'),
                                  (TRUE, 0, 'Storage Third Floor', 'Storage 32'),
                                  (TRUE, 0, 'Storage Third Floor', 'Storage 33'),
                                  (TRUE, 1, 'Crossroad 34 Third Floor', 'Crossroad 34 Third Floor'),
                                  (TRUE, 0, 'Toilet Third Floor', 'Storage');


INSERT INTO "PUBLIC"."EDGE" (DIRECTION, DISTANCE, TYPE, VERTEX1_ID , VERTEX2_ID) VALUES
                                         (0, 10, 1, 1, 2),
                                         (90, 5, 1, 2, 3),
                                         (270, 5, 1, 2, 4),
                                         (0, 20, 1, 4, 5),
                                         (270, 5, 1, 5, 6),
                                         (0, 10, 1, 6, 7),
                                         (90, 5, 1, 7, 8),
                                         (270, 5, 1, 7, 9),
                                         (90, 10, 0, 9, 10),
                                         (90, 5, 1, 10, 11),
                                         (0, 10, 1, 11, 12),
                                         (0, 10, 1, 12, 13),
                                         (90, 5, 1, 13, 14),
                                         (270, 5, 1, 14, 15),
                                         (180, 10, 1, 15, 16),
                                         (90, 10, 1, 16, 17),
                                         (45, 2, 1, 17, 18),
                                         (135, 10, 1, 18, 19),
                                         (90, 2, 1, 19, 20),
                                         (225, 10, 1, 20, 21),
                                         (135, 2, 1, 21, 22),
                                         (270, 20, 1, 22, 23),
                                         (225, 2, 1, 23, 24),
                                         (315, 10, 1, 24, 25),
                                         (270, 2, 1, 25, 26),
                                         (45, 10, 1, 26, 27),
                                         (315, 2, 1, 27, 28),
                                         (90, 10, 1, 28, 16),
                                         (90, 10, 0, 29, 29),
                                         (180, 10, 1, 30, 30),
                                         (90, 10, 1, 31, 31),
                                         (180, 2, 1, 32, 32),
                                         (0, 2, 1, 33, 33),
                                         (0, 10, 1, 34, 34),
                                         (270, 2, 1, 35, 35);
INSERT INTO "PUBLIC"."VERTEX_ANGLES" VALUES
                                         (0, 1, 1),
                                         (180, 1, 2),
                                         (90, 2, 2),
                                         (270, 2, 3),
                                         (270, 3, 2),
                                         (90, 3, 4),
                                         (0, 4, 2),
                                         (180, 4, 5),
                                         (270, 5, 5),
                                         (90, 5, 6),
                                         (0, 6, 5),
                                         (180, 6, 7),
                                         (270, 7, 7),
                                         (90, 7, 8),
                                         (90, 8, 7),
                                         (270, 8, 9),
                                         (90, 9, 6),
                                         (270, 9, 10),
                                         (90, 10, 10),
                                         (270, 10, 11),
                                         (0, 11, 11),
                                         (180, 11, 12),
                                         (0, 12, 12),
                                         (180, 12, 13),
                                         (90, 13, 13),
                                         (270, 13, 14),
                                         (270, 14, 13),
                                         (90, 14, 15),
                                         (180, 15, 11),
                                         (0, 15, 16),
                                         (90, 16, 16),
                                         (270, 16, 17),
                                         (45, 17, 17),
                                         (225, 17, 18),
                                         (135, 18, 17),
                                         (315, 18, 19),
                                         (90, 19, 19),
                                         (270, 19, 20),
                                         (215, 20, 19),
                                         (35, 20, 21),
                                         (135, 21, 21),
                                         (315, 21, 22),
                                         (270, 22, 21),
                                         (90, 22, 23),
                                         (215, 23, 23),
                                         (35, 23, 24),
                                         (305, 24, 23),
                                         (125, 24, 25),
                                         (270, 25, 25),
                                         (90, 25, 26),
                                         (45, 26, 25),
                                         (225, 26, 27),
                                         (305, 27, 27),
                                         (125, 27, 28),
                                         (90, 28, 27),
                                         (270, 28, 16),
                                         (90, 29, 12),
                                         (270, 29, 29),
                                         (180, 30, 29),
                                         (0, 30, 30),
                                         (90, 31, 29),
                                         (270, 31, 31),
                                         (180, 32, 31),
                                         (0, 32, 32),
                                         (0, 33, 31),
                                         (180, 33, 33),
                                         (0, 34, 29),
                                         (180, 34, 34),
                                         (270, 35, 34),
                                         (90, 35, 35);
INSERT INTO "PUBLIC"."SCHEME" (level) VALUES
                                          (1),
                                          (1),
                                          (1),
                                          (2);
INSERT INTO "PUBLIC"."SCHEME_EDGES" VALUES
                                        (1, 1),
                                        (2, 1),
                                        (3, 1),
                                        (4, 1),
                                        (5, 1),
                                        (6, 1),
                                        (7, 1),
                                        (8, 1),
                                        (10, 2),
                                        (11, 2),
                                        (12, 2),
                                        (13, 2),
                                        (14, 2),
                                        (15, 2),
                                        (16, 2),
                                        (17, 2),
                                        (18, 2),
                                        (19, 2),
                                        (20, 2),
                                        (21, 2),
                                        (22, 2),
                                        (23, 2),
                                        (24, 2),
                                        (25, 2),
                                        (26, 2),
                                        (27, 2),
                                        (28, 2),
                                        (30, 3),
                                        (31, 3),
                                        (32, 3),
                                        (33, 3),
                                        (34, 3),
                                        (35, 3),
                                        (1, 4),
                                        (2, 4),
                                        (3, 4),
                                        (4, 4),
                                        (5, 4),
                                        (6, 4),
                                        (7, 4),
                                        (8, 4),
                                        (9, 4),
                                        (10, 4),
                                        (11, 4),
                                        (12, 4),
                                        (13, 4),
                                        (14, 4),
                                        (15, 4),
                                        (16, 4),
                                        (17, 4),
                                        (18, 4),
                                        (19, 4),
                                        (20, 4),
                                        (21, 4),
                                        (22, 4),
                                        (23, 4),
                                        (24, 4),
                                        (25, 4),
                                        (26, 4),
                                        (27, 4),
                                        (28, 4),
                                        (29, 4),
                                        (30, 4),
                                        (31, 4),
                                        (32, 4),
                                        (33, 4),
                                        (34, 4),
                                        (35, 4);
INSERT INTO "PUBLIC"."SCHEME_VERTEXES" VALUES
                                           (1, 1),
                                           (1, 2),
                                           (1, 3),
                                           (1, 4),
                                           (1, 5),
                                           (1, 6),
                                           (1, 7),
                                           (1, 8),
                                           (1, 9),
                                           (2, 10),
                                           (2, 11),
                                           (2, 12),
                                           (2, 13),
                                           (2, 14),
                                           (2, 15),
                                           (2, 16),
                                           (2, 17),
                                           (2, 18),
                                           (2, 19),
                                           (2, 20),
                                           (2, 21),
                                           (2, 22),
                                           (2, 23),
                                           (2, 24),
                                           (2, 25),
                                           (2, 26),
                                           (2, 27),
                                           (2, 28),
                                           (3, 29),
                                           (3, 30),
                                           (3, 31),
                                           (3, 32),
                                           (3, 33),
                                           (3, 34),
                                           (3, 35),
                                           (4, 1),
                                           (4, 2),
                                           (4, 3),
                                           (4, 4),
                                           (4, 5),
                                           (4, 6),
                                           (4, 7),
                                           (4, 8),
                                           (4, 9),
                                           (4, 10),
                                           (4, 11),
                                           (4, 12),
                                           (4, 13),
                                           (4, 14),
                                           (4, 15),
                                           (4, 16),
                                           (4, 17),
                                           (4, 18),
                                           (4, 19),
                                           (4, 20),
                                           (4, 21),
                                           (4, 22),
                                           (4, 23),
                                           (4, 24),
                                           (4, 25),
                                           (4, 26),
                                           (4, 27),
                                           (4, 28),
                                           (4, 29),
                                           (4, 30),
                                           (4, 31),
                                           (4, 32),
                                           (4, 33),
                                           (4, 34),
                                           (4, 35);
INSERT INTO "PUBLIC"."SCHEME_SUB_SCHEME_IDS" VALUES
                                                 (4, 1),
                                                 (4, 2),
                                                 (4, 3);