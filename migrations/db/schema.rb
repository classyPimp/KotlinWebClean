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

ActiveRecord::Schema.define(version: 20171202154317) do

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

  create_table "companies", force: :cascade do |t|
    t.bigint "incorporation_form_id"
    t.string "name"
    t.string "name_short"
    t.string "adress_legal"
    t.string "adress_postal"
    t.string "bin"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["incorporation_form_id"], name: "index_companies_on_incorporation_form_id"
  end

  create_table "company_representatives", force: :cascade do |t|
    t.string "name"
    t.string "bin"
    t.string "identifying_document"
    t.bigint "company_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["company_id"], name: "index_company_representatives_on_company_id"
  end

  create_table "incorporation_forms", force: :cascade do |t|
    t.string "name"
    t.string "name_short"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  create_table "power_documents", force: :cascade do |t|
    t.string "file_name"
    t.string "file_mime"
    t.string "file_size"
    t.bigint "company_representative_id"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
    t.index ["company_representative_id"], name: "index_power_documents_on_company_representative_id"
  end

  create_table "users", force: :cascade do |t|
    t.string "name"
    t.datetime "created_at", null: false
    t.datetime "updated_at", null: false
  end

  add_foreign_key "accounts", "users"
  add_foreign_key "avatars", "users"
  add_foreign_key "companies", "incorporation_forms"
  add_foreign_key "company_representatives", "companies"
  add_foreign_key "power_documents", "company_representatives"
end
