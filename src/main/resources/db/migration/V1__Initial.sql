CREATE TABLE towns (
  town_uuid CHAR(36) PRIMARY KEY,
  name      VARCHAR(16) UNIQUE NOT NULL,
  motd      TEXT               NOT NULL,
  open      BOOLEAN            NOT NULL
);

CREATE TABLE town_players (
  player_uuid  CHAR(36) PRIMARY KEY
);

CREATE TABLE town_player_towns (
  player_uuid CHAR(36) PRIMARY KEY,
  town_uuid   CHAR(36),
  rank        VARCHAR(36),
  FOREIGN KEY (player_uuid) REFERENCES town_players (player_uuid)
    ON DELETE CASCADE,
  FOREIGN KEY (town_uuid) REFERENCES towns (town_uuid)
    ON DELETE CASCADE
);

CREATE TABLE town_player_invites (
  target_player_uuid CHAR(36),
  target_town_uuid   CHAR(36),
  sender_player_uuid CHAR(36),
  declined           BOOL,
  PRIMARY KEY (target_player_uuid, target_town_uuid),
  FOREIGN KEY (target_player_uuid) REFERENCES town_players (player_uuid)
    ON DELETE CASCADE,
  FOREIGN KEY (target_town_uuid) REFERENCES towns (town_uuid)
    ON DELETE CASCADE,
  FOREIGN KEY (sender_player_uuid) REFERENCES town_players (player_uuid)
);

CREATE TABLE town_plot_bank (
  town_uuid CHAR(36),
  plot_type VARCHAR(16),
  amount    INT,
  PRIMARY KEY (town_uuid, plot_type),
  FOREIGN KEY (town_uuid) REFERENCES towns (town_uuid)
    ON DELETE CASCADE
);

CREATE TABLE plots (
  plot_uuid CHAR(36) PRIMARY KEY,
  name      VARCHAR(16),
  plot_type VARCHAR(16),
  town_uuid CHAR(36),
  x         INT,
  z         INT,
  world     CHAR(36),
  FOREIGN KEY (town_uuid) REFERENCES towns (town_uuid)
    ON DELETE CASCADE
);

CREATE TABLE plot_flags (
  plot_uuid CHAR(36)
);