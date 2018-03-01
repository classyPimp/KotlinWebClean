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

ActiveRecord::Schema.define(version: 20180227054538) do

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

  create_table "incorporation_forms", force: :cascade do |t|
    t.string "name"
    t.string "name_short"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
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

  create_table "users", force: :cascade do |t|
    t.string "name"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  add_foreign_key "accounts", "users"
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
  add_foreign_key "document_template_to_document_variable_links", "document_template_variables"
  add_foreign_key "document_template_to_document_variable_links", "document_templates"
  add_foreign_key "document_templates", "document_template_categories"
  add_foreign_key "document_templates", "uploaded_documents"
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
end
