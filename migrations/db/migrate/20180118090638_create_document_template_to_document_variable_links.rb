class CreateDocumentTemplateToDocumentVariableLinks < ActiveRecord::Migration[5.1]
  def change
    create_table :document_template_to_document_variable_links do |t|
      t.references :document_template, foreign_key: true, index: {name: "dttdvl_to_dtempl_fk"}
      t.references :document_template_variable, foreign_key: true, index: {name: "dttdvl_to_dtvar_fk"}
      t.string :unique_identifier
      t.string :default_value
      t.timestamps
    end
  end
end
