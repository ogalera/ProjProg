--BORRAR TAULES
DROP TABLE Usuaris;
DROP TABLE Punts;

--TAULA DE USUARIS REGISTRATS A L'APLICACIÓ
CREATE TABLE Usuaris (
  usu_id INTEGER PRIMARY KEY AUTOINCREMENT,
  usu_nom TEXT NOT NULL,
  usu_pass TEXT NOT NULL,
  usu_path_imatge TEXT NOT NULL);
  
--TAULA PER PORTAR UN REGISTRE DELS PUNTS DE CADA USUARI;
CREATE TABLE Punts(
 pnt_nivell INTEGER NOT NULL,
 pnt_usu_id INTEGER NOT NULL,
 pnt_punts INTEGER NOT NULL,
 PRIMARY KEY (pnt_nivell, pnt_usu_id),
 FOREIGN KEY (pnt_usu_id) REFERENCES Usuaris(usu_id));
 
 --SENTENCIA PER AFEGIR UN REGISTRE A LA TAULA DE USUARIS
 INSERT INTO USUARIS (usu_nom, usu_pass)
 VALUES ('LeBron', 'the_King')
 
 --SENTENCIA PER AFEGIR UN REGISTRE A LA TAULA DE PUNTS
 INSERT INTO PUNTS (pnt_nivell, pnt_usu_id, pnt_punts)
 VALUES (5, 2, 500000);
 
 --PUNTS DE UN USUARI
 SELECT u.usu_nom AS "USUARI" , p.pnt_nivell AS "NIVELL" , p.pnt_punts AS "PUNTS"
 FROM usuaris u, punts p
 WHERE u.usu_id = p.pnt_usu_id AND p.usu_id = :ID_USUARI
 
 --TOP 10 USUARIS
 SELECT SUM(p.pnt_punts) AS "PUNTS", u.usu_nom AS "USUARI"
 FROM punts p, usuaris u
 WHERE u.usu_id = p.pnt_usu_id
 GROUP BY u.usu_id, u.usu_nom
 ORDER BY 1 DESC;
 
 --EXISTEIX UN USUARI
 SELECT 1
 WHERE EXISTS (SELECT usu_nom
					FROM USUARIS
					WHERE usu_nom = 'oscar')
 
 SELECT * FROM USUARIS
 
 SELECT usu_id, usu_nom, usu_ruta_imatge, nivell
 FROM usuaris,  (SELECT MAX(pnt_nivell) FROM punts where pnt_usu_id = usu_id)
 where usu_nom = 'Oscar'
 
 SELECT * FROM PUNTS