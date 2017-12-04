/*
 * This file is generated by jOOQ.
*/
package org.jooq.generated;


import javax.annotation.Generated;

import org.jooq.generated.tables.Accounts;
import org.jooq.generated.tables.ArInternalMetadata;
import org.jooq.generated.tables.Avatars;
import org.jooq.generated.tables.Companies;
import org.jooq.generated.tables.CompanyRepresentatives;
import org.jooq.generated.tables.IncorporationForms;
import org.jooq.generated.tables.PowerDocuments;
import org.jooq.generated.tables.SchemaMigrations;
import org.jooq.generated.tables.Users;


/**
 * Convenience access to all tables in 
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>accounts</code>.
     */
    public static final Accounts ACCOUNTS = org.jooq.generated.tables.Accounts.ACCOUNTS;

    /**
     * The table <code>ar_internal_metadata</code>.
     */
    public static final ArInternalMetadata AR_INTERNAL_METADATA = org.jooq.generated.tables.ArInternalMetadata.AR_INTERNAL_METADATA;

    /**
     * The table <code>avatars</code>.
     */
    public static final Avatars AVATARS = org.jooq.generated.tables.Avatars.AVATARS;

    /**
     * The table <code>companies</code>.
     */
    public static final Companies COMPANIES = org.jooq.generated.tables.Companies.COMPANIES;

    /**
     * The table <code>company_representatives</code>.
     */
    public static final CompanyRepresentatives COMPANY_REPRESENTATIVES = org.jooq.generated.tables.CompanyRepresentatives.COMPANY_REPRESENTATIVES;

    /**
     * The table <code>incorporation_forms</code>.
     */
    public static final IncorporationForms INCORPORATION_FORMS = org.jooq.generated.tables.IncorporationForms.INCORPORATION_FORMS;

    /**
     * The table <code>power_documents</code>.
     */
    public static final PowerDocuments POWER_DOCUMENTS = org.jooq.generated.tables.PowerDocuments.POWER_DOCUMENTS;

    /**
     * The table <code>schema_migrations</code>.
     */
    public static final SchemaMigrations SCHEMA_MIGRATIONS = org.jooq.generated.tables.SchemaMigrations.SCHEMA_MIGRATIONS;

    /**
     * The table <code>users</code>.
     */
    public static final Users USERS = org.jooq.generated.tables.Users.USERS;
}
