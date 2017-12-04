class PowerDocuments < ActiveRecord::Migration[5.1]
  def change
    create_table :power_documents do |t|
      t.string :file_name
      t.string :file_mime
      t.string :file_size
      t.references :company_representative, foreign_key: true
      t.timestamps
    end
  end
end
