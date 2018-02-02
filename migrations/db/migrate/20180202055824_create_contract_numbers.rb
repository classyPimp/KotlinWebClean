class CreateContractNumbers < ActiveRecord::Migration[5.1]
  def change
    create_table :contract_numbers do |t|
      t.string :internal_number
      t.string :number_assigned_by_counter_party
      t.string :assigned_number
      t.timestamps
    end
  end
end
