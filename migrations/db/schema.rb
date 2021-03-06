# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20180425061713) do

  # These are extensions that must be enabled in order to support this database
  enable_extension "plpgsql"

  create_table "accounts", force: :cascade do |t|
    t.string "password"
    t.string "email"
    t.bigint "user_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["user_id"], name: "index_accounts_on_user_id"
  end

  create_table "approval_rejection_to_uploaded_document_links", force: :cascade do |t|
    t.bigint "approval_rejection_id"
    t.bigint "uploaded_document_id"
    t.string "description"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["approval_rejection_id"], name: "aprej_updo"
    t.index ["uploaded_document_id"], name: "apretoupdo_updo"
  end

  create_table "approval_rejections", force: :cascade do |t|
    t.bigint "approval_id"
    t.bigint "user_id"
    t.string "reason_text"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.datetime "fulfilled"
    t.index ["approval_id"], name: "index_approval_rejections_on_approval_id"
    t.index ["user_id"], name: "index_approval_rejections_on_user_id"
  end

  create_table "approval_step_to_approver_links", force: :cascade do |t|
    t.bigint "approval_step_id"
    t.bigint "user_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.datetime "is_approved"
    t.index ["approval_step_id"], name: "index_approval_step_to_approver_links_on_approval_step_id"
    t.index ["user_id"], name: "index_approval_step_to_approver_links_on_user_id"
  end

  create_table "approval_step_to_uploaded_document_links", force: :cascade do |t|
    t.bigint "approval_step_id"
    t.bigint "uploaded_document_id"
    t.string "description"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["approval_step_id"], name: "apst_to_updoli"
    t.index ["uploaded_document_id"], name: "apsttoupdo_updo"
  end

  create_table "approval_steps", force: :cascade do |t|
    t.bigint "approval_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["approval_id"], name: "index_approval_steps_on_approval_id"
  end

  create_table "approval_to_approver_links", force: :cascade do |t|
    t.bigint "approval_id"
    t.bigint "user_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.datetime "is_approved"
    t.index ["approval_id"], name: "index_approval_to_approver_links_on_approval_id"
    t.index ["user_id"], name: "index_approval_to_approver_links_on_user_id"
  end

  create_table "approvals", force: :cascade do |t|
    t.string "approvable_type"
    t.bigint "approvable_id"
    t.bigint "last_stage_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["approvable_type", "approvable_id"], name: "index_approvals_on_approvable_type_and_approvable_id"
  end

  create_table "avatars", force: :cascade do |t|
    t.string "file_name"
    t.bigint "user_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["user_id"], name: "index_avatars_on_user_id"
  end

  create_table "contact_types", force: :cascade do |t|
    t.string "name"
    t.string "is_specific_for_type"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "contacts", force: :cascade do |t|
    t.string "value"
    t.bigint "contact_type_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["contact_type_id"], name: "index_contacts_on_contact_type_id"
  end

  create_table "contract_categories", force: :cascade do |t|
    t.string "name"
    t.string "description"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "contract_numbers", force: :cascade do |t|
    t.string "internal_number"
    t.string "number_assigned_by_counter_party"
    t.string "assigned_number"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "contract_statuses", force: :cascade do |t|
    t.boolean "is_committed"
    t.boolean "is_supplement"
    t.bigint "parent_contract_id"
    t.bigint "root_contract_id"
    t.boolean "is_supplemented"
    t.boolean "is_project"
    t.boolean "is_cancelled"
    t.datetime "valid_since"
    t.datetime "valid_to"
    t.boolean "is_completed"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.string "internal_number"
    t.string "assigned_number"
    t.datetime "formal_date"
    t.datetime "pending_approval"
    t.datetime "is_approved"
    t.index ["parent_contract_id"], name: "index_contract_statuses_on_parent_contract_id"
    t.index ["root_contract_id"], name: "index_contract_statuses_on_root_contract_id"
  end

  create_table "contract_to_counter_party_links", force: :cascade do |t|
    t.bigint "counter_party_id"
    t.bigint "contract_id"
    t.string "role_according_to_contract"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["contract_id"], name: "index_contract_to_counter_party_links_on_contract_id"
    t.index ["counter_party_id"], name: "index_contract_to_counter_party_links_on_counter_party_id"
  end

  create_table "contract_to_uploaded_document_link_reasons", force: :cascade do |t|
    t.string "name"
    t.string "description"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "contract_to_uploaded_document_links", force: :cascade do |t|
    t.bigint "contract_id"
    t.bigint "uploaded_document_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.bigint "contract_to_uploaded_document_link_reason_id"
    t.string "description"
    t.index ["contract_id"], name: "index_contract_to_uploaded_document_links_on_contract_id"
    t.index ["contract_to_uploaded_document_link_reason_id"], name: "contudl_contudlr"
    t.index ["uploaded_document_id"], name: "ctupdl_on_ud"
  end

  create_table "contracts", force: :cascade do |t|
    t.string "description"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.bigint "contract_status_id"
    t.bigint "contract_number_id"
    t.datetime "formal_date"
    t.bigint "contract_category_id"
    t.index ["contract_category_id"], name: "index_contracts_on_contract_category_id"
    t.index ["contract_number_id"], name: "index_contracts_on_contract_number_id"
    t.index ["contract_status_id"], name: "index_contracts_on_contract_status_id"
  end

  create_table "counter_parties", force: :cascade do |t|
    t.string "name"
    t.string "name_short"
    t.bigint "incorporation_form_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["incorporation_form_id"], name: "index_counter_parties_on_incorporation_form_id"
  end

  create_table "counter_party_to_contact_links", force: :cascade do |t|
    t.bigint "counter_party_id"
    t.bigint "contact_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["contact_id"], name: "index_counter_party_to_contact_links_on_contact_id"
    t.index ["counter_party_id"], name: "index_counter_party_to_contact_links_on_counter_party_id"
  end

  create_table "discussion_messages", force: :cascade do |t|
    t.bigint "discussion_id"
    t.bigint "discussion_message_id"
    t.bigint "user_id"
    t.integer "nest_level"
    t.string "text"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["discussion_id"], name: "index_discussion_messages_on_discussion_id"
    t.index ["discussion_message_id"], name: "index_discussion_messages_on_discussion_message_id"
    t.index ["user_id"], name: "index_discussion_messages_on_user_id"
  end

  create_table "discussions", force: :cascade do |t|
    t.string "topic"
    t.string "discussable_type"
    t.bigint "discussable_id"
    t.bigint "user_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["discussable_type", "discussable_id"], name: "index_discussions_on_discussable_type_and_discussable_id"
    t.index ["user_id"], name: "index_discussions_on_user_id"
  end

  create_table "document_template_categories", force: :cascade do |t|
    t.string "name"
    t.string "description"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "document_template_to_document_variable_links", force: :cascade do |t|
    t.bigint "document_template_id"
    t.bigint "document_template_variable_id"
    t.string "unique_identifier"
    t.string "default_value"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["document_template_id"], name: "dttdvl_to_dtempl_fk"
    t.index ["document_template_variable_id"], name: "dttdvl_to_dtvar_fk"
  end

  create_table "document_template_variables", force: :cascade do |t|
    t.string "name"
    t.string "humanized_name"
    t.string "description"
    t.string "default_value"
    t.boolean "is_preprogrammed"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "document_templates", force: :cascade do |t|
    t.string "name"
    t.string "description"
    t.bigint "uploaded_document_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.bigint "document_template_category_id"
    t.index ["document_template_category_id"], name: "index_document_templates_on_document_template_category_id"
    t.index ["uploaded_document_id"], name: "index_document_templates_on_uploaded_document_id"
  end

  create_table "generic_resource_access_permissions", force: :cascade do |t|
    t.string "resource_type"
    t.bigint "resource_id"
    t.string "access_granted_to_type"
    t.bigint "access_granted_to_id"
    t.string "access_reason_code"
    t.string "access_originating_from_type"
    t.bigint "access_originating_from_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["access_granted_to_type", "access_granted_to_id"], name: "gereacpe_acgrto"
    t.index ["access_originating_from_type", "access_originating_from_id"], name: "gereacpe_acorfr"
    t.index ["resource_type", "resource_id"], name: "gereacpe_re"
  end

  create_table "generic_to_uploaded_file_links", force: :cascade do |t|
    t.string "linked_model_type"
    t.bigint "linked_model_id"
    t.string "primary_linked_model_type"
    t.bigint "primary_linked_model_id"
    t.integer "primary_hardcoded_link_reason"
    t.bigint "primary_user_definable_link_reason_id"
    t.boolean "uploaded_file_is_folder"
    t.integer "secondary_hardcoded_link_reason"
    t.bigint "secondary_user_definable_link_reason_id"
    t.bigint "uploaded_file_id"
    t.datetime "file_is_soft_deleted_since"
    t.datetime "file_is_permanently_deleted_since"
    t.integer "access_level"
    t.string "access_hash_code"
    t.string "sub_identifier"
    t.string "arbitrary_text_information"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["linked_model_type", "linked_model_id"], name: "gemotoupfire_remoid"
    t.index ["primary_linked_model_type", "primary_linked_model_id"], name: "gemotoupfire_prremo"
    t.index ["primary_user_definable_link_reason_id"], name: "gemotoupfire_pr_usdere"
    t.index ["secondary_user_definable_link_reason_id"], name: "gemotoupfire_usderere"
    t.index ["uploaded_file_id"], name: "gemotoupfire_upfi"
  end

  create_table "incorporation_forms", force: :cascade do |t|
    t.string "name"
    t.string "name_short"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "job_position_delegations", force: :cascade do |t|
    t.bigint "delegated_position_id"
    t.bigint "delegated_from_user_id"
    t.bigint "delegated_to_user_id"
    t.datetime "delegated_since"
    t.datetime "delegated_till"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["delegated_from_user_id"], name: "index_job_position_delegations_on_delegated_from_user_id"
    t.index ["delegated_position_id"], name: "index_job_position_delegations_on_delegated_position_id"
    t.index ["delegated_to_user_id"], name: "index_job_position_delegations_on_delegated_to_user_id"
  end

  create_table "job_position_to_user_links", force: :cascade do |t|
    t.bigint "user_id"
    t.boolean "is_delegated"
    t.bigint "job_position_delegation_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["job_position_delegation_id"], name: "index_job_position_to_user_links_on_job_position_delegation_id"
    t.index ["user_id"], name: "index_job_position_to_user_links_on_user_id"
  end

  create_table "job_positions", force: :cascade do |t|
    t.bigint "parent_job_position_id"
    t.string "name"
    t.boolean "is_department"
    t.boolean "is_department_head"
    t.boolean "is_unique_position"
    t.string "uniqueness_identifier"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["parent_job_position_id"], name: "index_job_positions_on_parent_job_position_id"
  end

  create_table "monetary_obligation_parts", force: :cascade do |t|
    t.bigint "amount"
    t.datetime "due_date"
    t.bigint "monetary_obligation_id"
    t.bigint "fulfilled_amount"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["monetary_obligation_id"], name: "index_monetary_obligation_parts_on_monetary_obligation_id"
  end

  create_table "monetary_obligations", force: :cascade do |t|
    t.bigint "total_amount"
    t.boolean "is_credit"
    t.bigint "contract_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.string "description"
    t.index ["contract_id"], name: "index_monetary_obligations_on_contract_id"
  end

  create_table "people", force: :cascade do |t|
    t.string "name"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "person_to_contact_links", force: :cascade do |t|
    t.bigint "person_id"
    t.bigint "contact_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["contact_id"], name: "index_person_to_contact_links_on_contact_id"
    t.index ["person_id"], name: "index_person_to_contact_links_on_person_id"
  end

  create_table "person_to_counter_party_link_reasons", force: :cascade do |t|
    t.string "reason_name"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "person_to_counter_party_link_to_uploaded_doc_link_reasons", id: :bigint, default: -> { "nextval('person_to_counter_party_link_to_uploaded_doc_link_reason_id_seq'::regclass)" }, force: :cascade do |t|
    t.string "reason_name"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "person_to_counter_party_link_to_uploaded_document_links", force: :cascade do |t|
    t.bigint "person_to_counter_party_link_id"
    t.bigint "uploaded_document_id"
    t.integer "type"
    t.bigint "person_to_counter_party_link_to_uploaded_doc_link_reason_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["person_to_counter_party_link_id"], name: "psn_to_co_prty_to_upl_links_idx"
    t.index ["person_to_counter_party_link_to_uploaded_doc_link_reason_id"], name: "ptcpltudl_ptcpltuplr_fk"
    t.index ["uploaded_document_id"], name: "up_doc_links_idx"
  end

  create_table "person_to_counter_party_links", force: :cascade do |t|
    t.bigint "person_id"
    t.bigint "counter_party_id"
    t.bigint "person_to_counter_party_link_reason_id"
    t.string "specific_details"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["counter_party_id"], name: "index_person_to_counter_party_links_on_counter_party_id"
    t.index ["person_id"], name: "index_person_to_counter_party_links_on_person_id"
    t.index ["person_to_counter_party_link_reason_id"], name: "cptcpl_ptcplr_fk"
  end

  create_table "uploaded_documents", force: :cascade do |t|
    t.string "description"
    t.string "file_name"
    t.string "file_mime"
    t.integer "file_size"
    t.boolean "is_folder"
    t.bigint "uploaded_document_id"
    t.string "folder_name"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["uploaded_document_id"], name: "index_uploaded_documents_on_uploaded_document_id"
  end

  create_table "uploaded_file_permissions", force: :cascade do |t|
    t.bigint "uploaded_file_id"
    t.boolean "is_owner"
    t.integer "permission_level"
    t.string "permitted_to_type"
    t.bigint "permitted_to_id"
    t.string "access_hash_code"
    t.string "primary_permitted_to_type"
    t.bigint "primary_permitted_to_id"
    t.string "hardcoded_link_reason"
    t.bigint "user_definable_link_reason_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["permitted_to_type", "permitted_to_id"], name: "apretoupdo_poly_peto"
    t.index ["primary_permitted_to_type", "primary_permitted_to_id"], name: "apretoupdo_poly_prpeto"
    t.index ["uploaded_file_id"], name: "index_uploaded_file_permissions_on_uploaded_file_id"
    t.index ["user_definable_link_reason_id"], name: "upfipe_usderere"
  end

  create_table "uploaded_files", force: :cascade do |t|
    t.string "file_name"
    t.bigint "file_size"
    t.bigint "folder_id"
    t.bigint "shortcut_to_id"
    t.boolean "is_shortcut_to_folder"
    t.datetime "soft_deleted_since"
    t.integer "hardcoded_category"
    t.string "file_mime"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["folder_id"], name: "index_uploaded_files_on_folder_id"
    t.index ["shortcut_to_id"], name: "index_uploaded_files_on_shortcut_to_id"
  end

  create_table "user_definable_link_reasons", force: :cascade do |t|
    t.string "reason_name"
    t.string "for_type"
    t.string "sub_identifier"
    t.string "reason_description"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "user_roles", force: :cascade do |t|
    t.string "name"
    t.boolean "is_specific"
    t.string "specific_to_type"
    t.bigint "specific_to_id"
    t.bigint "user_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["user_id"], name: "index_user_roles_on_user_id"
  end

  create_table "user_to_user_role_links", force: :cascade do |t|
    t.bigint "user_id"
    t.bigint "user_role_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["user_id"], name: "index_user_to_user_role_links_on_user_id"
    t.index ["user_role_id"], name: "index_user_to_user_role_links_on_user_role_id"
  end

  create_table "users", force: :cascade do |t|
    t.string "name"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  add_foreign_key "accounts", "users"
  add_foreign_key "approval_rejection_to_uploaded_document_links", "approval_rejections"
  add_foreign_key "approval_rejection_to_uploaded_document_links", "uploaded_documents"
  add_foreign_key "approval_rejections", "approvals"
  add_foreign_key "approval_rejections", "users"
  add_foreign_key "approval_step_to_approver_links", "approval_steps"
  add_foreign_key "approval_step_to_approver_links", "users"
  add_foreign_key "approval_step_to_uploaded_document_links", "approval_steps"
  add_foreign_key "approval_step_to_uploaded_document_links", "uploaded_documents"
  add_foreign_key "approval_steps", "approvals"
  add_foreign_key "approval_to_approver_links", "approvals"
  add_foreign_key "approval_to_approver_links", "users"
  add_foreign_key "avatars", "users"
  add_foreign_key "contacts", "contact_types"
  add_foreign_key "contract_statuses", "contracts", column: "parent_contract_id"
  add_foreign_key "contract_statuses", "contracts", column: "root_contract_id"
  add_foreign_key "contract_to_counter_party_links", "contracts"
  add_foreign_key "contract_to_counter_party_links", "counter_parties"
  add_foreign_key "contract_to_uploaded_document_links", "contract_to_uploaded_document_link_reasons"
  add_foreign_key "contract_to_uploaded_document_links", "contracts"
  add_foreign_key "contract_to_uploaded_document_links", "uploaded_documents"
  add_foreign_key "contracts", "contract_categories"
  add_foreign_key "contracts", "contract_numbers"
  add_foreign_key "contracts", "contract_statuses"
  add_foreign_key "counter_parties", "incorporation_forms"
  add_foreign_key "counter_party_to_contact_links", "contacts"
  add_foreign_key "counter_party_to_contact_links", "counter_parties"
  add_foreign_key "discussion_messages", "discussion_messages"
  add_foreign_key "discussion_messages", "discussions"
  add_foreign_key "document_template_to_document_variable_links", "document_template_variables"
  add_foreign_key "document_template_to_document_variable_links", "document_templates"
  add_foreign_key "document_templates", "document_template_categories"
  add_foreign_key "document_templates", "uploaded_documents"
  add_foreign_key "generic_to_uploaded_file_links", "uploaded_files"
  add_foreign_key "generic_to_uploaded_file_links", "user_definable_link_reasons", column: "primary_user_definable_link_reason_id"
  add_foreign_key "generic_to_uploaded_file_links", "user_definable_link_reasons", column: "secondary_user_definable_link_reason_id"
  add_foreign_key "job_position_delegations", "job_positions", column: "delegated_position_id"
  add_foreign_key "job_position_delegations", "users", column: "delegated_from_user_id"
  add_foreign_key "job_position_delegations", "users", column: "delegated_to_user_id"
  add_foreign_key "job_position_to_user_links", "job_position_delegations"
  add_foreign_key "job_position_to_user_links", "users"
  add_foreign_key "job_positions", "job_positions", column: "parent_job_position_id"
  add_foreign_key "monetary_obligation_parts", "monetary_obligations"
  add_foreign_key "monetary_obligations", "contracts"
  add_foreign_key "person_to_contact_links", "contacts"
  add_foreign_key "person_to_contact_links", "people"
  add_foreign_key "person_to_counter_party_link_to_uploaded_document_links", "person_to_counter_party_link_to_uploaded_doc_link_reasons"
  add_foreign_key "person_to_counter_party_link_to_uploaded_document_links", "person_to_counter_party_links"
  add_foreign_key "person_to_counter_party_link_to_uploaded_document_links", "uploaded_documents"
  add_foreign_key "person_to_counter_party_links", "counter_parties"
  add_foreign_key "person_to_counter_party_links", "people"
  add_foreign_key "person_to_counter_party_links", "person_to_counter_party_link_reasons"
  add_foreign_key "uploaded_documents", "uploaded_documents"
  add_foreign_key "uploaded_file_permissions", "uploaded_files"
  add_foreign_key "uploaded_file_permissions", "user_definable_link_reasons"
  add_foreign_key "uploaded_files", "uploaded_files", column: "folder_id"
  add_foreign_key "uploaded_files", "uploaded_files", column: "shortcut_to_id"
  add_foreign_key "user_to_user_role_links", "user_roles"
  add_foreign_key "user_to_user_role_links", "users"
end
