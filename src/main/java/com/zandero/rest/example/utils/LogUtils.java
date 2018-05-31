package com.zandero.rest.example.utils;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import com.zandero.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * Logging helpers
 */
public final class LogUtils {

	private static final Logger log = LoggerFactory.getLogger(LogUtils.class);

	private LogUtils() {
		// hide constructor
	}

	/**
	 * Calculates duration in milliseconds from given start time until now()
	 *
	 * @param start time (must be before now())
	 * @return time passed in milliseconds (or negative time if start is after now())
	 */
	public static long durationMs(Instant start) {
		return Duration.between(start, Instant.now()).toMillis();
	}

	/**
	 * Sets logging config from given XML file
	 *
	 * @param logXmlFile file or resource
	 */
	public static void setConfig(String logXmlFile) {

		logXmlFile = StringUtils.trim(logXmlFile);
		if (StringUtils.isNullOrEmptyTrimmed(logXmlFile)) {
			// nothing to do
			log.info("Using internal logging settings!");
			return;
		}

		final URL xmlResource = LogUtils.class.getResource(logXmlFile);
		if (xmlResource != null && xmlResource.getFile() != null) {

			log.info("Reading logging configuration from resource '{}'", xmlResource.getFile());
			loadLogfile(new File(xmlResource.getFile()));
		}

		final File xmlFile = new File(logXmlFile);
		if (xmlFile.exists() && xmlFile.isFile()) {

			log.info("Reading logging configuration '{}'", logXmlFile);
			loadLogfile(xmlFile);
		}
	}

	private static void loadLogfile(final File logbackXmlFile) {
		final LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		final JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(context);
		context.reset();
		try {
			configurator.doConfigure(logbackXmlFile);
		} catch (final JoranException ex) {
			throw new RuntimeException(ex);
		}

		log.info("Logging initialized: {}", logbackXmlFile);
	}

	/**
	 * Formats handy memory usage output to be logged
	 *
	 * @param heapOnly true include heap data only, false add additional data
	 * @return multi line memory usage snippet
	 */
	public static String getMemoryUsage(boolean heapOnly) {

		StringBuilder builder = new StringBuilder();
		ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();

		builder.append(System.lineSeparator()).append("------ MEM -----").append(System.lineSeparator());
		builder.append("Heap: ")
		       .append(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage())
		       .append(System.lineSeparator());

		builder.append("NonHeap: ")
		       .append(ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage())
		       .append(System.lineSeparator());

		if (!heapOnly) {
			List<MemoryPoolMXBean> beans = ManagementFactory.getMemoryPoolMXBeans();
			for (MemoryPoolMXBean bean : beans) {
				builder.append(bean.getName()).append(": ")
				       .append(bean.getUsage())
				       .append(System.lineSeparator());
			}

			for (GarbageCollectorMXBean bean : ManagementFactory.getGarbageCollectorMXBeans()) {
				builder.append(bean.getName()).append(": ")
				       .append(bean.getCollectionCount()).append(" - ")
				       .append(bean.getCollectionTime())
				       .append(System.lineSeparator());
			}
		}

		builder.append("----------------").append(System.lineSeparator());
		return builder.toString();
	}
}
