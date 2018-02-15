class AddDescriptionToContractToUploadedDocumentLinks < ActiveRecord::Migration[5.1]
  def change
    add_column :contract_to_uploaded_document_links, :description, :string
  end
end
