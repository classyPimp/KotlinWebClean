class CreateGenericModelToUploadedFileRelations < ActiveRecord::Migration[5.1]
  def change
    create_table :generic_model_to_uploaded_file_relations do |t|
      t.references :related_model, polymorphic: true, index: {name: "gemotoupfire_remoid"}
      t.references :primary_related_model, polymorphic: true, index: {name: "gemotoupfire_prremo"}
      t.integer :primary_hardcoded_relation_reason
      t.references :primary_user_definable_relation_reason, foreign_key: {to_table: :user_definable_relation_reasons}, index: {name: "gemotoupfire_pr_usdere"}
      t.boolean :uploaded_file_is_folder
      t.integer :hardcoded_relation_reason
      t.references :user_definable_relation_reason, foreign_key: true, index: {name: "gemotoupfire_usderere"}
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
