class CreateContractToCounterPartyLinks < ActiveRecord::Migration[5.1]
  def change
    create_table :contract_to_counter_party_links do |t|
      t.references :counter_party, foreign_key: true
      t.references :contract, foreign_key: true
      t.string :role_according_to_contract
      t.timestamps
    end
  end
end
