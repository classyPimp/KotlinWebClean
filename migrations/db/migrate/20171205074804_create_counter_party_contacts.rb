class CreateCounterPartyContacts < ActiveRecord::Migration[5.1]
  def change
    create_table :counter_party_contacts do |t|
      t.references :counter_party, foreign_key: true
      t.integer :contact_type
      t.string :value
      t.timestamps
    end
  end
end
