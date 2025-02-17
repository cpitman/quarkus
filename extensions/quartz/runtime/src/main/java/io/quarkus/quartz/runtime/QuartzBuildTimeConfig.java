package io.quarkus.quartz.runtime;

import java.util.Map;
import java.util.Optional;

import io.quarkus.runtime.annotations.ConfigDocMapKey;
import io.quarkus.runtime.annotations.ConfigDocSection;
import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

@ConfigRoot(phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public class QuartzBuildTimeConfig {
    /**
     * Enable cluster mode or not.
     * <p>
     * If enabled make sure to set the appropriate cluster properties.
     */
    @ConfigItem
    public boolean clustered;

    /**
     * The frequency (in milliseconds) at which the scheduler instance checks-in with other instances of the cluster.
     */
    @ConfigItem(defaultValue = "15000")
    public long clusterCheckinInterval;

    /**
     * The type of store to use.
     * <p>
     * When using {@link StoreType#JDBC_CMT} or {@link StoreType#JDBC_TX} configuration values make sure that you have the
     * datasource configured. See <a href="https://quarkus.io/guides/datasource"> Configuring your datasource</a> for more
     * information.
     * <p>
     * To create Quartz tables, you can perform a schema migration via the <a href="https://quarkus.io/guides/flyway"> Flyway
     * extension</a> using a SQL script matching your database picked from <a href=
     * "https://github.com/quartz-scheduler/quartz/blob/master/quartz-core/src/main/resources/org/quartz/impl/jdbcjobstore">Quartz
     * repository</a>.
     */
    @ConfigItem(defaultValue = "ram")
    public StoreType storeType;

    /**
     * The name of the datasource to use.
     * <p>
     * Optionally needed when using the `jdbc-tx` or `jdbc-cmt` store types.
     * If not specified, defaults to using the default datasource.
     */
    @ConfigItem(name = "datasource")
    public Optional<String> dataSourceName;

    /**
     * The prefix for quartz job store tables.
     * <p>
     * Ignored if using a `ram` store.
     */
    @ConfigItem(defaultValue = "QRTZ_")
    public String tablePrefix;

    /**
     * The SQL string that selects a row in the "LOCKS" table and places a lock on the row.
     * <p>
     * If not set, the default value of Quartz applies, for which the "{0}" is replaced during run-time with the
     * `table-prefix`, the "{1}" with the `instance-name`.
     * <p>
     * An example SQL string `SELECT * FROM {0}LOCKS WHERE SCHED_NAME = {1} AND LOCK_NAME = ? FOR UPDATE`
     */
    @ConfigItem
    public Optional<String> selectWithLockSql;

    /**
     * Instance ID generators.
     */
    @ConfigItem
    @ConfigDocMapKey("generator-name")
    @ConfigDocSection
    public Map<String, QuartzExtensionPointConfig> instanceIdGenerators;

    /**
     * Trigger listeners.
     */
    @ConfigItem
    @ConfigDocMapKey("listener-name")
    @ConfigDocSection
    public Map<String, QuartzExtensionPointConfig> triggerListeners;
    /**
     * Job listeners.
     */
    @ConfigItem
    @ConfigDocMapKey("listener-name")
    @ConfigDocSection
    public Map<String, QuartzExtensionPointConfig> jobListeners;
    /**
     * Plugins.
     */
    @ConfigItem
    @ConfigDocMapKey("plugin-name")
    @ConfigDocSection
    public Map<String, QuartzExtensionPointConfig> plugins;
}
