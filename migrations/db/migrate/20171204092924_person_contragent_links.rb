class PersonContragentLinks < ActiveRecord::Migration[5.1]
  create_table :person_contragent_links do |t|
    t.references :contragent, foreign_key: true
    t.references :person, foreign_key: true
    t.integer :link_type
  end
end
