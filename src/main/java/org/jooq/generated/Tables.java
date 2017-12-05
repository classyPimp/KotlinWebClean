/*
 * This file is generated by jOOQ.
*/
package org.jooq.generated;


import javax.annotation.Generated;

import org.jooq.generated.tables.Accounts;
import org.jooq.generated.tables.ArInternalMetadata;
import org.jooq.generated.tables.Avatars;
import org.jooq.generated.tables.CounterParties;
import org.jooq.generated.tables.CounterPartyContacts;
import org.jooq.generated.tables.IncorporationForms;
import org.jooq.generated.tables.People;
import org.jooq.generated.tables.PersonContacts;
import org.jooq.generated.tables.PersonToCounterPartyLinkToUploadedDocumentLinks;
import org.jooq.generated.tables.PersonToCounterPartyLinks;
import org.jooq.generated.tables.SchemaMigrations;
import org.jooq.generated.tables.UploadedDocuments;
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
     * The table <code>counter_parties</code>.
     */
    public static final CounterParties COUNTER_PARTIES = org.jooq.generated.tables.CounterParties.COUNTER_PARTIES;

    /**
     * The table <code>counter_party_contacts</code>.
     */
    public static final CounterPartyContacts COUNTER_PARTY_CONTACTS = org.jooq.generated.tables.CounterPartyContacts.COUNTER_PARTY_CONTACTS;

    /**
     * The table <code>incorporation_forms</code>.
     */
    public static final IncorporationForms INCORPORATION_FORMS = org.jooq.generated.tables.IncorporationForms.INCORPORATION_FORMS;

    /**
     * The table <code>people</code>.
     */
    public static final People PEOPLE = org.jooq.generated.tables.People.PEOPLE;

    /**
     * The table <code>person_contacts</code>.
     */
    public static final PersonContacts PERSON_CONTACTS = org.jooq.generated.tables.PersonContacts.PERSON_CONTACTS;

    /**
     * The table <code>person_to_counter_party_link_to_uploaded_document_links</code>.
     */
    public static final PersonToCounterPartyLinkToUploadedDocumentLinks PERSON_TO_COUNTER_PARTY_LINK_TO_UPLOADED_DOCUMENT_LINKS = org.jooq.generated.tables.PersonToCounterPartyLinkToUploadedDocumentLinks.PERSON_TO_COUNTER_PARTY_LINK_TO_UPLOADED_DOCUMENT_LINKS;

    /**
     * The table <code>person_to_counter_party_links</code>.
     */
    public static final PersonToCounterPartyLinks PERSON_TO_COUNTER_PARTY_LINKS = org.jooq.generated.tables.PersonToCounterPartyLinks.PERSON_TO_COUNTER_PARTY_LINKS;

    /**
     * The table <code>schema_migrations</code>.
     */
    public static final SchemaMigrations SCHEMA_MIGRATIONS = org.jooq.generated.tables.SchemaMigrations.SCHEMA_MIGRATIONS;

    /**
     * The table <code>uploaded_documents</code>.
     */
    public static final UploadedDocuments UPLOADED_DOCUMENTS = org.jooq.generated.tables.UploadedDocuments.UPLOADED_DOCUMENTS;

    /**
     * The table <code>users</code>.
     */
    public static final Users USERS = org.jooq.generated.tables.Users.USERS;
}
