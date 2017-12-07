class PersonToCounterPartyLinkToUploadedDocumentLinkReasons < ActiveRecord::Migration[5.1]

  create_table :person_to_counter_party_link_to_uploaded_doc_link_reasons do |t|
    t.string :reason_name
    t.timestamps
  end

end
