class AddSomeFieldsToContracts < ActiveRecord::Migration[5.1]
  def change
    add_reference :contracts, :contract_status, foreign_key: true
    add_reference :contracts, :contract_number, foreign_key: true
    add_column :contracts, :formal_date, :datetime
    add_reference :contracts, :contract_category, foreign_key: true
  end
end
