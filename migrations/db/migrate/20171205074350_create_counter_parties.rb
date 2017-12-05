class CreateCounterParties < ActiveRecord::Migration[5.1]
  def change
    create_table :counter_parties do |t|
      t.string :name
      t.string :name_short
      t.references :incorporation_form, foreign_key: true
      t.timestamps
    end
  end
end
