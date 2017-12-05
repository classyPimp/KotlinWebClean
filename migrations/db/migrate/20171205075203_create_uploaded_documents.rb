class CreateUploadedDocuments < ActiveRecord::Migration[5.1]
  def change
    create_table :uploaded_documents do |t|
      t.string :description
      t.string :file_name
      t.string :file_mime
      t.integer :file_size
      t.boolean :is_folder
      t.references :uploaded_document, foreign_key: true
      t.string :folder_name
      t.timestamps
    end
  end
end
