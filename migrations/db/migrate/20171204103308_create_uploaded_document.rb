class CreateUploadedDocument < ActiveRecord::Migration[5.1]
  def change
    create_table :uploaded_documents do |t|
      t.string :file_name
      t.string :file_mime
      t.string :file_size
      t.string :description
    end
  end
end
