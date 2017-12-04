class CreateCompanyRepresentatives < ActiveRecord::Migration[5.1]
  def change
    create_table :company_representatives do |t|
      t.string :name
      t.string :bin
      t.string :identifying_document
      t.references :company, foreign_key: true
      t.timestamps
    end
  end
end
