class CreatePersonToContactLinks < ActiveRecord::Migration[5.1]
  def change
    create_table :person_to_contact_links do |t|
      t.references :person, foreign_key: true
      t.references :contact, foreign_key: true
      t.timestamps
    end
  end
end
