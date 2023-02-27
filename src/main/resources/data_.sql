INSERT INTO franchise (name,description) VALUES ('X-Men Trilogy','Its a good trilogy');
INSERT INTO movie (movie_title, movie_genre, release_year, director, poster_URL, trailer_link,franchise_id) VALUES ('X-Men', 'action, science fiction', 2000, 'Bryan Singer', 'https://upload.wikimedia.org/wikipedia/en/8/81/X-MenfilmPoster.jpg', 'https://www.youtube.com/watch?v=VNxwlx6etXI',1);
INSERT INTO movie (movie_title, movie_genre, release_year, director, poster_URL, trailer_link,franchise_id) VALUES ('X2', 'action, science fiction', 2003, 'Bryan Singer', 'https://upload.wikimedia.org/wikipedia/en/3/3e/X2_poster.jpg', 'https://www.youtube.com/watch?v=luHcOYS1HxQ',1);
INSERT INTO characters (alias, full_name, gender, picture) VALUES ('Deadpool', 'Wade Wilson', 'Male', 'https://example.com/deadpool.jpg');
INSERT INTO characters (alias, full_name, gender, picture) VALUES ('Daredevil', 'Matt Murdock', 'Male', 'https://example.com/daredevil.jpg');
INSERT INTO characters (alias, full_name, gender, picture) VALUES ('Elektra', 'Elektra Natchios', 'Female', 'https://example.com/elektra.jpg');
INSERT INTO movie_characters (movie_id, character_id) VALUES (1,1);
INSERT INTO movie_characters (movie_id, character_id) VALUES (1,2);
INSERT INTO movie_characters (movie_id, character_id) VALUES (1,3);
