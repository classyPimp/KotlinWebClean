class CreatePersonToCounterPartyLinkToUploadedDocumentLink < ActiveRecord::Migration[5.1]
  def change
    create_table :person_to_counter_party_link_to_uploaded_document_links do |t|
      t.references :person_to_counter_party_link, foreign_key: true, index: {name: "psn_to_co_prty_to_upl_links_idx"}
      t.references :uploaded_document, foreign_key: true, index: {name: "up_doc_links_idx"}
      t.integer :type
      t.timestamps
    end
  end
end
