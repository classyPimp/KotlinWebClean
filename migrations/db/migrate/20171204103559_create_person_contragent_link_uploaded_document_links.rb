class CreatePersonContragentLinkUploadedDocumentLinks < ActiveRecord::Migration[5.1]
  def change
    create_table :person_contragent_link_uploaded_document_links do |t|
      t.references :person_contragent_link, foreign_key: true
      t.references :uploaded_document, foreign_key: true
    end
  end
end
