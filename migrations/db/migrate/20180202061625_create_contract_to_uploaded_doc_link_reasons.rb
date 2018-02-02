class CreateContractToUploadedDocLinkReasons < ActiveRecord::Migration[5.1]
  def change
    create_table :contract_to_uploaded_document_link_reasons do |t|
      t.string :name
      t.string :description
      t.timestamps
    end
  end
end
