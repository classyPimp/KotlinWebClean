class CreateContragents < ActiveRecord::Migration[5.1]
  def change
    create_table :contragents do |t|
      t.string :name
      t.string :name_short
      t.string :bin
      t.references :incorporation_form, foreign_key: true
      t.timestamps
    end
  end
end
