class CreatePersonToCounterPartyLink < ActiveRecord::Migration[5.1]
  def change
    create_table :person_to_counter_party_links do |t|
      t.references :person, foreign_key: true
      t.references :counter_party, foreign_key: true
      t.integer :link_reason
      t.string :specific_details
      t.timestamps
    end
  end
end
