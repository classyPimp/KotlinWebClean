/*
 * This file is generated by jOOQ.
*/
package org.jooq.generated.tables.records;


import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Row1;
import org.jooq.generated.tables.SchemaMigrations;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SchemaMigrationsRecord extends UpdatableRecordImpl<SchemaMigrationsRecord> implements Record1<String> {

    private static final long serialVersionUID = -581836531;

    /**
     * Setter for <code>schema_migrations.version</code>.
     */
    public void setVersion(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>schema_migrations.version</code>.
     */
    public String getVersion() {
        return (String) get(0);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record1 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row1<String> fieldsRow() {
        return (Row1) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row1<String> valuesRow() {
        return (Row1) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return SchemaMigrations.SCHEMA_MIGRATIONS.VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SchemaMigrationsRecord value1(String value) {
        setVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SchemaMigrationsRecord values(String value1) {
        value1(value1);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SchemaMigrationsRecord
     */
    public SchemaMigrationsRecord() {
        super(SchemaMigrations.SCHEMA_MIGRATIONS);
    }

    /**
     * Create a detached, initialised SchemaMigrationsRecord
     */
    public SchemaMigrationsRecord(String version) {
        super(SchemaMigrations.SCHEMA_MIGRATIONS);

        set(0, version);
    }
}
