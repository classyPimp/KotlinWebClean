class CreateContacts < ActiveRecord::Migration[5.1]
  def change
    create_table :contacts do |t|
        t.string :value
        t.references :contact_type, foreign_key: true
        t.timestamps
    end
  end
end
