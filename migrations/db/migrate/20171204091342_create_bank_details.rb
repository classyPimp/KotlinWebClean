class CreateBankDetails < ActiveRecord::Migration[5.1]
  def change
    create_table :bank_details do |t|
      t.string :content
      t.references :contragent, foreign_key: true
    end
  end
end
