/*
 * This file is generated by jOOQ.
*/
package org.jooq.generated.tables;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.generated.DefaultSchema;
import org.jooq.generated.Keys;
import org.jooq.generated.tables.records.IncorporationFormsRecord;
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
public class IncorporationForms extends TableImpl<IncorporationFormsRecord> {

    private static final long serialVersionUID = 1680220925;

    /**
     * The reference instance of <code>incorporation_forms</code>
     */
    public static final IncorporationForms INCORPORATION_FORMS = new IncorporationForms();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<IncorporationFormsRecord> getRecordType() {
        return IncorporationFormsRecord.class;
    }

    /**
     * The column <code>incorporation_forms.id</code>.
     */
    public final TableField<IncorporationFormsRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('incorporation_forms_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>incorporation_forms.name</code>.
     */
    public final TableField<IncorporationFormsRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>incorporation_forms.name_short</code>.
     */
    public final TableField<IncorporationFormsRecord, String> NAME_SHORT = createField("name_short", org.jooq.impl.SQLDataType.VARCHAR, this, "");

    /**
     * The column <code>incorporation_forms.created_at</code>.
     */
    public final TableField<IncorporationFormsRecord, Timestamp> CREATED_AT = createField("created_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>incorporation_forms.updated_at</code>.
     */
    public final TableField<IncorporationFormsRecord, Timestamp> UPDATED_AT = createField("updated_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * Create a <code>incorporation_forms</code> table reference
     */
    public IncorporationForms() {
        this("incorporation_forms", null);
    }

    /**
     * Create an aliased <code>incorporation_forms</code> table reference
     */
    public IncorporationForms(String alias) {
        this(alias, INCORPORATION_FORMS);
    }

    private IncorporationForms(String alias, Table<IncorporationFormsRecord> aliased) {
        this(alias, aliased, null);
    }

    private IncorporationForms(String alias, Table<IncorporationFormsRecord> aliased, Field<?>[] parameters) {
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
    public Identity<IncorporationFormsRecord, Long> getIdentity() {
        return Keys.IDENTITY_INCORPORATION_FORMS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<IncorporationFormsRecord> getPrimaryKey() {
        return Keys.INCORPORATION_FORMS_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<IncorporationFormsRecord>> getKeys() {
        return Arrays.<UniqueKey<IncorporationFormsRecord>>asList(Keys.INCORPORATION_FORMS_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IncorporationForms as(String alias) {
        return new IncorporationForms(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public IncorporationForms rename(String name) {
        return new IncorporationForms(name, null);
    }
}
