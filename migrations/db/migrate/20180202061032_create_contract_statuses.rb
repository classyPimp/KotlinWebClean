class CreateContractStatuses < ActiveRecord::Migration[5.1]
  def change
    create_table :contract_statuses do |t|
      t.boolean :is_committed
      t.boolean :is_supplement
      t.references :parent_contract, index: true, foreign_key: {to_table: :contracts}
      t.references :root_contract, index: true, foreign_key: {to_table: :contracts}
      t.boolean :is_supplemented
      t.boolean :is_project
      t.boolean :is_cancelled
      t.datetime :valid_since
      t.datetime :valid_to
      t.boolean :is_completed
      t.timestamps
    end
  end
end
