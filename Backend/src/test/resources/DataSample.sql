
DELETE FROM party WHERE id > 0;
INSERT INTO `party` VALUES (1,'A - Socialdemokratiet'),
                           (2,'C - Det konservative Folkeparti\n'),
                           (3,'F - SF, Socialistisk Folkeparti'),
                           (4,'O - Dansk Folkeparti'),
                           (6,'Ø - Enhedslisten + De Rød Grønne'),
                           (5,'V - Venstre, Danmarks Liberale Parti');

DELETE FROM candidate WHERE id > 0;
INSERT INTO `candidate` VALUES
                            (1,'Marcel Meijer',1),
                            (2,'Michael Kristensen',1),
                            (3,'Helle Hansen',1),
                            (4,'Karina Knobelauch',1),
                            (5,'Stefan Hafstein Wolffbrandt',1),
                            (6,'Robert V. Rasmussen',1),
                            (7,'Pia Ramsing',1),
                            (8,'Anders Baun Sørensen',1),
                            (9,'Per Urban Olsen',2),
                            (10,'Peter Askjær',2),
                            (11,'Martin Sørensen',2),
                            (12,'Louise Bramstorp',2),
                            (13,'Sigfred Jensen',2),
                            (14,'Jørn C. Nissen',2),
                            (15,'Morten Ø. Kristensen',2),
                            (16,'Susanne Andersen',2),
                            (17,'Iulian V. Paiu',2),
                            (18,'Per Hingel',2),
                            (19,'Ulla Holm',3),
                            (20,'Kjeld Bønkel',3),
                            (21,'Anne Grethe Olsen',3),
                            (22,'Lone Krag',3),
                            (23,'Børge S. Buur',3),
                            (24,'Per Mortensen',4),
                            (25,'Søren Wiese',5),
                            (26,'Anita Elgaard Højholt Olesen',5),
                            (27,'Carsten Bruun',5),
                            (28,'Mogens Exner',5),
                            (29,'Anja Guldborg',5),
                            (30,'Klaus Holdorf',5),
                            (31,'Katrine Høegh Mc Quaid',6),
                            (32,'Jette M. Søgaard',6),
                            (33,'Søren Caspersen',6),
                            (34,'Pia Birkmand',6);


DELETE FROM vote WHERE cpr != 0;
INSERT INTO `vote` VALUES ('1112017123',34);