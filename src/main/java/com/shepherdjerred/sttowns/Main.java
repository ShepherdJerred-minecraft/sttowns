package com.shepherdjerred.sttowns;


import com.shepherdjerred.riotbase.RiotBase;
import com.shepherdjerred.sttowns.commands.PlotExecutor;
import com.shepherdjerred.sttowns.commands.TownExecutor;
import com.shepherdjerred.sttowns.commands.registers.PlotNodeRegister;
import com.shepherdjerred.sttowns.commands.registers.TownNodeRegister;
import com.shepherdjerred.sttowns.config.SpigotTownsConfig;
import com.shepherdjerred.sttowns.controllers.PlotController;
import com.shepherdjerred.sttowns.controllers.TownController;
import com.shepherdjerred.sttowns.database.PlotDAO;
import com.shepherdjerred.sttowns.database.TownDAO;
import com.shepherdjerred.sttowns.database.TownInviteDAO;
import com.shepherdjerred.sttowns.database.TownPlayerDAO;
import com.shepherdjerred.sttowns.exceptions.TrackerException;
import com.shepherdjerred.sttowns.listeners.PlotListener;
import com.shepherdjerred.sttowns.listeners.TownPlayerListener;
import com.shepherdjerred.sttowns.messages.Parser;
import com.shepherdjerred.sttowns.objects.Town;
import com.shepherdjerred.sttowns.objects.ranks.Ranks;
import com.shepherdjerred.sttowns.objects.trackers.Entities;
import com.shepherdjerred.sttowns.objects.trackers.Plots;
import com.shepherdjerred.sttowns.objects.trackers.TownPlayers;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;
import org.flywaydb.core.Flyway;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;


public class Main extends RiotBase {

    private Parser parser;

    private Ranks ranks;

    private TownPlayers townPlayers;
    private Entities<Town> towns;
    private Plots plots;
    private PlotDAO plotDAO;

    private TownDAO townDAO;
    private TownPlayerDAO townPlayerDAO;
    private TownInviteDAO townInviteDAO;

    private TownController townController;
    private PlotController plotController;

    private HikariDataSource hikariDataSource;
    private FluentJdbc fluentJdbc;

    private SpigotTownsConfig spigotTownsConfig;

    @Override
    public void onEnable() {
        parser = new Parser(ResourceBundle.getBundle("messages"));

        createObjects();
        setupConfigs();
        loadRanks();
        setupDatabase();

        townController = new TownController(parser, towns, townPlayers, ranks, townDAO, townPlayerDAO, townInviteDAO);
        plotController = new PlotController(parser, plots, townDAO, plotDAO);

        // TODO Setup Vault
        if (spigotTownsConfig.isVaultEnabled()) {

        }

        registerCommands();
        registerListeners();

        checkOnlinePlayers();

        startMetrics();
    }

    private void createObjects() {
        ranks = new Ranks();

        townPlayers = new TownPlayers();
        towns = new Entities<>();
        plots = new Plots();
    }

    private void loadRanks() {
        try {
            ranks.loadTowns(FileUtils.readFileToString(new File(getDataFolder().getAbsolutePath() + "/towns.json")));
            ranks.loadNations();
            ranks.loadPlots();
        } catch (IOException | TrackerException e) {
            e.printStackTrace();
        }
    }

    private void setupConfigs() {
        copyFile(getResource("config.yml"), getDataFolder().getAbsolutePath() + "/config.yml");
        copyFile(getResource("messages.properties"), getDataFolder().getAbsolutePath() + "/messages.properties");

        spigotTownsConfig = new SpigotTownsConfig(getConfig());

        copyFile(getResource("towns.json"), getDataFolder().getAbsoluteFile() + "/towns.json");

    }

    private void setupDatabase() {
        copyFile(getResource("hikari.properties"), getDataFolder().getAbsolutePath() + "/hikari.properties");
        copyFile(getResource("db/migration/V1__Initial.sql"), getDataFolder().getAbsolutePath() + "/db/migration/V1__Initial.sql");

        HikariConfig hikariConfig = new HikariConfig(getDataFolder().getAbsolutePath() + "/hikari.properties");
        hikariDataSource = new HikariDataSource(hikariConfig);

        fluentJdbc = new FluentJdbcBuilder().connectionProvider(hikariDataSource).build();

        Flyway flyway = new Flyway();
        flyway.setLocations("filesystem:" + getDataFolder().getAbsolutePath() + "/db/migration/");
        flyway.setDataSource(hikariDataSource);
        flyway.migrate();

        townDAO = new TownDAO(fluentJdbc, ranks);
        townPlayerDAO = new TownPlayerDAO(fluentJdbc, towns);
        townInviteDAO = new TownInviteDAO(fluentJdbc);

        plotDAO = new PlotDAO(fluentJdbc, towns);

        townDAO.loadAll().forEach(town -> {
            try {
                towns.add(town);
            } catch (TrackerException e) {
                e.printStackTrace();
            }
        });

        plotDAO.loadAll().forEach(plot -> plots.add(plot));
    }

    private void registerCommands() {
        TownNodeRegister tnr = new TownNodeRegister(parser, towns, townPlayers, townController);
        tnr.addNode(new TownExecutor(tnr));
        tnr.registerCommandsForBukkit(this);
        PlotNodeRegister pnr = new PlotNodeRegister(parser, plots, townPlayers, plotController);
        pnr.addNode(new PlotExecutor(pnr));
        pnr.registerCommandsForBukkit(this);
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new TownPlayerListener(parser, townPlayers, townPlayerDAO), this);
        getServer().getPluginManager().registerEvents(new PlotListener(plots, townPlayers), this);
    }

    /**
     * Loop through online players, loading their data. This should only be called when reloading the plugin
     */
    private void checkOnlinePlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            // TODO Load the players from the database
        }
    }

}
