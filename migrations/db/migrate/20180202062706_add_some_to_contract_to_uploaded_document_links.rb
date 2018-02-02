class AddSomeToContractToUploadedDocumentLinks < ActiveRecord::Migration[5.1]
  def change
    add_reference :contract_to_uploaded_document_links, :contract_to_uploaded_document_link_reason, foreign_key: true, index: {name: "contudl_contudlr"}
  end
end
