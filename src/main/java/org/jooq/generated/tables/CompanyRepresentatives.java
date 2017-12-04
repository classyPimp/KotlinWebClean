/*
 * This file is generated by jOOQ.
*/
package org.jooq.generated.tables;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.generated.DefaultSchema;
import org.jooq.generated.Keys;
import org.jooq.generated.tables.records.CompanyRepresentativesRecord;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CompanyRepresentatives extends TableImpl<CompanyRepresentativesRecord> {

    private static final long serialVersionUID = 1751314355;

    /**
     * The reference instance of <code>company_representatives</code>
     */
    public static final CompanyRepresentatives COMPANY_REPRESENTATIVES = new CompanyRepresentatives();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CompanyRepresentativesRecord> getRecordType() {
        return CompanyRepresentativesRecord.class;
    }

    /**
     * The column <code>company_representatives.id</code>.
     */
    public final TableField<CompanyRepresentativesRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('company_representatives_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>company_representatives.name</code>.
     */
    public final TableField<CompanyRepresentativesRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>company_representatives.bin</code>.
     */
    public final TableField<CompanyRepresentativesRecord, String> BIN = createField("bin", org.jooq.impl.SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>company_representatives.identifying_document</code>.
     */
    public final TableField<CompanyRepresentativesRecord, String> IDENTIFYING_DOCUMENT = createField("identifying_document", org.jooq.impl.SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>company_representatives.company_id</code>.
     */
    public final TableField<CompanyRepresentativesRecord, Long> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>company_representatives.created_at</code>.
     */
    public final TableField<CompanyRepresentativesRecord, Timestamp> CREATED_AT = createField("created_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>company_representatives.updated_at</code>.
     */
    public final TableField<CompanyRepresentativesRecord, Timestamp> UPDATED_AT = createField("updated_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * Create a <code>company_representatives</code> table reference
     */
    public CompanyRepresentatives() {
        this("company_representatives", null);
    }

    /**
     * Create an aliased <code>company_representatives</code> table reference
     */
    public CompanyRepresentatives(String alias) {
        this(alias, COMPANY_REPRESENTATIVES);
    }

    private CompanyRepresentatives(String alias, Table<CompanyRepresentativesRecord> aliased) {
        this(alias, aliased, null);
    }

    private CompanyRepresentatives(String alias, Table<CompanyRepresentativesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return DefaultSchema.DEFAULT_SCHEMA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<CompanyRepresentativesRecord, Long> getIdentity() {
        return Keys.IDENTITY_COMPANY_REPRESENTATIVES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CompanyRepresentativesRecord> getPrimaryKey() {
        return Keys.COMPANY_REPRESENTATIVES_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CompanyRepresentativesRecord>> getKeys() {
        return Arrays.<UniqueKey<CompanyRepresentativesRecord>>asList(Keys.COMPANY_REPRESENTATIVES_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<CompanyRepresentativesRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<CompanyRepresentativesRecord, ?>>asList(Keys.COMPANY_REPRESENTATIVES__FK_RAILS_C5C4A9F4EC);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompanyRepresentatives as(String alias) {
        return new CompanyRepresentatives(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public CompanyRepresentatives rename(String name) {
        return new CompanyRepresentatives(name, null);
    }
}
