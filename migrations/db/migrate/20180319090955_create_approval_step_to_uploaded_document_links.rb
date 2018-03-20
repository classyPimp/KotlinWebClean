class CreateApprovalStepToUploadedDocumentLinks < ActiveRecord::Migration[5.1]
  def change
    create_table :approval_step_to_uploaded_document_links do |t|
      t.references :approval_step, foreign_key: true, index: {name: "apst_to_updoli"}
      t.references :uploaded_document, foreign_key: true, index: {name: "apsttoupdo_updo"}
      t.string :description
      t.timestamps
    end
  end
end
