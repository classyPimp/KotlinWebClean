class CreateUploadedFilePermissions < ActiveRecord::Migration[5.1]
  def change
    create_table :uploaded_file_permissions do |t|
      t.references :uploaded_file, foreign_key: true
      t.boolean :is_owner
      t.integer :permission_level
      t.references :permitted_to, polymorphic: true, index: {name: "apretoupdo_poly_peto"}
      t.string :access_hash_code
      t.references :primary_permitted_to, polymorphic: true, index: {name: "apretoupdo_poly_prpeto"}
      t.string :hardcoded_link_reason
      t.references :user_definable_link_reason, foreign_key: true, index: {name: "upfipe_usderere"}
      t.timestamps
    end
  end
end
