class CreatePersonContacts < ActiveRecord::Migration[5.1]
  def change
    create_table :person_contacts do |t|
      t.string :contact_type
      t.string :value
      t.references :person, foreign_key: true
      t.timestamps
    end
  end
end
