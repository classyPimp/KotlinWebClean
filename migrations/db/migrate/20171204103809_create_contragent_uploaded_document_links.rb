class CreateContragentUploadedDocumentLinks < ActiveRecord::Migration[5.1]
  def change
    create_table :cntragent_uploaded_document_links do |t|
      t.references :contragent, foreign_key: true
      t.references :uploaded_document, foreign_key: true
    end
  end
end
