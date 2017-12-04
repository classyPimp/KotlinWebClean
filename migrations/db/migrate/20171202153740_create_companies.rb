class CreateCompanies < ActiveRecord::Migration[5.1]
  def change
    create_table :companies do |t|
      t.references :incorporation_form, foreign_key: true
      t.string :name
      t.string :name_short
      t.string :adress_legal
      t.string :adress_postal
      t.string :bin
      t.timestamps
    end
  end
end
