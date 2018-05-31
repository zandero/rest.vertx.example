package com.zandero.rest.example;

import com.zandero.cmd.CommandBuilder;
import com.zandero.cmd.CommandLineException;
import com.zandero.cmd.CommandLineParser;
import com.zandero.cmd.option.CommandOption;
import com.zandero.cmd.option.IntOption;
import com.zandero.cmd.option.StringOption;
import com.zandero.settings.Settings;

import java.util.List;

/**
 * Global settings holding all other settings ...
 */
public class ServerSettings extends Settings {

	private static final String PORT = "port";
	private static final String POOL_SIZE = "pool";
	private static final String VERSION = "version";
	private static final String LOG_FILE = "log";

	private final CommandBuilder builder;

	public ServerSettings() {

		CommandOption port = new IntOption("p")
			                     .longCommand(PORT)
			                     .setting(PORT)
			                     .description("Listening on port")
			                     .defaultsTo(4444);

		CommandOption poolSize = new IntOption("P")
			                         .longCommand(POOL_SIZE)
			                         .setting(POOL_SIZE)
			                         .description("Worker threads pool size")
			                         .defaultsTo(10);

		CommandOption<String> logging = new StringOption("l")
			                                .longCommand(LOG_FILE)
			                                .setting(LOG_FILE)
			                                .description("Alternative log file")
			                                .defaultsTo(null);

		CommandOption<String> version = new StringOption("v")
			                                .longCommand(VERSION)
			                                .setting(VERSION)
			                                .description("Show version")
			                                .defaultsTo(null);

		builder = new CommandBuilder();
		builder.add(port);
		builder.add(poolSize);

		builder.add(logging);
		builder.add(version);
	}

	public void parse(String[] args) throws CommandLineException {
		CommandLineParser parser = new CommandLineParser(builder);
		putAll(parser.parse(args));
	}

	public boolean showVersion() {

		return find(VERSION) != null;
	}

	public List<String> getHelp() {
		return builder.getHelp();
	}

	public int getPort() {
		return getInt(PORT);
	}

	public String getLog() {
		return findString(LOG_FILE);
	}

	public int getPoolSize() {
		return getInt(POOL_SIZE);
	}
}
