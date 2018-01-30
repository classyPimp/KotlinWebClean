class AddCategoryReferenceToDocumentTemplate < ActiveRecord::Migration[5.1]
  def change
    add_reference :document_templates, :document_template_category, foreign_key: true
  end
end
