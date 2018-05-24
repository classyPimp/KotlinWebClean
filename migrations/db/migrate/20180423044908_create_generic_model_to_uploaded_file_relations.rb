class CreateGenericModelToUploadedFileRelations < ActiveRecord::Migration[5.1]
  def change
    create_table :generic_to_uploaded_file_links do |t|
      t.references :linked_model, polymorphic: true, index: {name: "gemotoupfire_remoid"}
      t.references :primary_linked_model, polymorphic: true, index: {name: "gemotoupfire_prremo"}
      t.integer :primary_hardcoded_link_reason
      t.references :primary_user_definable_link_reason, foreign_key: {to_table: :user_definable_link_reasons}, index: {name: "gemotoupfire_pr_usdere"}
      t.boolean :uploaded_file_is_folder
      t.integer :secondary_hardcoded_link_reason
      t.references :secondary_user_definable_link_reason, foreign_key: {to_table: :user_definable_link_reasons}, index: {name: "gemotoupfire_usderere"}
      t.references :uploaded_file, foreign_key: true, index: {name: "gemotoupfire_upfi"}
      t.timestamp :file_is_soft_deleted_since
      t.timestamp :file_is_permanently_deleted_since
      t.integer :access_level
      t.string :access_hash_code
      t.string :sub_identifier
      t.string :arbitrary_text_information
      t.timestamps
    end
  end
end
