class ContragentContacts < ActiveRecord::Migration[5.1]
  create_table :contragent_contacts do |t|
        t.string :contact_type
        t.string :value
        t.references :contragent, foreign_key: true
        t.timestamps
  end
end
