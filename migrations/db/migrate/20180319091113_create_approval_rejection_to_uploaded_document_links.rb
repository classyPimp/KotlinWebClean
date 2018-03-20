class CreateApprovalRejectionToUploadedDocumentLinks < ActiveRecord::Migration[5.1]
  def change
    create_table :approval_rejection_to_uploaded_document_links do |t|
      t.references :approval_rejection, foreign_key: true, index: {name: "aprej_updo"}
      t.references :uploaded_document, foreign_key: true, index: {name: "apretoupdo_updo"}
      t.string :description
      t.timestamps
    end
  end
end
