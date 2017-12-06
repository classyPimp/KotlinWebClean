class CreateCounterPartyToContactLinks < ActiveRecord::Migration[5.1]
  def change
    create_table :counter_party_to_contact_links do |t|
      t.references :counter_party, foreign_key: true
      t.references :contact, foreign_key: true
      t.timestamps
    end
  end
end
