class CreateMonetaryObligationPart < ActiveRecord::Migration[5.1]
  def change
    create_table :monetary_obligation_parts do |t|
      t.bigint :amount
      t.datetime :due_date
      t.references :monetary_obligation, foreign_key: true
      t.bigint :fulfilled_amount
      t.timestamps
    end
  end
end
