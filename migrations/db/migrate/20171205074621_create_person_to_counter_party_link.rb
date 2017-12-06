class CreatePersonToCounterPartyLink < ActiveRecord::Migration[5.1]
  def change
    create_table :person_to_counter_party_links do |t|
      t.references :person, foreign_key: true
      t.references :counter_party, foreign_key: true
      t.references :person_to_counter_party_link_reason, foreign_key: true, index: {name: "cptcpl_ptcplr_fk"}
      t.string :specific_details
      t.timestamps
    end
  end
end
