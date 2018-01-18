class CreateContractToUploadedDocumentLinks < ActiveRecord::Migration[5.1]
  def change
    create_table :contract_to_uploaded_document_links do |t|
      t.references :contract, foreign_key: true
      t.references :uploaded_document, foreign_key: true, index: {name: "ctupdl_on_ud"}
      t.timestamps
    end
  end
end
