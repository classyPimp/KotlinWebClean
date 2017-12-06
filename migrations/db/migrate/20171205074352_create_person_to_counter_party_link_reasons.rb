class CreatePersonToCounterPartyLinkReasons < ActiveRecord::Migration[5.1]
  def change
    create_table :person_to_counter_party_link_reasons do |t|
      t.string :reason_name
      t.timestamps
    end
  end
end
