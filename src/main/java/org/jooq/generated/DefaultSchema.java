/*
 * This file is generated by jOOQ.
*/
package org.jooq.generated;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.generated.tables.Accounts;
import org.jooq.generated.tables.ArInternalMetadata;
import org.jooq.generated.tables.Avatars;
import org.jooq.generated.tables.ContactTypes;
import org.jooq.generated.tables.Contacts;
import org.jooq.generated.tables.ContractCategories;
import org.jooq.generated.tables.ContractNumbers;
import org.jooq.generated.tables.ContractStatuses;
import org.jooq.generated.tables.ContractToCounterPartyLinks;
import org.jooq.generated.tables.ContractToUploadedDocumentLinkReasons;
import org.jooq.generated.tables.ContractToUploadedDocumentLinks;
import org.jooq.generated.tables.Contracts;
import org.jooq.generated.tables.CounterParties;
import org.jooq.generated.tables.CounterPartyToContactLinks;
import org.jooq.generated.tables.DiscussionMessages;
import org.jooq.generated.tables.Discussions;
import org.jooq.generated.tables.DocumentTemplateCategories;
import org.jooq.generated.tables.DocumentTemplateToDocumentVariableLinks;
import org.jooq.generated.tables.DocumentTemplateVariables;
import org.jooq.generated.tables.DocumentTemplates;
import org.jooq.generated.tables.IncorporationForms;
import org.jooq.generated.tables.MonetaryObligationParts;
import org.jooq.generated.tables.MonetaryObligations;
import org.jooq.generated.tables.People;
import org.jooq.generated.tables.PersonToContactLinks;
import org.jooq.generated.tables.PersonToCounterPartyLinkReasons;
import org.jooq.generated.tables.PersonToCounterPartyLinkToUploadedDocLinkReasons;
import org.jooq.generated.tables.PersonToCounterPartyLinkToUploadedDocumentLinks;
import org.jooq.generated.tables.PersonToCounterPartyLinks;
import org.jooq.generated.tables.SchemaMigrations;
import org.jooq.generated.tables.UploadedDocuments;
import org.jooq.generated.tables.Users;
import org.jooq.impl.SchemaImpl;


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
public class DefaultSchema extends SchemaImpl {

    private static final long serialVersionUID = 1281639358;

    /**
     * The reference instance of <code></code>
     */
    public static final DefaultSchema DEFAULT_SCHEMA = new DefaultSchema();

    /**
     * The table <code>accounts</code>.
     */
    public final Accounts ACCOUNTS = org.jooq.generated.tables.Accounts.ACCOUNTS;

    /**
     * The table <code>ar_internal_metadata</code>.
     */
    public final ArInternalMetadata AR_INTERNAL_METADATA = org.jooq.generated.tables.ArInternalMetadata.AR_INTERNAL_METADATA;

    /**
     * The table <code>avatars</code>.
     */
    public final Avatars AVATARS = org.jooq.generated.tables.Avatars.AVATARS;

    /**
     * The table <code>contact_types</code>.
     */
    public final ContactTypes CONTACT_TYPES = org.jooq.generated.tables.ContactTypes.CONTACT_TYPES;

    /**
     * The table <code>contacts</code>.
     */
    public final Contacts CONTACTS = org.jooq.generated.tables.Contacts.CONTACTS;

    /**
     * The table <code>contract_categories</code>.
     */
    public final ContractCategories CONTRACT_CATEGORIES = org.jooq.generated.tables.ContractCategories.CONTRACT_CATEGORIES;

    /**
     * The table <code>contract_numbers</code>.
     */
    public final ContractNumbers CONTRACT_NUMBERS = org.jooq.generated.tables.ContractNumbers.CONTRACT_NUMBERS;

    /**
     * The table <code>contract_statuses</code>.
     */
    public final ContractStatuses CONTRACT_STATUSES = org.jooq.generated.tables.ContractStatuses.CONTRACT_STATUSES;

    /**
     * The table <code>contract_to_counter_party_links</code>.
     */
    public final ContractToCounterPartyLinks CONTRACT_TO_COUNTER_PARTY_LINKS = org.jooq.generated.tables.ContractToCounterPartyLinks.CONTRACT_TO_COUNTER_PARTY_LINKS;

    /**
     * The table <code>contract_to_uploaded_document_link_reasons</code>.
     */
    public final ContractToUploadedDocumentLinkReasons CONTRACT_TO_UPLOADED_DOCUMENT_LINK_REASONS = org.jooq.generated.tables.ContractToUploadedDocumentLinkReasons.CONTRACT_TO_UPLOADED_DOCUMENT_LINK_REASONS;

    /**
     * The table <code>contract_to_uploaded_document_links</code>.
     */
    public final ContractToUploadedDocumentLinks CONTRACT_TO_UPLOADED_DOCUMENT_LINKS = org.jooq.generated.tables.ContractToUploadedDocumentLinks.CONTRACT_TO_UPLOADED_DOCUMENT_LINKS;

    /**
     * The table <code>contracts</code>.
     */
    public final Contracts CONTRACTS = org.jooq.generated.tables.Contracts.CONTRACTS;

    /**
     * The table <code>counter_parties</code>.
     */
    public final CounterParties COUNTER_PARTIES = org.jooq.generated.tables.CounterParties.COUNTER_PARTIES;

    /**
     * The table <code>counter_party_to_contact_links</code>.
     */
    public final CounterPartyToContactLinks COUNTER_PARTY_TO_CONTACT_LINKS = org.jooq.generated.tables.CounterPartyToContactLinks.COUNTER_PARTY_TO_CONTACT_LINKS;

    /**
     * The table <code>discussion_messages</code>.
     */
    public final DiscussionMessages DISCUSSION_MESSAGES = org.jooq.generated.tables.DiscussionMessages.DISCUSSION_MESSAGES;

    /**
     * The table <code>discussions</code>.
     */
    public final Discussions DISCUSSIONS = org.jooq.generated.tables.Discussions.DISCUSSIONS;

    /**
     * The table <code>document_template_categories</code>.
     */
    public final DocumentTemplateCategories DOCUMENT_TEMPLATE_CATEGORIES = org.jooq.generated.tables.DocumentTemplateCategories.DOCUMENT_TEMPLATE_CATEGORIES;

    /**
     * The table <code>document_template_to_document_variable_links</code>.
     */
    public final DocumentTemplateToDocumentVariableLinks DOCUMENT_TEMPLATE_TO_DOCUMENT_VARIABLE_LINKS = org.jooq.generated.tables.DocumentTemplateToDocumentVariableLinks.DOCUMENT_TEMPLATE_TO_DOCUMENT_VARIABLE_LINKS;

    /**
     * The table <code>document_template_variables</code>.
     */
    public final DocumentTemplateVariables DOCUMENT_TEMPLATE_VARIABLES = org.jooq.generated.tables.DocumentTemplateVariables.DOCUMENT_TEMPLATE_VARIABLES;

    /**
     * The table <code>document_templates</code>.
     */
    public final DocumentTemplates DOCUMENT_TEMPLATES = org.jooq.generated.tables.DocumentTemplates.DOCUMENT_TEMPLATES;

    /**
     * The table <code>incorporation_forms</code>.
     */
    public final IncorporationForms INCORPORATION_FORMS = org.jooq.generated.tables.IncorporationForms.INCORPORATION_FORMS;

    /**
     * The table <code>monetary_obligation_parts</code>.
     */
    public final MonetaryObligationParts MONETARY_OBLIGATION_PARTS = org.jooq.generated.tables.MonetaryObligationParts.MONETARY_OBLIGATION_PARTS;

    /**
     * The table <code>monetary_obligations</code>.
     */
    public final MonetaryObligations MONETARY_OBLIGATIONS = org.jooq.generated.tables.MonetaryObligations.MONETARY_OBLIGATIONS;

    /**
     * The table <code>people</code>.
     */
    public final People PEOPLE = org.jooq.generated.tables.People.PEOPLE;

    /**
     * The table <code>person_to_contact_links</code>.
     */
    public final PersonToContactLinks PERSON_TO_CONTACT_LINKS = org.jooq.generated.tables.PersonToContactLinks.PERSON_TO_CONTACT_LINKS;

    /**
     * The table <code>person_to_counter_party_link_reasons</code>.
     */
    public final PersonToCounterPartyLinkReasons PERSON_TO_COUNTER_PARTY_LINK_REASONS = org.jooq.generated.tables.PersonToCounterPartyLinkReasons.PERSON_TO_COUNTER_PARTY_LINK_REASONS;

    /**
     * The table <code>person_to_counter_party_link_to_uploaded_doc_link_reasons</code>.
     */
    public final PersonToCounterPartyLinkToUploadedDocLinkReasons PERSON_TO_COUNTER_PARTY_LINK_TO_UPLOADED_DOC_LINK_REASONS = org.jooq.generated.tables.PersonToCounterPartyLinkToUploadedDocLinkReasons.PERSON_TO_COUNTER_PARTY_LINK_TO_UPLOADED_DOC_LINK_REASONS;

    /**
     * The table <code>person_to_counter_party_link_to_uploaded_document_links</code>.
     */
    public final PersonToCounterPartyLinkToUploadedDocumentLinks PERSON_TO_COUNTER_PARTY_LINK_TO_UPLOADED_DOCUMENT_LINKS = org.jooq.generated.tables.PersonToCounterPartyLinkToUploadedDocumentLinks.PERSON_TO_COUNTER_PARTY_LINK_TO_UPLOADED_DOCUMENT_LINKS;

    /**
     * The table <code>person_to_counter_party_links</code>.
     */
    public final PersonToCounterPartyLinks PERSON_TO_COUNTER_PARTY_LINKS = org.jooq.generated.tables.PersonToCounterPartyLinks.PERSON_TO_COUNTER_PARTY_LINKS;

    /**
     * The table <code>schema_migrations</code>.
     */
    public final SchemaMigrations SCHEMA_MIGRATIONS = org.jooq.generated.tables.SchemaMigrations.SCHEMA_MIGRATIONS;

    /**
     * The table <code>uploaded_documents</code>.
     */
    public final UploadedDocuments UPLOADED_DOCUMENTS = org.jooq.generated.tables.UploadedDocuments.UPLOADED_DOCUMENTS;

    /**
     * The table <code>users</code>.
     */
    public final Users USERS = org.jooq.generated.tables.Users.USERS;

    /**
     * No further instances allowed
     */
    private DefaultSchema() {
        super("", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Accounts.ACCOUNTS,
            ArInternalMetadata.AR_INTERNAL_METADATA,
            Avatars.AVATARS,
            ContactTypes.CONTACT_TYPES,
            Contacts.CONTACTS,
            ContractCategories.CONTRACT_CATEGORIES,
            ContractNumbers.CONTRACT_NUMBERS,
            ContractStatuses.CONTRACT_STATUSES,
            ContractToCounterPartyLinks.CONTRACT_TO_COUNTER_PARTY_LINKS,
            ContractToUploadedDocumentLinkReasons.CONTRACT_TO_UPLOADED_DOCUMENT_LINK_REASONS,
            ContractToUploadedDocumentLinks.CONTRACT_TO_UPLOADED_DOCUMENT_LINKS,
            Contracts.CONTRACTS,
            CounterParties.COUNTER_PARTIES,
            CounterPartyToContactLinks.COUNTER_PARTY_TO_CONTACT_LINKS,
            DiscussionMessages.DISCUSSION_MESSAGES,
            Discussions.DISCUSSIONS,
            DocumentTemplateCategories.DOCUMENT_TEMPLATE_CATEGORIES,
            DocumentTemplateToDocumentVariableLinks.DOCUMENT_TEMPLATE_TO_DOCUMENT_VARIABLE_LINKS,
            DocumentTemplateVariables.DOCUMENT_TEMPLATE_VARIABLES,
            DocumentTemplates.DOCUMENT_TEMPLATES,
            IncorporationForms.INCORPORATION_FORMS,
            MonetaryObligationParts.MONETARY_OBLIGATION_PARTS,
            MonetaryObligations.MONETARY_OBLIGATIONS,
            People.PEOPLE,
            PersonToContactLinks.PERSON_TO_CONTACT_LINKS,
            PersonToCounterPartyLinkReasons.PERSON_TO_COUNTER_PARTY_LINK_REASONS,
            PersonToCounterPartyLinkToUploadedDocLinkReasons.PERSON_TO_COUNTER_PARTY_LINK_TO_UPLOADED_DOC_LINK_REASONS,
            PersonToCounterPartyLinkToUploadedDocumentLinks.PERSON_TO_COUNTER_PARTY_LINK_TO_UPLOADED_DOCUMENT_LINKS,
            PersonToCounterPartyLinks.PERSON_TO_COUNTER_PARTY_LINKS,
            SchemaMigrations.SCHEMA_MIGRATIONS,
            UploadedDocuments.UPLOADED_DOCUMENTS,
            Users.USERS);
    }
}
