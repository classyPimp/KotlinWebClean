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

ActiveRecord::Schema.define(version: 20171205075347) do

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

  create_table "counter_parties", force: :cascade do |t|
    t.string "name"
    t.string "name_short"
    t.bigint "incorporation_form_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["incorporation_form_id"], name: "index_counter_parties_on_incorporation_form_id"
  end

  create_table "counter_party_contacts", force: :cascade do |t|
    t.bigint "counter_party_id"
    t.integer "contact_type"
    t.string "value"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["counter_party_id"], name: "index_counter_party_contacts_on_counter_party_id"
  end

  create_table "incorporation_forms", force: :cascade do |t|
    t.string "name"
    t.string "name_short"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "people", force: :cascade do |t|
    t.string "name"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "person_contacts", force: :cascade do |t|
    t.integer "contact_type"
    t.string "value"
    t.bigint "person_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["person_id"], name: "index_person_contacts_on_person_id"
  end

  create_table "person_to_counter_party_link_to_uploaded_document_links", force: :cascade do |t|
    t.bigint "person_to_counter_party_link_id"
    t.bigint "uploaded_document_id"
    t.integer "type"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["person_to_counter_party_link_id"], name: "psn_to_co_prty_to_upl_links_idx"
    t.index ["uploaded_document_id"], name: "up_doc_links_idx"
  end

  create_table "person_to_counter_party_links", force: :cascade do |t|
    t.bigint "person_id"
    t.bigint "counter_party_id"
    t.integer "link_reason"
    t.string "specific_details"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["counter_party_id"], name: "index_person_to_counter_party_links_on_counter_party_id"
    t.index ["person_id"], name: "index_person_to_counter_party_links_on_person_id"
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
  add_foreign_key "counter_parties", "incorporation_forms"
  add_foreign_key "counter_party_contacts", "counter_parties"
  add_foreign_key "person_contacts", "people"
  add_foreign_key "person_to_counter_party_link_to_uploaded_document_links", "person_to_counter_party_links"
  add_foreign_key "person_to_counter_party_link_to_uploaded_document_links", "uploaded_documents"
  add_foreign_key "person_to_counter_party_links", "counter_parties"
  add_foreign_key "person_to_counter_party_links", "people"
  add_foreign_key "uploaded_documents", "uploaded_documents"
end
