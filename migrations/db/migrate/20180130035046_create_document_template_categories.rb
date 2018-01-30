class CreateDocumentTemplateCategories < ActiveRecord::Migration[5.1]
  def change
    create_table :document_template_categories do |t|
      t.string :name
      t.string :description
      t.timestamps
    end
  end
end
