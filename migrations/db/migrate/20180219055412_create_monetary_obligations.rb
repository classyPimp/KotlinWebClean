class CreateMonetaryObligations < ActiveRecord::Migration[5.1]
  def change
    create_table :monetary_obligations do |t|
      t.bigint :total_amount
      t.boolean :is_credit
      t.references :contract, foreign_key: true
      t.timestamps
    end
  end
end
