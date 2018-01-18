class CreateDocumentTemplates < ActiveRecord::Migration[5.1]
  def change
    create_table :document_templates do |t|
      t.string :name
      t.string :description
      t.references :uploaded_document, foreign_key: true
      t.timestamps
    end
  end
end
