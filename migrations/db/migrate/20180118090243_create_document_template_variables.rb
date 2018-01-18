class CreateDocumentTemplateVariables < ActiveRecord::Migration[5.1]
  def change
    create_table :document_template_variables do |t|
      t.string :name
      t.string :humanized_name
      t.string :description
      t.string :default_value
      t.boolean :is_preprogrammed
      t.timestamps
    end
  end
end
