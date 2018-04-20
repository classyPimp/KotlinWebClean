/*
 * This file is generated by jOOQ.
*/
package org.jooq.generated;


import javax.annotation.Generated;

import org.jooq.generated.tables.Accounts;
import org.jooq.generated.tables.ApprovalRejectionToUploadedDocumentLinks;
import org.jooq.generated.tables.ApprovalRejections;
import org.jooq.generated.tables.ApprovalStepToApproverLinks;
import org.jooq.generated.tables.ApprovalStepToUploadedDocumentLinks;
import org.jooq.generated.tables.ApprovalSteps;
import org.jooq.generated.tables.ApprovalToApproverLinks;
import org.jooq.generated.tables.Approvals;
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
import org.jooq.generated.tables.UploadedFilePermissions;
import org.jooq.generated.tables.UploadedFiles;
import org.jooq.generated.tables.UserDefinableRelationReasons;
import org.jooq.generated.tables.UserRoles;
import org.jooq.generated.tables.UserToUserRoleLinks;
import org.jooq.generated.tables.Users;


/**
 * Convenience access to all tables in 
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.2"
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
     * The table <code>approval_rejection_to_uploaded_document_links</code>.
     */
    public static final ApprovalRejectionToUploadedDocumentLinks APPROVAL_REJECTION_TO_UPLOADED_DOCUMENT_LINKS = org.jooq.generated.tables.ApprovalRejectionToUploadedDocumentLinks.APPROVAL_REJECTION_TO_UPLOADED_DOCUMENT_LINKS;

    /**
     * The table <code>approval_rejections</code>.
     */
    public static final ApprovalRejections APPROVAL_REJECTIONS = org.jooq.generated.tables.ApprovalRejections.APPROVAL_REJECTIONS;

    /**
     * The table <code>approval_step_to_approver_links</code>.
     */
    public static final ApprovalStepToApproverLinks APPROVAL_STEP_TO_APPROVER_LINKS = org.jooq.generated.tables.ApprovalStepToApproverLinks.APPROVAL_STEP_TO_APPROVER_LINKS;

    /**
     * The table <code>approval_step_to_uploaded_document_links</code>.
     */
    public static final ApprovalStepToUploadedDocumentLinks APPROVAL_STEP_TO_UPLOADED_DOCUMENT_LINKS = org.jooq.generated.tables.ApprovalStepToUploadedDocumentLinks.APPROVAL_STEP_TO_UPLOADED_DOCUMENT_LINKS;

    /**
     * The table <code>approval_steps</code>.
     */
    public static final ApprovalSteps APPROVAL_STEPS = org.jooq.generated.tables.ApprovalSteps.APPROVAL_STEPS;

    /**
     * The table <code>approval_to_approver_links</code>.
     */
    public static final ApprovalToApproverLinks APPROVAL_TO_APPROVER_LINKS = org.jooq.generated.tables.ApprovalToApproverLinks.APPROVAL_TO_APPROVER_LINKS;

    /**
     * The table <code>approvals</code>.
     */
    public static final Approvals APPROVALS = org.jooq.generated.tables.Approvals.APPROVALS;

    /**
     * The table <code>ar_internal_metadata</code>.
     */
    public static final ArInternalMetadata AR_INTERNAL_METADATA = org.jooq.generated.tables.ArInternalMetadata.AR_INTERNAL_METADATA;

    /**
     * The table <code>avatars</code>.
     */
    public static final Avatars AVATARS = org.jooq.generated.tables.Avatars.AVATARS;

    /**
     * The table <code>contact_types</code>.
     */
    public static final ContactTypes CONTACT_TYPES = org.jooq.generated.tables.ContactTypes.CONTACT_TYPES;

    /**
     * The table <code>contacts</code>.
     */
    public static final Contacts CONTACTS = org.jooq.generated.tables.Contacts.CONTACTS;

    /**
     * The table <code>contract_categories</code>.
     */
    public static final ContractCategories CONTRACT_CATEGORIES = org.jooq.generated.tables.ContractCategories.CONTRACT_CATEGORIES;

    /**
     * The table <code>contract_numbers</code>.
     */
    public static final ContractNumbers CONTRACT_NUMBERS = org.jooq.generated.tables.ContractNumbers.CONTRACT_NUMBERS;

    /**
     * The table <code>contract_statuses</code>.
     */
    public static final ContractStatuses CONTRACT_STATUSES = org.jooq.generated.tables.ContractStatuses.CONTRACT_STATUSES;

    /**
     * The table <code>contract_to_counter_party_links</code>.
     */
    public static final ContractToCounterPartyLinks CONTRACT_TO_COUNTER_PARTY_LINKS = org.jooq.generated.tables.ContractToCounterPartyLinks.CONTRACT_TO_COUNTER_PARTY_LINKS;

    /**
     * The table <code>contract_to_uploaded_document_link_reasons</code>.
     */
    public static final ContractToUploadedDocumentLinkReasons CONTRACT_TO_UPLOADED_DOCUMENT_LINK_REASONS = org.jooq.generated.tables.ContractToUploadedDocumentLinkReasons.CONTRACT_TO_UPLOADED_DOCUMENT_LINK_REASONS;

    /**
     * The table <code>contract_to_uploaded_document_links</code>.
     */
    public static final ContractToUploadedDocumentLinks CONTRACT_TO_UPLOADED_DOCUMENT_LINKS = org.jooq.generated.tables.ContractToUploadedDocumentLinks.CONTRACT_TO_UPLOADED_DOCUMENT_LINKS;

    /**
     * The table <code>contracts</code>.
     */
    public static final Contracts CONTRACTS = org.jooq.generated.tables.Contracts.CONTRACTS;

    /**
     * The table <code>counter_parties</code>.
     */
    public static final CounterParties COUNTER_PARTIES = org.jooq.generated.tables.CounterParties.COUNTER_PARTIES;

    /**
     * The table <code>counter_party_to_contact_links</code>.
     */
    public static final CounterPartyToContactLinks COUNTER_PARTY_TO_CONTACT_LINKS = org.jooq.generated.tables.CounterPartyToContactLinks.COUNTER_PARTY_TO_CONTACT_LINKS;

    /**
     * The table <code>discussion_messages</code>.
     */
    public static final DiscussionMessages DISCUSSION_MESSAGES = org.jooq.generated.tables.DiscussionMessages.DISCUSSION_MESSAGES;

    /**
     * The table <code>discussions</code>.
     */
    public static final Discussions DISCUSSIONS = org.jooq.generated.tables.Discussions.DISCUSSIONS;

    /**
     * The table <code>document_template_categories</code>.
     */
    public static final DocumentTemplateCategories DOCUMENT_TEMPLATE_CATEGORIES = org.jooq.generated.tables.DocumentTemplateCategories.DOCUMENT_TEMPLATE_CATEGORIES;

    /**
     * The table <code>document_template_to_document_variable_links</code>.
     */
    public static final DocumentTemplateToDocumentVariableLinks DOCUMENT_TEMPLATE_TO_DOCUMENT_VARIABLE_LINKS = org.jooq.generated.tables.DocumentTemplateToDocumentVariableLinks.DOCUMENT_TEMPLATE_TO_DOCUMENT_VARIABLE_LINKS;

    /**
     * The table <code>document_template_variables</code>.
     */
    public static final DocumentTemplateVariables DOCUMENT_TEMPLATE_VARIABLES = org.jooq.generated.tables.DocumentTemplateVariables.DOCUMENT_TEMPLATE_VARIABLES;

    /**
     * The table <code>document_templates</code>.
     */
    public static final DocumentTemplates DOCUMENT_TEMPLATES = org.jooq.generated.tables.DocumentTemplates.DOCUMENT_TEMPLATES;

    /**
     * The table <code>incorporation_forms</code>.
     */
    public static final IncorporationForms INCORPORATION_FORMS = org.jooq.generated.tables.IncorporationForms.INCORPORATION_FORMS;

    /**
     * The table <code>monetary_obligation_parts</code>.
     */
    public static final MonetaryObligationParts MONETARY_OBLIGATION_PARTS = org.jooq.generated.tables.MonetaryObligationParts.MONETARY_OBLIGATION_PARTS;

    /**
     * The table <code>monetary_obligations</code>.
     */
    public static final MonetaryObligations MONETARY_OBLIGATIONS = org.jooq.generated.tables.MonetaryObligations.MONETARY_OBLIGATIONS;

    /**
     * The table <code>people</code>.
     */
    public static final People PEOPLE = org.jooq.generated.tables.People.PEOPLE;

    /**
     * The table <code>person_to_contact_links</code>.
     */
    public static final PersonToContactLinks PERSON_TO_CONTACT_LINKS = org.jooq.generated.tables.PersonToContactLinks.PERSON_TO_CONTACT_LINKS;

    /**
     * The table <code>person_to_counter_party_link_reasons</code>.
     */
    public static final PersonToCounterPartyLinkReasons PERSON_TO_COUNTER_PARTY_LINK_REASONS = org.jooq.generated.tables.PersonToCounterPartyLinkReasons.PERSON_TO_COUNTER_PARTY_LINK_REASONS;

    /**
     * The table <code>person_to_counter_party_link_to_uploaded_doc_link_reasons</code>.
     */
    public static final PersonToCounterPartyLinkToUploadedDocLinkReasons PERSON_TO_COUNTER_PARTY_LINK_TO_UPLOADED_DOC_LINK_REASONS = org.jooq.generated.tables.PersonToCounterPartyLinkToUploadedDocLinkReasons.PERSON_TO_COUNTER_PARTY_LINK_TO_UPLOADED_DOC_LINK_REASONS;

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
     * The table <code>uploaded_file_permissions</code>.
     */
    public static final UploadedFilePermissions UPLOADED_FILE_PERMISSIONS = org.jooq.generated.tables.UploadedFilePermissions.UPLOADED_FILE_PERMISSIONS;

    /**
     * The table <code>uploaded_files</code>.
     */
    public static final UploadedFiles UPLOADED_FILES = org.jooq.generated.tables.UploadedFiles.UPLOADED_FILES;

    /**
     * The table <code>user_definable_relation_reasons</code>.
     */
    public static final UserDefinableRelationReasons USER_DEFINABLE_RELATION_REASONS = org.jooq.generated.tables.UserDefinableRelationReasons.USER_DEFINABLE_RELATION_REASONS;

    /**
     * The table <code>user_roles</code>.
     */
    public static final UserRoles USER_ROLES = org.jooq.generated.tables.UserRoles.USER_ROLES;

    /**
     * The table <code>user_to_user_role_links</code>.
     */
    public static final UserToUserRoleLinks USER_TO_USER_ROLE_LINKS = org.jooq.generated.tables.UserToUserRoleLinks.USER_TO_USER_ROLE_LINKS;

    /**
     * The table <code>users</code>.
     */
    public static final Users USERS = org.jooq.generated.tables.Users.USERS;
}
