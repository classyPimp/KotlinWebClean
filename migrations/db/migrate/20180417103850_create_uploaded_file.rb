class CreateUploadedFile < ActiveRecord::Migration[5.1]
  def change
    create_table :uploaded_files do |t|
      t.string :file_name
      t.bigint :file_size
      t.references :folder, index: true, foreign_key: { to_table: :uploaded_files }
      t.references :shortcut_to, foreign_key: true, foreign_key: { to_table: :uploaded_files }
      t.boolean :is_shortcut_to_folder
      t.timestamp :soft_deleted_since
      t.integer :hardcoded_category
      t.string :file_mime
      t.timestamps
    end
  end
end
