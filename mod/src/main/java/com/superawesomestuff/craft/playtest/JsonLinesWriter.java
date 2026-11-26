package com.superawesomestuff.craft.playtest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.superawesomestuff.craft.SuperAwesomeCraft;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Appends JSON lines to one file per day ({@code <date>.jsonl}) in a directory, on a background thread so the server
 * never waits on the disk. A failed write is logged and dropped.
 */
final class JsonLinesWriter {
	private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();
	private final Path directory;
	private final ExecutorService executor = Executors.newSingleThreadExecutor(runnable -> {
		Thread thread = new Thread(runnable, "SuperAwesomeCraft playtest log");
		thread.setDaemon(true);
		return thread;
	});

	JsonLinesWriter(Path directory) {
		this.directory = directory;
	}

	Path directory() {
		return this.directory;
	}

	void write(JsonObject line) {
		String text = GSON.toJson(line) + "\n";
		this.executor.execute(() -> {
			Path file = this.directory.resolve(LocalDate.now() + ".jsonl");
			try {
				Files.createDirectories(this.directory);
				Files.writeString(file, text, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			} catch (IOException e) {
				SuperAwesomeCraft.LOGGER.warn("Couldn't write to the playtest log {}", file, e);
			}
		});
	}

	/** Finishes pending writes (waiting up to 10 seconds) and stops the thread. */
	void close() {
		this.executor.shutdown();
		try {
			this.executor.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
